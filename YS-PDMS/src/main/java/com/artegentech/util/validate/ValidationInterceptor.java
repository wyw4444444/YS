package com.artegentech.util.validate;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.artegentech.util.resource.ResourceReadUtil;
import com.artegentech.util.validator.ValidatorUtils;


public class ValidationInterceptor implements HandlerInterceptor {
	Logger log = Logger.getLogger(ValidationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag = true; // 默認放行
		try {
			// 需要取得HandlerMethod物件，這樣可以取得相關的Action資訊
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 表示具體的驗證處理操作，所有的錯誤資訊通過Map返回
			Map<String, String> errors = ValidatorUtils.validate(request, handlerMethod);
			if (errors.size() > 0) { // 有錯
				request.setAttribute("errors", errors); // 保存在Request屬性範圍之中
				flag = false; // 表示現在有錯誤，無法向下執行
				request.getRequestDispatcher(ResourceReadUtil.getErrorPageValue(handlerMethod)).forward(request,
						response);
			} else { // 沒有錯
				return true;
			}
		} catch (Exception e) {
		}
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// log.info("【*** postHandle ***】" + handler.getClass() + "、modelAndView = " +
		// modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// log.info("【*** afterCompletion ***】" + handler.getClass());
	}

}
