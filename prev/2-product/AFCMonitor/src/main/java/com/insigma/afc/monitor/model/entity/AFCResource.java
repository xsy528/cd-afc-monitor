/**
 * 
 */
package com.insigma.afc.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
@MappedSuperclass
@IdClass(AFCResourcePK.class)
public class AFCResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NAME")
	private String name;
	@Id
	@Column(name = "NAME_SPACE")
	private String nameSpace;

	@Column(name = "MD5", nullable = false)
	private String md5;

	@Column(name = "REMARK")
	private String remark;

	public AFCResource(String name, String nameSpace, String md5, String desc) {
		this.name = name;
		this.nameSpace = nameSpace;
		this.md5 = md5;
		this.remark = desc;
	}

	public AFCResource() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AFCResource [name=");
		builder.append(name);
		builder.append(", nameSpace=");
		builder.append(nameSpace);
		builder.append(", md5=");
		builder.append(md5);
		builder.append(", remark=");
		builder.append(remark);
		builder.append("]");
		return builder.toString();
	}
}