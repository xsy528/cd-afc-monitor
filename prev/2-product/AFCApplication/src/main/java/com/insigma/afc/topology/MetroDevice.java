package com.insigma.afc.topology;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCDeviceSubType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.commons.reflect.ClassUtil;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.ViewExpansion;
import com.insigma.commons.util.SystemPropertyUtil;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * MetroDevice entity.
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TMETRO_DEVICE")
public class MetroDevice extends MetroNode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private MetroDeviceId id;

	private String lineName;

	private String stationName;

	private String deviceName;

	private short deviceType = AFCDeviceType.TVM.shortValue();

	private Short deviceSubType;

	private String ipAddress;

	private AFCNodeLocation imageLocation;

	private AFCNodeLocation textLocation;

	private int commPort;

	private long logicAddress;

	private Short status;

	// 网卡的MAC地址
	private String addrMac;

	private String remark;

	private String picName;

	// Constructors

	/** default constructor */
	public MetroDevice() {
	}

	/** minimal constructor */
	public MetroDevice(MetroDeviceId id, String lineName, String stationName, String deviceName, Short status) {
		this.id = id;
		this.lineName = lineName;
		this.stationName = stationName;
		this.deviceName = deviceName;
		this.status = status;
	}

	public MetroDevice(MetroDeviceId id, String lineName, String stationName, String deviceName, String picName,
			short deviceType, short deviceSubType, String ipAddress, AFCNodeLocation imageLocation,
			AFCNodeLocation textLocation, int commPort, long logicAddress, Short status, String remark,
			List<MetroDeviceModule> ss) {
		super();
		this.id = id;
		this.lineName = lineName;
		this.stationName = stationName;
		this.deviceName = deviceName;
		this.picName = picName;
		this.deviceType = deviceType;
		this.deviceSubType = deviceSubType;
		this.ipAddress = ipAddress;
		this.imageLocation = imageLocation;
		this.textLocation = textLocation;
		this.commPort = commPort;
		this.logicAddress = logicAddress;
		this.status = status;
		this.remark = remark;
		this.ss = ss;
	}

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
			@AttributeOverride(name = "lineId", column = @Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0) ),
			@AttributeOverride(name = "stationId", column = @Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0) ),
			@AttributeOverride(name = "deviceId", column = @Column(name = "DEVICE_ID", nullable = false, scale = 0) ) })
	public MetroDeviceId getId() {
		return this.id;
	}

	public void setId(MetroDeviceId id) {
		this.id = id;
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

	@Column(name = "IP_ADDRESS", length = 32)
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

	@Column(name = "DEVICE_TYPE", nullable = false, precision = 5, scale = 0)
	public short getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(short deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "DEVICE_SUB_TYPE", nullable = true, precision = 5, scale = 0)
	public Short getDeviceSubType() {
		return deviceSubType;
	}

	public void setDeviceSubType(Short deviceSubType) {
		this.deviceSubType = deviceSubType;
	}


	//	@Column(name = "PIC_NAME")
	@Transient
	public String getPicName() {
		String imagePathName = "bom/BOM.png";
		if (getDeviceType() == AFCDeviceType.POST.shortValue()) {
			imagePathName = "bom/BOM.png";
		} else if ((AFCDeviceType.GATE == null
				&& AFCDeviceSubType.getInstance().getDicItemMap().containsKey(getDeviceType()))
				|| (AFCDeviceType.GATE != null && getDeviceType() == AFCDeviceType.GATE)) {
			imagePathName = "gate/GATE.png";
		} else if (getDeviceType() == AFCDeviceType.TVM.shortValue()) {
			imagePathName = "tvm/TVM.png";
		} else if (getDeviceType() == AFCDeviceType.TSM.shortValue()) {
			imagePathName = "tsm/TSM.png";
		} else if (getDeviceType() == AFCDeviceType.TCM.shortValue()) {
			imagePathName = "tcm/TCM.png";
		} else if (getDeviceType() == AFCDeviceType.AVM.shortValue()) {
			imagePathName = "avm/AVM.png";
		}

		return "/com/insigma/afc/ui/monitor/" + imagePathName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	/**
	 * @param commPort
	 *            the commPort to set
	 */
	public void setCommPort(int commPort) {
		this.commPort = commPort;
	}

	/**
	 * @return the commPort
	 */
	@Column(name = "COMM_PORT")
	public int getCommPort() {
		return commPort;
	}

	/**
	 * @param logicAdress
	 *            the logicAdress to set
	 */
	public void setLogicAddress(long logicAddress) {
		this.logicAddress = logicAddress;
	}

	/**
	 * @return the logicAdress
	 */
	@Column(name = "LOGIC_ADDRESS")
	public long getLogicAddress() {
		return logicAddress;
	}

	@Transient
	public long id() {
		return id.getDeviceId();
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof MetroDevice) {
			return ((MetroDevice) arg0).id() == id();
		}
		return false;
	}

	public String name() {
		return deviceName;
	}

	@Transient
	public AFCNodeLevel level() {
		return AFCNodeLevel.SLE;
	}

	private List<MetroDeviceModule> ss;

	@Override
	@Transient
	public List<MetroDeviceModule> getSubNodes() {
		return ss;
	}

	public void setModules(List<MetroDeviceModule> ss) {
		this.ss = ss;
		if (ss == null) {
			return;
		}
		for (MetroDeviceModule module : ss) {
			module.setParent(this);
		}
	}

	public MetroDevice(MetroDevice metroDevice) {
		this.id = new MetroDeviceId(metroDevice.id);
		this.lineName = metroDevice.lineName;
		this.stationName = metroDevice.stationName;
		this.deviceName = metroDevice.deviceName;
		this.picName = metroDevice.picName;
		this.deviceType = metroDevice.deviceType;
		this.deviceSubType = metroDevice.deviceSubType;
		this.ipAddress = metroDevice.ipAddress;
		this.imageLocation = new AFCNodeLocation(metroDevice.imageLocation);
		this.textLocation = new AFCNodeLocation(metroDevice.textLocation);
		this.commPort = metroDevice.commPort;
		this.logicAddress = metroDevice.logicAddress;
		this.status = metroDevice.status;
		this.remark = metroDevice.remark;
		this.ss = metroDevice.ss;
	}

	@Override
	public boolean addSubNode(MetroNode subMetroNode) {
		boolean add = ss.add((MetroDeviceModule) subMetroNode);
		Collections.sort(ss, MetroNodeComparator.comparator);
		return add;
	}

	@Override
	public boolean removeSubNode(MetroNode subMetroNode) {
		return ss.remove(subMetroNode);
	}

	private String extJsonValue;

	@View(label = "扩展信息")
	@ViewExpansion(viewClass = "${MetroextValue}")
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
		String extClassName = SystemPropertyUtil.resolvePlaceholders("${MetroextValue}");
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
	public long getNodeId() {
		return AFCApplication.getNodeId(id());
	}

	@Override
	@Transient
	public short getNodeType() {
		return AFCApplication.getNodeType(id());
	}

	@Override
	@Transient
	public MetroStation getParent() {
		return (MetroStation) super.getParent();
	}

	@Column(name = "ADDR_MAC")
	public String getAddrMac() {
		return addrMac;
	}

	public void setAddrMac(String mac) {
		this.addrMac = mac;
	}
}