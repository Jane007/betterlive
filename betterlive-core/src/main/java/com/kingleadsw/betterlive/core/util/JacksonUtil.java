//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kingleadsw.betterlive.core.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeBindings;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class JacksonUtil {
    private static Logger log = Logger.getLogger(JacksonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public JacksonUtil() {
    }

    public static String serializeObjectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception var2) {
            log.error("serialize object to json", var2);
            return null;
        }
    }

    public static String serializeObjectToJson(Object obj, boolean indent) {
        try {
            ObjectMapper e = new ObjectMapper();
            e.enable(new Feature[] { Feature.INDENT_OUTPUT });
            return e.writeValueAsString(obj);
        } catch (Exception var3) {
            log.error("serialize object to json", var3);
            return null;
        }
    }

    public static void serializeObjectToFile(Object obj, File file, boolean indent) {
        try {
            ObjectMapper e = new ObjectMapper();
            if (indent) {
                e.enable(new Feature[] { Feature.INDENT_OUTPUT });
            }

            e.writeValue(file, obj);
        } catch (Exception var4) {
            log.error("serialize object to json", var4);
        }

    }

    public static <T> T deserializeFormFile(File file, Class<T> clazz, boolean indent) {
        try {
            ObjectMapper e = new ObjectMapper();
            if (indent) {
                e.enable(new Feature[] { Feature.INDENT_OUTPUT });
            }

            return e.readValue(file, clazz);
        } catch (Exception var4) {
            log.error("deserializeFormFile", var4);
            return null;
        }
    }

    public static <T> T deserializeJsonToObject(String json, TypeReference<T> typeReference) {
        try {
            return (T)mapper.readValue(json, typeReference);
        } catch (Exception var3) {
            log.error("deserialize json to object", var3);
            return null;
        }
    }

    public static <T> T deserializeJsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception var3) {
            log.error("deserialize json to object", var3);
            return null;
        }
    }

    public static Object deserializeJsonToObject(String json, JavaType jt) {
        try {
            return mapper.readValue(json, jt);
        } catch (Exception var3) {
            log.error("deserialize json to object", var3);
            return null;
        }
    }

    public static <T> JavaType getListJavaType(Class<T> clazz) {
        TypeFactory instance = TypeFactory.defaultInstance();
        JavaType[] pt = new JavaType[] { instance._constructType(clazz, (TypeBindings) null) };
        JavaType subtype = instance.constructSimpleType(List.class, pt);
        JavaType[] collectionParams = instance.findTypeParameters(subtype, Collection.class);
        if (collectionParams.length != 1) {
            throw new IllegalArgumentException("Could not find 1 type parameter for Collection class list (found "
                                               + collectionParams.length + ")");
        } else {
            CollectionType jt = CollectionType.construct(List.class, collectionParams[0]);
            return jt;
        }
    }

    public static <T> List<T> deserializeJsonToList(String json, Class<T> clazz) {
        JavaType jt = getListJavaType(clazz);

        try {
            return (List) mapper.readValue(json, jt);
        } catch (Exception var4) {
            log.error("deserialize json to object", var4);
            return null;
        }
    }

    public static <K, V> JavaType getMapJavaType(Class<K> clazzKey, Class<V> clazzValue) {
        TypeFactory instance = TypeFactory.defaultInstance();
        JavaType[] pt = new JavaType[] { instance._constructType(clazzKey, (TypeBindings) null),
                instance._constructType(clazzValue, (TypeBindings) null) };
        JavaType subtype = instance.constructSimpleType(Map.class, pt);
        JavaType[] mapParams = instance.findTypeParameters(subtype, Map.class);
        if (mapParams.length != 2) {
            throw new IllegalArgumentException("Could not find 2 type parameter for Map class map (found "
                                               + mapParams.length + ")");
        } else {
            MapType jt = MapType.construct(Map.class, mapParams[0], mapParams[1]);
            return jt;
        }
    }

    public static <K, V> Map<K, V> deserializeJsonToMap(String json, Class<K> clazzKey, Class<V> clazzValue) {
        JavaType jt = getMapJavaType(clazzKey, clazzValue);

        try {
            return (Map) mapper.readValue(json, jt);
        } catch (Exception var5) {
            log.error("deserialize json to object", var5);
            return null;
        }
    }

    public static <K, V> List<Map<K, V>> deserializeJsonToListMap(String json, Class<K> clazzKey, Class<V> clazzValue) {
        JavaType tmp = getMapJavaType(clazzKey, clazzValue);
        TypeFactory instance = TypeFactory.defaultInstance();
        JavaType[] pt = new JavaType[] { tmp };
        JavaType subtype = instance.constructSimpleType(List.class, pt);
        JavaType[] collectionParams = instance.findTypeParameters(subtype, Collection.class);
        if (collectionParams.length != 1) {
            throw new IllegalArgumentException("Could not find 1 type parameter for Collection class list (found "
                                               + collectionParams.length + ")");
        } else {
            CollectionType jt = CollectionType.construct(List.class, collectionParams[0]);

            try {
                return (List) mapper.readValue(json, jt);
            } catch (Exception var10) {
                log.error("deserialize json to object", var10);
                return null;
            }
        }
    }
}
