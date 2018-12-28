package com.insigma.afc.topology;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.commons.reflect.ClassUtil;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.ViewExpansion;
import com.insigma.commons.util.SystemPropertyUtil;

/**
 * MetroDeviceModule entity.
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TMETRO_DEVICE_MODULE")
public class MetroDeviceModule extends MetroNode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private MetroDeviceModuleId id;

	private Short lineId;

	private String lineName;

	private String stationName;

	private String deviceName;

	@ColumnView(name = "部件简写", width = 65)
	private String moduleName;

	private String ipAddress;

	@ColumnView(name = "状态值", width = 55)
	private Short status;

	@ColumnView(name = "部件名称", width = 150)
	private String remark;

	// Constructors

	/** default constructor */
	public MetroDeviceModule() {
	}

	/** minimal constructor */
	public MetroDeviceModule(MetroDeviceModuleId id, Short lineId, String lineName, String stationName,
			String deviceName, String moduleName, Short status) {
		this.id = id;
		this.lineId = lineId;
		this.lineName = lineName;
		this.stationName = stationName;
		this.deviceName = deviceName;
		this.moduleName = moduleName;
		this.status = status;
	}

	private AFCNodeLocation imageLocation;

	private AFCNodeLocation textLocation;

	/**
	 * @return the imageLocation
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "image_x", nullable = false) ),
			@AttributeOverride(name = "y", column = @Column(name = "image_y", nullable = false) ),
			@AttributeOverride(name = "angle", column = @Column(name = "image_angle", nullable = false) ) })
	public AFCNodeLocation getImageLocation() {
		if (imageLocation == null) {
			imageLocation = new AFCNodeLocation();
		}
		return imageLocation;
	}

	/**
	 * @param imageLocation the imageLocation to set
	 */
	public void setImageLocation(AFCNodeLocation imageLocation) {
		this.imageLocation = imageLocation;
	}

	/**
	 * @return the textLocation
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "text_x", nullable = false) ),
			@AttributeOverride(name = "y", column = @Column(name = "text_y", nullable = false) ),
			@AttributeOverride(name = "angle", column = @Column(name = "text_angle", nullable = false) ) })
	public AFCNodeLocation getTextLocation() {
		if (textLocation == null) {
			textLocation = new AFCNodeLocation();
		}
		return textLocation;
	}

	/**
	 * @param textLocation the textLocation to set
	 */
	public void setTextLocation(AFCNodeLocation textLocation) {
		this.textLocation = textLocation;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stationId", column = @Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0) ),
			@AttributeOverride(name = "deviceId", column = @Column(name = "DEVICE_ID", nullable = false, scale = 0) ),
			@AttributeOverride(name = "moduleId", column = @Column(name = "MODULE_ID", nullable = false, scale = 0) ) })
	public MetroDeviceModuleId getId() {
		return this.id;
	}

	public void setId(MetroDeviceModuleId id) {
		this.id = id;
	}

	@Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0)
	public short getLineId() {
		return this.lineId;
	}

	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}

	@Column(name = "LINE_NAME", nullable = false, length = 30)
	public String getLineName() {
		return this.lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	@Column(name = "STATION_NAME", nullable = false, length = 30)
	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Column(name = "DEVICE_NAME", nullable = false, length = 30)
	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "MODULE_NAME", nullable = false, length = 30)
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "STATUS", nullable = false, precision = 5, scale = 0)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public long id() {
		return this.id.getModuleId();
	}

	@Override
	public String name() {
		return this.moduleName;
	}

	@Override
	public AFCNodeLevel level() {
		return AFCNodeLevel.MODULE;
	}

	@Override
	@Transient
	public List<? extends MetroNode> getSubNodes() {
		return new ArrayList<MetroNode>();
	}

	public MetroDeviceModule(MetroDeviceModule deviceModule) {
		this.id = new MetroDeviceModuleId(deviceModule.id);
		this.lineId = deviceModule.lineId;
		this.lineName = deviceModule.lineName;
		this.stationName = deviceModule.stationName;
		this.deviceName = deviceModule.deviceName;
		this.moduleName = deviceModule.moduleName;
		this.ipAddress = deviceModule.ipAddress;
		this.status = deviceModule.status;
		this.remark = deviceModule.remark;
		this.imageLocation = new AFCNodeLocation(deviceModule.imageLocation);
		this.textLocation = new AFCNodeLocation(deviceModule.textLocation);
	}

	@Override
	public boolean addSubNode(MetroNode subMetroNode) {
		return false;
	}

	@Override
	public boolean removeSubNode(MetroNode subMetroNode) {
		return false;
	}

	private String extJsonValue;

	@View(label = "扩展信息", group = "==扩展信息==")
	@ViewExpansion(viewClass = "${MetroDeviceModule.extValue}")
	@Transient
	private Object extValue;

	@Column(name = "EXT_JSON_VALUE", length = 2000)
	public String getExtJsonValue() {
		return extJsonValue;
	}

	public void setExtJsonValue(String extJsonValue) {
		this.extJsonValue = extJsonValue;
		if (extJsonValue == null || "".equals(extJsonValue)) {
			return;
		}
		String extClassName = SystemPropertyUtil.resolvePlaceholders("${MetroDeviceModule.extValue}");
		Class<?> clazz = ClassUtil.forName(extClassName);
//		extValue = JSONDecoder.decode(extJsonValue, clazz);
	}

	@Transient
	public Object getExtValue() {
		return extValue;
	}

	public void setExtValue(Object extValue) {
		this.extValue = extValue;
//		this.extJsonValue = JSONEncoder.encode(extValue);
	}

	@Override
	@Transient
	public String getPicName() {
		return null;
	}

	@Override
	public void setPicName(String picName) {
		// TODO Auto-generated method stub

	}

	//	@Override
	//	@Transient
	//	public long getNodeId() {
	//		return AFCApplication.getNodeId(id());
	//	}
	@Override
	@Transient
	public short getNodeType() {
		return AFCApplication.getNodeType(id());
	}

	@Override
	@Transient
	public MetroDevice getParent() {
		return (MetroDevice) super.getParent();
	}
}
