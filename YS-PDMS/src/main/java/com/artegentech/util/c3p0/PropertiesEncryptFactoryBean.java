package com.artegentech.util.c3p0;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.FactoryBean;

@SuppressWarnings("rawtypes")
public class PropertiesEncryptFactoryBean implements FactoryBean {

	private Properties properties;
	// user/cm9vdA==
	// admin/YWRtaW4=
	/*
	 * private static String beginCharactorUser = "Ask5Ba";
	 * 
	 * private static String endCharactorUser = "BcC83=";
	 * 
	 * private static String beginCharactorPassword = "Ui8l=Y";
	 * 
	 * private static String endCharactorPassword = "Pe9CBt";
	 */

	@Override
	public Object getObject() throws Exception {
		return getProperties();
	}

	@Override
	public Class getObjectType() {
		return java.util.Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) throws UnsupportedEncodingException {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("user");
		String originalPassword = properties.getProperty("password");
		if (originalUsername != null) {
			String newUsername = deEncrypt(originalUsername);
			properties.put("user", newUsername);
		}
		if (originalPassword != null) {
			String newPassword = deEncrypt(originalPassword);
			properties.put("password", newPassword);
		}
	}

	private String deEncrypt(String originalUsername) throws UnsupportedEncodingException {
		String realData = originalUsername.substring(6, 14);
		return new String(Base64.decodeBase64(realData), "UTF-8");
	}
	
}
