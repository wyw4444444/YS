package com.artegentech.util.register;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 读取网络时间
 *
 */
public class GetNetworkTime {

//  public static String webUrl1 = "http://www.bjtime.cn";//bjTime
//  public static String webUrl2 = "http://www.baidu.com";//百度
//  public static String webUrl3 = "http://www.taobao.com";//淘宝
//  public static String webUrl4 = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
//  public static String webUrl5 = "http://www.360.cn";//360
//  public static String webUrl6 = "http://www.beijing-time.org";//beijing-time

    public static List<String> webUrlList = new ArrayList<>();
    static{
        //webUrlList.add("http://www.bjtime.cn");
        webUrlList.add("http://www.baidu.com");
        webUrlList.add("http://www.taobao.com");
        webUrlList.add("http://www.ntsc.ac.cn");
        webUrlList.add("http://www.360.cn");
        webUrlList.add("http://www.beijing-time.org");
    }
    public static void main(String[] args) {
        //System.out.println(getWebsiteDatetime(webUrlList.get(0)) + " [bjtime]");
        System.out.println(getWebsiteDatetime(webUrlList.get(1)) + " [百度]");
        System.out.println(getWebsiteDatetime(webUrlList.get(2)) + " [淘宝]");
        System.out.println(getWebsiteDatetime(webUrlList.get(3)) + " [中国科学院国家授时中心]");
        System.out.println(getWebsiteDatetime(webUrlList.get(4)) + " [360安全卫士]");
        //System.out.println(getWebsiteDatetime(webUrlList.get(5)) + " [beijing-time]");
        System.out.println(getWebsiteDatetimeActiveTimeStamp() + " [TimeStamp]");
        System.out.println(getWebsiteDatetimeActiveString() + " [String]");
        System.out.println(getWebsiteDatetimeActiveLong() + " [Long]");
        System.out.println(getWebsiteDatetimeActiveDate() + " [Date]");
    }
    
    /**
     * 获取指定网站的日期时间
     * 
     * @param webUrl
     * @return 返回日期格式
     */
    public static Date getWebsiteDatetimeDate(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            return date;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取指定网站的日期时间
     * 
     * @param webUrl
     * @return
     */
    public static String getWebsiteDatetime(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定网站的日期时间
     * 
     * @param webUrl
     * @return  返回日期字符串
     */
    public static String getWebsiteDatetimeActiveString(){
        for (int i = 0; i < webUrlList.size(); i++) {
            try {
                URL url = new URL(webUrlList.get(i));// 取得资源对象
                URLConnection uc = url.openConnection();// 生成连接对象
                uc.connect();// 发出连接
                long ld = uc.getDate();// 读取网站日期时间
                Date date = new Date(ld);// 转换为标准时间对象
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
                return sdf.format(date);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return null;
    }

    /**
     * 获取指定网站的日期时间
     * 
     * @param webUrl
     * @return  返回java.util.Date;
     */
    public static Date getWebsiteDatetimeActiveDate(){
        for (int i = 0; i < webUrlList.size(); i++) {
            try {
                URL url = new URL(webUrlList.get(i));// 取得资源对象
                URLConnection uc = url.openConnection();// 生成连接对象
                uc.connect();// 发出连接
                long ld = uc.getDate();// 读取网站日期时间
                Date date = new Date(ld);// 转换为标准时间对象
                return date;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取指定网站的日期时间
     * 
     * @param webUrl
     * @return  返回日期long类型
     */
    public static long getWebsiteDatetimeActiveLong(){
        for (int i = 0; i < webUrlList.size(); i++) {
            try {
                URL url = new URL(webUrlList.get(i));// 取得资源对象
                URLConnection uc = url.openConnection();// 生成连接对象
                uc.connect();// 发出连接
                long ld = uc.getDate();// 读取网站日期时间
                return ld;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * 获取指定网站的日期时间
     * 
     * @param webUrl
     * @return  返回日期Timestamp类型
     */
    public static Timestamp getWebsiteDatetimeActiveTimeStamp(){
        for (int i = 0; i < webUrlList.size(); i++) {
            try {
                URL url = new URL(webUrlList.get(i));// 取得资源对象
                URLConnection uc = url.openConnection();// 生成连接对象
                uc.connect();// 发出连接
                long ld = uc.getDate();// 读取网站日期时间
                Timestamp ts = new Timestamp(ld);
                return ts;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}