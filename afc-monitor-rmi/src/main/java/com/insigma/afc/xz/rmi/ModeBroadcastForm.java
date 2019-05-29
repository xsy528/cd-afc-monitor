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
import java.util.ArrayList;
import java.util.List;

/**
 * Ticket:  车站模式广播请求
 * @author  matianming
 *
 */
public class ModeBroadcastForm implements Serializable {

	private static final long serialVersionUID = -6533498242844877344L;

	private List<ModeStation> modeStationList = new ArrayList<>();

	public List<ModeStation> getModeStationList() {
		return modeStationList;
	}

	public void setModeStationList(List<ModeStation> modeStationList) {
		this.modeStationList = modeStationList;
	}

	public static class ModeStation implements Serializable {

		private static final long serialVersionUID = -6799586590919615670L;

		private Integer stationId;

		private Integer modeCode;

		public Integer getStationId() {
			return stationId;
		}

		public void setStationId(Integer stationId) {
			this.stationId = stationId;
		}

		public Integer getModeCode() {
			return modeCode;
		}

		public void setModeCode(Integer modeCode) {
			this.modeCode = modeCode;
		}

		@Override
		public String toString() {
			return "ModeStation{" +
					"stationId=" + stationId +
					", modeCode=" + modeCode +
					'}';
		}
	}

	@Override
	public String toString() {
		return "ModeBroadcastForm{" +
				"modeStationList=" + modeStationList +
				'}';
	}
}
