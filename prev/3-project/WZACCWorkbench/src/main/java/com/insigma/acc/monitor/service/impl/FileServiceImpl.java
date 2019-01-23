package com.insigma.acc.monitor.service.impl;

import com.insigma.acc.monitor.exception.ErrorCode;
import com.insigma.acc.monitor.model.dto.Result;
import com.insigma.acc.monitor.service.FileService;
import com.insigma.commons.util.ResourceUtils;
import com.insigma.afc.entity.TsyResource;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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

    @Override
    public Result<String> saveTmpFile(byte[] data, String name) {
        Path path = Paths.get("tmp");
        File file = path.toFile();
        if (!file.exists()){
            file.mkdirs();
        }
        path = path.resolve(name);
        try {
            Files.write(path,data, StandardOpenOption.WRITE,StandardOpenOption.CREATE);
            return Result.success(path.toString());
        } catch (IOException e) {
            LOGGER.error("保存临时文件失败",e);
        }
        return Result.error(ErrorCode.SAVE_FILE_FAILED);
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

    //@Override
    public Result<String> uploadFile(){
        TsyResource tsyResource = new TsyResource();
        resourceService.save(tsyResource);
        return Result.success(tsyResource.getName());
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
