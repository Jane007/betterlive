package com.kingleadsw.betterlive.core.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * 图片尺寸计算
 */
public class PicUtil {
	
		private static Logger logger = Logger.getLogger(PicUtil.class);
	
		/**
		 * 获取网络图片
		 * @throws IOException 
		 * @throws MalformedURLException 
		 */
		public static BufferedImage getImg(String url) throws MalformedURLException, IOException{
			InputStream is = new URL(url).openStream();
        	BufferedImage sourceImg =ImageIO.read(is);
			return sourceImg; 
		}
	    /**
	     * 获取图片宽度
	     * @param file  图片文件
	     * @return 宽度
	     */
	    public static int getImgWidth(File file) {
	        InputStream is = null;
	        BufferedImage src = null;
	        int ret = -1;
	        try {
	            is = new FileInputStream(file);
	            src = javax.imageio.ImageIO.read(is);
	            ret = src.getWidth(null); // 得到源图宽
	            is.close();
	        } catch (Exception e) {
	         	logger.error("PicUtil/getImgWidth --error", e);
	        }
	        return ret;
	    }
	      
	      
	    /**
	     * 获取图片高度
	     * @param file  图片文件
	     * @return 高度
	     */
	    public static int getImgHeight(File file) {
	        InputStream is = null;
	        BufferedImage src = null;
	        int ret = -1;
	        try {
	            is = new FileInputStream(file);
	            src = javax.imageio.ImageIO.read(is);
	            ret = src.getHeight(null); // 得到源图高
	            is.close();
	        } catch (Exception e) {
	        	logger.error("PicUtil/getImgHeight --error", e);
	        }
	        return ret;
	    } 
}
