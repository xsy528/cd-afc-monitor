package com.insigma.acc.monitor.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DataBindingException;
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

    private HttpUtils(){

    }

    public static JsonNode getBody(HttpServletRequest request){
        JsonNode jsonNode;
        try(InputStream inputStream = request.getInputStream()){
            jsonNode = JsonUtils.readObject(inputStream);
            return jsonNode;
        }catch (IOException e){
            LOGGER.error("从请求获取jsonbody异常",e);
            throw new DataBindingException(e);
        }
    }
}