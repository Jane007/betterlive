package com.kingleadsw.betterlive.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtils {
	
	/**
     * 生成RAS公钥与私钥字符串，直接返回
     * @return
     */
    public static HashMap<String,String> getKeys(){
        HashMap<String,String> map = new HashMap<String,String>();
//         KeyPairGenerator keyPairGen = null;  
//         try {  
//             keyPairGen = KeyPairGenerator.getInstance("RSA");  
//         } catch (NoSuchAlgorithmException e) {  
             // TODO Auto-generated catch block  
//             e.printStackTrace();  
//         }  
         // 初始化密钥对生成器，密钥大小为96-1024位  
//         keyPairGen.initialize(1024,new SecureRandom());  
         // 生成一个密钥对，保存在keyPair中  
//         KeyPair keyPair = keyPairGen.generateKeyPair();  
         //得到公钥字符串  
//         String publicKey   = base64ToStr(keyPair.getPublic().getEncoded());  
         String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmXW8gjnZI5jed/aO3LZkbsQwFYGugY5CxCv8vsjDK3n/xCQHBc5bD8cm+K3hpDs3jzPOlMH2hxXYmeTQ1ndKAc/S7EpvAss6ORYnOo62zEzwnDwnR29fP4xpbix/YeQBJpddiiErVksyKG+ofZyLIm3pFsxFHmPL+IIYMkNH3xwIDAQAB";
         //得到私钥字符串  
//         String privateKey = base64ToStr(keyPair.getPrivate().getEncoded());
         String privateKey  = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOZdbyCOdkjmN539o7ctmRuxDAVga6BjkLEK/y+yMMref/EJAcFzlsPxyb4reGkOzePM86UwfaHFdiZ5NDWd0oBz9LsSm8Cyzo5Fic6jrbMTPCcPCdHb18/jGluLH9h5AEml12KIStWSzIob6h9nIsibekWzEUeY8v4ghgyQ0ffHAgMBAAECgYBnmxAWwVvbj4bmDKQsB1r8BFWWuXXOTdlOdtaseeTN6OH74wQiID1nZQBKAj0Gav0Yfh36ZmOqdSedSBe+IXwBJSRhL8LjdUOYEv5v02g+ShttSTMVzeT/OW478ir7KAcWdPBmNJNlYux5y5OCpgonj0wLhv3/GysZGmRBsGJNMQJBAPjXPsE9Cr19iCH1QKKuPH1d/KQQtFSOABcAcJZ6nArf1MTcc+VIsRjqNvkOamgkzMma4v/bAzDfc3f6pb4/5D8CQQDs/hyCQBWbwvswu+9/f8P3+Jd7lbzg2mdsB2ODXz8zYsPLIRF02W0OfcBIwiKto2zdNdux7AhqYoYNB9gko2p5AkEAtt5nzdawJ+Uyv9HeOC9XYMJLQb7M5z6brkuyccOVHSC02h8wRJWRIEAvOgRwCizRGm9q1p/6zlXII6ndV9zYPQJBAK07fllTQLhPyU8xQPrAyN2csBYdOShfXVPg/sPLvqXwHtB/hoQUXpxGHWTRy4mDORNlyAaBUxF4nSYvQrZdYXkCQQDKFesUanGqs9moMsKwvyF6qv5IOBr3oVTa0ghcnC/mob7Lq6CYLNbrmVl4u9GStMOpZb+8OAAxDhi7wpwDQ032";  
         map.put("publicKey", publicKey);
         map.put("privateKey", privateKey);
        return map;
    }
 
    /**
     * 从字符串中加载公钥
     * @param publicKeyStr  公钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {  
        try {  
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(publicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
 
    /**
     * 从字符串中加载私钥
     * @param privateKeyStr     私钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {  
        try {  
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
 
    /**
     * 公钥加密过程
     * @param publicKey      公钥
     * @param plainTextData  明文数据
     * @return
     * @throws Exception     加密过程中的异常信息
     */ 
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData)throws Exception {  
        if (publicKey == null) {  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return base64ToStr(output);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
 
    /**
     * 私钥加密过程
     *  
     * @param privateKey       私钥
     * @param plainTextData    明文数据
     * @return
     * @throws Exception       加密过程中的异常信息
     */ 
    public static String encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {  
        if (privateKey == null) {  
            throw new Exception("加密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return base64ToStr(output);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
 
    /**
     * 私钥解密过程
     *  
     * @param privateKey   私钥
     * @param cipherData   密文数据
     * @return                明文
     * @throws Exception   解密过程中的异常信息
     */ 
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {  
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return new String(output);
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏", e);  
        }  
    }  
 
    /**
     * 公钥解密过程
     * @param publicKey     公钥
     * @param cipherData    密文数据
     * @return              明文
     * @throws Exception    解密过程中的异常信息
     */ 
    public static String decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {  
        if (publicKey == null) {  
            throw new Exception("解密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return new String(output);
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
    }
 
    public static String base64ToStr(byte[] b) throws Exception{
        return Base64Utils.encode(b);
    }
 
    public static byte[] strToBase64(String str) throws Exception{
        return Base64Utils.decode(str);
    }
    
    public static void main(String[] args) throws Exception {
    	HashMap<String, String> map = RSAUtils.getKeys();
    	RSAPrivateKey privateKey = RSAUtils.loadPrivateKey(map.get("privateKey"));
    	//使用私钥解密传输过来的密码
    	String userUuid     = "h5aHCpSI6FF1aU9SYuRLiY14iLV1+ZBi7pg2xuorP4ZqzbJSYLXPk2BchnxQIYz/l6MLx7l/uDOGW92fT8yGjGWc3ajkAoMgcVrsizT0GVeRdOZxMjQ29YL+cbkyVQuCLKSjD/mza9YSZQGA3nsPMZwfxBS/B7yX8q1pVmKYSk4=";
//    	String oldPassword  = RSAUtils.decrypt(privateKey, RSAUtils.strToBase64(request.getParameter("oldPassword")));
//    	String newPassword  = RSAUtils.decrypt(privateKey, RSAUtils.strToBase64(request.getParameter("newPassword")));
    	String pars = RSAUtils.decrypt(privateKey, RSAUtils.strToBase64(userUuid));
    	System.out.println(pars);
    	
	}
}

