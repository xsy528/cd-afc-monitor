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

import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.table.TableGrid;

public class SearchResponse extends Response {

	private TableGrid data;

	public TableGrid getData() {
		return data;
	}

	public void setData(TableGrid data) {
		this.data = data;
	}

}
