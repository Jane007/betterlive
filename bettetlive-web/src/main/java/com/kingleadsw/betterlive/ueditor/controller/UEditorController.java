package com.kingleadsw.betterlive.ueditor.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import sun.misc.BASE64Decoder;

import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.util.JacksonUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.ueditor.ActionEnter;

/**
 * UE编辑器初始化
 */
@Controller
@RequestMapping("/ued")
public class UEditorController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(UEditorController.class);

    /**
     * ue 默认使用该方法加action值作为上传文件判断
     * @param request
     * @param response
     * @param action
     */
    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response,String action) {
        if(StringUtil.isNotNull(action) && action.equals("config")){
            response.setContentType("application/json");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            try {
                String exec = new ActionEnter(request, rootPath).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            } catch (IOException e) {
            	logger.error("UEditorController/config --error", e);
            }
        }else if(StringUtil.isNotNull(action) && !action.equals("config")){
            Map<String,Object> retMap = new HashMap<String,Object>();
            try {
                String key = System.currentTimeMillis() + "";
                String fileName = "";
                String filePath = WebConstant.UPLOAT_ROOT_PATH + WebConstant.UPLOAT_ROOT_PATH_IMAGE + WebConstant.UPLOAT_ROOT_PATH_IMAGE_ARTICLE + File.separator;
                if (action.equals("uploadscrawl")) {
                    fileName = key +".png";
                    generateImage(request.getParameter("upfile"),filePath + File.separator + fileName);
                }else{
                    DefaultMultipartHttpServletRequest multipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;
                    MultipartFile multipartFile = multipartHttpServletRequest.getFile("upfile");
                    File newFile = new File(filePath);
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                    }
                    fileName = multipartFile.getOriginalFilename();
                    multipartFile.transferTo(new File(filePath + File.separator + fileName));
                }
                String isMember = WebConstant.ISMEMBER;
                if(isMember.equals("true")){
                	 ImageUpload.simpleUpload(filePath + File.separator + fileName, key);
                     retMap.put("state", "SUCCESS");
                     retMap.put("url", WebConstant.QINIU_LINK + "/" + key);
                }else{
                	retMap.put("state", "SUCCESS");
                    retMap.put("url", WebConstant.MAIN_SERVER + WebConstant.UPLOAT_ROOT_PATH_IMAGE + WebConstant.UPLOAT_ROOT_PATH_IMAGE_ARTICLE + File.separator + fileName);
                }
               
            } catch (Exception e){
            	logger.error("UEditorController/config --error", e);
                retMap.put("success", "failure");
            }
            outJson(JacksonUtil.serializeObjectToJson(retMap), response);
        }
    }

    // base64字符串转化成图片
    public static boolean generateImage(String imgStr,String filename) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(filename);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
