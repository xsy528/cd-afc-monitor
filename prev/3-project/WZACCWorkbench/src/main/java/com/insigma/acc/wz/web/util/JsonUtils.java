package com.insigma.acc.wz.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ticket: JSON处理工具
 *
 * @author xuzhemin
 * 2018-12-24:16:26
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将数据对象转化成json字符串
     * @param o 需要转成json的对象
     * @return json字符串
     */
    public static String parseObject(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
