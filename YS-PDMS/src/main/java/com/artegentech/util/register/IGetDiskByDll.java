package com.artegentech.util.register;

import java.io.File;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface IGetDiskByDll extends Library {
	String fileName = FileUtil.getBasePath() + File.separator + "GetHDD.dll";
    //String filePath = IGetDiskByDll.class.getResource("").getPath().replaceFirst("/", "").replaceAll("%20", " ") + fileName;
    IGetDiskByDll Instance = (IGetDiskByDll) Native.loadLibrary(fileName,IGetDiskByDll.class);
	public String GetSerialNumber(int DiskIndex);
}
