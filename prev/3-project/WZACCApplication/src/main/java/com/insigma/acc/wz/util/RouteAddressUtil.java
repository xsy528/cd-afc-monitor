package com.insigma.acc.wz.util;

import com.insigma.acc.wz.define.WZDeviceType;
import com.insigma.acc.wz.define.WZFTPFileType;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCDeviceFileType;
import com.insigma.afc.topology.INodeIdStrategy;
import com.insigma.commons.application.Application;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Ticket:* <b> nodeId的转换策略</b><br/>
 * <li>根据线路，车站编号转换成NodeId</li>
 * <li>反之，根据NodeId获取线路编号、车站编号、设备编号</li>
 * @author yangyang
 */
public class RouteAddressUtil implements INodeIdStrategy {

	private static final Logger logger = Logger.getLogger(RouteAddressUtil.class);

	public static final int ACC_ADDRESS_TYPE = 1;

	public static final int LC_ADDRESS_TYPE = 2;

	public static final int SC_ADDRESS_TYPE = 3;

	public static final int SLE_ADDRESS_TYPE = 4;


	private static String dailyEndTime;

	/**
	 * 获取父节点
	 * 
	 * @param localAddress
	 * @return
	 */
	@Override
	public long getFatherNode(long localAddress) {
		int level = getAddressType(localAddress);
		switch (level) {
		case SLE_ADDRESS_TYPE:
			level = SC_ADDRESS_TYPE;
			break;
		case SC_ADDRESS_TYPE:
			level = LC_ADDRESS_TYPE;
			break;
		case LC_ADDRESS_TYPE:
			level = ACC_ADDRESS_TYPE;
			break;
		}
		return getAddress(localAddress, level);
	}

	/**
	 * 获取地址类型
	 * 
	 * @param address
	 * @return
	 * 
	 */
	public static int getAddressType(final long address) {

		//ACC 通信前置机
		//		if (((address & 0xffffff00L) >>> 8) == WZDeviceType.CCHS) {
		//			return ACC_ADDRESS_TYPE;
		//		}
		//		addressToSixtenn = Long.parseLong(Long.toHexString(address));
		if ((address & 0xffffffffL) == 0L) {
			return ACC_ADDRESS_TYPE;
		}

		// LC 通信前置机
		//		if ((address & 0xFF000000L) != 0L && ((address & 0x00FFFF00L) >>> 8) == WZDeviceType.LC) {
		//			return LC_ADDRESS_TYPE;
		//		}
		if ((address & 0xFF000000L) != 0L && (address & 0x00FFFFFFL) == 0L) {
			return LC_ADDRESS_TYPE;
		}

		// SC 通信前置机
		//		if ((address & 0xFF000000L) != 0L && (address & 0x00FF0000L) != 0L
		//				&& ((address & 0x0000FF00L) >>> 8) == WZDeviceType.SC) {
		//			return SC_ADDRESS_TYPE;
		//		}
		if ((address & 0xFF000000L) != 0L && (address & 0x00FF0000L) != 0L && (address & 0x0000FFFFL) == 0L) {
			return SC_ADDRESS_TYPE;
		}

		// SLE
		return SLE_ADDRESS_TYPE;

	}

	public static long getAddress(final long address) {
		return getAddress(address, getAddressType(address));
	}

	/**
	 * 根据节点类型获取指定父节点信息
	 * 
	 * @param address
	 *            节点
	 * @param addressType
	 *            父节点类型
	 * @return 父节点
	 */
	public static long getAddress(final long address, final int addressType) {

		long result = address;
		switch (addressType) {
		case ACC_ADDRESS_TYPE:
			//			result = (address & 0x00000000L) + (AFCDeviceType.CCHS << 8);
			result = address & 0x00000000L;
			break;
		case LC_ADDRESS_TYPE:
			//			result = (address & 0xFF000000L) + (AFCDeviceType.LC << 8);
			result = address & 0xFF000000L;
			break;
		case SC_ADDRESS_TYPE:
			//			result = (address & 0xFFFF0000L) + (AFCDeviceType.SC << 8);
			result = address & 0xFFFF0000L;
			break;
		}
		return result;

	}

	public static long getRouteAddress(final long srcAddress, final long destAddress) {

		int srcType = RouteAddressUtil.getAddressType(srcAddress);
		int destType = RouteAddressUtil.getAddressType(destAddress);
		long routeAddress = 0L;
		int routeType = 0;

		//		long destAddress1 = Long.valueOf(Long.toHexString(destAddress));

		logger.info(String.format("查找源地址：" + Application.getDeviceIdFormat() + "(%x级) 到 目的地址："
				+ Application.getDeviceIdFormat() + "(%x级) 的 路由信息", srcAddress, srcType, destAddress, destType));

		if (srcType == RouteAddressUtil.SLE_ADDRESS_TYPE && destType == RouteAddressUtil.ACC_ADDRESS_TYPE) {
			routeAddress = destAddress;
			routeType = RouteAddressUtil.ACC_ADDRESS_TYPE;
		} else if (srcType < destType) { // 下行      
			routeAddress = destAddress;
			routeType = srcType + 2;
		} else if (srcType > destType) { // 上行
			routeAddress = srcAddress;
			routeType = srcType - 1;
		} else {// 同级
			routeAddress = destAddress;
			routeType = srcType;
		}

		routeAddress = getAddress(routeAddress, routeType);
		return routeAddress;
	}

	@Override
	public short getNodeType(long nodeId) {
		//		long newNodeID = getNodeId(nodeId);
		//		short nodeType = (short) ((newNodeID & 0x0000ff00) >> 8);
		//		return nodeType;

		if ((nodeId >> 24) == 0L) { //ACC
			return WZDeviceType.CCHS;
		} else if ((nodeId >> 24) != 0L && (nodeId & 0x00FFFFFFL) == 0L) { //LC
			return WZDeviceType.LC;
		} else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
				&& ((nodeId & 0x0000FF00L) >> 8) == 0L) { //SC
			return WZDeviceType.SC;
		} else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
				&& ((nodeId & 0x0000FF00L) >> 8) != 0L) {
			return (short) ((nodeId & 0x0000ff00) >> 8); //SLE
		} else {
			throw new RuntimeException("无效的nodeId");
		}

	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static Short geFileHeaderTag_t(final String fileName) {
		String FILE_TYPE;
		//		if (fileName.substring(0, 12).equalsIgnoreCase("Transactions")) {
		//			FILE_TYPE = "U";
		//		} else if (fileName.substring(0, 1).equalsIgnoreCase("A")) {
		//			FILE_TYPE = "A";
		//		} else if (fileName.substring(0, 1).equalsIgnoreCase("R")) {
		//			FILE_TYPE = "R";
		//		} else {
		//			FILE_TYPE = fileName.substring(0, 2);
		//		}
		FILE_TYPE = fileName.substring(0, 2);
		if (AFCDeviceFileType.getInstance().getValueByName(FILE_TYPE) == null) {
			return null;
		}
		Short fileTypeValue = Short.valueOf(AFCDeviceFileType.getInstance().getValueByName(FILE_TYPE) + "");

		return fileTypeValue;
	}

	public static String formatString(final String originalString, final int size, final boolean isAddFront) {
		int stringSize = originalString.length();
		int different = size - stringSize;
		String tempString = "";
		if (different > 0) {
			for (int i = 0; i < different; i++) {
				tempString += "0";
			}
		}
		String resultString = null;
		if (isAddFront) {
			resultString = tempString + originalString;
		} else {
			resultString = originalString + tempString;
		}
		return resultString;
	}

	//节点号由线路+车站+设备类型+设备ID 组成
	//long id ：十进制线路、车站、设备号
	@Override
	public long getNodeId(long id) {
		//		long nodeId = 0;
		//		if (id < 0xff) {
		//			nodeId = (id << 24) | WZDeviceType.LC << 8;
		//		} else if (id > 0x100 && id < 0xffff) {
		//			nodeId = (id << 16) | WZDeviceType.SC << 8;
		//		} else {
		//			return id;
		//		}
		//		return nodeId;

		long nodeId = 0;
		if (id < 0xff) {
			nodeId = (id << 24);
		} else if (id > 0x100 && id < 0xffff) {
			nodeId = (id << 16);
		} else {
			return id;
		}
		return nodeId;

		//		return id;

	}

	@Override
	public short getLineId(long nodeId) {
		if (nodeId < 0xff) {
			return (short) nodeId;
		} else if (nodeId > 0xff && nodeId < 0xffff) {
			return (short) (nodeId >> 8);
		} else {
			return (short) (nodeId >> 24);
		}

	}

	// nodeId:十进制
	@Override
	public int getStationId(long nodeId) {
		//		if (nodeId > 0xff && nodeId < 0xffff) {
		//			return (int) nodeId;
		//		} else if ((nodeId & 0x00FF0000) > 0) {
		//			return (int) ((nodeId & 0xFFFF0000) >> 16);
		//		} else if ((nodeId & 0x00FF0000) == 0) {
		//			return 0;
		//		} else {
		//			throw new RuntimeException("无效的nodeId");
		//		}

		if ((nodeId >> 24) == 0L) { //ACC
			return 0;
		} else if ((nodeId >> 24) != 0L && (nodeId & 0x00FFFFFFL) == 0L) { //LC
			return 0;
		} else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
				&& ((nodeId & 0x0000FF00L) >> 8) == 0L) { //SC
			return (int) ((nodeId & 0xFFFF0000L) >> 16);
		} else if ((nodeId >> 24) != 0L && ((nodeId & 0x00FF0000L) >> 16) != 0L
				&& ((nodeId & 0x0000FF00L) >> 8) != 0L) {
			return (int) ((nodeId & 0xFFFF0000L) >> 16); //SLE
		} else {
			throw new RuntimeException("无效的nodeId");
		}
	}

	@Override
	public short getStationOnlyId(long nodeId) {
		return (short) (nodeId / 10000 % 100);

	}

	// nodeId:十进制
	@Override
	public AFCNodeLevel getNodeLevel(long nodeId) {
		short deviceType = getNodeType(nodeId);
		if (deviceType == WZDeviceType.CCHS) {
			return AFCNodeLevel.ACC;
		} else if (deviceType == WZDeviceType.LC) {
			return AFCNodeLevel.LC;
		} else if (deviceType == WZDeviceType.SC) {
			return AFCNodeLevel.SC;
		} else {
			return AFCNodeLevel.SLE;
		}
	}

	@Override
	public short getDeviceId(long nodeId) {
		// TODO Auto-generated method stub
		return (short) ((nodeId & 0x000000FF));
	}

	/**
	 * 根据设备编号获取设备类型
	 * 
	 * @param deviceId
	 *            01020605：设备类型为06
	 * @return
	 */
	public static long getDevicetype(long deviceId) {
		String hexDeviceId = Long.toHexString(deviceId);
		String devId = formatString(hexDeviceId, 8, true);
		String deviceType = devId.substring(4, 6);
		if (deviceType != null) {
			return Long.valueOf(deviceType);
		}
		return 0;
	}

	@Override
	public List<Object[]> getAllMetroNode() {
		return null;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static Short getFileType(String fileName) {
		// Short FILE_TYPE = Short.valueOf(fileName.substring(0, 2));
		Short FILE_TYPE = 0;
		String ft = fileName.substring(0, 2);
		if (ft != null) {
			if (ft.equals("UA")) {
				FILE_TYPE = WZFTPFileType.UD_TICKET.shortValue();
			} else if (ft.equals("UY")) {
				FILE_TYPE = WZFTPFileType.UD_CARD.shortValue();
			} else if (ft.equals("UM")) {
				FILE_TYPE = WZFTPFileType.UD_PHONECARD.shortValue();
			} else if (ft.equals("UB")) {
				FILE_TYPE = WZFTPFileType.UD_BANKCARD.shortValue();
			} else if (ft.equals("AG")) {
				FILE_TYPE = WZFTPFileType.AGM_AR.shortValue();
			} else if (ft.equals("AB")) {
				FILE_TYPE = WZFTPFileType.BOM_AR.shortValue();
			} else if (ft.equals("AT")) {
				FILE_TYPE = WZFTPFileType.TVM_AR.shortValue();
			} else if (ft.equals("AS")) {
				FILE_TYPE = WZFTPFileType.ISM_AR.shortValue();
			} else if (ft.equals("AP")) {
				FILE_TYPE = WZFTPFileType.PCA_AR.shortValue();
			} else if (ft.equals("UE")) {
				FILE_TYPE = WZFTPFileType.ES_TRANSACTION.shortValue();
			} else if (ft.equalsIgnoreCase("EN")) {
				FILE_TYPE = WZFTPFileType.ES_ENCODING.shortValue();
			}
		}
		return FILE_TYPE;
	}

}
