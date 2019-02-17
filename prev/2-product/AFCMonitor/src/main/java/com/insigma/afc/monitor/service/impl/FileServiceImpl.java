package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.model.entity.AFCResource;
import com.insigma.afc.monitor.service.FileService;
import com.insigma.afc.monitor.service.ITsyResourceService;
import com.insigma.commons.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ticket: 文件服务实现类
 * @author xuzhemin
 * date:
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    private ITsyResourceService resourceService;

    @Autowired
    public FileServiceImpl(ITsyResourceService resourceService){
        this.resourceService = resourceService;
    }

    /**
     * 资源名称-序号
     */
    private static Map<String,Integer> resourceMap = new HashMap<>();

    @PostConstruct
    public void init(){
        resourceMap.put("defaultPIC.png",0);
        //获取本地资源图片
        ResourceUtils.getImages().forEach(file ->putResource(file.getName()));
        //同步数据库资源
        synResources();
    }

    @Override
    public void synResources() {
        getResourceFromDB();
    }

    private void getResourceFromDB(){
        //同步数据库资源到本地
        resourceService.syncResouce();
        List<AFCResource> resources = resourceService.getAFCResourceList();
        resources.forEach(resource-> {
            String path = resource.getNameSpace() + "/"+resource.getName();
            LOGGER.info("数据库资源路径:"+path);
            putResource(path);
        });
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
    public List<Integer> getResourcesIndexs(List<String> resourcePaths) {
        List<Integer> indexs = new ArrayList<>();
        for (String resourcePath:resourcePaths){
            indexs.add(getResourceIndex(resourcePath));
        }
        return indexs;
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

}
