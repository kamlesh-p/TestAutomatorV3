package test.automator.control.file.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import test.automator.constants.Constants;
import test.automator.constants.TestCaseConstants;

/**
 * This class create test case specification file to be edited
 * 
 * @author kamalesh.p
 * 
 */
public class CreateTestSpec {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String                      testSpecTemplatePath;
    String                      newFileName;
    public static String        NEW_TESTSPEC_NAME_WITH_PATH;

    public CreateTestSpec(final String newFileNameGetter) {
        testSpecTemplatePath = Constants.PATH_TO_TESTSPEC_TEMPLATE_EXCEL;

        if (newFileNameGetter.equals("Use Default") || newFileNameGetter.equals("")) {
            newFileName = Constants.DEFAULT_TEST_SPEC_NAME;
        } else {
            if (newFileNameGetter.contains(".xls")) {
                newFileName = newFileNameGetter;
            } else {
                newFileName = newFileNameGetter + ".xls";
            }
        }
        NEW_TESTSPEC_NAME_WITH_PATH = TestCaseConstants.NEW_FILE_PATH + "/" + newFileName;
    }

    public void createTestSpec() throws Exception {
        try {
            File fileDir = new File(TestCaseConstants.NEW_FILE_PATH);
            fileDir.mkdirs();
            File excel = new File(testSpecTemplatePath + "/" + Constants.TEST_SPEC_TEMPLATE_NAME);
            FileInputStream fis = new FileInputStream(excel);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            FileOutputStream out = new FileOutputStream(TestCaseConstants.NEW_FILE_PATH + "/" + newFileName);
            LOGGER.info("Creating new file (Test Spec): " + TestCaseConstants.NEW_FILE_PATH + "/" + newFileName);
            workbook.write(out);
            workbook.close();
            out.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "TestSpecTemplate.xml File might be used\nby other application\nOR\nDoes not exist in the specified path\n\n" + e);
            throw new Exception("File not found");
        }
    }

}
