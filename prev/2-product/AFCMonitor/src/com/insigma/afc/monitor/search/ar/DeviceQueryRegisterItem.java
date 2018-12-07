/* 
 * 日期：2010-10-11
 * 描述（预留） 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.search.ar;

import java.io.Serializable;

import com.insigma.afc.entity.convertor.AFCDataFileConvertor;
import com.insigma.afc.entity.convertor.DeviceTypeConvertor;
import com.insigma.afc.topology.AFCNode;
import com.insigma.commons.ui.anotation.ColumnView;

/**
 * Ticket: 设备寄存器查询
 * 
 * @author zhengshuquan
 * @date 2010-10-11
 * @description
 */
public class DeviceQueryRegisterItem extends AFCNode implements Serializable {

	//	@ColumnView(name = "序号")
	private long index;

	//	@ColumnView(name = "线路/编号")
	private String lineName;

	//	@ColumnView(name = "车站/编号")
	private String stationName;

	//	@ColumnView(name = "设备名称/编号")
	private String deviceName;

	@ColumnView(name = "设备类型", convertor = DeviceTypeConvertor.class)
	private Short deviceType;

	@ColumnView(name = "文件类型", convertor = AFCDataFileConvertor.class)
	private Short fileType;

	@ColumnView(name = "文件名")
	private String fileName;

	@ColumnView(name = "生成时间")
	private String creatTime;

	@ColumnView(name = "上传时间")
	private String uploadTime;

	/** 设备编号 */
	private Long deviceId;

	/**
	 * @return the index
	 */
	public long getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(long index) {
		this.index = index;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName
	 *            the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @param stationName
	 *            the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * @return the deviceType
	 */
	public Short getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(Short deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the fileType
	 */
	public Short getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(Short fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the creatTime
	 */
	public String getCreatTime() {
		return creatTime;
	}

	/**
	 * @param creatTime
	 *            the creatTime to set
	 */
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	/**
	 * @return the uploadTime
	 */
	public String getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime
	 *            the uploadTime to set
	 */
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

}
