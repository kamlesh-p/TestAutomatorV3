package test.automator.control.file.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.automator.constants.Constants;
import test.automator.constants.TestDataConstants;
import static test.automator.constants.Constants.TEST_DATA_TEMPLATE_NAME;

/**
 * This class create new files to be edited.
 * 
 * @author kamalesh.p
 * 
 */
public class CreateDataManager {

    String               testDataTemplatePath;
    String               newFileName1;
    String               newFileName2;
    public static String NEW_TESTDATA_NAME_WITH_PATH;
    public static String NEW_TESTDATA_TEMPLATE_NAME_WITH_PATH;

    public CreateDataManager(final String newTestDataName, final String newTestTemplateName) {
        testDataTemplatePath = Constants.PATH_TO_TESTDATA_TEMPLATE_EXCEL;

        if (newTestDataName.equals("Use Default") || newTestDataName.equals("")) {
            if (TEST_DATA_TEMPLATE_NAME.endsWith(".xlsx")) {
                newFileName1 = Constants.DEFAULT_TEST_DATA_FILE_NAME_XLSX;
            } else {
                newFileName1 = Constants.DEFAULT_TEST_DATA_FILE_NAME;
            }

        }
        else {
            if (TEST_DATA_TEMPLATE_NAME.endsWith(".xlsx") && !newTestDataName.endsWith(".xlsx")) {
                newFileName1 = newTestDataName + ".xlsx";
            } else if (newTestDataName.endsWith(".xlsx") || newTestDataName.endsWith(".xls")) {
                newFileName1 = newTestDataName;
            } else {
                newFileName1 = newTestDataName + ".xls";
            }
        }
        NEW_TESTDATA_NAME_WITH_PATH = TestDataConstants.NEW_TESTDATA_PATH + "/" + newFileName1;

        if (newTestTemplateName.equals("Use Default") || newTestTemplateName.equals("")) {
            if (TEST_DATA_TEMPLATE_NAME.endsWith(".xlsx")) {
                newFileName2 = Constants.DEFAULT_TEST_DATA_TEMPLATE_NAME_XLSX;
            } else {
                newFileName2 = Constants.DEFAULT_TEST_DATA_TEMPLATE_NAME;
            }

        }
        else {
            if (TEST_DATA_TEMPLATE_NAME.endsWith(".xlsx") && !newTestTemplateName.endsWith(".xlsx")) {
                newFileName2 = newTestDataName + ".xlsx";
            } else if (newTestTemplateName.endsWith(".xlsx") || newTestTemplateName.contains(".xls")) {
                newFileName2 = newTestTemplateName;
            }
            else {
                newFileName2 = newTestTemplateName + ".xls";
            }
        }
        NEW_TESTDATA_TEMPLATE_NAME_WITH_PATH = TestDataConstants.NEW_TESTDATA_PATH + "/" + newFileName2;
    }

    public void createTestData() throws Exception {
        try {
            File fileDir = new File(TestDataConstants.NEW_TESTDATA_PATH);
            fileDir.mkdirs();
            File excel = new File(testDataTemplatePath + "/" + Constants.TEST_DATA_TEMPLATE_NAME);
            FileInputStream fis = new FileInputStream(excel);
            Workbook workbook;
            if (TEST_DATA_TEMPLATE_NAME.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new HSSFWorkbook(fis);
            }
            FileOutputStream out = new FileOutputStream(TestDataConstants.NEW_TESTDATA_PATH + "/" + newFileName1);
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "TestDataTemplate.xml File might be used\nby other application\nOR\nDoes not exist in the specified path\n\n" + e);
            throw new Exception("File not found");
        }
    }

    public void createTestDataTemplate() throws Exception {
        try {
            File fileDir = new File(TestDataConstants.NEW_TESTDATA_PATH);
            fileDir.mkdirs();
            // check if file exists
            File existingFile = new File(TestDataConstants.NEW_TESTDATA_PATH + "/" + newFileName2);
            if (existingFile.exists()) {

            } else {
                File excel = new File(testDataTemplatePath + "/" + Constants.TEST_DATA_TEMPLATE_NAME);
                FileInputStream fis = new FileInputStream(excel);
                Workbook workbook;
                if (TEST_DATA_TEMPLATE_NAME.endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(fis);
                } else {
                    workbook = new HSSFWorkbook(fis);
                }
                FileOutputStream out = new FileOutputStream(TestDataConstants.NEW_TESTDATA_PATH + "/" + newFileName2);
                workbook.write(out);
                out.close();
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "TestSpecTemplate.xml File might be used\nby other application\nOR\nDoes not exist in the specified path\n\n" + e);
            throw new Exception("File not found");
        }
    }

    public String toUpperFirstChar(final String description) {
        char[] stringArray = description.trim().toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        String EditedDescription = new String(stringArray);
        return EditedDescription;
    }

}
