/**
 * 
 */
package com.insigma.afc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.insigma.afc.entity.AFCResource;
import com.insigma.afc.entity.TsyResource;
import com.insigma.commons.codec.md5.MD5Util;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.io.FileUtil;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.Service;
import com.insigma.commons.ui.ResourceUtil;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class TsyResourceService extends Service implements ITsyResourceService {
	public final static String MD5_FILENAME = ".md5.list";
	public final static String MD5_FILENAME_TEMP = ".md5.list.temp";

	/**
	 * 同步所有资源文件到指定目录
	 */
	@Override
	public void syncResouce(String path) {
		List<TsyResource> needLoadList = getResourceList();

		for (TsyResource tsyResource : needLoadList) {
			String fileName = path + "/" + tsyResource.getName() + "." + tsyResource.getMd5();

			try {
				byte[] bytes = tsyResource.getContents();
				File localFile = new File(fileName);
				FileUtils.writeByteArrayToFile(localFile, bytes);
				logger.debug("更新文件文件：" + tsyResource + "--->到本地：" + localFile + "成功");
			} catch (Exception e) {
				logger.error("更新资源文件失败：" + tsyResource, e);
			}
		}
	}

	/**
	 * 同步所有资源文件到本地
	 */
	@Override
	public void syncResouce() {
		System.out.println("---- start to sync resources------");
		String rootRes = ResourceUtil.ROOT_RESOURCE_PATH;
		File rootFilePath = new File(rootRes);

		if (rootFilePath.exists()) {
			FileUtil.deleteFiles(rootFilePath);
		}
		rootFilePath.mkdirs();

		logger.debug("root resource path:" + rootRes);
		List<AFCResource> needLoadList = new ArrayList<AFCResource>();
		List<String> allResourceNameSpace = getAllResourceNameSpace();
		for (String ns : allResourceNameSpace) {
			needLoadList.addAll(syncPath(rootFilePath, ns));
		}
		//load different resouce
		for (AFCResource afcResource : needLoadList) {
			String path = rootFilePath + "/" + afcResource.getNameSpace();
			String fileName = afcResource.getName();

			try {
				TsyResource resource = loadResourceContent(afcResource);
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
	}

	private List<AFCResource> syncPath(File path, String nameSpace) {
		List<AFCResource> needLoadList = new ArrayList<AFCResource>();//需要下载
		String currentPath = path.getPath() + "/" + nameSpace;

		File md5file = new File(currentPath, MD5_FILENAME);

		if (!md5file.getParentFile().exists()) {
			md5file.getParentFile().mkdirs();
		}

		//get Locale md5 list
		List<AFCResource> dbResourceList = getResourceList(nameSpace);
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
			List<AFCResource> dbList = dbResourceList;
			for (AFCResource afcResource : dbList) {
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

	/**
	 * 
	 * @return
	 */
	public List<String> getAllResourceNameSpace() {
		String hql = "select nameSpace from TsyResource group by nameSpace order by nameSpace";
		try {
			return this.commonDAO.getEntityListHQL(hql);
		} catch (OPException e) {
			logger.error("查询所有的nameSpace异常", e);
			throw new ApplicationException("查询所有的nameSpace异常", e);
		}
	}

	public List<TsyResource> getResourceList() {
		List<Object[]> list = null;
		List<TsyResource> reslutList = new ArrayList<TsyResource>();
		String hql = "select name, md5, contents from TsyResource ";
		try {
			list = this.commonDAO.getEntityListHQL(hql);
			for (Object[] objects : list) {
				TsyResource item = convertObjectToTsyResource(objects);
				reslutList.add(item);
			}
			return reslutList;
		} catch (OPException e) {
			logger.error("查询所有的资源列表（不包含资源内容）异常", e);
			throw new ApplicationException("查询所有的资源列表（不包含资源内容）异常", e);
		}
	}

	private TsyResource convertObjectToTsyResource(Object[] obj) {
		TsyResource itemTemp = new TsyResource();
		itemTemp.setName(obj[0].toString());
		itemTemp.setMd5(obj[1].toString());
		itemTemp.setContents((byte[]) obj[2]);
		return itemTemp;
	}

	public List<AFCResource> getResourceList(String nameSpace) {
		String hql = "select new com.insigma.afc.entity.AFCResource(name, nameSpace, md5, remark) from TsyResource where nameSpace=?";
		try {
			return this.commonDAO.getEntityListHQL(hql, nameSpace);
		} catch (OPException e) {
			logger.error("根据nameSpace查询所有的资源列表（不包含资源内容）异常", e);
			throw new ApplicationException("根据nameSpace查询所有的资源列表（不包含资源内容）异常", e);
		}
	}

	public TsyResource loadResourceContent(AFCResource resource) throws FileNotFoundException {
		String hql = "from TsyResource where nameSpace=? and name=?";
		try {
			List<TsyResource> list = this.commonDAO.getEntityListHQL(hql, resource.getNameSpace(), resource.getName());
			if (list.isEmpty()) {
				throw new FileNotFoundException("不存在资源文件：" + resource);
			}
			TsyResource tsyResource = list.get(0);
			return tsyResource;
		} catch (OPException e) {
			logger.error("根据resource定义加载资源内容异常", e);
			throw new ApplicationException("根据resource定义加载资源内容异常", e);
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
		this.commonDAO.saveOrUpdateObj(resource);
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.service.ITsyResourceService#syncResouce(java.util.List, java.lang.String)
	 */
	@Override
	public void syncResouce(List<TsyResource> resources, String path) {

		for (TsyResource tsyResource : resources) {
			String fileName = path + "/" + tsyResource.getName() + ".png";

			try {
				byte[] bytes = tsyResource.getContents();
				File localFile = new File(fileName);
				FileUtils.writeByteArrayToFile(localFile, bytes);
				logger.debug("更新文件文件：" + tsyResource + "--->到本地：" + localFile + "成功");
			} catch (Exception e) {
				logger.error("更新资源文件失败：" + tsyResource, e);
			}
		}
	}

	/*	public static void main(String[] args) {
			Properties properties = System.getProperties();
			for (Object string : properties.keySet()) {
				System.out.println(string);
			}
			System.out.println("-------" + properties.get("user.dir"));
	
			URL rootPath = TsyResourceService.class.getResource("/images/Bluehills.jpg");
			File file = new File(URLDecoder.decode(rootPath.getFile()));
			System.out.println(file.exists());
			System.out.println(new File("./images/Bluehills.jpg").exists());
		}*/
}
