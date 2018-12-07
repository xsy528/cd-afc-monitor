/* 
 * 日期：Dec 28, 2007
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.op;

public class ImExportResult {

	private long elapseTime = 0;

	private long sucRowCount = 0;

	private long failRowCount = 0;

	public void setElapseTime(long elapseTime) {
		this.elapseTime = elapseTime;
	}

	public long getElapseTime() {
		return elapseTime;
	}

	public long getSucRowCount() {
		return sucRowCount;
	}

	public void setSucRowCount(long rowCount) {
		sucRowCount = rowCount;
	}

	public long getFailRowCount() {
		return failRowCount;
	}

	public void setFailRowCount(long failRowCount) {
		this.failRowCount = failRowCount;
	}

	public void addFail() {
		failRowCount++;
	}

	public void addSuccess(int suc) {
		sucRowCount += suc;
	}

	@Override
	public String toString() {
		return "成功:" + sucRowCount + "条;失败" + failRowCount + "条;耗时" + elapseTime / 1000 + "秒";
	}
}
