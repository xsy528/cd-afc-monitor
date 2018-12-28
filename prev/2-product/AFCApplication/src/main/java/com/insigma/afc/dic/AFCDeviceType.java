/* 
 * 日期：2010-10-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.DicOverwriteInitor;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.dic.loader.IDicClassListProvider;

/**
 * 产品级设备类型 <br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Dic(overClass = AFCDeviceType.class)
public class AFCDeviceType extends Definition {

	private static AFCDeviceType instance = new AFCDeviceType();

	public static AFCDeviceType getInstance() {
		return instance;
	}

	//@DicItem(name = "自动检票机", group = "SLE")
	public static Short GATE = null;//0x04;


	@DicItem(name = "编码分拣机", group = "E/S")
	public static Short E_S = 0x09;



	@DicItem(name = "清分中心设备", key = "ACC")
	public static Short CCHS = 0x01;

	@DicItem(name = "线路中心设备", key = "LC")
	public static Short LC = 0x02;

	@DicItem(name = "车站中心设备", key = "SC")
	public static Short SC = 0x03;

	@DicItem(name = "进站闸机", key = "ENG", group = "SLE", index = 4)
	public static Short ENG = DeviceType_t.DT_ENG;

	@DicItem(name = "出站闸机", key = "EXG", group = "SLE", index = 5)
	public static Short EXG = DeviceType_t.DT_EXG;

	@DicItem(name = "双向闸机", key = "REG", group = "SLE", index = 6)
	public static Short REG = DeviceType_t.DT_RG;

	@DicItem(name = "半自动售票机", key = "BOM", group = "SLE", index = 1)
	public static Short POST = DeviceType_t.DT_BOM;

	@DicItem(name = "自动售票机", key = "TVM", group = "SLE", index = 2)
	public static Short TVM = DeviceType_t.DT_TVM;

	@DicItem(name = "查询充值机", key = "ISM", group = "SLE", index = 9)
	public static Short TSM = DeviceType_t.DT_ISM;

	@DicItem(name = "手持式验票机", key = "PCA", group = "SLE", index = 8)
	public static Short PCA = DeviceType_t.DT_PCA;

	@DicItem(name = "编码分拣机", key = "ES")
	public static Short ES = DeviceType_t.DT_ES;

	//为了字典类覆盖
	@DicItem(name = "自动加值机")
	public static Short AVM = 0x05;

	@DicItem(name = "自动查询机")
	public static Short TCM = 0x0b;




	public static void main(String[] args) {
		IDicClassListProvider dicp = new IDicClassListProvider() {

			public List<Class<?>> getDicClassList() {
				List<Class<?>> list = new ArrayList<Class<?>>();
				list.add(AFCDeviceType.class);
				return list;
			}
		};
		DicOverwriteInitor.init(dicp);
		System.out.println("--------- product value---------");
		System.out.println("GATE:" + AFCDeviceType.GATE);
		System.out.println("TVM:" + AFCDeviceType.TVM);
		System.out.println("--------- product get---------");
		System.out.println("AVM:" + AFCDeviceType.getInstance().getNameByValue(AFCDeviceType.AVM));
		System.out.println("--------- product code List---------");
		System.out.println(StringUtils.join(AFCDeviceType.getInstance().getValueList(), "\n"));
		System.out.println("--------- product name map---------");
		System.out.println(StringUtils.join(AFCDeviceType.getInstance().getNameList(), "\n"));
		System.out.println("--------- product @dicItem map---------");
		System.out.println(AFCDeviceType.getInstance().getDicItemMap());
		System.out.println(AFCDeviceType.getInstance().getDicItecEntryMap());
	}
}
