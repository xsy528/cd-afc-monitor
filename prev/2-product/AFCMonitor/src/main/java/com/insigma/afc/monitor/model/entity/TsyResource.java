/**
 * 
 */
package com.insigma.afc.monitor.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
@Entity
@Table(name = "TSY_RESOURCE")
public class TsyResource extends AFCResource implements Serializable {
	private static final long serialVersionUID = 1L;

	public TsyResource() {
	}

	public TsyResource(String name, String nameSpace, String desc) {
		super(name, nameSpace, null, desc);
	}

	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "CONTENTS", nullable = false)
	private byte[] contents;

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}
}
