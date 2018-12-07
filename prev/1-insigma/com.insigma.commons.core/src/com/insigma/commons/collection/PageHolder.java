/* 
 * 日期：2010-9-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.collection;

import java.util.List;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class PageHolder<T> {
	private int pageIndex;

	private int pageSize;

	private List<T> datas;

	private int totalCount;

	/**
	 * @param pageIndex
	 * @param pageSize
	 * @param datas
	 */
	public PageHolder(int pageIndex, int pageSize, int totalCount, List<T> datas) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.datas = datas;
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the datas
	 */
	public List<T> getDatas() {
		return datas;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

}
