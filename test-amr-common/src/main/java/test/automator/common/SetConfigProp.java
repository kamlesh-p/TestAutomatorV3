package test.automator.common;

import test.automator.constants.Constants;

/**
 * This class reads the property values from config file(Should be located in same directory as the Automator file) at the start of the execution.
 * The config file contains :
 * 1. user inputs like what are the different types of validations used for test case creation
 * 2. File name and path details of test data template
 * 3. some information regarding script generation.
 * 
 * @author kamalesh.p
 * 
 */
public class SetConfigProp {

    private SetConfigProp() {
    }

    public static void getUserInput() {
        ReadConfig readConfig = new ReadConfig(System.getProperty("user.dir") + "/" + Constants.CONFIG_FILE_NAME);
        String generatedTestCase = readConfig.getValue("GenerateTestCase");
        Constants.GENERATE_TEST_CASE = null != generatedTestCase ? generatedTestCase : "N";
        /*
         * Test Case validation types
         * NOTE: Do not edit property names (should be same as config file property name)
         */
        Constants.VALID_SCENARIO = readConfig.getValue("ValidScenario", "N");
        Constants.NULL_VALIDATION = readConfig.getValue("NullValidation", "N");
        Constants.DATA_TYPE_VALIDATION = readConfig.getValue("DataTypeValidation", "N");
        Constants.DATE_VALIDATION = readConfig.getValue("DateValidation", "N");
        Constants.BOOLEAN_VALIDATION = readConfig.getValue("BooleanValidation", "N");
        Constants.INVALID_CHECK = readConfig.getValue("InvalidCheck", "N");
        Constants.BVA_VALIDATION = readConfig.getValue("BVAValidation", "N");
        Constants.FAULT_STRING_SCRIPT = readConfig.getValue("FaultStringScript", "N");
        // Script properties
        Constants.KEEP_UNDERSCORE = readConfig.getValue("KeepUnderscore", "N");
        Constants.USE_COMMON_UTIL_LIB = readConfig.getValue("UseCommonLibrary", "Y");
        Constants.COMMON_LIBRARY_NAME = readConfig.getValue("CommonLibraryName", "CommonUtils");
        // Template file names
        Constants.TEST_SPEC_TEMPLATE_NAME = readConfig.getValue("TestSpecTemplateName");
        Constants.TEST_DATA_TEMPLATE_NAME = readConfig.getValue("TestDataTemplateName");
        Constants.TEST_INPUT_DATA_FILE_NAME = readConfig.getValue("TestInputDataFileName");
        // if path contains fully qualified pathName set that path, else set default path as user.dir/bin
        String pathToTestSpecTemplateExcel = readConfig.getValue("PathToTestSpecTemplateExcel");
        pathToTestSpecTemplateExcel = pathToTestSpecTemplateExcel.contains(":/") || pathToTestSpecTemplateExcel.contains(":\\") ? pathToTestSpecTemplateExcel : System
                .getProperty("user.dir") + "/bin";
        Constants.PATH_TO_TESTSPEC_TEMPLATE_EXCEL = pathToTestSpecTemplateExcel;
        // if path contains fully qualified pathName set that path, else set default path as user.dir/bin
        String pathToTestDataTemplateExcel = readConfig.getValue("PathToTestDataTemplateExcel");
        pathToTestDataTemplateExcel = pathToTestDataTemplateExcel.contains(":/") || pathToTestDataTemplateExcel.contains(":\\") ? pathToTestDataTemplateExcel : System
                .getProperty("user.dir") + "/bin";
        Constants.PATH_TO_TESTDATA_TEMPLATE_EXCEL = pathToTestDataTemplateExcel;
        // if path contains fully qualified pathName set that path, else set default path as user.dir/bin
        String pathToTestGeneratorExcel = readConfig.getValue("PathToTestGeneratorExcel");
        pathToTestGeneratorExcel = pathToTestGeneratorExcel.contains(":/") || pathToTestGeneratorExcel.contains(":\\") ? pathToTestGeneratorExcel : System
                .getProperty("user.dir") + "/bin";
        Constants.PATH_TO_TEST_GENERATOR_EXCEL = pathToTestGeneratorExcel;

        readConfig.clearProperty();
    }
}
