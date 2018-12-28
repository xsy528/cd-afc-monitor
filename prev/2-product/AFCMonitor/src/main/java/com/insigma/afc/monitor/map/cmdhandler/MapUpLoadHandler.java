package com.insigma.afc.monitor.map.cmdhandler;

import java.io.File;
import java.util.List;

import com.insigma.afc.dic.AFCFTPFileType;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.commons.application.Application;
import com.insigma.commons.config.NetworkConfig;
import com.insigma.commons.io.FileUtil;

public abstract class MapUpLoadHandler {//extends AFCCommandHandler {
//
//	public abstract int getID();
//
//	@Override
//	public CmdHandlerResult execute(int id, String userid, Long src, Object arg, List<MetroNode> targets) {
//		logger.info("开始清理地图导入文件夹");
//		try {
//			NetworkConfig networkInfo = Application.getConfig(NetworkConfig.class);
//			IServerFolderService folderService = Application.getService(IServerFolderService.class);
//			String localFilePath = folderService.getLocalFilePath(networkInfo.getLineNo().shortValue(),
//					networkInfo.getStationNo(), AFCFTPFileType.STATION_MAP, null);
//			File dir = new File(localFilePath);
//			boolean hasDeleted = true;
//			for (File file : dir.listFiles()) {
//				if (!file.delete()) {
//					hasDeleted = false;
//				}
//			}
//			if (!hasDeleted) {
//				FileUtil.deleteFiles(dir);
//			}
//			CmdHandlerResult result = new CmdHandlerResult();
//			result.isOK = true;
//			result.returnValue = (short) 0x00;
//			logger.info("地图导入文件夹清理完毕");
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			CmdHandlerResult result = new CmdHandlerResult();
//			result.isOK = false;
//			result.returnValue = (short) 0x01;
//			return result;
//		}
//	}

}
