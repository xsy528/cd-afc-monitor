/* 
 * 日期：2010-6-25
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Ticket: UD文件基础类
 * 
 * @author Zhou-Xiaowei
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class UDFileBaseEntity implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "RECORDID")
	@XStreamOmitField
	private long recordId;

	@XmlTransient
	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

}
