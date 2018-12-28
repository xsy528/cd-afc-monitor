/* 
 * 日期：2008-12-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 * 
 */
package com.insigma.commons.xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XStream javaBean对象与XML文件相互转化的类
 * <p>
 * Ticket:
 * <p>
 * {@link @XStreamAlias 表示类别名，@XmlAttribute 作为属性而不是Element,@XmlElementRef 如果是复合对象,@XmlList
 * 如果是List集合对象，@XmlTransient 与@XmlList连用表示忽略List封装Element}
 * 
 * @author Dingluofeng
 */
public class XMLStream {

	private static Log logger = LogFactory.getLog(XMLStream.class);

	/**
	 * 保存javaBean到Xml文件
	 * 
	 * @param entity
	 *            javeBean对象
	 * @param fileName
	 *            要保存的路径（包括文件名）
	 * @throws IOException
	 *             文件不存在或流关闭异常
	 */
	public static void toXMLFile(Object entity, String fileName) throws IOException {
		OutputStream out = null;
		try {
			XStream xStream = new XStream();
			setAlias(xStream, entity.getClass());
			out = new FileOutputStream(fileName);
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
			xStream.toXML(entity, out);
		} catch (Exception e) {
			logger.error("保存javaBean到Xml文件异常。", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 把XML文件解析成javaBean对象
	 * 
	 * @param fileName
	 *            要解析的文件的路径（包括文件名）
	 * @param entity
	 *            解析到哪个实体对象
	 * @param classes
	 *            需要加载的节点类型
	 * @return
	 * @throws IOException
	 */
	public static Object fromXMLFile(String fileName, Object entity, Class<?>... classes) throws IOException {

		Object obj = null;
		FileInputStream fis = null;
		try {
			XStream xs = new XStream(new DomDriver());
			xs.autodetectAnnotations(true);
			xs.processAnnotations(classes);
			setAlias(xs, entity.getClass());
			fis = new FileInputStream(fileName);
			obj = xs.fromXML(fis, entity);
		} catch (Exception e) {
			logger.error("把Xml文件解析成javaBean对象异常。", e);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return obj;
	}

	/**
	 * 读取Annotation，并set相关属性
	 * 
	 * @param xStream
	 * @param obj
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Deprecated
	private static void setAlias(XStream xStream, Object obj) throws IllegalArgumentException, IllegalAccessException {
		if (obj == null) {
			return;
		}
		Class<?> clazz = obj.getClass();
		XStreamAlias rootEle = clazz.getAnnotation(XStreamAlias.class);
		if (rootEle != null) {
			xStream.alias(rootEle.value(), clazz);
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			XmlTransient xmlTransient = field.getAnnotation(XmlTransient.class);
			XmlAttribute attri = field.getAnnotation(XmlAttribute.class);
			XmlElementRef eleRef = field.getAnnotation(XmlElementRef.class);
			XmlList list = field.getAnnotation(XmlList.class);
			if (attri != null) {
				xStream.aliasAttribute(clazz, field.getName(), field.getName());
				continue;
			}
			if (xmlTransient != null) {
				xStream.addImplicitCollection(clazz, field.getName());
			}
			if (eleRef != null && list != null) {
				for (Object ob : (List) field.get(obj)) {
					setAlias(xStream, ob);
				}
			}
			if (eleRef != null && list == null) {
				xStream.aliasField(eleRef.name(), clazz, field.getName());
				setAlias(xStream, field.get(obj));
				continue;
			}
		}
	}

	/**
	 * 读取Annotation，并set相关属性
	 * 
	 * @param xStream
	 * @param obj
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void setAlias(XStream xStream, Class<?> clazz)
			throws IllegalArgumentException, IllegalAccessException {
		XStreamAlias rootEle = clazz.getAnnotation(XStreamAlias.class);
		if (rootEle != null) {
			xStream.alias(rootEle.value(), clazz);
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			XmlTransient xmlTransient = field.getAnnotation(XmlTransient.class);
			XmlAttribute attri = field.getAnnotation(XmlAttribute.class);
			XmlElementRef eleRef = field.getAnnotation(XmlElementRef.class);
			XmlList list = field.getAnnotation(XmlList.class);
			if (attri != null) {
				xStream.aliasAttribute(clazz, field.getName(), field.getName());
				continue;
			}
			if (xmlTransient != null) {
				xStream.addImplicitCollection(clazz, field.getName());
			}
			if (eleRef != null && list != null) {
				// xStream.aliasField(eleRef.name(), clazz, field.getName());
				setAlias(xStream, eleRef.type());
			}
			if (eleRef != null && list == null) {
				xStream.aliasField(eleRef.name(), clazz, field.getName());
				setAlias(xStream, field.getType());
				continue;
			}
		}
	}
}
