package com.artegentech.util;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class JsonNullConvert {
	@SuppressWarnings("unchecked")
	public static JSONObject filterNull(JSONObject jsonObj) {
		Iterator<String> it = jsonObj.keys();
		Object obj = null;
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			obj = jsonObj.get(key);
			if (obj instanceof JSONObject) {
				filterNull((JSONObject) obj);
			}
			if (obj instanceof JSONArray) {
				JSONArray objArr = (JSONArray) obj;
				for (int i = 0; i < objArr.size(); i++) {
					filterNull(objArr.getJSONObject(i));
				}
			}
			if (obj == null || obj instanceof JSONNull) {
				jsonObj.put(key, "");
			}
			if (obj.equals(null)) {
				jsonObj.put(key, "");
			}
		}
		return jsonObj;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONArray filterNull(JSONArray jsonArray) {
		Iterator<JSONObject> iterator = jsonArray.iterator();
		while(iterator.hasNext()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj=iterator.next();
			Iterator<String> it = jsonObj.keys();
			Object obj = null;
			String key = null;
			while (it.hasNext()) {
				key = it.next();
				obj = jsonObj.get(key);
				if (obj instanceof JSONObject) {
					filterNull((JSONObject) obj);
				}
				if (obj instanceof JSONArray) {
					JSONArray objArr = (JSONArray) obj;
					for (int i = 0; i < objArr.size(); i++) {
						filterNull(objArr.getJSONObject(i));
					}
				}
				if (obj == null || obj instanceof JSONNull) {
					jsonObj.put(key, "");
				}
				if (obj.equals(null)) {
					jsonObj.put(key, "");
				}
			}
		}
		
		return jsonArray;
	}
}
