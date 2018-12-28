/* 
 * 日期：2010-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.commons.dic.loader.DicLoader;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DefaultDicContext implements DicContext {

	private List<DicLoader> loaders = new ArrayList<DicLoader>();

	private Map<String, DicModel> dicMap = new HashMap<String, DicModel>();

	public DefaultDicContext() {

	}

	public void init() {
		for (DicLoader loader : loaders) {
			List<DicModel> list = loader.loadAllDic();
			for (DicModel dicModel : list) {
				this.registerDicModel(dicModel);
			}
		}
	}

	/**
	 * @return the loaders
	 */
	public List<DicLoader> getLoaders() {
		return loaders;
	}

	/**
	 * @param loaders
	 *            the loaders to set
	 */
	public void setLoaders(List<DicLoader> loaders) {
		this.loaders = loaders;
	}

	public DicModel getDicModelByName(String dicName) {
		return dicMap.get(dicName);
	}

	public boolean containDicModel(String dicName) {
		return dicMap.containsKey(dicName);
	}

	public boolean registerDicModel(DicModel dicModel) {
		if (containDicModel(dicModel.getName())) {
			return false;
		}
		dicMap.put(dicModel.getName(), dicModel);
		return true;
	}

	public List<DicModel> getDicModelsByType(String dicType) {
		List<DicModel> list = new ArrayList<DicModel>();
		for (DicModel dic : dicMap.values()) {
			if (dic.getType().equals(dicType)) {
				list.add(dic);
			}
		}
		return list;
	}

}
