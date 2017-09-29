package test.automator.control.excel.read;

import test.automator.constants.Constants;
import test.automator.constants.TestCaseConstants;

/**
 * This class provides getter methods to fetch data from external excel sheet.
 * An interface between core excel reader and data manipulator class
 * It calls the core ReadExcelSheet class to read data.
 * 
 * @author kamalesh.p
 * 
 */
public class ExcelDataReaderTestSpecGen {

    String                    path;
    String                    FileName;
    ReadExcelSheetTestSpecGen readExcel;

    /*
     * Constructor
     */
    public ExcelDataReaderTestSpecGen() {
        path = Constants.PATH_TO_TEST_GENERATOR_EXCEL;
        String FileNamein = Constants.TEST_INPUT_DATA_FILE_NAME;
        if (FileNamein.contains(".xlsx")) {
            FileName = FileNamein;
        } else {
            FileName = FileNamein + ".xlsx";
        }
        readExcel = new ReadExcelSheetTestSpecGen(path + "/" + FileName);

    }

    /*
     * getProjectNo,..: get details for ProjectNo,..
     * readOnly methods
     */
    public String getProjectNo() {
        String ProjectNo = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.PROJECT_NO_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return ProjectNo;
    }

    public String getProjectName() {
        String ProjectName = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.PROJECT_NAME_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return ProjectName;
    }

    public String getModuleName() {
        String ModuleName = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.MODULE_NAME_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return ModuleName;
    }

    public String getUseCaseName() {
        String UseCaseName = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.USECASE_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return UseCaseName;
    }

    public String getOperation() {
        String Operation = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.OPERATION_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return Operation;
    }

    public String getSubModuleName() {
        String SubModuleName = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.SUB_MODULE_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return SubModuleName;
    }

    public String getShortDescription() {
        String ShortDescription = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.SHORT_DESCRIPTOION_ROW_NO,
                TestCaseConstants.DESCRIPTION_COL_NO);
        return ShortDescription;
    }

    public String getAuthorName() {
        String AuthorName = readExcel.getCellData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.AUTHOR_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO);
        return AuthorName;
    }

    public Object[] getInputDataNames() {
        Object[] obj = readExcel.getColumnData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.INPUT_DATA_NAMES_COL_NO);
        return obj;
    }

    public Object[] getRowData(final int rowNum) throws Exception {
        Object[] obj = readExcel.getRowData(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, rowNum);
        return obj;
    }
}
