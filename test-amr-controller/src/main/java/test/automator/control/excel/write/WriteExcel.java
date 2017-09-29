package test.automator.control.excel.write;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.automator.constants.Constants;
import test.automator.control.file.generate.CreateTestSpec;

/**
 * This class is used to edit external excel files.
 * Used while generating new excel file or Editing created files
 * 
 * @author kamalesh.p
 * 
 */
public class WriteExcel {

	private final static Logger LOGGER           = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	String                      infilename;
	String                      outFile          = CreateTestSpec.NEW_TESTSPEC_NAME_WITH_PATH;
	static String               outFileXmlParser = Constants.PATH_TO_TEST_GENERATOR_EXCEL + "\\" + Constants.TEST_INPUT_DATA_FILE_NAME;
	static Workbook             workbook         = null;
	static Sheet                sheet;

	public WriteExcel(final String infilename, final int SheetNum) throws Exception {
		this.infilename = infilename;
		LOGGER.info("Writing data into excel file: " + infilename + "; >>> Sheet_No: " + SheetNum);
		File excel = new File(infilename);
		FileInputStream fis = new FileInputStream(excel);
		if (infilename.endsWith("xlsx")) {
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(SheetNum);
			fis.close();
		} else if (infilename.endsWith("xls")) {
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(SheetNum);
			fis.close();
		} else {
			fis.close();
			throw new Exception("Invalid file name, should be xls or xlsx");
		}
	}

	public void writeColumn(final int rowindex, final int columnIndex, final String[] array) throws Exception {

		int rowIndex = rowindex;
		int size = array.length;

		for (int i = rowIndex; i < size; i++) {

			sheet.createRow(i).createCell(columnIndex).setCellValue(array[i]);
		}
		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(outFile);
		workbook.write(fos);
		fos.close();
		System.out.println(outFile + " written successfully");
	}

	public void writeRow(final int rowindex, final String[] array) throws Exception {

		int rowIndex = rowindex;
		int size = array.length;
		Row row = sheet.createRow(rowIndex);
		for (int i = 0; i < size; i++) {

			row.createCell(i).setCellValue(array[i]);
		}
		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(outFile);
		workbook.write(fos);
		fos.close();
		System.out.println(outFile + " written successfully");
	}

	public void writeRow(final int rowindex, final int colindex, final String content) throws Exception {

		Row row = sheet.getRow(rowindex);
		row.getCell(colindex).setCellValue(content);
		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(outFile);
		workbook.write(fos);
		fos.close();
		System.out.println(outFile + " written successfully");
	}

	public void writeRow(final int TcNo, final String Functionality, final String objective, final String preCondition, final String ExecutionStep, final String ExpResult)
	        throws IOException {
		try {
			WriteExcel.copyRow(sheet, TcNo + 5, TcNo + 6);
			Row row = sheet.getRow(TcNo + 5);
			row.getCell(1).setCellValue(TcNo);
			row.getCell(2).setCellValue(Functionality);
			row.getCell(3).setCellValue(objective);
			row.getCell(4).setCellValue(null != preCondition ? preCondition : "");
			row.getCell(5).setCellValue(ExecutionStep);
			row.getCell(6).setCellValue(ExpResult);
			// lets write the excel data to file now
			FileOutputStream fos = new FileOutputStream(outFile);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			LOGGER.warning(e.getStackTrace().toString());
		}
	}

	private static void copyRow(final Sheet worksheet, final int sourceRowNum, final int destinationRowNum) {
		// Get the source / new row
		Row newRow = worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 1; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}
			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = workbook.getCellStyleAt((short) 0);
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

		}
	}

	/**
	 * 
	 * @param sheetNum
	 * @param rowindex
	 * @param array
	 * @throws Exception
	 */
	public static void writeReqColumn(final int sheetNum, final int rowindex, final List<String> array) throws Exception {
		File excel = new File(outFileXmlParser);
		workbook = new XSSFWorkbook(new FileInputStream(excel));
		sheet = workbook.getSheetAt(sheetNum);
		int rowIndex = rowindex;
		int size = array.size();
		int j = 0;
		for (int i = rowIndex; i < rowIndex + size; i++) {
			Row row = sheet.getRow(i);
			if (null != row) {
				sheet.getRow(i).createCell(0).setCellValue(array.get(j).toString());
			} else {
				sheet.createRow(i).createCell(0).setCellValue(array.get(j).toString());
			}
			j++;
		}
		int lastRow = sheet.getLastRowNum();
		if (lastRow >= rowIndex + size) {
			for (int i = rowIndex + size; i <= lastRow; i++) {
				Row row = sheet.getRow(i);
				if (null != row) {
					sheet.getRow(i).createCell(0).removeCellComment();
				} else {
					sheet.createRow(i).createCell(0).removeCellComment();
				}
			}
		}
		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(outFileXmlParser);
		workbook.write(fos);
		fos.close();
		System.out.println(outFileXmlParser + " written successfully");
	}

	/**
	 * 
	 * @param sheetNum
	 * @param rowindex
	 * @param array
	 * @throws Exception
	 */
	public static void writeResColumn(final int sheetNum, final int rowindex, final List<String> array) throws Exception {
		File excel = new File(outFileXmlParser);
		workbook = new XSSFWorkbook(new FileInputStream(excel));
		sheet = workbook.getSheetAt(sheetNum);
		int rowIndex = rowindex;
		int size = array.size();
		int j = 0;
		for (int i = rowIndex; i < rowIndex + size; i++) {
			Row row = sheet.getRow(i);
			if (null != row) {
				sheet.getRow(i).createCell(1).setCellValue(array.get(j).toString());
			} else {
				sheet.createRow(i).createCell(1).setCellValue(array.get(j).toString());
			}
			j++;
		}
		int lastRow = sheet.getLastRowNum();
		if (lastRow >= rowIndex + size) {
			for (int i = rowIndex + size; i <= lastRow; i++) {
				Row row = sheet.getRow(i);
				if (null != row) {
					sheet.getRow(i).createCell(1).removeCellComment();
				} else {
					sheet.createRow(i).createCell(1).removeCellComment();
				}
			}
		}
		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(outFileXmlParser);
		workbook.write(fos);
		fos.close();
		LOGGER.info(outFileXmlParser + " written successfully");
	}

	public static void writeRow(final int sheetNum, final int rowindex, final int colindex, final String content) throws Exception {
		File excel = new File(outFileXmlParser);
		workbook = new XSSFWorkbook(new FileInputStream(excel));
		sheet = workbook.getSheetAt(sheetNum);
		Row row = sheet.getRow(rowindex);
		row.getCell(colindex).setCellValue(content);
		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(outFileXmlParser);
		workbook.write(fos);
		fos.close();
		LOGGER.info(outFileXmlParser + " written successfully");
	}

}