package com.insigma.acc.wz.web.util;

import com.insigma.commons.util.SystemPropertyUtil;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2018-12-24:11:10
 */
public class ResourceUtils {

    private ResourceUtils(){}

    private static final String localResourceDir = "com/insigma/afc/ui/monitor";

    private static final String ROOT_RESOURCE = "root.resource";

    private static final String ROOT_RESOURCE_PATH = System.getProperty("user.dir") + "/"
            + SystemPropertyUtil.getProperty(ROOT_RESOURCE, "conf/resource");

    private static final Map<String,String> filenameMap = new HashMap<>();

    public static File getLocalResourceDir(){
        URL url = Thread.currentThread().getContextClassLoader().getResource(localResourceDir);
        if (url!=null){
            File localResourceDir = new File(url.getFile());
            if (!localResourceDir.exists()){
                if(!localResourceDir.mkdirs()){
                    throw new RuntimeException("创建本地资源路径失败");
                }
            }
            return localResourceDir;
        }
        return null;
    }

    public static File getResource(String filename){
        File target;
        String path = filenameMap.get(filename);
        if (path!=null) {
            target = new File(path);
            if (target.exists()) {
                return target;
            }
        }
        //先在本地资源文件查找
        target = findFile(filename,getLocalResourceDir());
        if (target!=null){
            filenameMap.put(filename,target.getPath());
            return target;
        }
        //数据库同步的资源查找
        target = new File(ROOT_RESOURCE_PATH + "/" + filename);
        if (target.exists()){
            return target;
        }
        return  null;
    }

    private static File findFile(String filename,File source){
        File target;
        if (source.isDirectory()){
            for (File subFile:source.listFiles()){
                target = findFile(filename,subFile);
                if (target!=null){
                    return target;
                }
            }
        }
        if (source.exists()&&source.getName().equals(filename)){
            return source;
        }
        return null;
    }
}
