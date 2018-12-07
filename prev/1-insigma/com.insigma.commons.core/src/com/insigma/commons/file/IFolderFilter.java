/* 
 * 日期：2010-3-26
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.file;

import java.io.File;

public abstract class IFolderFilter {

	private int depth;

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean filterFolder(File pathname) {
		depth++;
		return accept(pathname);
	}

	public abstract boolean accept(File pathname);

}
