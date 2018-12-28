/* 
 * 日期：2010-09-02
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 串口配置 Ticket:
 * 
 * @author fenghong
 */
@XStreamAlias("SerialPortConfig")
public class SerialPortConfig extends ConfigurationItem {

	// 串口类型
	private short portType;

	// 串口号
	private int serialPort;

	private int baudRate;

	private int dataBits;

	private int stopBits;

	//奇偶检验
	private int parityCheck;

	public short getPortType() {
		return portType;
	}

	public void setPortType(short portType) {
		this.portType = portType;
	}

	public int getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(int serialPort) {
		this.serialPort = serialPort;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getDataBits() {
		return dataBits;
	}

	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	public int getStopBits() {
		return stopBits;
	}

	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}

	public int getParityCheck() {
		return parityCheck;
	}

	public void setParityCheck(int parityCheck) {
		this.parityCheck = parityCheck;
	}

}
