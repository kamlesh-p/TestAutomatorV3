package test.automator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.junit.Test;

public class TestCase {

	@Test
	public void excelTest() {
		File excel = new File(this.getClass().getClassLoader().getResource("NewTestDataTemplate.xls").getFile());
		// this.getClass().getResourceAsStream("NewTestDataTemplate.xls");
		FileInputStream fis;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(excel);
			Workbook workbook = new HSSFWorkbook(fis);
			String safeName = WorkbookUtil.createSafeSheetName("acassascsasdfgsdgsdfgsggsdgfsdsfff");
			// this utility replaces invalid characters with a space (' ')
			workbook.setSheetName(0, safeName);
			fos = new FileOutputStream("./target/NewTestDataTemplate.xls");
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
