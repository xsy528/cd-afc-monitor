package com.insigma.afc.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * FTP文件夹配置
 * 
 * @author fenghong
 */
@XStreamAlias("FolderConfig")
public class FolderConfig extends ConfigurationItem {

	// FTP服务器地址
	private String ipAddr;

	// 用户名
	private String ftpName;

	// 密码
	private String ftpPassword;

	private boolean isPassiveMode = false;

	// 文件大类(像天津的自定有文件大类)
	private String fileFamily;

	// 文件类型
	private String fileType;

	// 上传下载标志0：上传1：下载
	private short upDown;

	// 端口
	private int port = 21;

	// 文件夹目录,FTP服务器本地该文件类型的文件夹名称
	private String folderName;

	/**
	 * 工作目录，为避免和FTPINFO中的workFolder冲突，目前这个域不推荐配置
	 * 这个域存在主要是一个项目中存在两种FTP协议：例如南京：ACC-LC报文中没有workFolder信息，LC-SC有workFolder信息
	 */
	private String workFolder;

	// 文件夹类型，不同的项目有些文件类型需要分车站、线路
	private short folderType;

	// 是否需要备份文件，true为需要备份，其余不备份,会被清理掉
	private boolean needBackup;

	//文件名格式，目前用于便于整理
	private String fileNameFormat;

	public boolean isNeedBackup() {
		return needBackup;
	}

	public void setNeedBackup(boolean needBackup) {
		this.needBackup = needBackup;
	}

	public short getFolderType() {
		return folderType;
	}

	public void setFolderType(short folderType) {
		this.folderType = folderType;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getFtpName() {
		return ftpName;
	}

	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public short getUpDown() {
		return upDown;
	}

	public void setUpDown(short upDown) {
		this.upDown = upDown;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getWorkFolder() {
		return workFolder;
	}

	public void setWorkFolder(String workFolder) {
		this.workFolder = workFolder;
	}

	public String getFileFamily() {
		return fileFamily;
	}

	public void setFileFamily(String fileFamily) {
		this.fileFamily = fileFamily;
	}

	public String getFileNameFormat() {
		return fileNameFormat;
	}

	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}

	public boolean isPassiveMode() {
		return isPassiveMode;
	}

	public void setPassiveMode(boolean isPassiveMode) {
		this.isPassiveMode = isPassiveMode;
	}

}
