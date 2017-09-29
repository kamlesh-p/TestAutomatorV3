package test.automator.constants;

/**
 * This class defines the constants used in Test Data file generation class
 * This is the location to update different properties w.r.to Test Data file generator and Excel Reader for Test Data file generation.
 * 
 * @author kamalesh.p
 * 
 */
public class TestDataConstants {

    private TestDataConstants() {
    }

    /*
     * Test Data Generator Constants
     */
    public static String NEW_TESTDATA_PATH          = Constants.OUT_FILE_PATH + "/TestDataFiles";
    public static String NEW_PROPERTY_FILE_PATH     = Constants.OUT_FILE_PATH + "/TestPropertyFiles";
    public static int    TEST_DATA_GEN_WORKSHEET_NO = 0;
    public static int    TEST_DATA_HEADER_ROW_NO    = 0;

}
