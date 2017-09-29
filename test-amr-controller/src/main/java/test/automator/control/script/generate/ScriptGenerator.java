package test.automator.control.script.generate;

import java.io.PrintWriter;

import javax.swing.JOptionPane;

import test.automator.common.Commons;

import test.automator.constants.Constants;
import test.automator.constants.ScriptConstants;
import test.automator.control.excel.read.ExcelDataReaderScriptGen;
import test.automator.control.excel.read.ExcelDataReaderTestSpecGen;
import test.automator.control.file.generate.CreateScript;

public class ScriptGenerator {

    public static void createScript(final String reqHandlerName, final String groovyScriptName) throws Exception {
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
            } else {
                Confirm = "NO";
                System.out.println("No");
            }
        }
        PrintWriter out1 = fileCreator.createTestScript(ScriptConstants.NEW_SCRIPT_PATH, fileCreator.getScriptName(0, reqHandlerName));

        if (Count1 != 0 && Confirm == "YES")
        {
            /*
             * GroovyScript1
             */
            out1.println("/** AUTO GENERATED SCRIPT **/");
            out1.println("/**");
            out1.println("* Project Name\t: " + ProjectName);
            out1.println("* Usecase Name\t: " + UseCaseName);
            out1.println("* Module Name\t: " + ModuleName);
            out1.println("* Description\t: ");
            out1.println("* Author\t\t: " + AuthorName);
            out1.println("* Modified By\t: ");
            out1.println("*/");
            out1.println("def groovyUtils = new com.eviware.soapui.support.GroovyUtils( context )");
            out1.println("def holder = groovyUtils.getXmlHolder(\"" +
                    soapRequestName + "#Request\")");
            if (templateStatus.equalsIgnoreCase("Y"))
            {
                out1.println("//clone test request with template request");
                out1.println("def requestTemplate = groovyUtils.getXmlHolder(\"Template#Request\")");
                out1.println("holder.getXmlObject().set(requestTemplate.getXmlObject())");
                out1.println();
            }

            // to define data sink property code (called only once)
            boolean defineRunnerComplete = true;

            if (requestNodeNames[0] != null)
            {
                for (int i = 0; i < Count1; i++)
                {
                    if (requestNodeNames[i] == null || requestNodeNames[i] == "") {
                        break;
                    }
                    // *************************--customized--*************************//
                    String variableNameToLower = Commons.getVariableNameToLower(requestNodeNames[i]);
                    String variableNameToUpper = Commons.getVariableNameToUpper(requestNodeNames[i]);
                    if (variableNameToLower.toLowerCase().contains("date")) {
                        while (defineRunnerComplete) {
                            out1.println("def runnerComplete = testRunner.testCase.testSteps.DataSink");
                            out1.println();

                            defineRunnerComplete = false;
                        }
                        out1.println("//define " + variableNameToLower + " from DataSource and set value to request");
                        out1.println("def " + variableNameToLower + " = CommonUtils.getDate(context.expand( '${" +
                                dataSourceName + "#" + variableNameToUpper + "}'))");
                        out1.println("runnerComplete.setPropertyValue(\"" + variableNameToUpper + "\"," + variableNameToLower + ")");
                        out1.println("holder.setNodeValue(\"//*:" + requestNodeNames[i] + "\"," + variableNameToLower + ")");
                        out1.println();
                    }
                }

                // out1.println("//remove node if null else set node value");
                // for (int i = 0; i < Count1; i++)
                // {
                // if (requestNodeNames[i] == null || requestNodeNames[i] == "") {
                // break;
                // }
                // if (getMandatoryVariable(requestNodeNames[i]))
                // {
                // String variable = scriptor.getVariableNameToLower(requestNodeNames[i]);
                // String nodeName = scriptor.getEditedName(requestNodeNames[i]);
                // out1.println("if(" + variable + ".equals(null) || " + variable +
                // ".equals(\"\") || " + variable + ".equals(''){");
                // out1.println("\tholder.removeDomNodes('//*:" + nodeName +
                // "')");
                // out1.println("\t}");
                // out1.println("else{");
                // out1.println("\tholder.setNodeValue(\"//*:" + nodeName +
                // "\"," + variable + ")");
                // out1.println("\t}");
                // out1.println();
                // }
                // else
                // {
                // String variable = scriptor.getVariableNameToLower(requestNodeNames[i]);
                // String nodeName = scriptor.getEditedName(requestNodeNames[i]);
                // out1.println("\tholder.setNodeValue(\"//*:" + nodeName +
                // "\"," + variable + ")");
                // }
                // }
                out1.println("holder.updateProperty()");
            }
        }
        out1.close();

        PrintWriter out2 = fileCreator.createTestScript(ScriptConstants.NEW_SCRIPT_PATH, fileCreator.getScriptName(1, groovyScriptName));
        if (Count1 != 0 && Confirm == "YES")
        {
            /*
             * GroovyScript2
             */
            out2.println("/** AUTO GENERATED SCRIPT **/");
            out2.println("/**");
            out2.println("* Project Name\t: " + ProjectName);
            out2.println("* Usecase Name\t: " + UseCaseName);
            out2.println("* Module Name\t: " + ModuleName);
            out2.println("* Description\t: ");
            out2.println("* Author\t\t: " + AuthorName);
            out2.println("* Modified By\t: ");
            out2.println("*/");
            out2.println("def groovyUtils = new com.eviware.soapui.support.GroovyUtils( context )");
            out2.println("def holder = groovyUtils.getXmlHolder(\""
                    + soapRequestName + "#Response\")");
            out2.println("def runnerComplete = testRunner.testCase.testSteps."
                    + dataSinkName);
            out2.println();
            // *************************--customized--*************************//
            /*
             * writing request xml to datasink code
             */
            out2.println("//write xml request message into datasink");
            out2.println("def requestXML = context.expand('${" + soapRequestName + "#Request}')");
            out2.println("runnerComplete.setPropertyValue(\"RequestXML\",requestXML)");
            out2.println();
            /*
             * responseTime
             */
            out2.println("//set response Time");
            out2.println("runnerComplete.setPropertyValue(\"ResponseTime\",testRunner.getTestCase().getTestSteps().get(\"" + soapRequestName
                    + "\").getTestRequest().getResponse().getTimeTaken()+\" ms\")");
            out2.println();
            // *************************--customized--*************************//
            /*
             * ExpectedValue and ActualValue
             */
            String[] responseNodeNames = dataReader.getResponseNodeNames();
            String[] ExpectedValue = Commons.getStringAppendvalue(responseNodeNames, "Expected");
            String[] ActualValue = Commons.getStringAppendvalue(responseNodeNames, "Actual");

            int Count = ExpectedValue.length;

            System.out.println(responseNodeNames[0]);
            out2.println("\t//define expected values from DataSource");
            /*
             * Define expected values
             */
            if (ExpectedValue[0] != null) {
                for (int i = 0; i < Count; i++) {
                    if (ExpectedValue[i] == null || ExpectedValue[i] == "") {
                        break;
                    } else {
                        String variable = Commons.getVariableNameToLower(ExpectedValue[i]);
                        if (Constants.USE_COMMON_UTIL_LIB.toLowerCase().trim().equals("y")) {
                            out2.println("\tdef " + variable + " = " + Constants.COMMON_LIBRARY_NAME + ".getStringValue(context.expand( '${"
                                    + dataSourceName + "#" + ExpectedValue[i] + "}'))");
                        } else {
                            out2.println("\tdef " + variable + " = context.expand( '${"
                                    + dataSourceName + "#" + ExpectedValue[i] + "}')");
                        }
                    }
                }
                out2.println();

                out2.println("\t\t//write expected values into DataSink");
                /*
                 * Write Expected values into DataSink
                 */
                for (int i = 0; i < Count; i++) {
                    if (ExpectedValue[i] == null || ExpectedValue[i] == "") {
                        break;
                    } else {
                        String variable = Commons.getVariableNameToLower(ExpectedValue[i]);
                        out2.println("\t\trunnerComplete.setPropertyValue(\""
                                + ExpectedValue[i] + "\"," + variable + ")");
                    }
                }
                out2.println();
            }
            /*
             * Define actual values from response
             */
            out2.println("\t//define actual values from response");
            if (ActualValue[0] != null) {
                for (int i = 0; i < Count; i++) {
                    if (ActualValue[i] == null || ActualValue[i] == "") {
                        break;
                    } else {
                        String variable = Commons.getVariableNameToLower(ActualValue[i]);
                        if (Constants.USE_COMMON_UTIL_LIB.toLowerCase().trim().equals("y")) {
                            // *************************--customized--*************************//
                            if (variable.toLowerCase().contains("date")) {
                                out2.println("\tdef "
                                        + variable
                                        + " = " + Constants.COMMON_LIBRARY_NAME + ".trimTimeStamp(holder.getNodeValues(\"//*:"
                                        + responseNodeNames[i] + "\"))");
                            }
                            else {
                                out2.println("\tdef "
                                        + variable
                                        + " = " + Constants.COMMON_LIBRARY_NAME + ".getStringValue(holder.getNodeValues(\"//*:"
                                        + responseNodeNames[i] + "\"))");
                            }
                            // *************************--customized--*************************//
                        } else {
                            out2.println("\tdef "
                                    + variable
                                    + " = holder.getNodeValues(\"//*:"
                                    + responseNodeNames[i] + "\")");
                        }
                    }

                }
                out2.println();
                out2.println("\t\t//write actual values to data sink");
                // write actual values to data sink
                for (int i = 0; i < Count; i++) {
                    if (ActualValue[i] == null || ActualValue[i] == "") {
                        break;
                    } else {
                        String variable = Commons.getVariableNameToLower(ActualValue[i]);
                        out2.println("\t\trunnerComplete.setPropertyValue(\""
                                + ActualValue[i] + "\"," + variable + ")");
                    }
                }
                out2.println();
                if (Constants.FAULT_STRING_SCRIPT.toLowerCase().trim().equals("y")) {
                    if (Constants.KEEP_UNDERSCORE.toLowerCase().trim().equals("y")) {
                        out2.println(ScriptConstants.FAULT_CODE_UNDERSCORE);
                    }
                    else {
                        out2.println(ScriptConstants.FAULT_CODE);
                    }
                }
                out2.println("//compare actual and expected values");
                /*
                 * Compare actual and expected values
                 */
                out2.print("if( ");
                for (int i = 0; i < Count; i++) {
                    if (ExpectedValue[i] == null || ExpectedValue[i] == "") {
                        break;
                    } else {
                        String ExpVariable = Commons.getVariableNameToLower(ExpectedValue[i]);
                        String ActVariable = Commons.getVariableNameToLower(ActualValue[i]);
                        out2.print(ExpVariable + ".equals(" + ActVariable + ") ");
                        if (i + 1 == Count || ExpectedValue[i + 1] == null || ExpectedValue[i + 1] == "") {
                            out2.println("){");
                        } else {
                            if (i % 2 == 0) {
                                out2.print("&& ");
                            } else {
                                out2.println("&& ");
                            }
                        }
                    }
                }
                out2.println("\tTestStatus = \"Pass\"");
                // *************************--customized--*************************//
                out2.println("\trunnerComplete.setPropertyValue(\"Comments\",\"\")");
                out2.println("\trunnerComplete.setPropertyValue(\"Test_Status\",TestStatus)");
                // *************************--customized--*************************//
                out2.println("} else {");
                out2.println("\tTestStatus = \"Fail\"");
                // *************************--customized--*************************//
                out2.println("\ttestFailComments = CommonUtils.compareExpectedActual(context ,log, testRunner,\""+dataSinkName+"\")[1]");
                out2.println("\trunnerComplete.setPropertyValue(\"Comments\",testFailComments)");
                out2.println("\trunnerComplete.setPropertyValue(\"Test_Status\",TestStatus)");
                out2.println("//\ttestRunner.runTestStepByName(\""+dataSinkName+"\")"); 
                out2.println("//\tthrow new Exception(\"Test Fail: \" + testFailComments)");
                // *************************--customized--*************************//
                out2.println("}");
                out2.println();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Oops\nSomething gone Wrong!\nCheck Excel sheet data");
        }
        out2.close();
        JOptionPane.showMessageDialog(null, "Script Generated Successfully");
    }
}
