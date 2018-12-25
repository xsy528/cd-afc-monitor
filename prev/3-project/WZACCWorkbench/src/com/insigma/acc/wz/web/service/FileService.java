package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.web.model.vo.Result;

public interface FileService {

    Result<String[]> getResourceList();

    Integer getResourceIndex(String resourcePath);

    byte[] getFileBytes(String filename);
}
