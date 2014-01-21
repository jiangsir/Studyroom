package tw.jiangsir.Utils.Tools;

import java.util.TreeSet;

public class StringTool {
	/**
	 * 將 set 自動轉出的字串轉回 TreeSet。字串格式如下：["aa", "bb", "cc"]
	 * 
	 * @param setstring
	 * @return
	 */
	public static TreeSet<String> String2TreeSet(String setstring) {
		setstring = setstring.replaceAll("\\[", "");
		setstring = setstring.replaceAll("\\]", "");
		setstring = setstring.replaceAll("\"", "").trim();
		TreeSet<String> set = new TreeSet<String>();
		if (setstring.equals("")) {
			return set;
		}
		for (String s : setstring.split(",")) {
			set.add(s.trim());
		}
		return set;
	}

	/**
	 *  還原 Arrays.toString 自動轉出的字串 String[]。字串格式如下：[aa, bb, cc]
	 * 
	 * @param setstring
	 * @return
	 */
	public static String[] String2Array(String string) {
		string = string.replaceAll("\\[", "").replaceAll("\\]", "")
				.replaceAll("\"", "").trim();
		if (string.equals("")) {
			return new String[] {};
		}
		String[] array = string.split(",");
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}

}
