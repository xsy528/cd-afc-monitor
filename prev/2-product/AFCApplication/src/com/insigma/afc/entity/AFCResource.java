/**
 * 
 */
package com.insigma.afc.entity;

import java.io.Serializable;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.insigma.afc.entity.AFCResource.AFCResourcePK;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
@MappedSuperclass
@IdClass(AFCResourcePK.class)
public class AFCResource implements Serializable {

	private static final long serialVersionUID = -7230682081711086878L;

	@Id
	@Column(name = "NAME")
	private String name;
	@Id
	@Column(name = "NAME_SPACE")
	private String nameSpace;

	@Column(nullable = false)
	private String md5;
	private String remark;

	@Transient
	private URL resource;

	public AFCResource(String name, String nameSpace, String md5, String desc) {
		super();
		this.name = name;
		this.nameSpace = nameSpace;
		this.md5 = md5;
		this.remark = desc;
	}

	public AFCResource() {
		super();
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

	public URL getResource() {
		return resource;
	}

	public void setResource(URL resource) {
		this.resource = resource;
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

	public static class AFCResourcePK implements Serializable {
		private static final long serialVersionUID = 846664540617722812L;
		@Column(name = "NAME")
		private String name;
		@Column(name = "NAME_SPACE")
		private String nameSpace;

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

	}
}