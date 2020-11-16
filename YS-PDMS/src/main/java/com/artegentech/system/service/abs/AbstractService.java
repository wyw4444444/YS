package com.artegentech.system.service.abs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractService {
	/**
	 * 針對於MyBatis中需要接受Map集合的操作進行統一的定義，以保證傳入的空字符串可以變為null
	 * 
	 * @param column      模糊查詢的數據列
	 * @param keyword     模糊查詢關鍵字
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行
	 * @return 根據指定的參數返回相關的數據，包含以下內容：<br>
	 *         1、key=column,value=具體的列或者是null<br>
	 *         2、key=keyword,value=具體的模糊查詢關鍵字或null<br>
	 *         3、key=start,value=(currentPage - 1) * lineSize<br>
	 *         4、key=lineSize,value=每頁顯示的數據行數<br>
	 */
	protected Map<String, Object> handleParams(String column, String keyword, int currentPage, int lineSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(column) || column == null || "null".equalsIgnoreCase(column)) {
			map.put("column", null);
		} else {
			map.put("column", column);
		}
		if ("".equals(keyword) || keyword == null || "null".equalsIgnoreCase(keyword)) {
			map.put("keyword", null);
		} else {
			map.put("keyword", "%" + keyword + "%");
		}
		if ((currentPage - 1) * lineSize < 0) {
			map.put("start", 0);
		} else {
			map.put("start", (currentPage - 1) * lineSize);
		}
		map.put("lineSize", lineSize > 0 ? lineSize : 10);
		return map;
	}

	/**
	 * 針對於MyBatis中需要接受Map集合的操作進行統一的定義，以保證傳入的空字符串可以變為null
	 * 
	 * @param column  模糊查詢的數據列
	 * @param keyword 模糊查詢關鍵字
	 * @return 根據指定的參數返回相關的數據，包含以下內容：<br>
	 *         1、key=column,value=具體的列或者是null<br>
	 *         2、key=keyword,value=具體的模糊查詢關鍵字或null<br>
	 */
	protected Map<String, Object> handleParams(String column, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(column) || column == null || "null".equalsIgnoreCase(column)) {
			map.put("column", null);
		} else {
			map.put("column", column);
		}
		if ("".equals(keyword) || keyword == null || "null".equalsIgnoreCase(keyword)) {
			map.put("keyword", null);
		} else {
			map.put("keyword", "%" + keyword + "%");
		}
		return map;
	}

	/**
	 * 針對於MyBatis中需要接受Map集合的操作進行統一的定義，以保證傳入的空字符串可以變為null
	 * 
	 * @param column      模糊查詢的數據列
	 * @param keyword     模糊查詢關鍵字
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行
	 * @return 根據指定的參數返回相關的數據，包含以下內容：<br>
	 *         1、key=column,value=具體的列或者是null<br>
	 *         2、key=keyword,value=具體的模糊查詢關鍵字或null<br>
	 *         3、key=start,value=(currentPage - 1) * lineSize<br>
	 *         4、key=lineSize,value=每頁顯示的數據行數<br>
	 */
	protected Map<String, Object> handleParams2(String column, String keyword, int currentPage, int lineSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(column) || column == null || "null".equalsIgnoreCase(column)) {
			map.put("column", null);
		} else {
			map.put("column", column);
		}
		if ("".equals(keyword) || keyword == null || "null".equalsIgnoreCase(keyword)) {
			map.put("keyword", null);
		} else {
			map.put("keyword", keyword);
		}
		if ((currentPage - 1) * lineSize < 0) {
			map.put("start", 0);
		} else {
			map.put("start", (currentPage - 1) * lineSize);
		}
		map.put("lineSize", lineSize > 0 ? lineSize : 10);
		return map;
	}

	/**
	 * 針對於MyBatis中需要接受Map集合的操作進行統一的定義，以保證傳入的空字符串可以變為null
	 * 
	 * @param column  模糊查詢的數據列
	 * @param keyword 模糊查詢關鍵字
	 * @return 根據指定的參數返回相關的數據，包含以下內容：<br>
	 *         1、key=column,value=具體的列或者是null<br>
	 *         2、key=keyword,value=具體的模糊查詢關鍵字或null<br>
	 */
	protected Map<String, Object> handleParams2(String column, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(column) || column == null || "null".equalsIgnoreCase(column)) {
			map.put("column", null);
		} else {
			map.put("column", column);
		}
		if ("".equals(keyword) || keyword == null || "null".equalsIgnoreCase(keyword)) {
			map.put("keyword", null);
		} else {
			map.put("keyword", keyword);
		}
		return map;
	}

	protected Map<String, Object> handleParams2(String column1, String keyword1, String column2, String keyword2) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(column1) || column1 == null || "null".equalsIgnoreCase(column1)) {
			map.put("column1", null);
		} else {
			map.put("column1", column1);
		}
		if ("".equals(keyword1) || keyword1 == null || "null".equalsIgnoreCase(keyword1)) {
			map.put("keyword1", null);
		} else {
			map.put("keyword1", keyword1);
		}

		if ("".equals(column2) || column2 == null || "null".equalsIgnoreCase(column2)) {
			map.put("column2", null);
		} else {
			map.put("column2", column2);
		}
		if ("".equals(keyword2) || keyword2 == null || "null".equalsIgnoreCase(keyword2)) {
			map.put("keyword2", null);
		} else {
			map.put("keyword2", keyword2);
		}
		return map;
	}
	
	protected Map<String, Object> handleParams2(Date beginDate, Date endDate, int currentPage, int lineSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (beginDate == null) {
			map.put("beginDate", null);
		} else {
			map.put("beginDate", beginDate);
		}
		if (endDate == null) {
			map.put("endDate", null);
		} else {
			map.put("endDate", endDate);
		}
		if ((currentPage - 1) * lineSize < 0) {
			map.put("start", 0);
		} else {
			map.put("start", (currentPage - 1) * lineSize);
		}
		map.put("lineSize", lineSize > 0 ? lineSize : 10);
		return map;
	}
	
	protected Map<String, Object> handleParams2(Date beginDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (beginDate == null) {
			map.put("beginDate", null);
		} else {
			map.put("beginDate", beginDate);
		}
		if (endDate == null) {
			map.put("endDate", null);
		} else {
			map.put("endDate", endDate);
		}
		return map;
	}
}
