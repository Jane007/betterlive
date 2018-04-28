package com.kingleadsw.betterlive.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	private static Logger log = Logger.getLogger(JsonUtil.class);
	
	private static ObjectMapper mapper;

	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		ObjectMapper objMapper = getMapperInstance(false);
		try {
			return objMapper.writeValueAsString(object);
		} catch ( JsonMappingException e) {
			log.info("JSON exception", e);
		} catch(JsonGenerationException e){
			
		}catch (IOException e) {
			log.info("JSON ioexception", e);
		} 
		return null;
	}
	
	/**
	 * 将忽略对象中的 default 字段
	 * @param object
	 * @return
	 */
	public static String toJsonStringNonDefault(Object object){
		ObjectMapper objMapper = getMapperInstance(false);
		objMapper.setSerializationInclusion(Inclusion.NON_DEFAULT);
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonGenerationException | JsonMappingException e) {
			log.info("JSON exception", e);
		} catch (IOException e) {
			log.info("JSON ioexception", e);
		} 
		return null;
	}
	
	public static String toJsonStringNonEmpty(Object object){
		ObjectMapper objMapper = getMapperInstance(false);
		objMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonGenerationException | JsonMappingException e) {
			log.info("JSON exception", e);
		} catch (IOException e) {
			log.info("JSON ioexception", e);
		} 
		return null;
	}
	
	/**
	 * 忽略null元素
	 * @param object
	 * @return
	 */
	public static String toJsonStringIgnoreNull(Object object){
		ObjectMapper objMapper = getMapperInstance(false);
		objMapper.setSerializationInclusion(Inclusion.NON_NULL);
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonGenerationException | JsonMappingException e) {
			log.info("JSON exception", e);
		} catch (IOException e) {
			log.info("JSON ioexception", e);
		} 
		return null;
	}
	
	/**
	 * 
	 * @param jsonNode
	 * @param i jsonNode为null时，返回该值
	 * @return
	 */
	public static final int getJsonNodeIntValue(JsonNode jsonNode, int i) {
		if (jsonNode == null) {
			return i;
		} else {
			return jsonNode.getIntValue();
		}
	}
	
	public static final long getJsonNodeLongValue(JsonNode jsonNode, long l) {
		if (jsonNode == null) {
			return l;
		} else {
			return jsonNode.getLongValue();
		}
	}
	
	public static final String getJsonNodeTextValue(JsonNode jsonNode) {
		if (jsonNode == null) {
			return "";
		} else {
			return jsonNode.getTextValue();
		}
	}
	
	
	/**
	 * 
	 * 描述　普通的javaBean转化为json
	 * 
	 * @param obj  javaBean
	 * @return 转化为json
	 * @throws IOException
	 */
	public static String objToJson(Object obj) throws IOException {
		ObjectMapper objMapper = getMapperInstance(false);
		StringWriter writer = new StringWriter();
		String json = null;
		JsonGenerator gen = null;
		try {
			gen = objMapper.getJsonFactory().createJsonGenerator(writer);
			gen.writeObject(obj);
			json = writer.toString();
		} catch (Exception e) {
			log.error("JsonUtil/objToJson --error", e);
		} finally {
			if (gen != null && !gen.isClosed()) {
				gen.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
		
		return json;
	}

	/**
	 * 
	 * 描述 json转化为javaBean
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToBean(String json, Class<?> cls) throws Exception {
		Object vo = mapper.readValue(json, cls);
		return vo;
	}

	/**
	 * 从json中读取tagPath处的值 tagPath用 :分隔
	 * 
	 * @param json
	 * @param tagPath
	 * @return
	 * @throws Exception
	 */
	public static List<String> readValueFromJson(String json, String tagPath)
			throws Exception {
		// 返回值
		List<String> value = new ArrayList<String>();
		if (JsonUtil.isEmpty(json) || (JsonUtil.isEmpty(tagPath))) {
			return value;
		}
		ObjectMapper mapper = getMapperInstance(false);
		String[] path = tagPath.split(":");
		JsonNode node = mapper.readTree(json);
		getJsonValue(node, path, value, 1);
		return value;
	}

	public static void getJsonValue(JsonNode node, String[] path,
			List<String> values, int nextIndex) {
		if (JsonUtil.isEmpty(node)) {
			return;
		}
		// 是路径的最后就直接取值
		if (nextIndex == path.length) {
			if (node.isArray()) {
				for (int i = 0; i < node.size(); i++) {
					JsonNode child = node.get(i).get(path[nextIndex - 1]);
					if (JsonUtil.isEmpty(child)) {
						continue;
					}
					values.add(child.toString());
				}
			} else {
				JsonNode child = node.get(path[nextIndex - 1]);
				if (!JsonUtil.isEmpty(child)) {
					values.add(child.toString());
				}
			}
			return;
		}
		// 判断是Node下是集合还是一个节点
		node = node.get(path[nextIndex - 1]);
		if (node.isArray()) {
			for (int i = 0; i < node.size(); i++) {
				getJsonValue(node.get(i), path, values, nextIndex + 1);
			}
		} else {
			getJsonValue(node, path, values, nextIndex + 1);
		}
	}

	/**
	 * 使用find的方法从实体中取出所有匹配的值
	 * 
	 * @param vo
	 * @param path
	 * @return
	 */
	public static List<String> getValueByFind(JsonNode node, String path)
			throws Exception {
		List<String> values = new ArrayList<String>();
		/*
		 * values = node.findValuesAsText(path); 这里提供两种方法 一种是只填写path
		 * 它会返回List<String>，另外一种就是下面用的 它会直接把找到的value填到你传入的集合中
		 */
		node.findValuesAsText(path, values);
		return values;
	}
	/** 
	 * 查找当前Node中第一个匹配的值 
	 *  
	 * @param node 
	 * @param path 
	 * @return 
	 * @throws Exception 
	 */  
	public static int getFirstValueByFind(JsonNode node, String path) throws Exception {  
	    /* 
	     * 注意这点不能使用getTextValue()方法，因为找到的值为Int类型的所以使用getTextValue是查不到值的。 
	     * 不过如果想返回String字符串可以使用asText()方法。这里使用asInt是为了看到其实JackSon是可以直接返回相应类型的值的。 
	     */  
	    int value = node.findValue(path).asInt();  
	    return value;  
	}  
	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		boolean result = true;
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			result = (obj.toString().trim().length() == 0)
					|| obj.toString().trim().equals("null");
		} else if (obj instanceof Collection) {
			result = ((Collection) obj).size() == 0;
		} else {
			result = ((obj == null) || (obj.toString().trim().length() < 1)) ? true
					: false;
		}
		return result;
	}
	/**
	 * 从一个JSON数组得到一个java对象集合
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static List getDTOList(String jsonString, Class clazz) {
		JSONArray array = null;
		try {

			array = JSONArray.fromObject(jsonString);
			List list = new ArrayList();
			for (Iterator iter = array.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				list.add(JSONObject.toBean(jsonObject, clazz));
			}
			return list;

		} catch (Exception e) {

			log.error("JsonUtil/getDTOList --error", e);
		}
		return null;

	}
}
