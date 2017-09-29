package test.automator.constants;

/**
 * This class defines the constants used in Test Case generation class
 * This is the location to update different properties w.r.to Test Case generator and Excel Reader for Test Case generation.
 * 
 * @author kamalesh.p
 * 
 */
public class TestCaseConstants {

    private TestCaseConstants() {
    }

    /*
     * Test Case generator constants
     */
    public static String NEW_FILE_PATH               = Constants.OUT_FILE_PATH + "/TestSpecFiles";
    public static int    TEST_CASE_GEN_WORKSHEET_NO  = 1;
    public static int    TEST_CASE_GEN_INPUTSHEET_NO = 0;
    public static String NULL                        = "NULL";
    public static int    PROJECT_NO_ROW_NO           = 0;
    public static int    PROJECT_NAME_ROW_NO         = 1;
    public static int    MODULE_NAME_ROW_NO          = 2;
    public static int    USECASE_ROW_NO              = 3;
    public static int    OPERATION_ROW_NO            = 4;
    public static int    SUB_MODULE_ROW_NO           = 5;
    public static int    SHORT_DESCRIPTOION_ROW_NO   = 6;
    public static int    AUTHOR_ROW_NO               = 7;
    public static int    DESCRIPTION_COL_NO          = 1;
    public static int    INPUT_DATA_NAMES_COL_NO     = 0;
    public static int    INPUT_DATA_ROW_NO           = 9;
    public static String PRE_CONDITION               = " data should be present in the database.";
    public static String EXP_RESULT_PASS_HEAD        = "1. System should read the ";
    public static String EXP_RESULT_PASS_FOOT        = " details and generate a success response xml message with below details:\n\nError Code= 0\nSeverity= 0\nMessage=OK\n";
    public static String EXP_RESULT_FAIL_HEAD        = "1. System should not read the ";
    public static String EXP_RESULT_FAIL_FOOT        = " details and generate a response xml message with below details:\n\nValid Error Message";
    public static String EXECUTION_STEPS1            = "1. Request XML contains the below test data.\n2. This request XML is sent to ";
    public static String EXECUTION_STEPS2            = " web service.\n\nTest Data should contain the below:\n";
}
