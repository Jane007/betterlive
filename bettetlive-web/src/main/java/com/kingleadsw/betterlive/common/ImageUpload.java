package com.kingleadsw.betterlive.common;

import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

/**
 * author szx
 * date 2016-08-23 16:51
 * 7牛文件上传下载
 **/
public class ImageUpload {
    private static Logger logger = Logger.getLogger(ImageUpload.class);
    //设置好账号的ACCESS_KEY和SECRET_KEY
    //密钥配置
    private static Auth auth = Auth.create(WebConstant.QINIU_ACCESS_KEY, WebConstant.QINIU_SECRET_KEY);
    
    //构造一个带指定Zone对象的配置类
    private static Configuration configuration = new Configuration(Zone.zone2());
    
    //创建上传对象
    private static UploadManager uploadManager = new UploadManager(configuration);
    //上传文件的路径
    private static String filePath = WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE+WebConstant.UPLOAT_ROOT_PATH_IMAGE_ARTICLE;
    
    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private static String getUpToken(String key){
        return auth.uploadToken(WebConstant.QINIU_CLOUD_KEY,key);
    }

    /**
     * 上传图片
     * @param fileName
     * @param key
     * @return
     * @throws IOException
     */
    public static String simpleUpload(String filePath,String key) throws IOException {
        try {
            logger.info("开始上传图片："+filePath);
            //调用put方法上传
            Response res = uploadManager.put(filePath,key , getUpToken(key));
            String bodyString = res.bodyString();
            logger.info("上传图片结束："+bodyString);
            //打印返回的信息
            return WebConstant.QINIU_LINK + key;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            String bodyString = r.bodyString();
            logger.error("上传图片失败："+bodyString,e);
            return "";
        }finally {
        	/*
            File file = new File(fileName);
            if(file.exists()){
                if(file.delete()){
                    logger.error("文件："+fileName+"上传处理后已删除!!!");
                }else{
                    logger.error("文件："+fileName+"上传处理后删除失败!!!");
                }
            }
            */
        }
    }
    
    /**
     *将图片url链接持久化到本地(建立标记文件夹)
     * @param imageUrl
     */
    public static String fileDownUpload(String imageUrl){
        logger.info("准备写入文件："+imageUrl+"到本地");
        long startTime = System.currentTimeMillis();
        try {
            URL url = new URL(imageUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            String fileName = UUID.randomUUID().toString().replace("-", "");

            File file = new File(filePath + File.separator + fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            int i;
            byte by[] = new byte[512];
            //这里不建议使用byte by[] = new byte[inputStream.available()]; inputStream.read(by);outputStream.write(by);内存一次开销太大
            while((i = inputStream.read(by)) != -1){
                outputStream.write(by,0,i);
            }
            outputStream.flush();

            inputStream.close();
            outputStream.close();
            logger.info("文件："+imageUrl+"写入本地成功,花费时间："+(System.currentTimeMillis() - startTime));
            return filePath + File.separator + fileName;
        } catch (Exception e) {
            logger.error("图片资源访问失败!!!"+imageUrl,e);
        }
        return "";
    }
    
    /**
     * 将前端文件对象保存到本地同时上传到七牛</br>
     * 此方法会重名名原始文件的名称
     * @param multipartFile
     * @param relativePath 图片相对路径，相对于UPLOAT_ROOT_PATH_IMAGE的路径，路径必须以文件分隔符结尾
     */
    public static String uploadFile(MultipartFile multipartFile, String relativePath){
        long startTime = System.currentTimeMillis();
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String fileName = multipartFile.getOriginalFilename(); // 原始文件名
			String prefix = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀名
			fileName = StringUtil.get32UUID() + prefix; // 新的文件名称
            
			String fileFullPath = WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE
					+ File.separator + relativePath;
			File fileDir = new File(fileFullPath);  //文件目录
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
            File file = new File(fileDir, fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            int i;
            byte by[] = new byte[512];
            //这里不建议使用byte by[] = new byte[inputStream.available()]; inputStream.read(by);outputStream.write(by);内存一次开销太大
            while((i = inputStream.read(by)) != -1){
                outputStream.write(by,0,i);
            }
            outputStream.flush();

            inputStream.close();
            outputStream.close();
            String filePath = fileFullPath + fileName;
            String isMember = WebConstant.ISMEMBER;
            if(isMember.equals("true")){
            	 String imgUrl = simpleUpload(filePath, fileName);
                 if (imgUrl == null || "".equals(imgUrl)) {
                 	logger.error("上传文件"+fileName+"到七牛失败");
                 	if (fileFullPath.contains("/images/")) {
                 		relativePath = relativePath.replace(File.separator, "/");
                 		imgUrl = WebConstant.MAIN_SERVER + "/images/" + relativePath + fileName;
                 	} 
     			}
                 logger.info("文件："+fileName+"写入本地成功,花费时间："+(System.currentTimeMillis() - startTime));
                 return imgUrl;
            }else{	//测试环境
    			String showLocal = WebConstant.MAIN_SERVER + WebConstant.UPLOAT_ROOT_PATH_IMAGE + "/" + relativePath + fileName;
            	 logger.info("文件："+fileName+"写入本地成功,花费时间："+(System.currentTimeMillis() - startTime));
                 return showLocal;
            }
        } catch (Exception e) {
            logger.error("图片资源访问失败!!!",e);
        }
        return "";
    }
    
    /**
	 * 等比例压缩算法： 
	 * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
	 * @param srcURL 原图地址
	 */
	public static Map<String, Object> saveMinPhoto(String srcURL, BufferedImage buffImg, String nodeFileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//scale 压缩限制(宽/高)比例  一般用1：当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
			double scale = 1d;
			//comBase 压缩基数
			double comBase = buffImg.getHeight();
			int srcHeight = buffImg.getHeight();
			int srcWidth = buffImg.getWidth();
			int deskHeight = 0;// 缩略图高
			int deskWidth = 0;// 缩略图宽
			double srcScale = (double) srcHeight / srcWidth;
			if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
				if (srcScale >= scale || 1 / srcScale > scale) {
					if (srcScale >= scale) {
						deskHeight = (int) comBase;
						deskWidth = srcWidth * deskHeight / srcHeight;
					} else {
						deskWidth = (int) comBase;
						deskHeight = srcHeight * deskWidth / srcWidth;
					}
				} else {
					if ((double) srcHeight > comBase) {
						deskHeight = (int) comBase;
						deskWidth = srcWidth * deskHeight / srcHeight;
					} else {
						deskWidth = (int) comBase;
						deskHeight = srcHeight * deskWidth / srcWidth;
					}
				}
			} else {
				deskHeight = srcHeight;
				deskWidth = srcWidth;
			}

			String suffix = srcURL.substring(srcURL.lastIndexOf(".")).toLowerCase();
			Random rnd = new Random();
		    int num = rnd.nextInt(89999) + 10000;
			String fileName = "article_" + DateUtil.convertDatetoMyString("yyyyMMddHHmmss", new Date())+num;
			String realPath = WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE
					+ File.separator + nodeFileName + fileName + suffix;
			BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = tag.getGraphics();
			g.drawImage(buffImg, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
			String removePoint = suffix.substring(suffix.indexOf(".")+1, suffix.length());
			ImageIO.write(tag, removePoint, new File(realPath));
			
			
		    String isMember = WebConstant.ISMEMBER;
		    String fileLocal = WebConstant.MAIN_SERVER + "/images/" + nodeFileName + fileName + suffix;
			if(isMember.equals("true")){
				fileLocal = simpleUpload(realPath, fileName);
	        }
			
			String orgFileName = srcURL.substring(srcURL.lastIndexOf("/"), srcURL.lastIndexOf("."));
			File removeFile = new File(WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE
					+ File.separator + nodeFileName + orgFileName + suffix);
			buffImg = null;
			removeFile.delete();
	        map.put("saveLocal", fileLocal);	
			map.put("height", tag.getHeight());
			map.put("width", tag.getWidth());
			map.put("flag", 1);
			tag = null;
			g.dispose();
			return map;
		} catch (Exception e) {
			logger.error("ImageUpload/saveMinPhoto -------error", e);
			map.put("flag", 0);
			return map;
		}
	}
	
	 /**
     * 将前端文件对象临时保存到服务器
     * 此方法会重名名原始文件的名称
     * @param multipartFile
     * @param relativePath 图片相对路径，相对于UPLOAT_ROOT_PATH_IMAGE的路径，路径必须以文件分隔符结尾
     */
    public static String uploadTempFile(MultipartFile multipartFile, String relativePath){
        long startTime = System.currentTimeMillis();
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String fileName = multipartFile.getOriginalFilename(); // 原始文件名
			String prefix = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀名
			fileName = StringUtil.get32UUID() + prefix; // 新的文件名称
            
			String fileFullPath = WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE
					+ File.separator + relativePath;
			File fileDir = new File(fileFullPath);  //文件目录
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
            File file = new File(fileDir, fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            int i;
            byte by[] = new byte[512];
            //这里不建议使用byte by[] = new byte[inputStream.available()]; inputStream.read(by);outputStream.write(by);内存一次开销太大
            while((i = inputStream.read(by)) != -1){
                outputStream.write(by,0,i);
            }
            outputStream.flush();

            inputStream.close();
            outputStream.close();
            String filePath = fileFullPath + fileName;
        	File saveFile = new File(new File(filePath), fileName);// 在该实际路径下实例化一个文件
			if (saveFile.exists()) {
				saveFile.delete();
			}
			// 判断父目录是否存在
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			String showLocal = WebConstant.MAIN_SERVER + WebConstant.UPLOAT_ROOT_PATH_IMAGE + "/" + relativePath + fileName;
        	 logger.info("文件："+fileName+"写入本地成功,花费时间："+(System.currentTimeMillis() - startTime));
             return showLocal;
        } catch (Exception e) {
            logger.error("图片资源访问失败!!!",e);
        }
        return "";
    }
	
	/**
	 * 按质量权重压缩文件
	 * @param image
	 * @param filePath
	 * @param quality
	 * @param nodeFileName
	 * @param orgSize
	 * @return
	 * @throws Exception
	 */
	 public static Map<String, Object> saveImageByQuality(BufferedImage image,  
	            String filePath, float quality, String nodeFileName, long orgSize) throws Exception {  
			Map<String, Object> map = new HashMap<String, Object>();
	        try {
	        	String suffix = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
	        	String removePoint = suffix.substring(suffix.indexOf(".")+1, suffix.length()).toLowerCase().trim();
	        	
	        	int orgWidth = image.getWidth();
	        	int orgHeight = image.getHeight();
	        	int newWidth = orgWidth;
	        	int newHeight = orgHeight;
	        	long fileSize = 2 * 1024 * 1024;
	        	if(orgSize >= fileSize){	//原图大于2M，控制宽高
	        		if (orgWidth > orgHeight) {
						newWidth = orgHeight;
						newHeight = orgHeight * newWidth / orgWidth;
					}else if (orgWidth < orgHeight){
						newHeight = orgWidth;
						newWidth = orgWidth * newHeight / orgHeight;
					}else{
						newWidth = orgWidth/2;
						newHeight = orgHeight/2;
					}
	        	}
	      
	        	BufferedImage newBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);  
		        newBufferedImage.getGraphics().drawImage(image, 0, 0, null);  
		     
		        String[] checkPrefix = { "jpg", "jpeg"};
		    	if (!Arrays.asList(checkPrefix).contains(removePoint)) {
		    		removePoint = "jpg";
		    	}
		        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(removePoint);  
		  
		        ImageWriter imageWriter = iter.next();  
		        ImageWriteParam iwp = imageWriter.getDefaultWriteParam();  
		  
		        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);  
		        iwp.setCompressionQuality(quality);  
		        Random rnd = new Random();
		        int num = rnd.nextInt(89999) + 10000;
		    	String fileName = "article_"+DateUtil.convertDatetoMyString("yyyyMMddHHmmss", new Date())+num;
		    	String realPath = WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE
						+ File.separator + nodeFileName + fileName + suffix;
		        File file = new File(realPath);  
		        FileImageOutputStream fileImageOutput = new FileImageOutputStream(file);  
		        imageWriter.setOutput(fileImageOutput);  
		        IIOImage iio_image = new IIOImage(newBufferedImage, null, null);  
		        imageWriter.write(null, iio_image, iwp);
		        imageWriter.dispose();  
		        fileImageOutput.flush();
		        fileImageOutput.close();

		        String fileLocal = WebConstant.MAIN_SERVER + "/images/" + nodeFileName + fileName + suffix;
		        String isMember = WebConstant.ISMEMBER;
				if(isMember.equals("true")){
					fileLocal = simpleUpload(realPath, fileName);
		        }
				String orgFileName = filePath.substring(filePath.lastIndexOf("/"), filePath.lastIndexOf("."));
				File removeFile = new File(WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE
						+ File.separator + nodeFileName + orgFileName + suffix);
				removeFile.delete();
				image = null;
	    		map.put("saveLocal", fileLocal);	
				map.put("height", newBufferedImage.getHeight());
				map.put("width", newBufferedImage.getWidth());
				map.put("flag", 1);
				newBufferedImage = null;
				return map;
			} catch (Exception e) {
				logger.error("saveImageByQuality -----error", e);
		        map.put("flag", 0);
	    		return map;
			}
	    }  
    
}
