package com.artegentech.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 将多张图片合并转为PDF；需要用到iTextpdf包，
 * 
 * @author 浴缸
 * 
 */
public class PrintToPdfUtil {
	/**
	 * 
	 * @param imageFolderPath
	 *            图片文件夹地址
	 * @param pdfPath
	 *            PDF文件保存地址
	 * 
	 */
	public static void toPdf(List<String> imagePathList, String pdfPath) {
		try {
			// 输入流
			FileOutputStream fos = new FileOutputStream(pdfPath);
			// 创建文档
			Document doc = new Document();
			//doc.open();
			// 写入PDF文档
			PdfWriter.getInstance(doc, fos);
			// 读取图片流
			BufferedImage img = null;
			// 实例化图片
			Image image = null;
			doc.open();
			// 循环获取图片文件夹内的图片
			for (String fileUrl : imagePathList) {
				// 实例化图片
				image = Image.getInstance(fileUrl);
				// 添加图片到文档
				doc.setPageSize(image);
				doc.newPage();
				image.setAbsolutePosition(0, 0);
		        doc.add(image);
				
			}
			// 关闭文档
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}


}