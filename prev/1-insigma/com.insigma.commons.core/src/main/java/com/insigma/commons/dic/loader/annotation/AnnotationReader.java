/* 
 * 日期：2010-10-29
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader.annotation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AnnotationReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationReader.class);

	private static final List<String> INSIGMA_LOCATION = Arrays.asList("classpath*:com/insigma/afc",
			"classpath*:com/insigma/acc");

	private List<String> locations;

	public AnnotationReader() {
		this(INSIGMA_LOCATION);
	}

	public AnnotationReader(List<String> locations) {
		this.locations = locations;
		if (this.locations == null) {
			this.locations = INSIGMA_LOCATION;
		}
	}

	public List<Class<?>> findAnnotations(Class<?> annotationClass) {
		long s = System.currentTimeMillis();
		List<Class<?>> annotations = new ArrayList<>();

		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		for (String location:locations){
			Resource[] resources;
			try {
				resources = resourcePatternResolver.getResources(location);
			} catch (IOException e) {
				LOGGER.error("读取资源异常",e);
				continue;
			}
			for (Resource resource:resources){
				URL url;
				try {
					url = resource.getURL();
					if (ResourceUtils.isJarURL(url)){
						LOGGER.debug("开始扫描jar包{}",url);
						JarFile jarFile = new JarFile(url.getFile());
						Enumeration<JarEntry> enumeration = jarFile.entries();
						while (enumeration.hasMoreElements()){
							JarEntry jarEntry = enumeration.nextElement();
							getAnnotation(jarEntry.getName(),annotationClass,annotations);
						}
					}else{
						getAnnotation(new File(url.getFile()),annotationClass,annotations);
					}
				} catch (IOException e) {
					LOGGER.error("获取资源异常",e);
					continue;
				}
			}
		}
		LOGGER.info("扫描classpath中所有的@{}定义,耗时{}秒",annotationClass.getName(),
				(System.currentTimeMillis() - s) / 1000);
		return annotations;
	}

	private void getAnnotation(File file,Class<?> annotationClass,List<Class<?>> annotations){
		if (file.isDirectory()){
			for(File subFile:file.listFiles()) {
				getAnnotation(subFile,annotationClass,annotations);
			}
		}else{
			getAnnotation(file.getPath(),annotationClass,annotations);
		}
	}

	private void getAnnotation(String filepath,Class<?> annotationClass,List<Class<?>> annotations){
		if (filepath.endsWith(".class")){
			int beginIndex = filepath.indexOf("classes/")+8;
			String className = filepath.substring(beginIndex,filepath.length()-6)
					.replaceAll("/",".");
			try {
				Class clazz = Class.forName(className);
				if(clazz.getAnnotation(annotationClass)!=null){
					annotations.add(clazz);
				}
			} catch (ClassNotFoundException e) {
				LOGGER.error("获取类异常",e);
			}
		}
	}
}
