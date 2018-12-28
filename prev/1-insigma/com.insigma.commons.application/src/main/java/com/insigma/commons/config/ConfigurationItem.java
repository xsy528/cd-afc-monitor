/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ConfigurationItem {

	@XStreamAsAttribute
	@XmlAttribute(required = false)
	private long version;

	public void setVersion(long version) {
		this.version = version;
	}

	public long getVersion() {
		return version;
	}

}
