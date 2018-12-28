package com.insigma.afc.monitor.map.action;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.dic.AFCFTPFileType;
//import com.insigma.afc.manager.IFTPInfoManager;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.application.Application;
//import com.insigma.commons.communication.ftp.FtpInfo;
//import com.insigma.commons.communication.ftp.FtpUtil;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.file.filter.RegexFilenameFilter;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;

public class MapExportAction extends Action {

	private ILogService logSysService;

	public class MapExportActionHandler extends ActionHandlerAdapter {

		@Override
		public void perform(final ActionContext context) {

			if (logSysService != null) {
				this.logService = logSysService;
			}

			DirectoryDialog dlg = new DirectoryDialog(context.getFrameWork().getShell(), SWT.SAVE);
			String folderPath = dlg.open();
			if (null != folderPath) {
				try {
					boolean hasFile = rmiGenerateFiles();

					if (!hasFile) {
						logger.info("无地图导出");
						return;
					}
					Map<Integer, String> result = export(folderPath);
					Collection<Integer> c = result.keySet();
					Iterator it = c.iterator();
					String ret = result.get(it.next());
					logger.info(ret);
					MessageForm.Message(ret);
					logService.doBizLog(ret);
				} catch (Exception e) {
					logger.error("车站地图导出失败", e);
					logService.doBizErrorLog("车站地图导出失败", e);
					MessageForm.Message("警告信息", "车站地图导出失败。", SWT.ICON_WARNING);
				}
			}
		}

		public boolean rmiGenerateFiles() {
			ICommandService commandService = null;
			try {
				commandService = Application.getService(ICommandService.class);
				commandService.alive();
			} catch (Exception e) {
				logger.error("导出地图失败：工作台与通信服务器离线。", e);
				logService.doBizErrorLog("导出地图失败：工作台与通信服务器离线。", e);
				MessageForm.Message("错误信息", "导出地图失败：工作台与通信服务器离线。", SWT.ICON_ERROR);
				return false;
			}
			CmdHandlerResult command = commandService.command(CommandType.CMD_EXPORT_MAP,
					Application.getUser().getUserID(), 1l);
			return command.isOK;
		}

		public Map<Integer, String> export(String folderPath) throws Exception {
			File folder = new File(folderPath);
			//0表示地图导出成功，1表示FTP信息初始化有问题，2表示仅节点信息下载有问题，3表示仅背景图片信息下载有问题，4表示节点信息和背景图片信息下载都有问题,5下载目录不存在
			Integer resultFlag = new Integer(0);
			String resultMsg = "地图信息导出成功。";
			//初始默认值
			Map<Integer, String> resultInfo = new HashMap<Integer, String>();
			resultInfo.put(resultFlag, resultMsg);
//			if (folder.exists()) {
//				IFTPInfoManager ftpInfoManager = (IFTPInfoManager) Application.getService(IFTPInfoManager.class);
//				FtpInfo ftpInfo = ftpInfoManager.getExportFTPInfo(AFCApplication.getLineId(),
//						AFCApplication.getStationId(), AFCFTPFileType.STATION_MAP);
//				if (ftpInfo == null) {
//					logger.warn("获取FTP信息为空，STATION_MAP EXPORT");
//					resultInfo.clear();
//					resultFlag = 1;
//					resultMsg = "获取FTP信息为空。";
//					resultInfo.put(resultFlag, resultMsg);
//					return resultInfo;
//				}
//				logger.debug("文件下载操作开始");
//				logger.info("FTP目录 : " + ReflectionToStringBuilder.toString(ftpInfo));
//				boolean isDownloadSeccess = false;
//				boolean isDownloadNodeInfos = false;
//				boolean isDownloadMaps = false;
//				try {
//					isDownloadNodeInfos = FtpUtil.downloadFiles(ftpInfo, folderPath,
//							new RegexFilenameFilter("^EQMDATA*.*"));
//					isDownloadMaps = FtpUtil.downloadFiles(ftpInfo, folderPath, new RegexFilenameFilter("^Metro*_*.*"));
//
//					if (isDownloadNodeInfos && isDownloadMaps) {
//						isDownloadSeccess = true;
//					} else if (!isDownloadNodeInfos && isDownloadMaps) {
//						resultInfo.clear();
//						resultFlag = 2;
//						resultMsg = "地图节点信息下载失败，背景图片下载成功。";
//						resultInfo.put(resultFlag, resultMsg);
//					} else if (isDownloadNodeInfos && !isDownloadMaps) {
//						resultInfo.clear();
//						resultFlag = 3;
//						resultMsg = "地图节点信息下载成功，背景图片下载失败。";
//						resultInfo.put(resultFlag, resultMsg);
//					} else {
//						resultInfo.clear();
//						resultFlag = 4;
//						resultMsg = "地图节点信息和背景图片都下载失败。";
//						resultInfo.put(resultFlag, resultMsg);
//					}
//
//					logger.info("地图文件下载:" + (isDownloadSeccess ? "成功。" : "失败。"));
//				} catch (Exception e) {
//					logger.error("地图文件下载失败。", e);
//				}
//			} else {
//				resultInfo.clear();
//				resultFlag = 5;
//				resultMsg = "通信前置机地图下载目录不存在。";
//				resultInfo.put(resultFlag, resultMsg);
//			}
			return resultInfo;
		}
	}

	public MapExportAction() {
		super("导出地图");
		setImage("/com/insigma/afc/monitor/images/toolbar/broadcast-mode.png");
		setHandler(new MapExportActionHandler());
	}

	public void setLogSysService(ILogService logSysService) {
		this.logSysService = logSysService;
	}

}
