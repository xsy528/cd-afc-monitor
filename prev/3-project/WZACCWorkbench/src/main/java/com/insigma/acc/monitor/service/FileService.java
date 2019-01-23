package com.insigma.acc.monitor.service;

import com.insigma.acc.monitor.model.dto.Result;

import java.util.List;

/**
 * 文件服务
 * author:徐哲民
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
     * @param resourcePath 资源文件名称数组
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

    /**
     * 保存临时文件到本地
     * @param data
     * @param name
     * @return 保存结果，包含文件路径
     */
    Result<String> saveTmpFile(byte[] data, String name) ;
}
