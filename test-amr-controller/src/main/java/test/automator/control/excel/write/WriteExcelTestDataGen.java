package test.automator.control.excel.write;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.automator.constants.Constants;
import test.automator.constants.TestCaseConstants;
import test.automator.constants.TestDataConstants;

/**
 * This class is used to edit external excel files.
 * Used while generating new excel file or Editing created files
 * 
 * @author kamalesh.p
 * 
 */
public class WriteExcelTestDataGen {

    private final static Logger LOGGER   = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String                      infilename;
    Workbook                    workbook = null;
    Sheet                       sheet;

    public WriteExcelTestDataGen(final String infilename, final int SheetNum) throws Exception {
        this.infilename = infilename;
        LOGGER.info("Writing data into excel file (Test Data Gen) from: " + infilename + "; >>> Sheet_No: " + SheetNum);
        File excel = new File(infilename);
        FileInputStream fis = new FileInputStream(excel);
        if (infilename.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(SheetNum);
            fis.close();
        } else if (infilename.endsWith("xls")) {
            workbook = new HSSFWorkbook(fis);
            sheet = workbook.getSheetAt(SheetNum);
            fis.close();
        } else {
            fis.close();
            throw new Exception("invalid file name, should be .xls or .xlsx");
        }
    }

    public void addColumn(final int rowNum, final String colName, final String outputFile) throws IOException {
        sheet = workbook.getSheetAt(TestDataConstants.TEST_DATA_GEN_WORKSHEET_NO);
        int colNum = sheet.getRow(rowNum).getLastCellNum() - 1;
        Row newRow = sheet.getRow(rowNum);
        Cell oldCell = newRow.getCell(colNum);
        Cell newCell = newRow.createCell(colNum + 1);
        newCell.setCellValue(colName);
        // Copy style from old cell and apply to new cell
        CellStyle newCellStyle = workbook.createCellStyle();
        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
        newCell.setCellStyle(newCellStyle);

        FileOutputStream fos = new FileOutputStream(outputFile);
        workbook.write(fos);
        fos.close();
    }

    public void copyRow(final String inputfile, final String outputFile) throws Exception {
        Workbook workbook1 = null;
        Workbook workbook2 = workbook;
        //
        File excel1 = new File(inputfile);
        FileInputStream fis1 = new FileInputStream(excel1);
        if (inputfile.endsWith("xlsx")) {
            workbook1 = new XSSFWorkbook(fis1);
            fis1.close();
        } else if (inputfile.endsWith("xls")) {
            workbook1 = new HSSFWorkbook(fis1);
            fis1.close();
        } else {
            fis1.close();
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet worksheet1 = workbook1.getSheetAt(TestCaseConstants.TEST_CASE_GEN_WORKSHEET_NO);
        Sheet worksheet2 = workbook2.getSheetAt(TestDataConstants.TEST_DATA_GEN_WORKSHEET_NO);
        int sourceRowCount = worksheet1.getLastRowNum();

        for (int i = 0; i < sourceRowCount - 6; i++) {
            Row sourceRow = worksheet1.getRow(i + 6);
            Row newRow = worksheet2.getRow(i + 1);
            // If the row exist in destination, push down all rows by 1 else create a new row
            if (newRow != null) {
                worksheet2.shiftRows(i + 1, worksheet2.getLastRowNum(), 1);
            } else {
                newRow = worksheet2.createRow(i + 1);
            }
            // Loop through source columns to add to new row
            for (int j = 1; j < 4; j++) {
                // Grab a copy of the old/new cell
                Cell oldCell = sourceRow.getCell(j);
                Cell newCell = newRow.createCell(j - 1);

                // If the old cell is null jump to next cell
                if (oldCell == null) {
                    newCell = null;
                    continue;
                }
                newCell.setCellValue(cellToString(oldCell));
                // Copy style from old cell and apply to new cell
                CellStyle newCellStyle = workbook2.createCellStyle();
                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
                newCell.setCellStyle(newCellStyle);

                FileOutputStream fos = new FileOutputStream(outputFile);
                workbook.write(fos);
                fos.close();
            }
        }
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

    public static void updateSheetName(final String file, final String sheetName, final int SheetNo) throws IOException {
        File excel = new File(file);
        FileInputStream fis = new FileInputStream(excel);
        Workbook workbook;
        if (file.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new HSSFWorkbook(fis);
        }

        String safeName = WorkbookUtil.createSafeSheetName(sheetName); // this utility replaces invalid characters with a space (' ')
        workbook.setSheetName(SheetNo, safeName);
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
    }

}
