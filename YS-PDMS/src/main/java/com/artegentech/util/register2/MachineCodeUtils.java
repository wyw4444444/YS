package com.artegentech.util.register2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MachineCodeUtils {

	private static Logger logger = LoggerFactory.getLogger(MachineCodeUtils.class);

	// 主板序列号 linux
	public static String getMainBordId_linux() {

		String result = "";
		String maniBord_cmd = "dmidecode -s system-serial-number";
		Process p;
		try {
			p = Runtime.getRuntime().exec(new String[] { "sh", "-c", maniBord_cmd });// 管道
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
				break;
			}
			br.close();
		} catch (IOException e) {
			logger.error("获取主板信息错误", e);
		}
		return result;
	}

	/**
	 * 获取CPU序列号 linux
	 * 
	 * @return
	 */
	public static String getCPUID_linux() throws InterruptedException {
		String result = "";
		String CPU_ID_CMD = "dmidecode";
		BufferedReader bufferedReader = null;
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(new String[] { "sh", "-c", CPU_ID_CMD });// 管道
			bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("uuid");
				if (index >= 0) {// 找到了
					// 取出mac地址并去除2边空格
					result = line.substring(index + "uuid".length() + 1).trim();
					break;
				}
			}

		} catch (IOException e) {
			logger.error("获取cpu信息错误", e);
		}
		return result.trim();
	}

}
