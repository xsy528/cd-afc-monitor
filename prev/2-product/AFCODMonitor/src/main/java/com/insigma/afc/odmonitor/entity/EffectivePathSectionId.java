/*
 * 日期：2010-9-29
 * 描述（预留）
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.odmonitor.entity;

import java.io.Serializable;

/**
 * Ticket: 有效路径路段表实体
 * 
 * @author zhengshuquan
 * @date 2010-9-29
 * @description
 */
public class EffectivePathSectionId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 600302493534207673L;

	/**
	 * 有效路径编号
	 */
	private Integer pathId;

	/**
	 * 路段ID
	 */
	private Long sectionId;

	/**
	 *
	 */
	public EffectivePathSectionId() {
		super();
	}

	/**
	 * @param pathId
	 * @param sectionId
	 * @param sectionIndex
	 */
	public EffectivePathSectionId(Integer pathId, Long sectionId) {
		super();
		this.pathId = pathId;
		this.sectionId = sectionId;
	}

	/**
	 * @return the pathId
	 */
	public Integer getPathId() {
		return pathId;
	}

	/**
	 * @param pathId
	 *            the pathId to set
	 */
	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}

	/**
	 * @return the sectionId
	 */
	public Long getSectionId() {
		return sectionId;
	}

	/**
	 * @param sectionId
	 *            the sectionId to set
	 */
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

}
