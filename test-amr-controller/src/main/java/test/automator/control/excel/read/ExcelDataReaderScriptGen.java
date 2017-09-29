package test.automator.control.excel.read;

import test.automator.constants.Constants;
import test.automator.constants.ScriptConstants;

/**
 * This class provides getter methods to fetch data from external excel sheet.
 * An interface between core excel reader code and data manipulator class
 * It calls the core ReadExcelSheet class to read data.
 * 
 * @author kamalesh.p
 * 
 */
public class ExcelDataReaderScriptGen {

    String                  path;
    String                  FileName;
    ReadExcelSheetScriptGen readExcel;

    /*
     * Constructor
     */
    public ExcelDataReaderScriptGen() {
        path = Constants.PATH_TO_TEST_GENERATOR_EXCEL;
        String FileNamein = Constants.TEST_INPUT_DATA_FILE_NAME;
        if (FileNamein.contains(".xlsx")) {
            FileName = FileNamein;
        } else {
            FileName = FileNamein + ".xlsx";
        }
        readExcel = new ReadExcelSheetScriptGen(path + "/" + FileName);
    }

    /*
     * getTemplateStatus,..: get details for template,..
     */
    public String getTemplateStatus() {
        String TemplateStatus = readExcel.getCellData(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.TEMPLATE_ROW_NO, ScriptConstants.DESCRIPTION_COLUMN_NO);
        return TemplateStatus;
    }

    public String getDataSourceName() {
        String DataSourceName = readExcel.getCellData(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.DATASOURCE_NAME_ROW_NO, ScriptConstants.DESCRIPTION_COLUMN_NO);
        return DataSourceName;
    }

    public String getSOAPRequestName() {
        String RequestServiceName = readExcel.getCellData(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.TESTREQUEST_NAME_ROW_NO, ScriptConstants.DESCRIPTION_COLUMN_NO);
        return RequestServiceName;
    }

    public String getDataSinkName() {
        String DataSinkName = readExcel.getCellData(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.DATASINK_NAME_ROW_NO, ScriptConstants.DESCRIPTION_COLUMN_NO);
        return DataSinkName;
    }

    public String[] getRequestNodeNames() {
        String[] obj = readExcel.getColumnData(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.REQUEST_NODE_NAME_COL_NO);
        return obj;
    }

    public String[] getResponseNodeNames() {
        String[] obj = readExcel.getColumnData(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.RESPONSE_NODE_NAME_COL_NO);
        return obj;
    }
}
