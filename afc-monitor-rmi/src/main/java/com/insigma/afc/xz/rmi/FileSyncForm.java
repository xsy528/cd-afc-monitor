/* 
 * 日期：2019年3月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.xz.rmi;

import java.io.Serializable;

/**
 * Ticket:  数据同步下载请求
 * @author  matianming
 *
 */
public class FileSyncForm implements Serializable {

	private static final long serialVersionUID = -5540189984731099854L;

	private Integer fileType;

	private Short operationType;

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Short getOperationType() {
		return operationType;
	}

	public void setOperationType(Short operationType) {
		this.operationType = operationType;
	}

}
