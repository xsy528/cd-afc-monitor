/**
 * 
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TsyResourceDao;
import com.insigma.afc.monitor.model.entity.AFCResource;
import com.insigma.afc.monitor.model.entity.TsyResource;
import com.insigma.afc.monitor.service.ITsyResourceService;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.util.MD5Util;
import com.insigma.commons.util.ResourceUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
@Service
public class TsyResourceServiceImpl implements ITsyResourceService {

	private static final Logger logger = LoggerFactory.getLogger(TsyResourceServiceImpl.class);

	public final static String MD5_FILENAME = ".md5.list";
	public final static String MD5_FILENAME_TEMP = ".md5.list.temp";

	private TsyResourceDao resourceDao;

	@Autowired
	public TsyResourceServiceImpl(TsyResourceDao resourceDao){
		this.resourceDao = resourceDao;
	}

	/**
	 * 同步所有资源文件到本地
	 */
	@Override
	public List<TsyResource> syncResouce() {
		List<TsyResource> resources = new ArrayList<>();
		System.out.println("---- start to sync resources------");
		String rootRes = ResourceUtils.ROOT_RESOURCE_PATH;
		File rootFilePath = new File(rootRes);

		if (!rootFilePath.exists()) {
			rootFilePath.mkdirs();
		}

		logger.debug("root resource path:" + rootRes);
		List<AFCResource> needLoadList = new ArrayList<>();
		List<String> allResourceNameSpace = resourceDao.selectAllNamespace();
		for (String ns : allResourceNameSpace) {
			needLoadList = syncPath(rootFilePath, ns);
		}
		//load different resouce
		for (AFCResource afcResource : needLoadList) {
			String path = rootFilePath + "/" + afcResource.getNameSpace();
			String fileName = afcResource.getName();

			try {
				TsyResource resource = resourceDao.findFirstByNameSpaceAndName(afcResource.getNameSpace(),
						afcResource.getName()).orElseThrow(()->new FileNotFoundException());
				resources.add(resource);
				byte[] bytes = resource.getContents();
				File localFile = new File(path, fileName);
				FileUtils.writeByteArrayToFile(localFile, bytes);
				logger.debug("更新文件文件：" + afcResource + "--->到本地：" + localFile + "成功");
			} catch (Exception e) {
				logger.error("更新资源文件失败：" + afcResource, e);
			}
		}
		//update locale md5 list
		updateMd5List(rootFilePath);
		return resources;
	}

	private List<AFCResource> syncPath(File path, String nameSpace) {
		//需要下载
		List<AFCResource> needLoadList = new ArrayList<>();
		String currentPath = path.getPath() + "/" + nameSpace;

		File md5file = new File(currentPath, MD5_FILENAME);

		if (!md5file.getParentFile().exists()) {
			md5file.getParentFile().mkdirs();
		}

		//get Locale md5 list
		if (!md5file.exists()) {
			try {
				boolean ok = md5file.createNewFile();
				if (!ok) {
					throw new ApplicationException("创建md5列表文件失败：" + md5file);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//find
		FileReader reader = null;
		File md5fileTemp = new File(currentPath, MD5_FILENAME_TEMP);
		FileWriter writer = null;
		try {
			writer = new FileWriter(md5fileTemp);
			reader = new FileReader(md5file);
			Properties prop = new Properties();
			prop.load(reader);
			Properties propTemp = new Properties();

			//compare md5 list
			List<AFCResource> dbResourceList = resourceDao.findByNameSpace(nameSpace);
			for (AFCResource afcResource : dbResourceList) {
				String name = afcResource.getName();
				String md5 = afcResource.getMd5();
				propTemp.put(name, md5);//先更新temp

				Object value = prop.get(name);
				if (value == null) {//数据库有，本地没有，load文件
					needLoadList.add(afcResource);
				} else if (!value.equals(md5)) {//md5不相等
					needLoadList.add(afcResource);
				} else {
					if (!new File(currentPath, name).exists()) {//md5相等，但本地文件不存在
						needLoadList.add(afcResource);
					}
				}
			}
			propTemp.store(writer, null);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return needLoadList;
	}

	private void updateMd5List(File path) {
		File[] listFiles = path.listFiles();
		for (File file : listFiles) {
			if (file.isFile()) {
				if (file.getName().equals(MD5_FILENAME)) {
					FileUtils.deleteQuietly(file);
				} else if (file.getName().equals(MD5_FILENAME_TEMP)) {
					File file2 = new File(file.getParent(), MD5_FILENAME);
					FileUtils.deleteQuietly(file2);
					file.renameTo(file2);
				}
			} else {
				updateMd5List(file);
			}
		}
	}

	@Override
	public void save(TsyResource resource) {
		if (resource == null) {
			return;
		}
		if (resource.getMd5() == null && resource.getContents() != null) {
			resource.setMd5(MD5Util.MD5(resource.getContents()));
		}
		resourceDao.save(resource);
	}

	@Override
	public List<AFCResource> getAFCResourceList() {
		return resourceDao.findAllAFCResource();
	}

}
