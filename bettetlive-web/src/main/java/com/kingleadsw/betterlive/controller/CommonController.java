package com.kingleadsw.betterlive.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.LabelManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.JacksonUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.LabelVo;
import com.qiniu.util.Auth;

/**
 * 
 * class Name [CommonController]. description [公共控制器]
 * 
 * @author ltp
 * @version 1.0
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(CommonController.class);
	
	@Autowired
	private LabelManager labelManager;
	/**
	 * 资源配置信息.
	 */
	public static ResourceBundle config = ResourceBundle
			.getBundle("web-constants");

	
	/**
	 * 微信端 错误提示页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toFuwubc")
	public ModelAndView toFuwubc(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		ModelAndView modelAndView = new ModelAndView("/weixin/fuwubc");
		try {
			String tt = new String(pd.getString("ttitle").getBytes("iso8859-1"),"utf-8");
			String tct = new String(pd.getString("tipsContent").getBytes("iso8859-1"),"utf-8");
			modelAndView.addObject("tipsTitle", tt);
			modelAndView.addObject("tipsContent", tct);
		} catch (UnsupportedEncodingException e) {
			logger.error("/common/toFuwubc --error", e);
		}
		return modelAndView;
	}
	
    /**
     * 搜索框默认值
     */
    @RequestMapping("/queryHomeLabel")
    @ResponseBody
    public Map<String,Object> queryHomeLabel(HttpServletRequest request) {
    	PageData hl = new PageData();
		hl.put("homeFlag", 1);
		hl.put("status", 0);
		LabelVo hotLabelVo = this.labelManager.queryOne(hl);
		if(hotLabelVo == null){
			hotLabelVo = new LabelVo();
		}
        return CallBackConstant.SUCCESS.callback(hotLabelVo);
    }
	
	/**
	 * 上传图片通用方法，上传成功后，图片存在本地及七牛云
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public Map<String, Object> uploadImg(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("/admin/uploadImg，开始");
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			// 获得文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFiles = multipartRequest.getFile("file");

			String filename = null;
			if (multipartFiles != null) {
				filename = multipartFiles.getOriginalFilename(); // 原始文件名
				String prefix = filename.substring(filename.lastIndexOf(".")); // 文件后缀名
				filename = StringUtil.get32UUID() + prefix; // 新的文件名称
				logger.info("图片名称：" + filename);
			} else {
				logger.error("上传图片内容为空");
				throw new Exception();
			}

			// 文件保存目录路径
			String savePath = WebConstant.UPLOAT_ROOT_PATH + WebConstant.UPLOAT_ROOT_PATH_IMAGE+ File.separator;
			// 创建文件夹
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			SaveFileFromInputStream(multipartFiles.getInputStream(), savePath, filename);

			String filePath = savePath + File.separator + filename;
			logger.info("图片存储在本地路径：" + filename);
			String imgUrl = "";
			String isMember = WebConstant.ISMEMBER;
            if(isMember.equals("true")){
            	imgUrl = ImageUpload.simpleUpload(filePath, filename);
            }else{
            	imgUrl = WebConstant.MAIN_SERVER + "/images/" + filename;
            }
			

			logger.info("上传图片地址:" + imgUrl);
			respMap.put("result", "success");
			respMap.put("imgurl", imgUrl);
			respMap.put("msg", "上传图片成功！");
		} catch (Exception e) {
			respMap.put("result", "failure");
			respMap.put("msg", "上传图片,出现异常！");
			return respMap;
		}

		logger.info("--->结束调用/admin/uploadImg，结束");
		return respMap;
	}

	/**
	 * 上传图片名称
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String msg = "upload";
		logger.info("/admin/common/" + msg + " begin");
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();

		try {

			// 获得文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFiles = multipartRequest.getFile("Filedata");

			String filename = null;
			if (multipartFiles != null) {

				filename = UUID.randomUUID().toString().replaceAll("-", "")
						+ ".png";
			} else {
				logger.error("上传图片内容为空");
				throw new Exception();
			}

			// 文件保存目录路径
			String savePath = WebConstant.UPLOAT_ROOT_PATH;

			String imagePath = WebConstant.UPLOAT_ROOT_PATH_IMAGE;

			// 创建文件夹
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			SaveFileFromInputStream(multipartFiles.getInputStream(), savePath,
					filename);

			String url = imagePath + "/" + filename;
			json.put("result", "success");
			json.put("imgurl", url);
			json.put("msg", "上传图片成功！");

			logger.info("--->结束调用/admin/common/" + msg);
		} catch (Exception e) {
			json.put("result", "failure");
			json.put("msg", "上传图片,出现异常！");
		}
		// this.outObjectToJson(CallBackConstant.SUCCESS.callback("success"),
		// response);
		this.outObjectToJson(CallBackConstant.SUCCESS.callbackUrl("操作成功",
						json.getString("imgurl")), response);

	}

	private void SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + File.separator
				+ filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	public void SaveFileFromInputStream(HttpServletRequest request, HttpServletResponse response) throws IOException{
   	 request.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        
   	 //文件保存目录路径  
       String savePath = WebConstant.UPLOAT_ROOT_PATH;
       
       //String savePath = this.getServletContext().getRealPath("/") + configPath;  
         
       // 临时文件目录   
       String tempPath = this.getServletContext().getRealPath("/") ;  
         
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
       String ymd = sdf.format(new Date());  
       savePath += "/" + ymd + "/";  
       //创建文件夹  
       File dirFile = new File(savePath);  
       if (!dirFile.exists()) {  
           dirFile.mkdirs();  
       }  
         
       tempPath += "/" + ymd + "/";  
       //创建临时文件夹  
       File dirTempFile = new File(tempPath);  
       if (!dirTempFile.exists()) {  
           dirTempFile.mkdirs();  
       }  
         
       DiskFileItemFactory  factory = new DiskFileItemFactory();  
       factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。     
       factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。     
       ServletFileUpload upload = new ServletFileUpload(factory);  
       upload.setHeaderEncoding("UTF-8");  
       try {  
           List items = upload.parseRequest(request);  
           Iterator itr = items.iterator();  
             
           while (itr.hasNext())   
           {  
               FileItem item = (FileItem) itr.next();  
               String fileName = item.getName();  
               long fileSize = item.getSize();  
               if (!item.isFormField()) {  
                   String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
                   SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
                   String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
                   try{  
                       File uploadedFile = new File(savePath, newFileName);  
                       
                       OutputStream os = new FileOutputStream(uploadedFile);  
                       InputStream is = item.getInputStream();  
                       byte buf[] = new byte[1024];//可以修改 1024 以提高读取速度  
                       int length = 0;    
                       while( (length = is.read(buf)) > 0 ){    
                           os.write(buf, 0, length);    
                       }    
                       //关闭流    
                       os.flush();  
                       os.close();    
                       is.close();    
                       out.print(savePath+"/"+newFileName);  
                   }catch(Exception e){  
                	   logger.error("/common/SaveFileFromInputStream --error", e);  
                   }  
               }          
           }   
             
       } catch (FileUploadException e) {  
           logger.error("/common/SaveFileFromInputStream --error", e);
       }  
       out.flush();  
       out.close(); 
		
   }   

	/**
	 * 七牛上传token
	 * 
	 * @param response
	 */
	@RequestMapping("/qntoken")
	public void getQNToken(HttpServletResponse response) {
		Map<String, Object> token = new HashMap<String, Object>();
		token.put("uptoken", QiNiuConfig.getUpToken());
		outJson(JacksonUtil.serializeObjectToJson(token), response);
	}

	/**
	 * 七牛配置
	 */
	private static class QiNiuConfig {
		private static Auth auth;
		static {
			// 设置好账号的ACCESS_KEY和SECRET_KEY
			// 密钥配置
			auth = Auth.create(WebConstant.QINIU_ACCESS_KEY,
					WebConstant.QINIU_SECRET_KEY);
		}

		// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
		public static String getUpToken() {
			return auth.uploadToken(WebConstant.QINIU_CLOUD_KEY);
		}
	}

}
