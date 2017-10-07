package test.automator.common;

import java.util.ArrayList;

import test.automator.constants.Constants;

/**
 * This class contains some of the common conversion methods used in
 * testCaseGenerator class and ScriptGererator Class
 * 
 * @author kamalesh.p
 * 
 */
public class Commons {

	public static ArrayList<String> mandatoryList = null;
	public static ArrayList<String> nonMandatoryList = null;

	private Commons() {
	}

	/**
	 * Check if name ends with * (Mandatory) and add it to mandatory list else
	 * add to nonMandatoryList
	 * 
	 * @param variableName
	 *            - LIST OF NODE NAMES
	 * @return - No return
	 */

	public static void getMandatoryVariables(final Object[] variableName) {
		mandatoryList = new ArrayList<String>();
		nonMandatoryList = new ArrayList<String>();
		int size = variableName.length;
		for (int i = 0; i < size; i++) {
			String name = variableName[i].toString();
			String editedName = name;
			char[] stringArray = name.trim().toCharArray();
			int Length = stringArray.length;
			if (stringArray[Length - 1] == '*') {
				editedName = name.replace(name.substring(name.length() - 1), "");
				mandatoryList.add(editedName);
			} else {
				nonMandatoryList.add(editedName);
			}
		}
	}

	/**
	 * Convert first character of string to lower Case
	 * 
	 * @param description
	 * @return string with first character in lower case
	 */
	public static String toLowerFirstChar(final String description) {
		char[] stringArray = description.trim().toCharArray();
		stringArray[0] = Character.toLowerCase(stringArray[0]);
		String EditedDescription = new String(stringArray);
		return EditedDescription;
	}

	/**
	 * Convert first character of string to Upper Case
	 * 
	 * @param description
	 * @return string with first character in lower case
	 */
	public static String toUpperFirstChar(final String description) {
		char[] stringArray = description.trim().toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		String EditedDescription = new String(stringArray);
		return EditedDescription;
	}

	/**
	 * Convert object array into String array
	 * 
	 * @param rowDetail
	 * @return String array containing details about mandatory field
	 * @throws Exception
	 */

	public static String[] getRowDetail(final Object[] rowDetail) throws Exception {
		int size = rowDetail.length;
		String[] StrAry = new String[size + 1];
		String name;
		String editedName;
		String DataType;
		String Enum;
		String MinLen;
		String MaxLen;

		if (rowDetail[0] != null || rowDetail[0] != "") {
			name = rowDetail[0].toString();
			editedName = name;
			char[] stringArray = name.trim().toCharArray();
			int Length = stringArray.length;
			if (stringArray[Length - 1] == '*') {
				editedName = name.replace(name.substring(name.length() - 1), "");
				StrAry[5] = Constants.MANDATORY;
			} else {
				StrAry[5] = Constants.NON_MANDATORY;
			}
			StrAry[0] = editedName;
			if (rowDetail[1] != null) {
				DataType = rowDetail[1].toString();
			} else {
				DataType = Constants.NULL;
			}
			StrAry[1] = DataType;
			if (rowDetail[2] != null) {
				Enum = rowDetail[2].toString();
			} else {
				Enum = Constants.NULL;
			}
			StrAry[2] = Enum;
			if (rowDetail[3] != null) {
				MinLen = rowDetail[3].toString();
			} else {
				MinLen = Constants.NULL;
			}
			StrAry[3] = MinLen;
			if (rowDetail[4] != null) {
				MaxLen = rowDetail[4].toString();
			} else {
				MaxLen = Constants.NULL;
			}
			StrAry[4] = MaxLen;
		} else {
			throw new Exception("Missing Input Name!");
		}
		return StrAry;
	}

	/**
	 * convert list to string by replacing "," by "\n"
	 * 
	 * @param aryList
	 * @return
	 */
	public static String getStringValue(final ArrayList<String> aryList) {
		String listString = "";
		for (String s : aryList) {
			listString += s + " \n";
		}
		return listString;

	}

	/**
	 * convert list to string by replacing "," by "\n" and append string value
	 * before each value of list.
	 * 
	 * @param aryList
	 * @param name
	 * @return
	 */
	public static String getStringValueAppendName(final ArrayList<String> aryList, final String name) {
		String listString = "";
		for (String s : aryList) {
			listString += name + " " + s + " \n";
		}
		return listString;

	}

	/**
	 * return String(Alphabets) of given length
	 * 
	 * @param length
	 * @return
	 */
	public static String getSrtingOfLength(final int length) {
		StringBuffer outputBuffer = new StringBuffer(length);
		if (length < 37) {
			for (int i = 0; i < length; i++) {
				int randomNum = 0 + (int) (Math.random() * 15);
				char[] stringArray = Constants.ALPHA_16.toCharArray();
				outputBuffer.append(stringArray[randomNum]);
			}
		}
		return outputBuffer.toString();
	}

	/**
	 * return String(Alphanumeric) of given length
	 * 
	 * @param length
	 * @return
	 */
	public static String getAlphaNumOfLength(final int length) {
		StringBuffer outputBuffer = new StringBuffer(length);
		if (length < 37) {
			for (int i = 0; i < length; i++) {
				int randomNum = 0 + (int) (Math.random() * 19);
				char[] stringArray = Constants.ALPHA_NUM_20.toCharArray();
				outputBuffer.append(stringArray[randomNum]);
			}
		}
		return outputBuffer.toString();
	}

	/**
	 * return numbers of given length
	 * 
	 * @param length
	 * @return
	 */
	public static String getNumberOfLength(final int length) {
		StringBuffer outputBuffer = new StringBuffer(length);
		if (length < 37) {
			for (int i = 0; i < length; i++) {
				int randomNum = 0 + (int) (Math.random() * 9);
				char[] stringArray = Constants.NUMERIC_10.toCharArray();
				outputBuffer.append(stringArray[randomNum]);
			}
		}
		return outputBuffer.toString();
	}

	/**
	 * return Special Characters of given length
	 * 
	 * @param length
	 * @return
	 */
	public static String getSpecialCharOfLength(final int length) {
		StringBuffer outputBuffer = new StringBuffer(length);
		if (length < 37) {
			for (int i = 0; i < length; i++) {
				int randomNum = 0 + (int) (Math.random() * 19);
				char[] stringArray = Constants.SPECIAL_CHAR_20.toCharArray();
				outputBuffer.append(stringArray[randomNum]);
			}
		}
		return outputBuffer.toString();
	}

	/**
	 * replace first character of name to upper case replace * from name if it
	 * ends with *
	 * 
	 * @param name
	 *            - variable name
	 * @return Edited name
	 */
	public static String getVariableNameToUpper(final String name) {
		String Name = name.replace("/*:", "_");
		Name = Name.replaceAll("[/]", "_");
		char[] stringArray = Name.trim().toCharArray();
		int Length = stringArray.length;
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		String EditedName = new String(stringArray);
		if (stringArray[Length - 1] == '*') {
			EditedName = EditedName.replace(EditedName.substring(EditedName.length() - 1), "");
			return EditedName;
		} else {
			return EditedName;
		}
	}

	/**
	 * replace first character of name to lower case replace * from name if it
	 * ends with *
	 * 
	 * @param name
	 * @return
	 */
	public static String getVariableNameToLower(final String name) {
		String Name = name.replace("/*:", "_");
		Name = Name.replaceAll("[/]", "_");
		char[] stringArray = Name.trim().toCharArray();
		int Length = stringArray.length;
		stringArray[0] = Character.toLowerCase(stringArray[0]);
		String EditedName = new String(stringArray);
		if (stringArray[Length - 1] == '*') {
			EditedName = EditedName.replace(EditedName.substring(EditedName.length() - 1), "");
			return EditedName;
		} else {
			return EditedName;
		}
	}

	// /**
	// * replace * from name
	// *
	// * @param name
	// * @return
	// */
	// String getEditedName(final String name) {
	// String Name = name;
	// char[] stringArray = Name.trim().toCharArray();
	// int Length = stringArray.length;
	// String EditedName = new String(stringArray);
	// if (stringArray[Length - 1] == '*') {
	// EditedName = Name.replace(Name.substring(Name.length() - 1), "");
	// return EditedName;
	// } else {
	// return EditedName;
	// }
	// }

	/**
	 * Check if name ends with * (Mandatory)
	 * 
	 * @param variableName
	 * @return
	 */
	static boolean getMandatoryVariable(final String variableName) {
		String Name = variableName;
		char[] stringArray = Name.trim().toCharArray();
		int Length = stringArray.length;
		if (stringArray[Length - 1] == '*') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * append given value to the name
	 * 
	 * @param names
	 * @param appendName
	 * @return
	 */
	public static String[] getStringAppendvalue(final String[] names, final String appendName) {
		String[] appendedNames = new String[names.length];
		int i = 0;
		for (String name : names) {
			if (name == null || name == "") {
				break;
			} else {
				if (Constants.EDIT_NODE_NAMES_IN_SCRIPTS == true) {
					name = getVariableNameToUpper(name);
				} else {
					name = name.replace("/*:", "_");
					name = name.replaceAll("[/]", "_");
				}

				String newName;
				if (Constants.KEEP_UNDERSCORE.toLowerCase().trim().equals("y")) {
					newName = appendName + "_" + name;
				} else {
					newName = appendName + name;
				}
				appendedNames[i] = newName;
			}
			i++;
		}
		return appendedNames;
	}

}
