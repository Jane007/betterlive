package com.kingleadsw.betterlive.util.wx;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
 

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlParser {
	
	private static Logger logger = Logger.getLogger(XmlParser.class);
	
	public synchronized static Object parser(String xmlData, Class cls)
			throws DocumentException {

		Document doc = DocumentHelper.parseText(xmlData);
		Element root = doc.getRootElement();

		Object o = null;
		try {
			o = getBean(root, cls);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			logger.error("XmlParser/parser --error", e);
		}
		return o;
	}

	public static Element getRootElement(String xmlData) {
		Document doc;
		Element root = null;
		try {
			doc = DocumentHelper.parseText(xmlData);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			logger.error("XmlParser/getRootElement --error", e);
		}
		return root;
	}

	private synchronized static Object getBean(Element root, Class cls)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Field[] fields = cls.getDeclaredFields();
		Object o = cls.newInstance();

		for (Field field : fields) {
			String name = field.getName();

			Element e = root.element(name);
			if (e != null) {
				String text = e.getTextTrim();
				String methodName = "set" + name.substring(0, 1).toUpperCase()
						+ name.replaceFirst("\\w", "");
				Method m = cls.getMethod(methodName, String.class);
				m.invoke(o, text);
			}
		}
		return o;
	}
}
