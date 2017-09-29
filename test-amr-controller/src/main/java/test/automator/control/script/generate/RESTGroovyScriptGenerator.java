package test.automator.control.script.generate;

import java.io.PrintWriter;

import javax.swing.JOptionPane;

import test.automator.common.Commons;

import test.automator.constants.Constants;
import test.automator.constants.ScriptConstants;
import test.automator.control.excel.read.ExcelDataReaderScriptGen;
import test.automator.control.excel.read.ExcelDataReaderTestSpecGen;
import test.automator.control.file.generate.CreateScript;

public class RESTGroovyScriptGenerator {

    public static void createScript(final String restGroovyScriptName) throws Exception {
        /*
         * Create directory
         */
        CreateScript fileCreator = new CreateScript();

        /*
         * initialization
         */
        ExcelDataReaderScriptGen dataReader = new ExcelDataReaderScriptGen();

        /*
         * Get test step names
         */
        String templateStatus = dataReader.getTemplateStatus();
        String dataSourceName = dataReader.getDataSourceName();
        String soapRequestName = dataReader.getSOAPRequestName();
        String dataSinkName = dataReader.getDataSinkName();

        System.out.println("TemplateStatus:" + templateStatus);
        System.out.println("DataSourceName:" + dataSourceName);
        System.out.println("SOAPRequestName:" + soapRequestName);
        System.out.println("DataSinkName:" + dataSinkName);
        // TODO : replace this by single call to ExcelDataReaderTestSpecGen and update variable
        ExcelDataReaderTestSpecGen excelDataReader = new ExcelDataReaderTestSpecGen();
        String ProjectName = excelDataReader.getProjectName();
        String ModuleName = excelDataReader.getModuleName();
        String UseCaseName = excelDataReader.getUseCaseName();
        String AuthorName = excelDataReader.getAuthorName();

        /*
         * request nodes
         */
        String[] requestNodeNames = dataReader.getRequestNodeNames();

        int Count1 = requestNodeNames.length;

        System.out.println("Arraylength:" + Count1);
        /*
         * Confirm if more than 100 value
         */
        int dialogResult = 0;
        String Confirm = "YES";
        if (Count1 > 100) {
            int dialogButton = JOptionPane.OK_CANCEL_OPTION;
            dialogResult = JOptionPane.showConfirmDialog(null, "More Than 100 inputs!!!\n Press OK to continue", "Title on Box", dialogButton);

            if (dialogResult == 0) {
                Confirm = "YES";
                System.out.println("YES");
            }
            else {
                Confirm = "NO";
                System.out.println("No");
            }
        }
        PrintWriter outRGS = fileCreator.createTestScript(ScriptConstants.NEW_SCRIPT_PATH, fileCreator.getScriptName(1, restGroovyScriptName));

        if (Count1 != 0 && Confirm == "YES")
        {

            /*
             * GroovyScript2
             */
            outRGS.println("/** AUTO GENERATED SCRIPT **/");
            outRGS.println("/**");
            outRGS.println("* Project Name\t: " + ProjectName);
            outRGS.println("* Usecase Name\t: " + UseCaseName);
            outRGS.println("* Module Name\t: " + ModuleName);
            outRGS.println("* Description\t: ");
            outRGS.println("* Author\t\t: " + AuthorName);
            outRGS.println("* Modified By\t: ");
            outRGS.println("*/");
            outRGS.println("def groovyUtils = new com.eviware.soapui.support.GroovyUtils( context )");
            outRGS.println("def holder = groovyUtils.getXmlHolder(\""
                    + soapRequestName + "#ResponseAsXML\")");
            outRGS.println("def runnerComplete = testRunner.testCase.testSteps."
                    + dataSinkName);
            outRGS.println();
            /*
             * clear the property values in the data sink
             */
            outRGS.println("// clear the property values in the data sink");
            outRGS.println("CommonUtils.emptyDataSinkProperty(testRunner.getTestCase(), \"" + dataSinkName + "\" )");
            /*
             * update expected property values in dataSink with expected property values in dataSource.
             */
            outRGS.println("// update expected property values in dataSink with expected property values in dataSource.");
            outRGS.println("CommonUtils.updateDataSinkProperty(testRunner.getTestCase(), \"" + dataSinkName + "\" , \"" + dataSourceName + "\" )");

            /*
             * ExpectedValue and ActualValue
             */
            String[] responseNodeNames = dataReader.getResponseNodeNames();
            String[] ExpectedValue = Commons.getStringAppendvalue(responseNodeNames, "Expected");

            int Count = ExpectedValue.length;

            System.out.println(responseNodeNames[0]);

            /*
             * Define expected values for date variables
             */
            if (ExpectedValue[0] != null) {
                for (int i = 0; i < Count; i++) {
                    if (ExpectedValue[i] == null || ExpectedValue[i] == "") {
                        break;
                    } else {
                        String variable = Commons.getVariableNameToLower(ExpectedValue[i]);
                        if (variable.toLowerCase().contains("date")) {
                            outRGS.println("\tdef " + variable + " = " + Constants.COMMON_LIBRARY_NAME + ".getDateList(context.expand( '${"
                                    + dataSourceName + "#" + ExpectedValue[i] + "}').split(\",\"))");

                        }
                    }
                }
                for (int i = 0; i < Count; i++) {
                    if (ExpectedValue[i] == null || ExpectedValue[i] == "") {
                        break;
                    } else {
                        String variable = Commons.getVariableNameToLower(ExpectedValue[i]);
                        if (variable.toLowerCase().contains("date")) {
                            outRGS.println("\t\trunnerComplete.setPropertyValue(\""
                                    + ExpectedValue[i] + "\"," + variable + ")");
                        }
                    }
                }
                outRGS.println();

            }
            // update the response details into dataSink
            outRGS.println("//update the response details into dataSink");
            outRGS.println("CommonUtils.updateResponseToDataSink(testRunner.getTestCase(), \"" + dataSinkName + "\" , holder )");

            // Compare the expected and actual in data sink and returns the test status
            outRGS.println("//Compare the expected and actual in data sink and returns the test status");
            outRGS.println("testStatus = CommonUtils.compareExpectedActual(log, testRunner.getTestCase(),  \"" + dataSinkName + "\")");
            outRGS.println("runnerComplete.setPropertyValue(\"Test_Status\",TestStatus)");

            /*
             * responseTime
             */
            outRGS.println("//set response Time");
            outRGS.println("runnerComplete.setPropertyValue(\"ResponseTime\",testRunner.getTestCase().getTestSteps().get(\"" + soapRequestName
                    + "\").getTestRequest().getResponse().getTimeTaken()+\" ms\")");
            outRGS.println();
        }
        else {
            JOptionPane.showMessageDialog(null, "Oops\nSomething gone Wrong!\nCheck Excel sheet data");
        }
        outRGS.close();
        JOptionPane.showMessageDialog(null, "Rest Groovy Script Generated Successfully");
    }
}