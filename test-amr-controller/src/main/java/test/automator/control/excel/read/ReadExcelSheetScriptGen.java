package test.automator.control.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.automator.constants.ScriptConstants;

/**
 * This class reads data from external excel file.
 * It includes methods to Read column data, cell data etc
 * 
 * @author kamalesh.p
 * 
 */
public class ReadExcelSheetScriptGen {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String                      XlsFilePath;
    Object[][]                  data;
    String[]                    data1;
    Workbook                    workbook;
    Sheet                       sheet;
    Row                         row;
    Cell                        cell;
    String                      value;

    public ReadExcelSheetScriptGen(final String xlFilePath) {
        XlsFilePath = xlFilePath;
        LOGGER.info("Reading Excel file (Script Gen): " + xlFilePath);
        File excel = new File(XlsFilePath);
        try (FileInputStream fis = new FileInputStream(excel);) {
            if (XlsFilePath.contains(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (XlsFilePath.contains(".xls")) {
                workbook = new HSSFWorkbook(fis);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getStackTrace().toString());
            e.printStackTrace();
        }
    }

    public String[] getColumnData(final int sheetNum, final int colNum) {
        try {
            sheet = workbook.getSheetAt(sheetNum);
            row = sheet.getRow(0);
            int rowNum = sheet.getLastRowNum() + 1;
            data1 = new String[rowNum - ScriptConstants.INPUT_DATA_SG_ROW_NO];
            int Ci = 0;
            for (int i = ScriptConstants.INPUT_DATA_SG_ROW_NO; i < rowNum; i++, Ci++) {

                row = sheet.getRow(i);
                cell = row.getCell(colNum);
                if (cell == null) {
                    break;
                } else {
                    value = cellToString(cell);
                    data1[Ci] = value;
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return data1;
    }

    public String getCellData(final int sheetNum, final int row1, final int column1) {
        try {
            sheet = workbook.getSheetAt(sheetNum);
            // sheet = workbook.getSheetAt(index);
            row = sheet.getRow(row1);
            cell = row.getCell(column1);
            if (cell == null) {
                value = "";
            } else {
                value = cellToString(cell);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;

    }

    public static String cellToString(final Cell cell) {
        int type;
        Object result;
        // getCellType will return integer value 0 to 5, depends on cell Type,
        // which is used to get cell Value in Switch Case.
        type = cell.getCellType();
        switch (type) {
        case 0:
            double num = cell.getNumericCellValue();
            result = Math.round(num);
            break;
        case 1:
            result = cell.getStringCellValue();
            break;
        case 2:
            result = cell.getCellFormula();
            break;
        case 3:
            result = "";
            break;
        case 4:
            result = cell.getBooleanCellValue();
            break;
        case 5:
            result = cell.getErrorCellValue();
            break;
        default:
            throw new RuntimeException(
                    "There are no support for this type of cell");
        }
        return result.toString();
    }

    /*
     * main method to test methods in this class
     */
    public static void main(final String[] args) throws Exception {

    }
}
