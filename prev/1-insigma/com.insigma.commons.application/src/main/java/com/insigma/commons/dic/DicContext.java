/* 
 * 日期：2010-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic;

import java.util.List;

import com.insigma.commons.application.IService;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public interface DicContext extends IService {
	public DicModel getDicModelByName(String dicName);

	public List<DicModel> getDicModelsByType(String dicType);

	public boolean containDicModel(String dicName);

	public boolean registerDicModel(DicModel dicModel);

}
