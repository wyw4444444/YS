package com.artegentech.util.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractAction {
	public void print(HttpServletResponse response, Object value) {
		try {
			response.getWriter().print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Set<Integer> getSetByParam(HttpServletRequest request, String param) {
		Set<Integer> all = new HashSet<Integer>();
		String values[] = request.getParameterValues(param);
		System.out.println("values : " + values);
		if(values!=null) {
			for (int x = 0; x < values.length; x++) {
				if (values[x].matches("\\d+")) {
					all.add(Integer.parseInt(values[x]));
				}
			}
			return all;
		}else {
			return null;
		}
	}

	@Resource
	private MessageSource msgSource; // 標識此對象直接引用配置好的類對象（根據類型匹配）

	/**
	 * 根據指定的key的信息進行資源數據的讀取控制
	 * 
	 * @param msgKey 表示要讀取的資源文件的key內容
	 * @return 表示資源對應的內容
	 */
	public String getValue(String msgKey, Object... args) {
		return this.msgSource.getMessage(msgKey, args, Locale.getDefault());
	}

	/**
	 * 獲取request傳輸過來的參數值
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public String getParam(HttpServletRequest request, String param) {
		String[] value = request.getParameterValues(param);
		return value[0];
	}

	/**
	 * 設置跳轉後所需要的相關的提示信息以及自動跳轉路徑的內容
	 * 
	 * @param mav
	 * @param msgKey
	 * @param urlKey
	 */
	public void setMsgAndUrl(ModelAndView mav, String msgKey, String urlKey) {
		if (this.getType() == null) {
			mav.addObject("msg", this.getValue(msgKey));
		} else {
			mav.addObject("msg", this.getValue(msgKey, this.getType()));
		}
		mav.addObject("url", this.getValue(urlKey));
	}

	/**
	 * 取得要操作數據的標記
	 * 
	 * @return
	 */
	public abstract String getType();
	
}
