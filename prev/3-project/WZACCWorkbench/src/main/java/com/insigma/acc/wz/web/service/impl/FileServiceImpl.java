package com.insigma.acc.wz.web.service.impl;

import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.FileService;
import com.insigma.acc.wz.web.util.ResourceUtils;
import com.insigma.afc.service.ITsyResourceService;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.CommonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private ITsyResourceService resourceService;

    @Autowired
    private CommonDAO commonDAO;

    //资源名称-序号
    private static Map<String,Integer> resourceMap = new HashMap<>();

    static{
        resourceMap.put("defaultPIC.png",0);
        //获取本地资源图片
        ResourceUtils.getImages().forEach(file ->putResource(file.getName()));
    }

    @Override
    public void synResources() {
        getResourceFromDB();
    }
    private void getResourceFromDB(){
        //同步数据库资源到本地
        resourceService.syncResouce();
        try {
            List<Map<String,String>> records = commonDAO.execSqlQueryList("SELECT NAME_SPACE,NAME FROM TSY_RESOURCE");
            records.forEach(map-> {
                String path = map.get("NAME_SPACE") + "/" + map.get("NAME");
                LOGGER.info("数据库资源路径:"+path);
                putResource(path);
            });
        } catch (OPException e) {
            LOGGER.error("同步数据库资源到本地失败",e);
        }
    }

    @Override
    public byte[] getFileBytes(String filename){
        File file = ResourceUtils.getResource(filename);
        try {
            if (file!=null){
                return Files.readAllBytes(file.toPath());
            }
        } catch (IOException e) {
            return new byte[0];
        }
        return new byte[0];
    }

    @Override
    public Integer getResourceIndex(String resourcePath){
        Integer index = resourceMap.get(resourcePath);
        if (index==null) {
            index = resourceMap.get(resourcePath.substring(resourcePath.lastIndexOf("/") + 1));
            if (index==null){
                index=0;
            }
        }
        return index;
    }

    @Override
    public Result<String[]> getResourceList(){
        String[] resourceList = new String[resourceMap.size()];
        resourceMap.forEach((k,v)->resourceList[v]=k);
        return Result.success(resourceList);
    }

    private static void putResource(String path){
        if (!resourceMap.containsKey(path)){
            resourceMap.put(path,resourceMap.size());
        }
    }

    public void setResourceService(ITsyResourceService iTsyResourceService){
        this.resourceService = iTsyResourceService;
    }

    public void setCommonDAO(CommonDAO commonDAO){
        this.commonDAO = commonDAO;
    }
}
