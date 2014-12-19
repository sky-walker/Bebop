package org.cgz.oseye.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

public class FileUploadUtils {
	
	/**
	 * 获取图片的宽度
	 * @param input
	 * @return
	 * @throws java.io.IOException
	 */
	public static int getImgWidth(InputStream input) throws IOException {
		BufferedImage img = ImageIO.read(input);
		return img.getWidth();
	}
	
	/**
	 * 获取图片的高度
	 * @param input
	 * @return
	 * @throws java.io.IOException
	 */
	public static int getImgHeight(InputStream input) throws IOException {
		BufferedImage img = ImageIO.read(input);
		return img.getHeight();
	}
	
	/**
	 * 验证文件的类型
	 * @return
	 */
	public static boolean validateFileType(String uploadContentType,List<String> allowType) {
		if(allowType.contains(uploadContentType.toLowerCase())) {
			return true;//属于图片类型
		}
		return false;
	}
	
	/**
	 * 验证是否是真实的图片文件
	 * @param inputStream
	 * @return
	 */
	public static boolean isImage(InputStream inputStream) {
		try {
			if(ImageIO.getImageReaders(ImageIO.createImageInputStream(inputStream)).hasNext()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 验证文件的大小
	 * @param allowSize
	 * @return
	 */
	public static boolean validateFileSize(File file,long allowSize) {
		if(file.length()<allowSize && file.length()>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取文件的后缀名
	 * @param uploadFileName
	 * @return
	 */
	public static String getFileExt(String uploadFileName) {
		return uploadFileName.substring(uploadFileName.lastIndexOf("."));
	}
}
