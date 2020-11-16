package com.artegentech.util.register;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.artegentech.util.register2.MachineCodeUtils;

public class SystemRegister {

	public static Map<String, Object> registerSystem(String registerCode, String publicKey) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = false;
		String msg = "";
		String validity_date = "";
		// 先将注册码用Base64解密
		byte[] afterRSACode = MyCodeEncrypt.decodeString64Byte(registerCode);
		// 再将注册码用公钥解密
		String allCode = MyCodeEncrypt.decodeByte(afterRSACode, publicKey);

		// 抓取注册码中最后10位的日期字符
		String dateValue = allCode.substring(allCode.length() - 10, allCode.length());
		// 判断注册码中的日期与当前日期和网络日期的对比
		// 如果可以抓到网络日期，则使用网络日期对比，如果网络日期是null，则使用本机日期对比
		// 如果注册码的日期大于网络日期则将内容写入注册文件，如果小于等于的话，则弹窗提醒
		if (dateValue.equals("0000000000")) {
			try {
				//System.out.println("FileUtil.getBasePath():" + FileUtil.getBasePath());
				Base64Utils.byteArrayToFile(afterRSACode, FileUtil.getBasePath() + File.separator + "license.dat");
				Base64Utils.byteArrayToFile(publicKey.getBytes(),
						FileUtil.getBasePath() + File.separator + "license2.dat");
				flag = true;
				msg = "系统激活成功!";
				validity_date = "永久激活";
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				msg = "系统激活失败！";
				validity_date = "";
			}
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
			Date netTime = GetNetworkTime.getWebsiteDatetimeDate("http://www.baidu.com");
			Date curDateTime;
			if (netTime == null) {
				curDateTime = new Date();
				// System.out.println("本机时间");
			} else {
				curDateTime = netTime;
				// System.out.println("网络时间");
			}
			Date regTime;
			try {
				regTime = sdf.parse(dateValue + " 23:59:59");
				long diffDays = (regTime.getTime() - curDateTime.getTime()) / 1000 / 3600 / 24;
				if (diffDays > 0) {
					try {
						Base64Utils.byteArrayToFile(afterRSACode,
								FileUtil.getBasePath() + File.separator + "license.dat");
						//System.out.println("FileUtil.getBasePath():" + FileUtil.getBasePath());
						Base64Utils.byteArrayToFile(publicKey.getBytes(),
								FileUtil.getBasePath() + File.separator + "license2.dat");
						System.out.println("系统激活成功!");
						flag = true;
						msg = "系统激活成功!";
						validity_date = sdf.format(regTime);
					} catch (Exception e) {
						e.printStackTrace();
						flag = false;
						msg = "系统激活失败！";
						validity_date = "";
					}
				} else {
					// System.out.println("有效截止日期小于当前日期，请与管理员联系！");
					flag = false;
					msg = "系统激活失败！有效截止日期小于当前日期！";
					validity_date = "";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("validity_date", validity_date);
		return map;
	}

	/**
	 * 判斷軟件是否已註冊
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> isRegister() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = false;
		String msg = "";
		String validity_date = "";
		File file = new File(FileUtil.getBasePath() + File.separator + "license.dat");
		File file2 = new File(FileUtil.getBasePath() + File.separator + "license2.dat");
		//System.out.println(FileUtil.getBasePath() + File.separator + "license.dat");
		if (!file.exists() || !file2.exists()) {
			flag = false;
			msg = "本系统未激活！请联系系统管理人员！";
			validity_date = "系统未激活";
		} else {
			byte[] afterRSACode = Base64Utils.fileToByte(FileUtil.getBasePath() + File.separator + "license.dat");
			String publicKey = new String(
					Base64Utils.fileToByte(FileUtil.getBasePath() + File.separator + "license2.dat"), "UTF-8");
			// byte[] regCodeByte = MyCodeEncrypt.decodeByte64Byte(afterRSACode);
			// System.out.println("註冊碼解密前：" + new String(afterRSACode, "UTF-8"));
			String regCode = MyCodeEncrypt.decodeByte(afterRSACode, publicKey);
			// System.out.println("註冊碼解密後：" + regCode);
			String regMachineCode = regCode.substring(0, regCode.length() - 10);
			// System.out.println("註冊碼中提取的機器碼:" + regMachineCode);
			String machineCode = IGetDiskByDll.Instance.GetSerialNumber(0);
			// System.out.println("本機機器碼:" + machineCode);
			if (regMachineCode.equals(machineCode)) {
				// 如果機器碼相同，則開始提取有效期，並執行判斷
				// System.out.println("機器碼相同！");
				String dateValue = regCode.substring(regCode.length() - 10, regCode.length());
				if (dateValue.equals("0000000000")) {
					flag = true;
					msg = "此系统已经永久激活！请放心使用！";
					validity_date = "永久激活";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 輸出北京時間
					Date netTime = GetNetworkTime.getWebsiteDatetimeDate("http://www.baidu.com");
					Date curDateTime;
					if (netTime == null) {
						curDateTime = new Date();
						//System.out.println("本機時間" + curDateTime);
					} else {
						curDateTime = netTime;
						//System.out.println("網絡時間" + curDateTime);
					}
					Date regTime;
					try {
						regTime = sdf.parse(dateValue + " 23:59:59");
						long diffDays = (regTime.getTime() - curDateTime.getTime()) / 1000 / 3600 / 24;
						// System.out.println("剩餘天數" + diffDays);
						if (diffDays > 0) {
							if (diffDays <= 10) {
								flag = true;
								msg = "有效期剩余" + diffDays + "天！";
								validity_date = sdf.format(regTime);
							} else {
								flag = true;
								msg = "本系统有效期至：" + regTime;
								validity_date = sdf.format(regTime);
							}
						} else {
							flag = false;
							msg = "本系统已过期！请联系管理员重新激活！";
							validity_date = sdf.format(regTime);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else {
				flag = false;
				msg = "硬件码匹配出错！本机没有权限使用系统！请联系管理员！";
				validity_date = "";
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("validity_date", validity_date);
		return map;
	}
	
	/**
	 * 判斷軟件是否已註冊
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> isRegisterLinux() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = false;
		String msg = "";
		String validity_date = "";
		File file = new File(FileUtil.getBasePath() + File.separator + "license.dat");
		File file2 = new File(FileUtil.getBasePath() + File.separator + "license2.dat");
		//System.out.println(FileUtil.getBasePath() + File.separator + "license.dat");
		if (!file.exists() || !file2.exists()) {
			flag = false;
			msg = "本系统未激活！请联系管理人员！";
			validity_date = "系统未激活";
		} else {
			byte[] afterRSACode = Base64Utils.fileToByte(FileUtil.getBasePath() + File.separator + "license.dat");
			String publicKey = new String(Base64Utils.fileToByte(FileUtil.getBasePath() + File.separator + "license2.dat"), "UTF-8");
			// byte[] regCodeByte = MyCodeEncrypt.decodeByte64Byte(afterRSACode);
			// System.out.println("註冊碼解密前：" + new String(afterRSACode, "UTF-8"));
			String regCode = MyCodeEncrypt.decodeByte(afterRSACode, publicKey);
			// System.out.println("註冊碼解密後：" + regCode);
			String regMachineCode = regCode.substring(0, regCode.length() - 10);
			//System.out.println("註冊碼中提取的機器碼:" + regMachineCode);
			String machineCode = MachineCodeUtils.getCPUID_linux().toUpperCase();
			//System.out.println("本機機器碼:" + machineCode);
			if (regMachineCode.equals(machineCode)) {
				// 如果機器碼相同，則開始提取有效期，並執行判斷
				// System.out.println("機器碼相同！");
				String dateValue = regCode.substring(regCode.length() - 10, regCode.length());
				if (dateValue.equals("0000000000")) {
					flag = true;
					msg = "此系统已经永久激活！请放心使用！";
					validity_date = "永久激活";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 輸出北京時間
					Date netTime = GetNetworkTime.getWebsiteDatetimeDate("http://www.baidu.com");
					Date curDateTime;
					if (netTime == null) {
						curDateTime = new Date();
						//System.out.println("本機時間" + curDateTime);
					} else {
						curDateTime = netTime;
						//System.out.println("網絡時間" + curDateTime);
					}
					Date regTime;
					try {
						regTime = sdf.parse(dateValue + " 23:59:59");
						long diffDays = (regTime.getTime() - curDateTime.getTime()) / 1000 / 3600 / 24;
						// System.out.println("剩餘天數" + diffDays);
						if (diffDays > 0) {
							if (diffDays <= 10) {
								flag = true;
								msg = "有效期剩余" + diffDays + "天！";
								validity_date = sdf.format(regTime);
							} else {
								flag = true;
								msg = "本系统有效期至：" + regTime;
								validity_date = sdf.format(regTime);
							}
						} else {
							flag = false;
							msg = "本系统已过期！请联系管理员重新激活！";
							validity_date = sdf.format(regTime);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else {
				flag = false;
				msg = "硬件码匹配错误！本机没有权限使用系统！请联系管理员！";
				validity_date = "";
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("validity_date", validity_date);
		return map;
	}
}
