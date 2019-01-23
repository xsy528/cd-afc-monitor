package com.insigma.afc.topology;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.commons.reflect.ClassUtil;
import com.insigma.commons.util.SystemPropertyUtil;

import javax.persistence.*;
import java.util.*;

/**
 * MetroLine entity.
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TMETRO_LINE")
public class MetroLine extends MetroNode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private short lineID;

	private String lineName;

	private String ipAddress;

	private AFCNodeLocation imageLocation;

	private AFCNodeLocation textLocation;

	private String picName;

	private String remark;

	private Short status;

	public MetroLine(short lineID, String lineName, short status) {
		this.lineID = lineID;
		this.lineName = lineName;
		this.status = status;
	}

	public MetroLine() {

	}

	/**
	 * @return the imageLocation
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "image_x", nullable = false)),
			@AttributeOverride(name = "y", column = @Column(name = "image_y", nullable = false)),
			@AttributeOverride(name = "angle", column = @Column(name = "image_angle", nullable = false)) })
	public AFCNodeLocation getImageLocation() {
		if (imageLocation == null) {
			imageLocation = new AFCNodeLocation();
		}
		return imageLocation;
	}

	/**
	 * @param imageLocation the imageLocation to set
	 */
	@Override
	public void setImageLocation(AFCNodeLocation imageLocation) {
		this.imageLocation = imageLocation;
	}

	/**
	 * @return the textLocation
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "text_x", nullable = false)),
			@AttributeOverride(name = "y", column = @Column(name = "text_y", nullable = false)),
			@AttributeOverride(name = "angle", column = @Column(name = "text_angle", nullable = false)) })
	public AFCNodeLocation getTextLocation() {
		if (textLocation == null) {
			textLocation = new AFCNodeLocation();
		}
		return textLocation;
	}

	/**
	 * @param textLocation the textLocation to set
	 */
	@Override
	public void setTextLocation(AFCNodeLocation textLocation) {
		this.textLocation = textLocation;
	}

	@Id
	@Column(name = "LINE_ID", length = 5)
	public short getLineID() {
		return lineID;
	}

	public void setLineID(short lineID) {
		this.lineID = lineID;
	}

	@Column(name = "LINE_NAME", length = 30)
	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	@Column(name = "STATUS", length = 5)
	public Short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Column(name = "IP_ADDRESS", length = 32)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	@Column(name = "PIC_NAME")
	public String getPicName() {
		return picName;
	}

	@Override
	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[线路号:");
		sb.append(lineID);
		sb.append("线路名称:");
		sb.append(lineName);
		sb.append("]");
		return sb.toString();
	}

	@Override
	@Transient
	public long id() {
		return lineID;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof MetroLine) {
			return ((MetroLine) arg0).id() == id();
		}
		return false;
	}

	@Override
	public String name() {
		return lineName;
	}

	@Override
	@Transient
	public AFCNodeLevel level() {
		return AFCNodeLevel.LC;
	}

	private Map<Long, MetroStation> ssMap = new HashMap<Long, MetroStation>();

	private List<MetroStation> ss = new ArrayList<MetroStation>();

	@Transient
	public Map<Long, MetroStation> getStationMap() {
		return ssMap;
	}

	@Transient
	public List<MetroStation> getStationList() {
		return ss;
	}

	@Override
	@Transient
	public List<MetroStation> getSubNodes() {
		return getStationList();
	}

	public void setStations(List<MetroStation> lines) {
		this.ss = lines;
		if (lines != null) {
			for (MetroStation station : lines) {
				ssMap.put(AFCApplication.getNodeId(station.id()), station);
				station.setParent(this);
			}
		}
	}

	@Override
	public boolean addSubNode(MetroNode subMetroNode) {
		boolean add = ss.add((MetroStation) subMetroNode);
		Collections.sort(ss, MetroNodeComparator.comparator);
		return add;
	}

	@Override
	public boolean removeSubNode(MetroNode subMetroNode) {
		return ss.remove(subMetroNode);
	}

	private String extJsonValue;

	//	@View(label = "扩展信息", group = "==扩展信息==")
	//	@ViewExpansion(viewClass = "${MetroLine.extValue}")
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
		String extClassName = SystemPropertyUtil.resolvePlaceholders("${MetroLine.extValue}");
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
}
