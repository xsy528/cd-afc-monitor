package com.insigma.afc.topology;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.commons.reflect.ClassUtil;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.ViewExpansion;
import com.insigma.commons.util.SystemPropertyUtil;

import javax.persistence.*;
import java.util.*;

/**
 * TmetroStation entity.
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TMETRO_STATION")
public class MetroStation extends MetroNode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@View()
	private MetroStationId id = new MetroStationId();

	@View(label = "线路名称", regexp = "^[\\u4e00-\\u9fa5,\\w]{0,16}$", modify = false)
	private String lineName;

	@View(label = "车站名称", regexp = "^[\\u4e00-\\u9fa5,\\w]{0,16}$")
	private String stationName;

	@View(label = "网络地址", regexp = "[0-9\\.]{1,15}")
	private String ipAddress;

	//	@View(label = "图片坐标")
	private AFCNodeLocation imageLocation;

	//	@View(label = "文字坐标")
	private AFCNodeLocation textLocation;

	@View(label = "车站图片", type = "File")
	private String picName;

	private String backPicName;

	private Short status = 0;

	private String remark;

	// Constructors

	/** default constructor */
	public MetroStation() {
	}

	/** minimal constructor */
	public MetroStation(MetroStationId id, String lineName, String stationName, Short status) {
		this.id = id;
		this.lineName = lineName;
		this.stationName = stationName;
		this.status = status;
	}

	public MetroStation(MetroStationId id, String lineName, String stationName, String ipAddress,
			AFCNodeLocation imageLocation, AFCNodeLocation textLocation, String picName, String backPicName,
			Short status, String remark, Map<Long, MetroDevice> deviceMap, List<MetroDevice> devices) {
		super();
		this.id = id;
		this.lineName = lineName;
		this.stationName = stationName;
		this.ipAddress = ipAddress;
		this.imageLocation = imageLocation;
		this.textLocation = textLocation;
		this.picName = picName;
		this.backPicName = backPicName;
		this.status = status;
		this.remark = remark;
		this.deviceMap = deviceMap;
		this.devices = devices;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "lineId", column = @Column(name = "LINE_ID", nullable = false, precision = 5, scale = 0) ),
			@AttributeOverride(name = "stationId", column = @Column(name = "STATION_ID", nullable = false, precision = 11, scale = 0) ) })
	public MetroStationId getId() {
		return this.id;
	}

	public void setId(MetroStationId id) {
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

	@Column(name = "PIC_NAME", length = 255)
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "BACK_PIC_NAME")
	public String getBackPicName() {
		if (backPicName == null) {
			backPicName = picName;
		}
		return backPicName;
	}

	public void setBackPicName(String backPicName) {
		this.backPicName = backPicName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[车站编号:");
		sb.append(id.getStationId());
		sb.append("车站名称:");
		sb.append(stationName);
		sb.append("]");
		return sb.toString();
	}

	@Transient
	public long id() {
		return id.getStationId();
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof MetroStation) {
			return ((MetroStation) arg0).id() == id();
		}
		return false;
	}

	public String name() {
		return stationName;
	}

	@Transient
	public AFCNodeLevel level() {
		return AFCNodeLevel.SC;
	}

	private Map<Long, MetroDevice> deviceMap = new HashMap<Long, MetroDevice>();

	private List<MetroDevice> devices = new ArrayList<MetroDevice>();

	/**
	 * @return the devices
	 */
	@Transient
	public List<MetroDevice> getDevices() {
		return devices;
	}

	@Override
	@Transient
	public List<MetroDevice> getSubNodes() {
		return getDevices();
	}

	/**
	 * @param devices
	 *            the devices to set
	 */
	public void setDevices(List<MetroDevice> devices) {
		this.devices = devices;
		if (devices != null) {
			for (MetroDevice metroDevice : devices) {
				deviceMap.put(AFCApplication.getNodeId(metroDevice.id()), metroDevice);
				metroDevice.setParent(this);
			}
		}
	}

	/**
	 * @return the deviceMap
	 */
	@Transient
	public Map<Long, MetroDevice> getDeviceMap() {
		return deviceMap;
	}

	public MetroStation(MetroStation metroStation) {

		this.id = new MetroStationId(metroStation.id);
		this.lineName = metroStation.lineName;
		this.stationName = metroStation.stationName;
		this.ipAddress = metroStation.ipAddress;
		this.imageLocation = new AFCNodeLocation(metroStation.imageLocation);
		this.textLocation = new AFCNodeLocation(metroStation.textLocation);
		this.picName = metroStation.picName;
		this.backPicName = metroStation.backPicName;
		this.status = metroStation.status;
		this.remark = metroStation.remark;
		//this.deviceMap = deviceMap;
		//this.devices = metroStation.devices;
	}

	@Override
	public boolean addSubNode(MetroNode subMetroNode) {
		if (devices == null) {
			devices = new ArrayList<MetroDevice>();
		}
		boolean add = devices.add((MetroDevice) subMetroNode);
		Collections.sort(devices, MetroNodeComparator.comparator);
		return add;
	}

	@Override
	public boolean removeSubNode(MetroNode subMetroNode) {
		return devices.remove(subMetroNode);
	}

	private String extJsonValue;

	@View(label = "扩展信息")
	@ViewExpansion(viewClass = "${MetroStation.extValue}")
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
		String extClassName = SystemPropertyUtil.resolvePlaceholders("${MetroStation.extValue}");
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

	@Transient
	public <EXT> EXT decode(Class<EXT> type) {
//		if (extJsonValue == null) {
			return null;
//		}
//		EXT decode = JSONDecoder.decode(extJsonValue, type);
//		this.extValue = decode;
//		return decode;
	}

	@Override
	@Transient
	public long getNodeId() {

		//		//仅适用于石家庄项目；
		//		return id() * 10000 + AFCDeviceType.SC * 100;
		return AFCApplication.getNodeId(id());
	}

	@Override
	@Transient
	public short getNodeType() {
		return AFCApplication.getNodeType(id());
	}

	@Override
	@Transient
	public MetroLine getParent() {
		return (MetroLine) super.getParent();
	}

}
