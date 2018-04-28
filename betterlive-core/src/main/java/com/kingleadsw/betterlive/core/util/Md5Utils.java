package com.kingleadsw.betterlive.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * @author zhanghui
 * @date  2015/10/14.
 */
public class Md5Utils {
	
	private static Logger logger = Logger.getLogger(Md5Utils.class);
	
    /**
     * MD5算法
     *
     * @param data
     * @return
     */
    public final static String getMd5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            return byteToStr(md.digest());
            //32位加密
        } catch (NoSuchAlgorithmException e) {
        	logger.error("Md5Utils/getMd5 --error", e);
            return null;
        }
    }

    /**
     * 文件MD5
     * 对文件进行MD5也可以像字符串MD5一样的，首先要把文件转成字节数组，后面和字符串MD5完全一样。
     * @param inputFile
     * @return
     * @throws IOException
     */
    public static String fileMD5(String inputFile){
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
            byte[] buffer =new byte[bufferSize];
            while (digestInputStream.read() > 0);
            // 拿到结果，也是字节数组，包含16个元素
            // 同样，把字节数组转换成字符串
            return byteToStr(digestInputStream.getMessageDigest().digest());
        } catch (Exception e) {
        	logger.error("Md5Utils/fileMD5 --error", e);
            return null;
        } finally {
            try {
                digestInputStream.close();
                fileInputStream.close();
            }catch (Exception e){
             	logger.error("Md5Utils/fileMD5 --error", e);
            }
        }
    }
    private static String byteToStr(byte[] sts){
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < sts.length; offset++) {
            if ((sts[offset] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(sts[offset] & 0xff, 16));
        }
        return buf.toString();
    }
}
