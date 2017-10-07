package test.automator.common;

import org.junit.Before;
import org.junit.Test;

import test.automator.constants.Constants;

import java.util.ArrayList;

import org.junit.Assert;

public class TestCase {

	Object[] variableName;
	Object[] rowDetails;
	ArrayList<String> aryList = new ArrayList<String>();
	String varNameUpper = "TEST_VARIABLE";
	String varNameLower = "test_variable";

	@Before
	public void setup() {
		Constants.KEEP_UNDERSCORE = "N";
		variableName = new Object[] { "var01", "var2*" };
		rowDetails = new Object[] { "var01", "var2*", "var3", "var4", "var5", "var6" };
		aryList.add("line01");
		aryList.add("line02");

	}

	@Test
	public void test_getMandatoryVariables() {
		Commons.getMandatoryVariables(variableName);
		Assert.assertNotNull(Commons.mandatoryList);
		Assert.assertNotNull(Commons.nonMandatoryList);
	}

	@Test
	public void test_toLowerFirstChar() {
		Assert.assertTrue("tEST_VARIABLE".equals(Commons.toLowerFirstChar(varNameUpper)));
	}

	@Test
	public void test_toUpperFirstChar() {
		Assert.assertTrue("Test_variable".equals(Commons.toUpperFirstChar(varNameLower)));
	}

	@Test
	public void test_getRowDetail() {
		try {
			Assert.assertNotNull(Commons.getRowDetail(rowDetails));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_getStringValue() {
		Assert.assertTrue("line01 \nline02 \n".equals(Commons.getStringValue(aryList)));
	}

	@Test
	public void test_getStringValueAppendName() {
		Assert.assertNotNull(Commons.getStringValueAppendName(aryList, "a"));
	}

	@Test
	public void test_getSrtingOfLength() {
		Assert.assertNotNull(Commons.getSrtingOfLength(5));
	}

	@Test
	public void test_getAlphaNumOfLength() {
		Assert.assertNotNull(Commons.getAlphaNumOfLength(9));
	}

	@Test
	public void test_getNumberOfLength() {
		Assert.assertNotNull(Commons.getNumberOfLength(6));
	}

	@Test
	public void test_getSpecialCharOfLength() {
		Assert.assertNotNull(Commons.getSpecialCharOfLength(6));
	}

	@Test
	public void test_getVariableNameToUpper() {
		Assert.assertTrue("Test_variable".equals(Commons.getVariableNameToUpper(varNameLower)));
		Assert.assertTrue("Test_variable".equals(Commons.getVariableNameToUpper(varNameLower + "*")));
	}

	@Test
	public void test_getVariableNameToLower() {
		Assert.assertTrue("tEST_VARIABLE".equals(Commons.getVariableNameToLower(varNameUpper)));
		Assert.assertTrue("tEST_VARIABLE".equals(Commons.getVariableNameToLower(varNameUpper + "*")));
	}

	@Test
	public void test_getStringAppendvalue() {
		Assert.assertNotNull(Commons.getStringAppendvalue(new String[] { "asd" }, "a")[0]);
		Constants.KEEP_UNDERSCORE = "Y";
		Assert.assertNotNull(Commons.getStringAppendvalue(new String[] { "a" }, "a")[0]);
	}

	@Test
	public void test_getMandatoryVariable() {
		Assert.assertNotNull(Commons.getMandatoryVariable(varNameLower));
		Assert.assertNotNull(Commons.getMandatoryVariable(varNameLower + "*"));
	}

}
