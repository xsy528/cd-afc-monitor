/**
 * 
 */
package com.insigma.commons.ui;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.insigma.commons.log.Logs;
import com.insigma.commons.util.SystemPropertyUtil;
import com.swtdesigner.SWTResourceManager;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class ResourceUtil {

	public static final String ROOT_RESOURCE = "root.resource";

	public static final String ROOT_RESOURCE_PATH = System.getProperty("user.dir") + "/"
			+ SystemPropertyUtil.getProperty(ResourceUtil.ROOT_RESOURCE, "conf/resource");

	public static Image getImage(String imageName) {
		File file = new File(imageName);
		if (file.isAbsolute()) {
			return SWTResourceManager.getImage(imageName);
		}

		File rootFilePath = new File(ROOT_RESOURCE_PATH + "/" + imageName);
		if (rootFilePath.exists()) {
			return SWTResourceManager.getImage(ROOT_RESOURCE_PATH + "/" + imageName);
		} else {
			Logs.get().warn(rootFilePath + "资源文件不存在，取默认图片");
			return SWTResourceManager.getImage(ResourceUtil.class, "images/defaultPIC.png");
		}

	}
}
