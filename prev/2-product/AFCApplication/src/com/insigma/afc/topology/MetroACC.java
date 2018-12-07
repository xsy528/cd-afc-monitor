package com.insigma.afc.topology;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.commons.reflect.ClassUtil;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.ViewExpansion;
import com.insigma.commons.util.SystemPropertyUtil;

/**
 * MetroACC entity.
 * 
 * @author 廖红自
 */
@Entity
@Table(name = "TMETRO_ACC")
public class MetroACC extends MetroNode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private short accID;

	@View(label = "清分名称")
	private String accName;

	@View(label = "网络地址")
	private String ipAddress;

	//@View(label = "图片坐标")
	private AFCNodeLocation imageLocation;

	//@View(label = "文字坐标")
	private AFCNodeLocation textLocation;

	@View(label = "图片名称", type = "File")
	private String picName;

	private String remark;

	private int status;

	public MetroACC(short accID, String accName, int status) {
		this.accID = accID;
		this.accName = accName;
		this.status = status;
	}

	public MetroACC() {

	}

	@Id
	@Column(name = "ACC_ID", length = 5)
	public short getAccID() {
		return accID;
	}

	public void setAccID(short accID) {
		this.accID = accID;
	}

	@Column(name = "ACC_NAME", length = 30)
	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	@Column(name = "STATUS", length = 5)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	@Column(name = "PIC_NAME")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Transient
	public long id() {
		return accID;
	}

	public String name() {
		return accName;
	}

	@Transient
	public AFCNodeLevel level() {
		return AFCNodeLevel.ACC;
	}

	private Map<Long, MetroLine> lineMap = new HashMap<Long, MetroLine>();

	private List<MetroLine> lines;

	/**
	 * @return the imageLocation
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "IMAGE_X", nullable = false) ),
			@AttributeOverride(name = "y", column = @Column(name = "IMAGE_Y", nullable = false) ),
			@AttributeOverride(name = "angle", column = @Column(name = "IMAGE_ANGLE", nullable = false) ) })
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
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "TEXT_X", nullable = false) ),
			@AttributeOverride(name = "y", column = @Column(name = "TEXT_Y", nullable = false) ),
			@AttributeOverride(name = "angle", column = @Column(name = "TEXT_ANGLE", nullable = false) ) })
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

	@Transient
	public Map<Long, MetroLine> getLineMap() {
		return lineMap;
	}

	@Transient
	public List<MetroLine> getLineList() {
		return lines;
	}

	@Override
	@Transient
	public List<MetroLine> getSubNodes() {
		return getLineList();
	}

	public void setLines(List<MetroLine> lines) {
		this.lines = lines;
		if (lines != null) {
			for (MetroLine metroLine : lines) {
				lineMap.put(AFCApplication.getNodeId(metroLine.id()), metroLine);
			}
		}
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof MetroACC) {
			return ((MetroACC) arg0).id() == id();
		}
		return false;
	}

	@Override
	public boolean addSubNode(MetroNode subMetroNode) {
		boolean add = lines.add((MetroLine) subMetroNode);
		Collections.sort(lines, MetroNodeComparator.comparator);
		return add;
	}

	@Override
	public boolean removeSubNode(MetroNode subMetroNode) {
		return lines.remove(subMetroNode);
	}

	private String extJsonValue;

	@View(label = "扩展信息", group = "==扩展信息==")
	@ViewExpansion(viewClass = "${MetroACC.extValue}")
	@Transient
	private Object extValue;

	@Column(name = "EXT_JSON_VALUE", length = 2000)
	public String getExtJsonValue() {
		return extJsonValue;
	}

	public void setExtJsonValue(String extJsonValue) {
		this.extJsonValue = extJsonValue;
//		if (extJsonValue == null || "".equals(extJsonValue)) {
//			return;
//		}
//		String extClassName = SystemPropertyUtil.resolvePlaceholders("${MetroACC.extValue}");
//		Class<?> clazz = ClassUtil.forName(extClassName);
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
