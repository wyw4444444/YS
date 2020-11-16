package com.artegentech.util.validator;

import javax.servlet.http.HttpServletRequest;

/**
 * 完成的是一個個具體的驗證規則的判斷操作
 * 
 * @author mldn
 */
public class ValidateRuleUtil {
	/**
	 * 驗證傳入的mime類型是否複合于當前的開發要求
	 * 
	 * @param mimeRules 整體的驗證規則
	 * @param mime      每一個上傳檔的類型
	 * @return
	 */
	public static boolean isMime(String mimeRules[], String mime) {
		if (isString(mime)) {
			for (int x = 0; x < mimeRules.length; x++) {
				if (mime.equals(mimeRules[x])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 進行驗證碼的檢測，驗證碼的屬性名稱固定為rand
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public static boolean isRand(HttpServletRequest request, String str) {
		if (isString(str)) {
			String rand = (String) request.getSession().getAttribute("rand");
			if (isString(rand)) {
				return rand.equalsIgnoreCase(str);
			}
		}
		return false;
	}

	/**
	 * 判斷是否是整數
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		if (isString(str)) { // 驗證資料是否為空
			return str.matches("\\d+");
		}
		return false; // 資料為空返回false
	}

	/**
	 * 驗證是否是日期，格式為“yyyy-MM-dd HH:mm:ss”
	 * 
	 * @return
	 */
	public static boolean isDatetime(String str) {
		if (isString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		}
		return false;
	}

	/**
	 * 驗證是否是日期，格式為“yyyy-MM-dd”
	 * 
	 * @return
	 */
	public static boolean isDate(String str) {
		if (isString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2}");
		}
		return false;
	}

	/**
	 * 驗證該資料是否是小數
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (isString(str)) {
			return str.matches("\\d+(\\.\\d+)?");
		}
		return false;
	}

	/**
	 * 如果傳入的內容為null或者是空字串，則表示錯誤，返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isString(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}
}
