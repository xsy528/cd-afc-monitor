package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.Result;

import java.util.List;

/**
 * 文件服务
 * author:xuzhemin
 */
public interface FileService {

    /**
     * 获取所有图片的集合
     * @return 响应数据
     */
    Result<String[]> getResourceList();

    /**
     * 获取图片索引
     * @param resourcePath 资源文件名称
     * @return 索引
     */
    Integer getResourceIndex(String resourcePath);

    /**
     * 获取图片索引数组
     * @param resourcePaths 资源文件名称数组
     * @return 索引数组
     */
    List<Integer> getResourcesIndexs(List<String> resourcePaths);

    /**
     * 文件数据
     * @param filename 文件名
     * @return 文件字节数组
     */
    byte[] getFileBytes(String filename);

    /**
     * 同步数据库资源
     */
    void synResources();
}
