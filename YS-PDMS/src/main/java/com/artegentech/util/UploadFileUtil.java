package com.artegentech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 进行文件的保存处理
 * @author mldn
 */
public class UploadFileUtil {
	/**
	 * 创建要保存的文件名称
	 * @param mime
	 * @return
	 */
	public static String createFileName(String mime) {	// 需要创建一个文件名称
		String fileName = UUID.randomUUID() + "." + mime.split("/")[1] ;
		return fileName ;
	}
	/**
	 * 进行文件的保存操作
	 * @param srcFile 上传的原始文件数据输入流
	 * @param destFile 要保存的目标文件路径
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean save(InputStream inputStream, File destFile) {
		boolean flag = false ;
		OutputStream output = null ;
		if (!destFile.getParentFile().exists()) {	// 父路径不存在
			destFile.getParentFile().mkdirs() ;	// 创建父路径
		}
		try {
			output = new FileOutputStream(destFile) ;
			byte data [] = new byte [2048] ;	// 每块数据的保存大小
			int temp = 0 ;	// 保存每次的个数
			while ((temp = inputStream.read(data)) != -1) {
				output.write(data, 0, temp);
			} 
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag ;
	}
	
	public static boolean saveAs(String fileName,String NewName) throws IOException {
		File oldFile = new File(fileName);
		if(!oldFile.exists()){
			oldFile.createNewFile();
		}
		FileInputStream inputStream = new FileInputStream(oldFile);
		boolean flag = false ;
		OutputStream output = null ;
		if (!new File(NewName).getParentFile().exists()) {	// 父路径不存在
			new File(NewName).getParentFile().mkdirs() ;	// 创建父路径
		}
		try {
			output = new FileOutputStream(new File(NewName)) ;
			byte data [] = new byte [2048] ;	// 每块数据的保存大小
			int temp = 0 ;	// 保存每次的个数
			while ((temp = inputStream.read(data)) != -1) {
				output.write(data, 0, temp);
			} 
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		inputStream.close();
		return flag ;
	 }
	
	
	public static boolean update(String fileName,String NewName) throws IOException {
		File oldFile = new File(fileName);
		if(!oldFile.exists()){
			oldFile.createNewFile();
		}
		System.out.println("修改前文件名称是："+oldFile.getName());
		String rootPath = oldFile.getParent();
		File newFile = new File(rootPath + File.separator + NewName);
		System.out.println("修改后文件名称是："+newFile.getName());
		if (oldFile.renameTo(newFile))  {
			 System.out.println("修改成功!");
			 return true;
		}else{
			  System.out.println("修改失败");
			 return false;
		}
	 }
	
	
	
	// String fileName = "g:/temp/xwz.txt";
	// DeleteFileUtil.deleteFile(fileName);
	
	// String fileDir = "D:\\temp\\pom.xml";
	// DeleteFileUtil.deleteDirectory(fileDir);
	// DeleteFileUtil.delete(fileDir);
	
	//	delFolder("c:/bb");

	
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {
 
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}
	
	

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		String path=file.getParent();
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("deletepath:"+path);
			File file2 = new File(path);
			File[] listFiles = file2.listFiles();
			if(listFiles.length < 1){
				delFolder(path);
			} 
			System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			System.out.println("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
 
		if (!flag) {
			System.out.println("删除目录失败");
			return false;
		}
 
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			System.out.println("删除目录" + dir + "失败！");
			return false;
		}
	}
	
	
	
	
	
	
	
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}


}