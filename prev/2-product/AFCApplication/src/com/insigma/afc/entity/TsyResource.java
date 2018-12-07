/**
 * 
 */
package com.insigma.afc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
@Entity
@Table(name = "TSY_RESOURCE")
public class TsyResource extends AFCResource implements Serializable {
	private static final long serialVersionUID = 7001466674448001137L;
	@Lob()
	@Column(name = "CONTENTS", nullable = false)
	private byte[] contents;

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public TsyResource() {
		super();
	}

	public TsyResource(String name, String nameSpace, String md5, String desc) {
		super(name, nameSpace, md5, desc);
	}

	public TsyResource(String name, String nameSpace, String desc) {
		super(name, nameSpace, null, desc);
	}
}
