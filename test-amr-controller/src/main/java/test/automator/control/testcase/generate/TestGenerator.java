package test.automator.control.testcase.generate;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import test.automator.common.Commons;

import test.automator.constants.Constants;
import test.automator.constants.TestCaseConstants;
import test.automator.control.excel.read.ExcelDataReaderTestSpecGen;
import test.automator.control.excel.write.WriteExcel;
import test.automator.control.file.generate.CreateTestSpec;

public class TestGenerator {

    public static int testCaseNo = 0;

    public static void createTestSpec(final String TestSpecName) throws Exception {
        TestCaseGeneratorAndWriter(TestSpecName);
    }

    static String shortDescriptiontoLower = null;

    /**
     * @param args
     * @throws Exception
     */
    private static void TestCaseGeneratorAndWriter(final String TestSpecName) throws Exception {
        // Constants.getUserInput();
        testCaseNo = 1;
        if (Constants.GENERATE_TEST_CASE.toLowerCase().trim().equals("y")) {
            CreateTestSpec creator = new CreateTestSpec(TestSpecName);
            creator.createTestSpec();

            ExcelDataReaderTestSpecGen excelDataReader = new ExcelDataReaderTestSpecGen();

            String ProjectNo = excelDataReader.getProjectNo();
            String ProjectName = excelDataReader.getProjectName();
            String ModuleName = excelDataReader.getModuleName();
            String UseCaseName = excelDataReader.getUseCaseName();
            String Operation = excelDataReader.getOperation();
            // *************************--customized--*************************//
            String serviceName = Commons.toUpperFirstChar(Operation.toLowerCase().replace("service", ""));
            // *************************--customized--*************************//
            String SubModuleName = excelDataReader.getSubModuleName();
            String ShortDescription = excelDataReader.getShortDescription();
            shortDescriptiontoLower = Commons.toLowerFirstChar(ShortDescription);
            String AuthorName = excelDataReader.getAuthorName();
            WriteExcel write = new WriteExcel(CreateTestSpec.NEW_TESTSPEC_NAME_WITH_PATH,
                    TestCaseConstants.TEST_CASE_GEN_WORKSHEET_NO);
            /*
             * Write header information ,
             */
            write.writeRow(1, 1, "Project No: " + ProjectNo);
            write.writeRow(1, 4, "Project Name: " + ProjectName);
            write.writeRow(1, 5, "Module: " + ModuleName);
            write.writeRow(2, 1, "Use Case/Specification: " + UseCaseName);
            write.writeRow(2, 4, "Operation: " + Operation);
            write.writeRow(2, 5, "SubModuleName: " + SubModuleName);
            write.writeRow(3, 1, "Author: " + AuthorName);
            Object[] inputData = excelDataReader.getInputDataNames();
            // list input details into mandatory and non-mandatory list
            Commons.getMandatoryVariables(inputData);
            String validMandatoryListString = Commons.getStringValueAppendName(Commons.mandatoryList, "Valid");
            String validNonMandatoryListString = Commons.getStringValueAppendName(Commons.nonMandatoryList, "Valid");
            String nullNonMandatoryListString = Commons.getStringValueAppendName(Commons.nonMandatoryList, "Null");
            /*
             * Positive Scenarios
             */
            String validScenario = Constants.VALID_SCENARIO;
            String nullValidation = Constants.NULL_VALIDATION;
            String precondition = "1. " + serviceName + TestCaseConstants.PRE_CONDITION;
            String expResultPass = TestCaseConstants.EXP_RESULT_PASS_HEAD + SubModuleName + TestCaseConstants.EXP_RESULT_PASS_FOOT + SubModuleName + " details.\n";
            String expResultFail = TestCaseConstants.EXP_RESULT_FAIL_HEAD + SubModuleName + TestCaseConstants.EXP_RESULT_FAIL_FOOT;

            if (Commons.mandatoryList.size() != 0 && validScenario.toLowerCase().trim().equals("y")) {
                String Objective1 = ShortDescription + " with all mandatory and non mandatory field values.\n";
                String ExeSteps1 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + validMandatoryListString + validNonMandatoryListString;
                write.writeRow(testCaseNo, "Functionality", Objective1, precondition, ExeSteps1, expResultPass);
                testCaseNo = testCaseNo + 1;
                if (Commons.nonMandatoryList.size() != 0) {
                    String Objective2 = ShortDescription
                            + " with all mandatory and null non-mandatory field values.\n";
                    String ExeSteps2 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + validMandatoryListString + nullNonMandatoryListString;

                    write.writeRow(testCaseNo, "Functionality", Objective2, precondition, ExeSteps2, expResultPass);
                    testCaseNo = testCaseNo + 1;
                }
                if (nullValidation.toLowerCase().trim().equals("y")) {
                    for (int i = 0; i < Commons.mandatoryList.size(); i++) {
                        String Objective3 = "Failed to " + shortDescriptiontoLower
                                + " due to null " + Commons.mandatoryList.get(i);
                        String ExeSteps3 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + "Null " + Commons.mandatoryList.get(i) + " \n";
                        write.writeRow(testCaseNo, "Negative", Objective3, null, ExeSteps3, expResultFail);
                        testCaseNo = testCaseNo + 1;
                    }
                }
            }
            else if (Commons.nonMandatoryList.size() != 0) {
                String Objective1 = ShortDescription
                        + " with valid all field values.\n";
                String ExeSteps1 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + validNonMandatoryListString;
                write.writeRow(testCaseNo, "Functionality", Objective1, precondition, ExeSteps1, expResultPass);
                testCaseNo = testCaseNo + 1;

                String Objective2 = ShortDescription
                        + " with all optional field values as null.\n";
                String ExeSteps2 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + nullNonMandatoryListString;
                write.writeRow(testCaseNo, "Functionality", Objective2, precondition, ExeSteps2, expResultPass);
                testCaseNo = testCaseNo + 1;
            }
            else {

            }
            int count = inputData.length;
            /*
             * DataType Validation
             */
            String dataTypeValidation = Constants.DATA_TYPE_VALIDATION;
            if (dataTypeValidation.toLowerCase().trim().equals("y")) {
                for (int i = 0; i < count; i++) {
                    Object[] InputData = excelDataReader.getRowData(TestCaseConstants.INPUT_DATA_ROW_NO + i);
                    String[] InputDetail = Commons.getRowDetail(InputData);
                    mapInput(InputDetail);
                    String Objective4 = null;
                    String ExeSteps4 = null;
                    if (inputDataMap.get("Enum").equals("N")
                            || inputDataMap.get("Enum").equals(Constants.NULL)) {
                        if (inputDataMap.get("Datatype").equals("Numeric")) {
                            String alpha = minMaxAlpha();
                            Objective4 = "Failed to " + shortDescriptiontoLower
                                    + " due to invalid " + inputDataMap.get("InputName") + " DataType\n";
                            ExeSteps4 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + InputDetail[0] + " = "
                                    + alpha + " \n";

                        } else if (inputDataMap.get("Datatype").equals("Alphabets")) {
                            String numbers = minMaxNumeric();
                            Objective4 = "Failed to " + shortDescriptiontoLower
                                    + " due to invalid " + inputDataMap.get("InputName") + " DataType\n";

                            ExeSteps4 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = " + numbers
                                    + " \n";
                        } else {
                            continue;
                        }
                        write.writeRow(testCaseNo, "Validation", Objective4, null, ExeSteps4, expResultFail);
                        testCaseNo = testCaseNo + 1;
                    }
                }
            }
            /*
             * Invalid Check
             */
            String invalidCheck = Constants.INVALID_CHECK;
            if (invalidCheck.toLowerCase().trim().equals("y")) {
                for (int i = 0; i < count; i++) {
                    Object[] InputData = excelDataReader.getRowData(TestCaseConstants.INPUT_DATA_ROW_NO + i);
                    String[] InputDetail = Commons.getRowDetail(InputData);
                    mapInput(InputDetail);
                    String Objective5 = null;
                    String Objective6 = null;
                    String ExeSteps5 = null;
                    String ExeSteps6 = null;
                    String specialChar = minMaxSpecialChar();

                    if (inputDataMap.get("Datatype").equals("Boolean(0-1)")) {
                        if (Constants.BOOLEAN_VALIDATION.toLowerCase().trim().equals("y")) {
                            Objective5 = "Failed to " + shortDescriptiontoLower
                                    + " due to invalid " + inputDataMap.get("InputName");
                            ExeSteps5 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = $" + " \n";
                        }
                        else {
                            continue;
                        }
                    } else if (inputDataMap.get("Datatype").equals(
                            "Boolean(true-false)")) {
                        if (Constants.BOOLEAN_VALIDATION.toLowerCase().trim().equals("y")) {
                            Objective5 = "Failed to " + shortDescriptiontoLower
                                    + " due to invalid " + inputDataMap.get("InputName");
                            ExeSteps5 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = $*@%�" + " \n";
                        }
                        else {
                            continue;
                        }
                    } else if (inputDataMap.get("Datatype").equals("Date")) {
                        if (Constants.DATE_VALIDATION.toLowerCase().trim().equals("y")) {
                            Objective5 = "Failed to " + shortDescriptiontoLower
                                    + " due to invalid " + inputDataMap.get("InputName");
                            ExeSteps5 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + "Invalid " + inputDataMap.get("InputName") + " \n";
                            Objective6 = "Failed to " + shortDescriptiontoLower
                                    + " due to invalid Date format of " + inputDataMap.get("InputName");
                            ExeSteps6 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + "Invalid " + inputDataMap.get("InputName")
                                    + " format" + " \n";
                            write.writeRow(testCaseNo, "Validation", Objective6, null, ExeSteps6, expResultFail);
                            testCaseNo = testCaseNo + 1;
                        }
                        else {
                            continue;
                        }
                    } else if (inputDataMap.get("Datatype").equals("IPaddress")) {
                        Objective5 = "Failed to " + shortDescriptiontoLower
                                + " due to reserved IPaddress " + inputDataMap.get("InputName");
                        ExeSteps5 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + "Reservad IPaddress " + inputDataMap.get("InputName")
                                + " \n";
                        Objective6 = "Failed to " + shortDescriptiontoLower
                                + " due to Invalid IPaddress " + inputDataMap.get("InputName");
                        ExeSteps6 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + "Invalid IPaddress " + inputDataMap.get("InputName")
                                + " \n";
                        write.writeRow(testCaseNo, "Validation", Objective6, null, ExeSteps6, expResultFail);
                        testCaseNo = testCaseNo + 1;
                    } else if (inputDataMap.get("Datatype").equals("Alphanumeric")) {
                        continue;
                    } else {
                        Objective5 = "Failed to " + shortDescriptiontoLower
                                + " due to invalid " + inputDataMap.get("InputName");

                        ExeSteps5 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = " + specialChar
                                + " \n";
                    }
                    write.writeRow(testCaseNo, "Validation", Objective5, null, ExeSteps5, expResultFail);
                    testCaseNo = testCaseNo + 1;
                }
            }
            /*
             * BVA-1
             */
            String BVAValidation = Constants.BVA_VALIDATION;
            if (BVAValidation.toLowerCase().trim().equals("y")) {
                for (int i = 0; i < count; i++) {
                    Object[] InputData = excelDataReader
                            .getRowData(TestCaseConstants.INPUT_DATA_ROW_NO + i);
                    String[] InputDetail = Commons.getRowDetail(InputData);
                    mapInput(InputDetail);
                    String Objective7 = null;
                    String ExeSteps7 = null;

                    if (inputDataMap.get("Enum").equals("N")
                            || inputDataMap.get("Enum").equals(Constants.NULL)) {
                        getBoundry();
                        if (!(Minlen <= 1) || inputDataMap.get("Datatype").equals("IPaddress")) {
                            if (inputDataMap.get("Datatype").equals("Numeric")) {
                                String lowerBVA = lowerLimitBVANum();
                                if (!lowerBVA.equals(Constants.UNBOUND)) {
                                    Objective7 = "Failed to " + shortDescriptiontoLower
                                            + " due to " + inputDataMap.get("InputName") + " BVA-1\n";
                                    ExeSteps7 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = "
                                            + lowerBVA + " \n";
                                } else {
                                    continue;
                                }
                            } else if (inputDataMap.get("Datatype").equals("Alphabets")) {
                                String lowerBVA = lowerLimitBVAAlpha();
                                if (!lowerBVA.equals(Constants.UNBOUND)) {
                                    Objective7 = "Failed to " + shortDescriptiontoLower
                                            + " due to " + inputDataMap.get("InputName") + " BVA-1\n";
                                    ExeSteps7 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = "
                                            + lowerBVA + " \n";
                                } else {
                                    continue;
                                }
                            } else if (inputDataMap.get("Datatype").equals("Alphanumeric")) {
                                String lowerBVA = lowerLimitBVAAlphaNum();
                                if (!lowerBVA.equals(Constants.UNBOUND)) {
                                    Objective7 = "Failed to " + shortDescriptiontoLower
                                            + " due to " + inputDataMap.get("InputName") + " BVA-1\n";
                                    ExeSteps7 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = "
                                            + lowerBVA + " \n";
                                } else {
                                    continue;
                                }
                            } else if (inputDataMap.get("Datatype").equals("IPaddress")) {
                                Objective7 = "Failed to " + shortDescriptiontoLower
                                        + " due to " + inputDataMap.get("InputName") + " BVA-1\n";
                                ExeSteps7 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = 123.123.123"
                                        + " \n";
                            }
                            else {
                                continue;
                            }
                            write.writeRow(testCaseNo, "Validation-BVA", Objective7, null, ExeSteps7, expResultFail);
                            testCaseNo = testCaseNo + 1;
                        }
                    }
                }
                /*
                 * BVA+1
                 */
                for (int i = 0; i < count; i++) {
                    Object[] InputData = excelDataReader
                            .getRowData(TestCaseConstants.INPUT_DATA_ROW_NO + i);
                    String[] InputDetail = Commons.getRowDetail(InputData);
                    mapInput(InputDetail);
                    String Objective8 = null;
                    String ExeSteps8 = null;
                    if (inputDataMap.get("Enum").equals("N")
                            || inputDataMap.get("Enum").equals(Constants.NULL)) {
                        getBoundry();
                        if (!(Maxlen <= 0) || inputDataMap.get("Datatype").equals("IPaddress")) {
                            if (inputDataMap.get("Datatype").equals("Numeric")) {
                                String UpperBVA = upperLimitBVANum();
                                if (!UpperBVA.equals(Constants.UNBOUND)) {
                                    Objective8 = "Failed to " + shortDescriptiontoLower
                                            + " due to " + inputDataMap.get("InputName") + " BVA+1\n";
                                    ExeSteps8 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName") + " = "
                                            + UpperBVA + " \n";
                                } else {
                                    continue;
                                }
                            } else if (inputDataMap.get("Datatype").equals("Alphabets")) {
                                String UpperBVA = upperLimitBVAAlpha();
                                if (!UpperBVA.equals(Constants.UNBOUND)) {
                                    Objective8 = "Failed to " + shortDescriptiontoLower
                                            + " due to " + inputDataMap.get("InputName") + " BVA+1\n";
                                    ExeSteps8 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName")
                                            + " = " + UpperBVA + " \n";
                                } else {
                                    continue;
                                }
                            } else if (inputDataMap.get("Datatype").equals(
                                    "Alphanumeric")) {
                                String UpperBVA = upperLimitBVAAlphaNum();
                                if (!UpperBVA.equals(Constants.UNBOUND)) {
                                    Objective8 = "Failed to " + shortDescriptiontoLower
                                            + " due to " + inputDataMap.get("InputName") + " BVA+1\n";
                                    ExeSteps8 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName")
                                            + " = " + UpperBVA + " \n";
                                }
                            } else if (inputDataMap.get("Datatype").equals("IPaddress")) {
                                Objective8 = "Failed to " + shortDescriptiontoLower
                                        + " due to " + inputDataMap.get("InputName") + " BVA+1\n";
                                ExeSteps8 = TestCaseConstants.EXECUTION_STEPS1 + Operation + TestCaseConstants.EXECUTION_STEPS2 + inputDataMap.get("InputName")
                                        + " = 123.123.123.123.123" + " \n";
                            }
                            else {
                                continue;
                            }
                            write.writeRow(testCaseNo, "Validation-BVA", Objective8, null, ExeSteps8, expResultFail);
                            testCaseNo = testCaseNo + 1;
                        }
                    }
                }
            }

            JOptionPane
                    .showMessageDialog(null,
                            "Test Spec Created Successfully \nFolder C:\\AutoGeneratedFile\\TestSpecGenerator");

        }
        else {
            JOptionPane
                    .showMessageDialog(null,
                            "Test Spec not Generated");

        }
    }

    /**
     * mapInput: Map inputs with name (Readability ease)
     * 
     * @param inputdetails
     */
    static Map<String, String> inputDataMap = new HashMap<String, String>();

    public static void mapInput(final String[] inputdetails) {
        // Add variables.
        inputDataMap.put("InputName", inputdetails[0]);
        inputDataMap.put("Datatype", inputdetails[1]);
        inputDataMap.put("Enum", inputdetails[2]);
        inputDataMap.put("MinLen", inputdetails[3]);
        inputDataMap.put("Maxlen", inputdetails[4]);
        inputDataMap.put("IsMandatory", inputdetails[5]);
    }

    static int Maxlen = 0;
    static int Minlen = 0;

    /**
     * set Minimum length and maximum length
     * Minlen - minimum length
     * Maxlen - maximum length
     */
    public static void getBoundry() {
        if (inputDataMap.get("MinLen").equals(inputDataMap.get("Maxlen"))
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Minlen = Maxlen = Integer.parseInt(inputDataMap.get("MinLen"));
        } else if (inputDataMap.get("MinLen").equals(Constants.NULL)
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
        } else if (inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
        } else if (!inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
        } else {
            Minlen = 0;
            Maxlen = 0;
        }
    }

    /**
     * Check length and return appropriate string of alphabets
     * 
     * @return
     */
    public static String minMaxAlpha() {
        String alpha;
        if (inputDataMap.get("MinLen").equals(inputDataMap.get("Maxlen"))
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Minlen = Maxlen = Integer.parseInt(inputDataMap.get("MinLen"));
            alpha = Commons.getSrtingOfLength(Maxlen);
        } else if (inputDataMap.get("MinLen").equals(Constants.NULL)
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            alpha = Commons.getSrtingOfLength(Maxlen);
        } else if (inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            alpha = Commons.getSrtingOfLength(Minlen);
        } else if (!inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            alpha = Commons.getSrtingOfLength(Maxlen);
        } else {
            alpha = Commons.getSrtingOfLength(8);
        }
        return alpha;
    }

    /**
     * Check length and return appropriate string of Numbers
     * 
     * @return
     */
    public static String minMaxNumeric() {
        String Numeric;
        if (inputDataMap.get("MinLen").equals(inputDataMap.get("Maxlen"))
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Minlen = Maxlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Numeric = Commons.getNumberOfLength(Maxlen);
        } else if (inputDataMap.get("MinLen").equals(Constants.NULL)
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            Numeric = Commons.getNumberOfLength(Maxlen);
        } else if (inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Numeric = Commons.getNumberOfLength(Minlen);
        } else if (!inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            Numeric = Commons.getNumberOfLength(Maxlen);
        } else {
            Numeric = Commons.getNumberOfLength(8);
        }
        return Numeric;
    }

    /**
     * Check length and return appropriate string of
     * SpecialCharacters
     * 
     * @return
     */
    public static String minMaxSpecialChar() {
        String SpecialChar;
        if (inputDataMap.get("MinLen").equals(inputDataMap.get("Maxlen"))
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Minlen = Maxlen = Integer.parseInt(inputDataMap.get("MinLen"));
            SpecialChar = Commons.getSpecialCharOfLength(Maxlen);
        } else if (inputDataMap.get("MinLen").equals(Constants.NULL)
                && !inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            SpecialChar = Commons.getSpecialCharOfLength(Maxlen);
        } else if (inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            SpecialChar = Commons.getSpecialCharOfLength(Minlen);
        } else if (!inputDataMap.get("Maxlen").equals(Constants.NULL)
                && !inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            SpecialChar = Commons.getSrtingOfLength(Maxlen);
        } else {
            SpecialChar = Commons.getSpecialCharOfLength(8);
        }
        return SpecialChar;
    }

    /**
     * Check lower limit boundary value and return number of size BVA-1
     * 
     * @return
     */
    public static String lowerLimitBVANum() {
        String Numeric;
        if (!inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Numeric = Commons.getNumberOfLength(Minlen - 1);
        } else {
            Numeric = Constants.UNBOUND;
        }
        return Numeric;
    }

    /**
     * Check lower limit boundary value and return string(alphabets) of size BVA-1
     * 
     * @return
     */
    public static String lowerLimitBVAAlpha() {
        String Numeric;
        if (!inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Numeric = Commons.getSrtingOfLength(Minlen - 1);
        } else {
            Numeric = Constants.UNBOUND;
        }
        return Numeric;
    }

    /**
     * Check lower limit boundary value and return string(alpha-numeric) of size BVA-1
     * 
     * @return
     */
    public static String lowerLimitBVAAlphaNum() {
        String Numeric;
        if (!inputDataMap.get("MinLen").equals(Constants.NULL)) {
            Minlen = Integer.parseInt(inputDataMap.get("MinLen"));
            Numeric = Commons.getSrtingOfLength(Minlen - 1);
        } else {
            Numeric = Constants.UNBOUND;
        }
        return Numeric;
    }

    /**
     * Check lower limit boundary value and return number of size BVA+1
     * 
     * @return
     */
    public static String upperLimitBVANum() {
        String Numeric;
        if (!inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            Numeric = Commons.getNumberOfLength(Maxlen + 1);
        } else {
            Numeric = Constants.UNBOUND;
        }
        return Numeric;
    }

    /**
     * Check lower limit boundary value and return string(alphabets) of size BVA+1
     * 
     * @return
     */
    public static String upperLimitBVAAlpha() {
        String Numeric;
        if (!inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            Numeric = Commons.getSrtingOfLength(Maxlen + 1);
        } else {
            Numeric = Constants.UNBOUND;
        }
        return Numeric;
    }

    /**
     * Check lower limit boundary value and return string(alpha-numeric) of size BVA+1
     * 
     * @return
     */
    public static String upperLimitBVAAlphaNum() {
        String Numeric;
        if (!inputDataMap.get("Maxlen").equals(Constants.NULL)) {
            Maxlen = Integer.parseInt(inputDataMap.get("Maxlen"));
            Numeric = Commons.getSrtingOfLength(Maxlen + 1);
        } else {
            Numeric = Constants.UNBOUND;
        }
        return Numeric;
    }
}
