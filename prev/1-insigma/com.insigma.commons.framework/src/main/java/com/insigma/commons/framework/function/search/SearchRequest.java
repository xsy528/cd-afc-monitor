/**
 * iFrameWork 框架
 *
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description
 * @date          2009-4-4
 * @copyright     浙江浙大网新众合轨道交通工程有限公司
 * @history
 */
package com.insigma.commons.framework.function.search;

import java.util.List;

import com.insigma.commons.framework.function.form.FormRequest;

public class SearchRequest<T> extends FormRequest<T> {

	private int pageSize;

	private int page;

	private List<Object> selection;

	private List<T> selectionData; //选中的数据
	private int[] selectionIndexs;//选中的索引

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Object> getSelection() {
		return selection;
	}

	public void setSelection(List<Object> selection) {
		this.selection = selection;
	}

	public Object getSelectNode() {
		if (selection == null || selection.isEmpty()) {
			return null;
		}
		return selection.get(0);
	}

	/**
	 * @param selectionData the selectionData to set
	 */
	public void setSelectionData(List<T> selectionData) {
		this.selectionData = selectionData;
	}

	/**
	 * @return the selectionData
	 */
	public List<T> getSelectionData() {
		return selectionData;
	}

	public int[] getSelectionIndexs() {
		return selectionIndexs;
	}

	public void setSelectionIndexs(int[] selectionIndexs) {
		this.selectionIndexs = selectionIndexs;
	}

}
