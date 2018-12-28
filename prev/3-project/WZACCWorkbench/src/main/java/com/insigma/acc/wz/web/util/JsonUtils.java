package com.insigma.acc.wz.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Ticket: JSON处理工具
 *
 * @author xuzhemin
 * 2018-12-24:16:26
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String parseObject(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
