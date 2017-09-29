package test.automator.control.testdata.generate;

import java.io.PrintWriter;

import javax.swing.JOptionPane;

import test.automator.common.Commons;

import test.automator.constants.Constants;
import test.automator.constants.TestDataConstants;
import test.automator.control.excel.read.ExcelDataReaderScriptGen;
import test.automator.control.excel.write.WriteExcelTestDataGen;
import test.automator.control.file.generate.CreateDataManager;
import test.automator.control.file.generate.CreateScript;
import test.automator.control.file.generate.CreateTestSpec;

public class TestDataGenerator {

    public static void createTestDataFiles(final String dataSourceFileName, final String dataSinkFileName, final String propertyFileName) throws Exception {
        TestDataCreator(dataSourceFileName, dataSinkFileName, propertyFileName);
    }

    private static void TestDataCreator(final String DataSourceFileName, final String DataSinkFileName, final String propertyFileName) throws Exception {

        CreateDataManager create = new CreateDataManager(DataSourceFileName, DataSinkFileName);
        create.createTestData();
        create.createTestDataTemplate();

        String outFileTestData = CreateDataManager.NEW_TESTDATA_NAME_WITH_PATH;
        String outFileTestDataTemplate = CreateDataManager.NEW_TESTDATA_TEMPLATE_NAME_WITH_PATH;

        ExcelDataReaderScriptGen dataReader = new ExcelDataReaderScriptGen();
        WriteExcelTestDataGen write1 = new WriteExcelTestDataGen(Constants.PATH_TO_TESTDATA_TEMPLATE_EXCEL + "/" + Constants.TEST_DATA_TEMPLATE_NAME,
                TestDataConstants.TEST_DATA_GEN_WORKSHEET_NO);
        WriteExcelTestDataGen write2 = new WriteExcelTestDataGen(Constants.PATH_TO_TESTDATA_TEMPLATE_EXCEL + "/" + Constants.TEST_DATA_TEMPLATE_NAME,
                TestDataConstants.TEST_DATA_GEN_WORKSHEET_NO);

        String[] requestNodeNames = dataReader.getRequestNodeNames();
        String[] responseNodeNames = dataReader.getResponseNodeNames();
        String[] ExpectedValues = Commons.getStringAppendvalue(responseNodeNames, "Expected");
        String[] ActualValues = Commons.getStringAppendvalue(responseNodeNames, "Actual");
        CreateScript fileCreator = new CreateScript();
        PrintWriter out = fileCreator.createTestScript(TestDataConstants.NEW_PROPERTY_FILE_PATH, fileCreator.getScriptName(2, propertyFileName));
        out.println("TestCaseID=");
        out.println("TestCaseType=");
        out.println("TestingObjective=");

        // *************************--customized--*************************//
        write2.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, "RequestXML", outFileTestDataTemplate);
        // *************************--customized--*************************//

        /*
         * Add request node columns
         */

        for (String requestNodeName : requestNodeNames) {
            if (requestNodeName == null || requestNodeName == "") {
            }
            else {
                String variableNameToUpper = Commons.getVariableNameToUpper(requestNodeName);
                write1.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, variableNameToUpper, outFileTestData);

                out.println(variableNameToUpper + "=");
                write2.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, variableNameToUpper, outFileTestDataTemplate);
            }
        }
        /*
         * Add Expected columns
         */
        try {
            for (String ExpectedValue : ExpectedValues) {
                if (ExpectedValue == null || ExpectedValue == "") {
                }
                else {
                    write1.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, ExpectedValue, outFileTestData);
                    out.println(ExpectedValue + "=");
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEption in testDataGen");
        }
        out.close();
        /*
         * Add Expected and Actual columns
         */
        for (int i = 0; i < ExpectedValues.length; i++) {
            if (ExpectedValues[i] == null || ExpectedValues[i] == "") {
            }
            else {
                write2.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, ExpectedValues[i], outFileTestDataTemplate);
                write2.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, ActualValues[i], outFileTestDataTemplate);
            }
        }
        // *************************--customized--*************************//
        write2.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, "ResponseTime", outFileTestDataTemplate);
        // *************************--customized--*************************//
        write2.addColumn(TestDataConstants.TEST_DATA_HEADER_ROW_NO, "Test_Status", outFileTestDataTemplate);
        if (Constants.GENERATE_TEST_CASE.toLowerCase().trim().equals("y")) {
            write1.copyRow(CreateTestSpec.NEW_TESTSPEC_NAME_WITH_PATH, outFileTestData);
        }
        WriteExcelTestDataGen.updateSheetName(outFileTestDataTemplate, dataReader.getSOAPRequestName(), 0);
        JOptionPane.showMessageDialog(null, "Test Data Generated Successfully\n Location :  C:\\AutoGeneratedFile");
    }
}
