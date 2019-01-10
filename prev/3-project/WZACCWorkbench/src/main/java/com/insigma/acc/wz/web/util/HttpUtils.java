package com.insigma.acc.wz.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-04:17:18
 */
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    public static JsonNode getBody(HttpServletRequest request){
        JsonNode jsonNode = null;
        try(InputStream inputStream = request.getInputStream()){
            jsonNode = JsonUtils.readObject(inputStream);
        }catch (IOException e){
            LOGGER.error("从请求获取jsonbody异常",e);
        }
        return jsonNode;
    }
}
