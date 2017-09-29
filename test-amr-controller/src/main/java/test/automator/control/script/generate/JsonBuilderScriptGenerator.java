package test.automator.control.script.generate;

import java.io.PrintWriter;

import javax.swing.JOptionPane;

import test.automator.common.Commons;

import test.automator.constants.ScriptConstants;
import test.automator.control.excel.read.ExcelDataReaderScriptGen;
import test.automator.control.excel.read.ExcelDataReaderTestSpecGen;
import test.automator.control.file.generate.CreateScript;

public class JsonBuilderScriptGenerator {

    public static void createScript(final String JSONBuilderGroovyScriptName) throws Exception {
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
        // TODO : replace this by single call in ExcelDataReaderTSG and update variable
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
        PrintWriter outJSON = fileCreator.createTestScript(ScriptConstants.NEW_SCRIPT_PATH, fileCreator.getScriptName(0, JSONBuilderGroovyScriptName));

        if (Count1 != 0 && Confirm == "YES")
        {
            String variableNameToLower = null;
            String variableNameToUpper = null;
            /*
             * GroovyScript1
             */
            outJSON.println("/** AUTO GENERATED SCRIPT **/");
            outJSON.println("/**");
            outJSON.println("* Project Name\t: " + ProjectName);
            outJSON.println("* Usecase Name\t: " + UseCaseName);
            outJSON.println("* Module Name\t: " + ModuleName);
            outJSON.println("* Description\t: ");
            outJSON.println("* Author\t\t: " + AuthorName);
            outJSON.println("* Modified By\t: ");
            outJSON.println("*/");
            outJSON.println();
            outJSON.println("def builder = new groovy.json.JsonBuilder()");
            outJSON.println();
            // define input values from dataSource
            if (requestNodeNames[0] != null)
            {
                for (int i = 0; i < Count1; i++)
                {
                    if (requestNodeNames[i] == null || requestNodeNames[i] == "") {
                        break;
                    }
                    variableNameToUpper = Commons.getVariableNameToUpper(requestNodeNames[i]);
                    outJSON.println("def " + variableNameToUpper + " = context.expand( '${" +
                            dataSourceName + "#" + variableNameToUpper + "}')");
                }
                outJSON.println();
                for (int i = 0; i < Count1; i++)
                {
                    if (requestNodeNames[i] == null || requestNodeNames[i] == "") {
                        break;
                    }
                    variableNameToLower = Commons.getVariableNameToLower(requestNodeNames[i]);
                    variableNameToUpper = Commons.getVariableNameToUpper(requestNodeNames[i]);
                    if (variableNameToLower.toLowerCase().contains("date")) {
                        outJSON.println("if( " + variableNameToUpper + ".size() < 7){");
                        outJSON.println("\t" + variableNameToUpper + " = CommonUtils.getDate(" + variableNameToUpper + ")");
                        outJSON.println("}");
                    }
                }
                outJSON.println();
                // Build the Request
                outJSON.println("//Build the Request");
                outJSON.println("builder{");

                for (int i = 0; i < Count1; i++)
                {
                    if (requestNodeNames[i] == null || requestNodeNames[i] == "") {
                        break;
                    }
                    variableNameToLower = Commons.getVariableNameToLower(requestNodeNames[i]);
                    variableNameToUpper = Commons.getVariableNameToUpper(requestNodeNames[i]);
                    outJSON.println("\tif( " + variableNameToUpper + " != '')");
                    outJSON.println("\t\t" + variableNameToLower + " " + variableNameToUpper);
                }
                outJSON.println("}");
                outJSON.println();
                outJSON.println("request.setRequestContent(builder.toPrettyString())");
            }
        }
        outJSON.close();
        // JOptionPane.showMessageDialog(null, "JSON builder Groovy Script Generated Successfully");
    }
}
