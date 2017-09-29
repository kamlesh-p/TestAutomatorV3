package test.automator.control.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.automator.constants.Constants;
import test.automator.constants.TestCaseConstants;

/**
 * This class reads data from external excel file.
 * It includes methods to Read row data, column data, cell data etc
 * 
 * @author kamalesh.p
 * 
 */
public class ReadExcelSheetTestSpecGen {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String                      XlsFilePath;
    Object[][]                  data;
    Object[]                    data1;
    Object[]                    data2;
    Workbook                    workbook;
    Sheet                       sheet;
    Row                         row;
    Cell                        cell;
    String                      value;

    public ReadExcelSheetTestSpecGen(final String xlFilePath) {
        XlsFilePath = xlFilePath;
        LOGGER.info("Reading Excel file (Test Gen): " + xlFilePath);
        try {
            File excel = new File(XlsFilePath);
            FileInputStream fis = new FileInputStream(excel);

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

    public Object[] getRowData(final int SheetNum, final int rowNum) throws Exception {

        try {

            sheet = workbook.getSheetAt(SheetNum);
            // int rowCount = sheet.getLastRowNum() + 1;
            int colNum = sheet.getRow(0).getLastCellNum();
            // rowNum-1 is used to remove 1st row count
            data2 = new Object[colNum];
            Row row = sheet.getRow(rowNum);
            for (int j = 0; j < colNum; j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    data2[j] = Constants.NULL;
                } else {
                    value = cellToString(cell);
                    data2[j] = value;
                }
                // System.out.println("The value is" + value);
            }
        } catch (Exception e) {
            System.out.println("error in getRowData()");
        }
        return data2;
    }

    public Object[] getColumnData(final int sheetNum, final int colNum) {
        try {

            sheet = workbook.getSheetAt(sheetNum);
            row = sheet.getRow(0);
            int rowNum = sheet.getLastRowNum() + 1;
            data1 = new Object[rowNum - 9];
            int Ci = 0;
            for (int i = TestCaseConstants.INPUT_DATA_ROW_NO; i < rowNum; i++, Ci++) {
                row = sheet.getRow(i);
                cell = row.getCell(colNum);
                if (cell == null) {
                    throw new Exception("Exception in getColumnData ReadExcelPOI");
                } else {
                    value = cellToString(cell);
                    data1[Ci] = value;
                }

            }
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Input Data File might be corrupted.\nPress CTRL+SHIFT+END to get last used cell on the worksheet");
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
                value = Constants.NULL;
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
            result = Constants.NULL;
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
