package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.web.model.vo.Result;

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
