package test.automator.control.file.generate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import test.automator.constants.Constants;

/**
 * This class create new script files to be edited
 * 
 * @author kamalesh.p
 * 
 */
public class CreateScript {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public PrintWriter createTestScript(final String newFilePath, final String TestScriptName) throws IOException {
        String testScriptName = TestScriptName;
        if (!TestScriptName.contains(".")) {
            testScriptName = TestScriptName + ".txt";
        }
        File fileDir = new File(newFilePath);
        fileDir.mkdirs();
        File file = new File(newFilePath + "/" + testScriptName);
        file.createNewFile();
        PrintWriter outputFile = new PrintWriter(newFilePath + "/" + testScriptName);
        LOGGER.info("Creating new file (Script/txt): " + newFilePath + "/" + testScriptName);
        return outputFile;
    }

    public String getScriptName(final int value, final String fileName) {
        String newName = fileName;

        if (newName.equals("Use Default") || newName.equals("")) {
            /*
             * value 0 calls request for RequestHandler name
             */
            if (value == 0) {
                if (Constants.SOAP_OR_REST.equals(Constants.SOAP_SERVICE)) {
                    newName = Constants.DEFAULT_REQUEST_HANDLER_SCRIPT_NAME;
                } else {
                    newName = Constants.DEFAULT_JSON_BUILDER_NAME;
                }

            }
            /*
             * value 1 calls request for GroovyScript name
             */
            else if (value == 1) {
                if (Constants.SOAP_OR_REST.equals(Constants.SOAP_SERVICE)) {
                    newName = Constants.DEFAULT_GROOVY_SCRIPT_NAME;
                } else {
                    newName = Constants.DEFAULT_REST_GROOVYSCRIPT_NAME;
                }

            }
            /*
             * value 2 calls request for propertyFile name
             */
            else if (value == 2) {
                newName = Constants.DEFAULT_PROPERTY_FILE_NAME;
            }
        }
        else {
            if (!newName.contains(".groovy") && (value == 0 || value == 1)) {
                newName = newName + ".groovy";
            }
            else if (!newName.contains(".txt") && value == 2) {
                newName = newName + ".txt";
            }
        }
        return newName;

    }
}