package com.kingleadsw.betterlive.init;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 下载文件.
 * @author wanggang
 * @version 1.0
 */
public class FileDownload implements Serializable {

	public FileDownload() {
	}
	
	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = -2966425521742064947L;
	
	/**
	 * 文件字符集.
	 */
	private static final String CHARACTER_SET = "UTF-8";

	/**
	 * 文件内容 类型.
	 */
	private static final Map<String, String> CONTENT_TYPE = new HashMap<String, String>();

	static {
		CONTENT_TYPE.put("xls", "application/vnd.ms-excel");
		CONTENT_TYPE.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
		CONTENT_TYPE.put("rar", "application/octet-stream");
		CONTENT_TYPE.put("zip", "application/zip");
		CONTENT_TYPE.put("doc", "application/msword");
		CONTENT_TYPE.put("jpg", "image/jpeg");
		CONTENT_TYPE.put("jpe", "image/jpeg");
		CONTENT_TYPE.put("jpeg", "image/jpeg");
		CONTENT_TYPE.put("gif", "image/gif");
		CONTENT_TYPE.put("ai", "application/postscript");
		CONTENT_TYPE.put("txt", "text/plain");
	}

	/**
	 * 图片类型.
	 */
	public static final List<String> IMG_TYPES = new ArrayList<String>();
	static {
		IMG_TYPES.add("jpg");
		IMG_TYPES.add("png");
	}

	/**
	 * 读取的文件.
	 */
	private String fileIn;

	/**
	 * 读取時文件用的编码.
	 */
	private String fileInEn;

	/**
	 * 写出的文件.
	 */
	private String fileOut;

	/**
	 * 写出時文件用的编码.
	 */
	private String fileOutEn;

	/**
	 * 文件下载.
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param storeName
	 *            服务器存储文件名称
	 * @throws IOException 
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName) throws IOException {
		response.setContentType("text/html;charset=" + CHARACTER_SET);
		request.setCharacterEncoding(CHARACTER_SET);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String downLoadPath = "";
			if (StringUtil.isNotEmpty(storeName)) {
				downLoadPath +=  storeName;
				long fileLength = new File(downLoadPath).length();
				if (fileLength != 0) {
					response.setContentType(CONTENT_TYPE
							.get(getExtensionName(storeName)));
					response.setHeader("Content-disposition", "attachment; filename="
							+ new String(storeName.getBytes("UTF-8"), "ISO8859-1"));
					response.setHeader("Content-Length", String.valueOf(fileLength));

					bis = new BufferedInputStream(new FileInputStream(downLoadPath));
					bos = new BufferedOutputStream(response.getOutputStream());
					byte[] buff = new byte[2048];
					int bytesRead;
					while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
						bos.write(buff, 0, bytesRead);
					}
				}
				
			}
		} catch (Exception e) {
			
		} finally {
			bis.close();
			bos.close();
		}
	}

	/**
	 * 文件操作 获取文件扩展名.
	 * 
	 * @param filename
	 *            filename
	 * @return String
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 返回 fileIn 的值.
	 * 
	 * @return fileIn
	 */

	public String getFileIn() {
		return fileIn;
	}

	/**
	 * 设置 fileIn 的值.
	 * 
	 * @param fileIn
	 *            fileIn
	 */
	public void setFileIn(String fileIn) {
		this.fileIn = fileIn;
	}

	/**
	 * 返回 fileInEn 的值.
	 * 
	 * @return fileInEn
	 */

	public String getFileInEn() {
		return fileInEn;
	}

	/**
	 * 设置 fileInEn 的值.
	 * 
	 * @param fileInEn
	 *            fileInEn
	 */
	public void setFileInEn(String fileInEn) {
		this.fileInEn = fileInEn;
	}

	/**
	 * 返回 fileOut 的值.
	 * 
	 * @return fileOut
	 */

	public String getFileOut() {
		return fileOut;
	}

	/**
	 * 设置 fileOut 的值.
	 * 
	 * @param fileOut
	 *            fileOut
	 */
	public void setFileOut(String fileOut) {
		this.fileOut = fileOut;
	}

	/**
	 * 返回 fileOutEn 的值.
	 * 
	 * @return fileOutEn
	 */

	public String getFileOutEn() {
		return fileOutEn;
	}

	/**
	 * 设置 fileOutEn 的值.
	 * 
	 * @param fileOutEn
	 *            fileOutEn
	 */
	public void setFileOutEn(String fileOutEn) {
		this.fileOutEn = fileOutEn;
	}

	/**
	 * @param response 
	 * @param filePath		//文件完整路径(包括文件名和扩展名)
	 * @param fileName		//下载后看到的文件名
	 * @return  文件名
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception{  
		     
		    byte[] data = FileUtil.toByteArray2(filePath);  
		    fileName = URLEncoder.encode(fileName, "UTF-8");  
		    response.reset();  
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
		    response.addHeader("Content-Length", "" + data.length);  
		    response.setContentType("application/octet-stream;charset=UTF-8");  
		    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
		    outputStream.write(data);  
		    outputStream.flush();  
		    outputStream.close();
		    response.flushBuffer();
		    
		} 
}
