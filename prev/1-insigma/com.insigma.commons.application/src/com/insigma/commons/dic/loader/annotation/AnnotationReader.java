/* 
 * 日期：2010-10-29
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader.annotation;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scannotation.archiveiterator.FileIterator;
import org.scannotation.archiveiterator.Filter;
import org.scannotation.archiveiterator.JarIterator;
import org.scannotation.archiveiterator.StreamIterator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AnnotationReader {

	private static final Log logger = LogFactory.getLog(AnnotationReader.class);

	public static String[] INSIGMA_LOCATION = { "classpath*:com/insigma/afc", "classpath*:com/insigma/acc" };

	protected Map<String, Set<String>> classIndex = new HashMap<String, Set<String>>();

	protected Map<String, Set<String>> annotationIndex = new HashMap<String, Set<String>>();

	Class<?> annotationClass;

	String[] location;

	public AnnotationReader(Class<?> annotationClass) {
		this(INSIGMA_LOCATION, annotationClass);
	}

	public AnnotationReader(String[] location, Class<?> annotationClass) {
		this.annotationClass = annotationClass;
		this.location = location;
		if (this.location == null) {
			this.location = INSIGMA_LOCATION;
		}
	}

	public List<Class<?>> findAnnotations(Class<?> annotationClass) {
		long s = System.currentTimeMillis();
		initBeanByAnnotation();

		List<Class<?>> annotations = new ArrayList<Class<?>>();
		Set<String> entities = annotationIndex.get(annotationClass.getName());

		if (entities == null)
			return annotations;

		for (String typeName : entities) {
			Class<?> resultType = null;
			try {
				resultType = Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				logger.error(String.format("ClassNotFoundException %s", e.getMessage()), e);
				continue;
			}
			// log.debug(String.format("resource found %s", resultType.getName()));
			annotations.add(resultType);
		}
		logger.debug("扫描classpath中所有的@" + annotationClass.getName() + "定义，耗时（秒 ）："
				+ (System.currentTimeMillis() - s) / 1000);
		return annotations;
	}

	/**
	 * 通过注解初始化Bean
	 */
	@SuppressWarnings("deprecation")
	private void initBeanByAnnotation() {
		PathMatchingResourcePatternResolver res = new PathMatchingResourcePatternResolver();
		for (String lo : location) {
			Resource[] rs = null;
			try {
				rs = res.getResources(lo);
			} catch (IOException e1) {
				logger.error("classpath 读取异常.", e1);
				return;
			}
			Filter filter = new Filter() {
				public boolean accepts(String filename) {
					if (filename.endsWith(".class")) {
						return true;
					}
					return false;
				}
			};
			for (Resource re : rs) {
				URL url = null;
				try {
					url = re.getURL();
					logger.debug("开始扫描:" + url.getPath());
					StreamIterator it = null;
					if (ResourceUtils.isJarURL(url)) {
						final String urlpath = url.getPath();
						final int separatorIndex = urlpath.indexOf(ResourceUtils.JAR_URL_SEPARATOR);
						String jarFileUrl = null;// jar文件名称
						String packageUrl = null;// 包的名称
						if (separatorIndex != -1) {
							jarFileUrl = urlpath.substring(5, separatorIndex);
							packageUrl = urlpath.substring(separatorIndex + 2);
						} else {
							jarFileUrl = urlpath;
							packageUrl = urlpath;
						}
						final String _matchPath = packageUrl;
						jarFileUrl = URLDecoder.decode(jarFileUrl);
						Filter jarFilter = new Filter() {
							public boolean accepts(String filename) {
								if (filename.startsWith(_matchPath) && filename.endsWith(".class")) {
									return true;
								}
								return false;
							}
						};

						File file = new File(jarFileUrl);
						it = new JarIterator(file, jarFilter);
					} else {
						it = new FileIterator(re.getFile(), filter);
					}
					InputStream stream;
					while ((stream = it.next()) != null)
						scanClass(stream);
				} catch (IOException e1) {
					logger.error(e1);
					continue;
				}

			}
		}
	}

	public void scanClass(InputStream bits) throws IOException {
		DataInputStream dstream = new DataInputStream(new BufferedInputStream(bits));
		ClassFile cf = null;
		try {
			cf = new ClassFile(dstream);
			String className = cf.getName();
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			if (visible != null) {
				Annotation[] annotations = visible.getAnnotations();
				Set<String> classAnnotations = classIndex.get(className);
				if (classAnnotations == null) {
					classAnnotations = new HashSet<String>();
					classIndex.put(className, classAnnotations);
				}
				for (Annotation ann : annotations) {
					if (!ann.getTypeName().equals(annotationClass.getName())) {
						continue;
					}
					Set<String> classes = annotationIndex.get(ann.getTypeName());
					if (classes == null) {
						classes = new HashSet<String>();
						annotationIndex.put(ann.getTypeName(), classes);
					}
					classes.add(className);
					classAnnotations.add(ann.getTypeName());
				}
			}

		} finally {
			dstream.close();
			bits.close();
		}
	}
}
