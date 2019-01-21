package com.insigma.acc.monitor.util;

import com.insigma.commons.util.SystemPropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket: 资源工具类
 *
 * @author xuzhemin
 * 2018-12-24:11:10
 */
public class ResourceUtils {

    private ResourceUtils(){}

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);

    private static final String ROOT_RESOURCE = "root.resource";
    private static final Map<String,String> filenameMap = new HashMap<>();
    private static final String[] imageSuffixs = {"png","jpeg","jpg","gif"};
    public static final String ROOT_RESOURCE_PATH = System.getProperty("user.dir") + "/"
            + SystemPropertyUtil.getProperty(ROOT_RESOURCE, "conf/resource");
    public static final String IMAGE_RESOURCE_PATH = System.getProperty("user.dir") + "/"
            + SystemPropertyUtil.getProperty(ROOT_RESOURCE, "conf/images");

    public static List<File> getImages(){
        List<File> fileList = new ArrayList<>();
        getImages(new File(IMAGE_RESOURCE_PATH),fileList);
        return fileList;
    }
    private static void getImages(File file,List<File> fileList){
        if (file.isDirectory()){
            for(File subFile:file.listFiles()){
                getImages(subFile,fileList);
            }
        }else {
            String filename = file.getName();
            for (String suffix:imageSuffixs){
                if (filename.endsWith(suffix)) {
                    LOGGER.info("扫描到图片:" + file.getPath());
                    fileList.add(file);
                    break;
                }
            }
        }
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
        //数据库同步的资源查找
        target = new File(ROOT_RESOURCE_PATH + "/" + filename);
        if (target.exists()){
            return target;
        }
        //在本地资源文件夹查找
        target = findFile(filename,new File(IMAGE_RESOURCE_PATH));
        if (target!=null){
            filenameMap.put(filename,target.getPath());
            return target;
        }
        LOGGER.error("文件未找到:"+filename);
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
