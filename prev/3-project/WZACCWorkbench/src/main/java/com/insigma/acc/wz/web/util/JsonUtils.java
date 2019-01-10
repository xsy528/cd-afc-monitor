package com.insigma.acc.wz.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Ticket: JSON处理工具
 *
 * @author xuzhemin
 * 2018-12-24:16:26
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 将数据对象转化成json字符串
     * @param o 需要转成json的对象
     * @return json字符串
     */
    public static String parseObject(Object o){
        if (o==null){
            return "";
        }
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOGGER.error("json序列化失败",e);
        }
        return null;
    }

    /**
     * 转化成对应的视图序列化对象
     * @param o 对象
     * @param view 视图
     * @return json字符串
     */
    public static String parseObject(Object o,Class view){
        if(o==null){
            return "";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false); //没有注解的不展示
            return objectMapper.writerWithView(view).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOGGER.error("json序列化失败",e);
        }
        return null;
    }

    //通过输入流生成json对象
    public static JsonNode readObject(InputStream inputStream) throws IOException {
        return new ObjectMapper().readTree(inputStream);
    }

    //通过输入流生成java对象
    public static <T> T readObject(InputStream inputStream,Class<T> type) throws IOException {
        return new ObjectMapper().reader(type).readValue(inputStream);
    }

}
