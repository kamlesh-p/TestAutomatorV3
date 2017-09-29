package test.automator.control.xml.parse;

import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JOptionPane;

import test.automator.constants.Constants;
import test.automator.constants.ScriptConstants;
import test.automator.constants.TestCaseConstants;
import test.automator.control.excel.write.WriteExcel;

public class EditInputExcel {

    public static void writeRequest(final List<String> requestParamList) throws Exception {
        try {
            // write node names into TestGenerator input sheet
            // scriptor input sheet
            WriteExcel.writeReqColumn(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.INPUT_DATA_SG_ROW_NO, requestParamList);
            // test gen input sheet
            WriteExcel.writeReqColumn(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.INPUT_DATA_ROW_NO, requestParamList);
        } catch (FileNotFoundException e) {
            JOptionPane
                    .showMessageDialog(null, Constants.TEST_INPUT_DATA_FILE_NAME + " File might be used\nby other application\nOR\nDoes not exist in the specified path\n\n" + e);
            throw new Exception("File not found");
        }
    }

    public static void writeResponse(final List<String> responseParamList) throws Exception {
        try {
            WriteExcel.writeResColumn(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.INPUT_DATA_SG_ROW_NO, responseParamList);
        } catch (FileNotFoundException e) {
            JOptionPane
                    .showMessageDialog(null, Constants.TEST_INPUT_DATA_FILE_NAME + " File might be used\nby other application\nOR\nDoes not exist in the specified path\n\n" + e);
            throw new Exception("File not found");
        }
    }

    public static void updateInuptFileHeader() throws Exception {
        // update input file header details
        if (!Constants.SERVICE_NAME.equals("")) {
            WriteExcel.writeRow(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.MODULE_NAME_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO, Constants.SERVICE_NAME);
            WriteExcel.writeRow(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.USECASE_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO, Constants.SERVICE_NAME);
        }
        if (!Constants.OPERATION_NAME.equals("")) {
            WriteExcel.writeRow(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.OPERATION_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO, Constants.OPERATION_NAME);
            WriteExcel.writeRow(TestCaseConstants.TEST_CASE_GEN_INPUTSHEET_NO, TestCaseConstants.SUB_MODULE_ROW_NO, TestCaseConstants.DESCRIPTION_COL_NO, Constants.OPERATION_NAME);
            WriteExcel.writeRow(ScriptConstants.SCRIPTOR_INPUTSHEET_NO, ScriptConstants.TESTREQUEST_NAME_ROW_NO, ScriptConstants.DESCRIPTION_COLUMN_NO, Constants.OPERATION_NAME);
        }
    }
}
