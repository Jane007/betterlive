package com.kingleadsw.betterlive.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * redis序列化&反序列对象工具类
 * @author tigerhoo
 *
 */
public class SerializeUtil {
	
	private static Logger logger = Logger.getLogger(SerializeUtil.class);
	
	/**
	 * 序列化对象为byte数组
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		if (object == null)
		{
			throw new NullPointerException("Can't serialize null");
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
	    	logger.error("SerializeUtil/serialize --error", e);
		}
		return null;
	}
	/**
	 * 反序列化byte数组为对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error("SerializeUtil/unserialize --error", e);
		}
		return null;
	}
	/**
	 * 序列化对象list为byte数组
	 * @param list
	 * @return
	 */
	public static <T> byte[] serializeList(List<T> list) {
		if (list == null)
		{
			throw new NullPointerException("Can't serialize null");
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			for (T t : list) {
				oos.writeObject(t);
			}
			//这一步添加null是为了获取时好及时结束,避免java.io.EOFException异常
			oos.writeObject(null);
			byte[] bytes = baos.toByteArray();
			oos.close();
			baos.close();
			return bytes;
			
		} catch (Exception e) {
			logger.error("SerializeUtil/serializeList --error", e);
		}
		
		return null;
	}
	/**
	 * 反序列化byte数组为对象list
	 * @param <T>
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> unserializeList(byte[] bytes) {
		ByteArrayInputStream bais = null;
		List<T> list = new ArrayList<T>();
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			while (true) {
	           Object obj;
	           if((obj=ois.readObject())==null)
	           {
	        	  break;
	           }
	          list.add((T)obj);
	        }
			return list;
		} catch (Exception e) {
			logger.error("SerializeUtil/unserializeList --error", e);
		}
		return null;
	}
}
