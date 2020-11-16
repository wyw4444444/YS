package com.artegentech.util.validator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.artegentech.util.resource.ResourceReadUtil;

public class ValidatorUtils {
	private static Logger log = Logger.getLogger(ValidatorUtils.class);

	/**
	 * 實現提交參數的驗證，使用指定Action的指定驗證規則處理
	 * 
	 * @param request
	 * @param handlerMethod
	 * @return 所有的驗證錯誤資訊保存在Map集合中返回，如果沒有錯誤，則Map集合的長度為0
	 */
	public static Map<String, String> validate(HttpServletRequest request, HandlerMethod handlerMethod) {
		// 通過給定的Action名稱以及要調用的業務方法“rules”一起拼湊出要取出的驗證規則，在Validations.properties中定義
		String beanName = handlerMethod.getBean().getClass().getSimpleName();
		try {
			beanName = beanName.substring(0, beanName.indexOf("$$"));
		} catch (Exception e1) {
		}
		String validationKey = beanName + "." + handlerMethod.getMethod().getName() + ".rules";
		log.info("【*** preHandle ***】validationValue = " + validationKey);

		Map<String, String> errors = new HashMap<String, String>(); // 保存所有的驗證資訊
		try {
			// 現在取得了驗證規則的key的資訊之後實際上並無法知道該key對應的具體的內容是什麼，而內容需要依靠AbstractAction.getValue()取得
			// 既然類繼承了AbstractAction類，那麼該類也會有getValue()方法，那麼直接利用反射調用該類中的該方法
			Method getValueMethod = handlerMethod.getBean().getClass().getMethod("getValue", String.class,
					Object[].class);
			try { // 如果現在沒有指定的key有可能產生異常，就認為現在沒有具體的驗證規則出現
					// 通過getValue()方法的Method物件取得對應的驗證資訊
				String validationValue = (String) getValueMethod.invoke(handlerMethod.getBean(), validationKey, null);
				if (validationValue != null) { // 表示規則現在存在
					log.info("【*** preHandle ***】validationValue = " + validationValue);
					// 取得全部的提交參數， 需要針對於給定的規則進行拆分控制
					String result[] = validationValue.split("\\|"); // 按照分隔號拆分
					for (int x = 0; x < result.length; x++) { // 每一個規則的組成“參數名稱:規則類型”
						String temp[] = result[x].split(":");
						String paramName = temp[0];
						String paramRule = temp[1]; // 驗證規則
						String paramValue = request.getParameter(paramName);
						log.info("【提交参数】paramName = " + paramName + "、paramValue = " + request.getParameter(paramName));
						switch (paramRule) {
						case "string": {
							if (!ValidateRuleUtil.isString(paramValue)) { // 該驗證沒有通過
								String msg = (String) getValueMethod.invoke(handlerMethod.getBean(),
										"validation.string.msg", null);
								errors.put(paramName, msg);
							}
							break;
						}
						case "int": {
							if (!ValidateRuleUtil.isInt(paramValue)) { // 該驗證沒有通過
								String msg = (String) getValueMethod.invoke(handlerMethod.getBean(),
										"validation.int.msg", null);
								errors.put(paramName, msg);
							}
							break;
						}
						case "double": {
							if (!ValidateRuleUtil.isDouble(paramValue)) { // 該驗證沒有通過
								String msg = (String) getValueMethod.invoke(handlerMethod.getBean(),
										"validation.double.msg", null);
								errors.put(paramName, msg);
							}
							break;
						}
						case "date": {
							if (!ValidateRuleUtil.isDate(paramValue)) { // 該驗證沒有通過
								String msg = (String) getValueMethod.invoke(handlerMethod.getBean(),
										"validation.date.msg", null);
								errors.put(paramName, msg);
							}
							break;
						}
						case "datetime": {
							if (!ValidateRuleUtil.isDatetime(paramValue)) { // 該驗證沒有通過
								String msg = (String) getValueMethod.invoke(handlerMethod.getBean(),
										"validation.datetime.msg", null);
								errors.put(paramName, msg);
							}
							break;
						}
						case "rand": {
							if (!ValidateRuleUtil.isRand(request, paramValue)) { // 該驗證沒有通過
								String msg = (String) getValueMethod.invoke(handlerMethod.getBean(),
										"validation.rand.msg", null);
								errors.put(paramName, msg);
							}
							break;
						}
						}
					}
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		if (errors.size() == 0) { // 之前沒有錯誤資訊，現在表示我可以對上傳檔案類型進行驗證
			// 需要判斷是否當前有上傳檔
			MultipartResolver mr = new CommonsMultipartResolver(); // 通過它來判斷對於上傳檔的接收操作
			if (mr.isMultipart(request)) { // 表示的是當前有上傳檔
				// 需要拼湊驗證規則使用的key的資訊
				String mimeKey = handlerMethod.getBean().getClass().getSimpleName() + "."
						+ handlerMethod.getMethod().getName() + ".mime.rules";
				// 取得具體的驗證規則的消息
				String mimeValue = ResourceReadUtil.getValue(handlerMethod, mimeKey);
				if (mimeValue == null) { // 沒有消息讀到，沒有設置單獨的驗證規則
					mimeValue = ResourceReadUtil.getValue(handlerMethod, "mime.rules");
				}
				// 進行每一個上傳檔的具體驗證操作
				String mimeResult[] = mimeValue.split("\\|"); // 因為是一組規則，所以需要拆分
				MultipartRequest mreq = (MultipartRequest) request; // 處理上傳時的request
				Map<String, MultipartFile> fileMap = mreq.getFileMap(); // 取得全部的上傳檔
				if (fileMap.size() > 0) { // 現在有上傳檔
					// 需要判斷每一個檔的類型
					Iterator<Map.Entry<String, MultipartFile>> iter = fileMap.entrySet().iterator();
					while (iter.hasNext()) { // 判斷每一個檔的類型
						Map.Entry<String, MultipartFile> me = iter.next();
						if (me.getValue().getSize() > 0) { // 當前的這個上傳檔的長度大於0，有上傳
							if (!ValidateRuleUtil.isMime(mimeResult, me.getValue().getContentType())) { // 沒有驗證通過
								errors.put("file", ResourceReadUtil.getValue(handlerMethod, "validation.mime.msg"));
							}
						}
					}
				}
			}
		}
		log.info(errors);
		return errors;
	}
}
