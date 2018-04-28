package com.kingleadsw.betterlive.core.util;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * json时间反序列化
 * @author Administrator
 *
 */
public class CustomDateDeserializer extends JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext cxt)
			throws IOException, JsonProcessingException {
	       JsonNode node = jp.getCodec().readTree(jp); 
	       String schedualTimeStr=node.asText();
	       return DateUtil.stringToDateShort(schedualTimeStr);
	}

}
