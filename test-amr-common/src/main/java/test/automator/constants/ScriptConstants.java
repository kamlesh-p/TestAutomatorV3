package test.automator.constants;

/**
 * This class defines the constants used in Script generation class
 * This is the location to update different properties w.r.to Script Generator and Excel Reader for Script generation.
 * 
 * @author kamalesh.p
 * 
 */
public class ScriptConstants {

    private ScriptConstants() {
    }

    /*
     * Test Script generator Constants
     */

    public static String NEW_SCRIPT_PATH           = Constants.OUT_FILE_PATH + "/TestScriptFiles";
    public static int    SCRIPTOR_WORKSHEET_NO     = 0;
    public static int    SCRIPTOR_INPUTSHEET_NO    = 1;
    public static int    TEMPLATE_ROW_NO           = 0;
    public static int    DATASOURCE_NAME_ROW_NO    = 1;
    public static int    TESTREQUEST_NAME_ROW_NO   = 2;
    public static int    DATASINK_NAME_ROW_NO      = 3;
    public static int    DESCRIPTION_COLUMN_NO     = 1;
    public static int    INPUT_DATA_SG_ROW_NO      = 5;
    public static int    REQUEST_NODE_NAME_COL_NO  = 0;
    public static int    RESPONSE_NODE_NAME_COL_NO = 1;
    public static String FAULT_CODE                = "def faultString = holder.getNodeValue(\"//*:faultstring\")\nrunnerComplete.setPropertyValue(\"ActualMessage\", ((faultString == null))?actualMessage : faultString)\n";
    public static String FAULT_CODE_UNDERSCORE     = "def faultString = holder.getNodeValue(\"//*:faultstring\")\nrunnerComplete.setPropertyValue(\"Actual_Message\", ((faultString == null))?actual_Message : faultString)\n";

}
