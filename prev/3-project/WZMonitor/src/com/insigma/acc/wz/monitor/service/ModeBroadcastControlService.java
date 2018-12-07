/* 
 * 日期：2010-12-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.acc.wz.define.WZAFCCmdLogType;
import com.insigma.acc.wz.monitor.form.WZModeInfo;
import com.insigma.acc.wz.monitor.handler.ModeBroadcastCheckDialog;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.search.TmoModeBroadcastViewItem;
import com.insigma.afc.monitor.service.IModeService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.IUser;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 模式广播控制服务
 * 
 * @author Zhou-Xiaowei
 */
public class ModeBroadcastControlService {

	private static final Log s_log = LogFactory.getLog(ModeBroadcastControlService.class);

	private ModeService modeService = null;

	private ILogService logService = null;

	private List<TmoModeBroadcastViewItem> modeBroadcastList;

	private String validtaionUserId = "";

	private List<String[]> validationList = new ArrayList<String[]>();

	final short AUTO_BROADCAST = 0;

	final short MANUL_BROADCAST = 1;

	final short NO_VALIDATION = 0;

	final short SEND_SUCCESS = 1;

	final short SEND_FAILURE = 2;

	@SuppressWarnings("unchecked")
	public ModeBroadcastControlService(Object[] result, ILogService logService) {
		this.logService = logService;
		modeBroadcastList = (List<TmoModeBroadcastViewItem>) result[0];

		IUser user = (IUser) Application.getUser();
		validtaionUserId = user.getUserID();

		try {
			modeService = (ModeService) Application.getService(IModeService.class);
		} catch (ServiceException e) {
			s_log.error("获取IModeService失败。", e);
		}
	}

	public void send() {
		if (modeBroadcastList == null || modeBroadcastList.size() == 0) {
			MessageForm.Message("选择模式广播记录为空。");
			return;
		}
		for (TmoModeBroadcastViewItem item : modeBroadcastList) {
			List<MetroStation> destLogicAddressList;
			MetroStation destStationAddress;
			if (item.getTargetId() != null && !item.getTargetId().equals("")) {
				destLogicAddressList = new ArrayList<MetroStation>();
				//                destLineAddress = modeService.getDestMetroLine(Long.decode(item
				//                        .getTargetId().substring(item.getTargetId().lastIndexOf('/') + 1)));
				destStationAddress = modeService.getMetroStationByStationId(
						Integer.parseInt(item.getTargetId().substring(item.getTargetId().lastIndexOf('/') + 3), 16));

				destLogicAddressList.add(destStationAddress);
			} else {
				destLogicAddressList = getAllMetroStation();
			}

			for (MetroStation dest : destLogicAddressList) {
				String[] viewItem = new String[4];
				viewItem[0] = getStatioNameById(dest.getId().getStationId());
				viewItem[1] = item.getModeCode();
				if (null == item.getLineName() || "".equals(item.getLineName())) {
					viewItem[2] = "--";
				} else {
					viewItem[2] = item.getLineName();
				}
				if (null == item.getStationName() || "".equals(item.getStationName())) {
					viewItem[3] = "--";
				} else {
					viewItem[3] = item.getStationName();
				}
				validationList.add(viewItem);
			}
		}

		if (validationList == null || validationList.size() == 0) {
			s_log.error("模式广播列表为空！");
			return;
		}
		ModeBroadcastCheckDialogRunnable runnable = new ModeBroadcastCheckDialogRunnable();
		Display.getDefault().syncExec(runnable);
		if (runnable.getResult() == null) {
			s_log.error("模式广播列表校验错误！");
			return;
		}
		int rst = (Integer) runnable.getResult();

		if (rst != 1) {
			s_log.error("模式广播列表校验失败！");
			return;
		}

		String resultMessageShow = "发送结果：\n";
		short sendResult = -1;
		for (TmoModeBroadcastViewItem item : modeBroadcastList) {
			// 构造发送数据
			List<WZModeInfo> modeChangeDataList = new ArrayList<WZModeInfo>();
			WZModeInfo modeInfo = new WZModeInfo();
			Integer modeCode = Integer.decode(item.getModeCode().substring(item.getModeCode().lastIndexOf('/') + 1));
			Integer stationId = Integer
					.parseInt(item.getStationName().substring(item.getStationName().lastIndexOf('/') + 3), 16);
			modeInfo.setModeCode(modeCode);
			modeInfo.setStationId(stationId);
			modeInfo.setModeOccurTime(DateTimeUtil.stringToDate(item.getModeUploadTime(), "yyyy-mm-dd hh24:mi:ss"));
			modeChangeDataList.add(modeInfo);

			// 构造发送目的地址
			List<MetroStation> destLogicAddressList;
			MetroStation destStationAddress;

			if (item.getTargetId() != null && !item.getTargetId().equals("")) {
				destLogicAddressList = new ArrayList<MetroStation>();

				//                destLineAddress = modeService.getDestMetroLine(Long.decode(item
				//                        .getTargetId().substring(item.getTargetId().lastIndexOf('/') + 1)));

				destStationAddress = modeService.getMetroStationByStationId(
						Integer.parseInt(item.getTargetId().substring(item.getTargetId().lastIndexOf('/') + 3), 16));

				destLogicAddressList.add(destStationAddress);
			} else {
				s_log.info("向全路网发送！");
				//                destLogicAddressList = getLineIds();
				destLogicAddressList = getAllMetroStation();
			}

			if (destLogicAddressList == null || destLogicAddressList.size() == 0) {
				s_log.error("目的地址列表为空！");
				return;
			}

			boolean deleteFlag = true;

			for (MetroNode dest : destLogicAddressList) {
				CmdHandlerResult result = null;
				try {
					result = ((ICommandService) Application.getService(ICommandService.class)).command(
							CommandType.CMD_BROADCAST_MODE, Application.getUser().getUserID(),
							AFCApplication.getNodeId(), (Object) modeChangeDataList, dest);
				} catch (Exception e) {
					resultMessageShow += "发送命令失败：工作台与车站["
							+ String.format("0x%04x", ((MetroStation) dest).getId().getStationId()) + "]通讯前置机离线\n";
					sendResult = 2;
					s_log.error("发送命令失败：工作台与车站[" + String.format("0x%04x", ((MetroStation) dest).getId().getStationId())
							+ "]通讯前置机离线。", e);
				}
				if (sendResult != 2) {
					//					resultMessageShow += "";
					//                    // 06h 下游节点应答超时
					//                    if (result.returnValue != 0x06) {
					//                        if (result == 0) {
					//                            resultMessageShow += "向线路["
					//                                    + String.valueOf(dest.getLineId())
					//                                    + "]发送" + "模式广播命令成功";
					//                            sendResult = 0;
					//                        } else {
					//                            resultMessageShow += "向线路["
					//                                    + String.valueOf(dest.getLineId())
					//                                    + "]发送" + "模式广播命令失败";
					//                            sendResult = 1;
					//                        }
					//                    } else {
					//                        resultMessageShow += "向线路["
					//                                + String.valueOf(dest.getLineId()) + "]发送"
					//                                + "模式广播命令失败";
					//                        sendResult = 1;
					//
					//                    }

					if (result.isOK) {
						resultMessageShow += "向车站["
								+ String.format("0x%04x", ((MetroStation) dest).getId().getStationId()) + "]发送"
								+ "模式广播命令成功\n";
						sendResult = 0;
					} else {
						resultMessageShow += "向车站["
								+ String.format("0x%04x", ((MetroStation) dest).getId().getStationId()) + "]发送"
								+ "模式广播命令失败\n";
						sendResult = 1;

					}

					s_log.info("sendResult: " + sendResult);

					// 保存命令执行结果
					saveCmdResult(dest,
							"模式广播[" + item.getModeCode().substring(0, item.getModeCode().lastIndexOf('/')) + "]",
							sendResult);

					// save to TMO_MODE_BROADCAST
					if (sendResult == 0) {
						if (item.getBroadcastType().equals("自动")) {
							deleteBroadcastData(item.getId());
							saveAutoModeBroadcast(item, SEND_SUCCESS);
						} else if (item.getBroadcastType().equals("手动")) {
							if (deleteFlag) {
								deleteBroadcastData(item.getId());
								deleteFlag = false;
							}
							saveManualModeBroadcast(item, SEND_SUCCESS, dest.getNodeId());
						}
					} else if (sendResult == 1) {
						if (item.getBroadcastType().equals("手动")) {
							if (deleteFlag) {
								deleteBroadcastData(item.getId());
								deleteFlag = false;
							}
							saveManualModeBroadcast(item, SEND_FAILURE, dest.getNodeId());
						} else if (item.getBroadcastType().equals("自动")) {
							deleteBroadcastData(item.getId());
							saveAutoModeBroadcast(item, SEND_FAILURE);
						}
					}
				}
			}
		}
		s_log.info(resultMessageShow);
		// changeBy ChenYao trac23067模式广播记录存储类型出错
		//		try {
		//			logService = Application.getService(ILogService.class);
		//		} catch (ServiceException e) {
		//			s_log.error("获取ILogService失败。", e);
		//		}
		if (sendResult == 0) {
			MessageForm.Message("提示信息", resultMessageShow, SWT.ICON_INFORMATION);
			logService.doBizLog(resultMessageShow);
		} else {
			MessageForm.Message("错误信息", resultMessageShow, SWT.ICON_ERROR);
			logService.doBizErrorLog(resultMessageShow);
		}

	}

	private void saveCmdResult(MetroNode dest, String cmdName, Short cmdResult) {
		TmoCmdResult tmoCmdResult = new TmoCmdResult();
		tmoCmdResult.setOccurTime(DateTimeUtil.getNow());
		tmoCmdResult.setCmdName(cmdName);
		tmoCmdResult.setLineId((short) (dest.getLineId() >> 24));
		tmoCmdResult.setStationId(0);
		tmoCmdResult.setNodeId(dest.getNodeId());
		tmoCmdResult.setUploadStatus((short) 0);
		tmoCmdResult.setOperatorId(Application.getUser().getUserID());//
		tmoCmdResult.setCmdType(WZAFCCmdLogType.LOG_OTHER_MODE.shortValue());
		tmoCmdResult.setCmdResult(cmdResult);

		modeService.getCommonDAO().saveOrUpdateObj(tmoCmdResult);
	}

	private void saveAutoModeBroadcast(TmoModeBroadcastViewItem item, short status) {
		TmoModeBroadcast tmoModeBroadCast = new TmoModeBroadcast();
		tmoModeBroadCast.setLineId(
				Long.valueOf(item.getLineName().substring(item.getLineName().lastIndexOf('/') + 3), 16).shortValue());
		tmoModeBroadCast.setStationId(
				Integer.parseInt(item.getStationName().substring(item.getStationName().lastIndexOf('/') + 3), 16));
		tmoModeBroadCast
				.setModeCode(Short.decode(item.getModeCode().substring(item.getModeCode().lastIndexOf('/') + 1)));
		tmoModeBroadCast.setOperatorId(validtaionUserId);
		tmoModeBroadCast.setModeBroadcastTime(new Date());
		tmoModeBroadCast
				.setModeEffectTime(DateTimeUtil.parseStringToDate(item.getModeUploadTime(), "yyyy-MM-dd HH:mm:ss"));

		tmoModeBroadCast.setBroadcastStatus(status);
		tmoModeBroadCast.setBroadcastType(AUTO_BROADCAST);

		tmoModeBroadCast.setTargetId(
				Long.parseLong(item.getTargetId().substring(item.getTargetId().lastIndexOf('/') + 3), 16) << 16);

		modeService.getCommonDAO().saveOrUpdateObj(tmoModeBroadCast);
	}

	private void saveManualModeBroadcast(TmoModeBroadcastViewItem item, short broadcastStatus, long targetId) {
		TmoModeBroadcast tmoModeBroadCast = new TmoModeBroadcast();
		tmoModeBroadCast.setLineId(
				Long.valueOf(item.getLineName().substring(item.getLineName().lastIndexOf('/') + 3), 16).shortValue());
		tmoModeBroadCast.setStationId(
				Integer.parseInt(item.getStationName().substring(item.getStationName().lastIndexOf('/') + 3), 16));
		tmoModeBroadCast
				.setModeCode(Short.decode(item.getModeCode().substring(item.getModeCode().lastIndexOf('/') + 1)));
		tmoModeBroadCast.setOperatorId(validtaionUserId);
		tmoModeBroadCast.setModeBroadcastTime(new Date());
		tmoModeBroadCast
				.setModeEffectTime(DateTimeUtil.parseStringToDate(item.getModeUploadTime(), "yyyy-MM-dd HH:mm:ss"));

		tmoModeBroadCast.setBroadcastStatus(broadcastStatus);
		tmoModeBroadCast.setBroadcastType(MANUL_BROADCAST);

		tmoModeBroadCast.setTargetId(targetId);

		modeService.getCommonDAO().saveOrUpdateObj(tmoModeBroadCast);
	}

	private void deleteBroadcastData(Long recordId) {
		String hql = "delete TmoModeBroadcast t  where t.recordId=?";
		modeService.getCommonDAO().executeHQLUpdate(hql, recordId);
	}

	//    private List<Long> getLineIds() {
	//        IMetroNodeService metroNodeService = null;
	//        try {
	//            metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
	//        } catch (ServiceException e1) {
	//            s_log.error("获取IMetroNodeService服务失败！");
	//            e1.printStackTrace();
	//        }
	//
	//        List<MetroLine> metroLineList = metroNodeService.getAllMetroLine();
	//
	//        List<Long> lineIds = new ArrayList<Long>();
	//        RouteAddressUtil routeAddressUtil = new RouteAddressUtil();
	//        if (metroLineList != null && !metroLineList.isEmpty()) {
	//            for (MetroLine line : metroLineList) {
	//                lineIds.add(routeAddressUtil.getDestAdressByLineId(line.getLineID()));
	//            }
	//
	//            if (lineIds != null && lineIds.size() != 0) {
	//                return lineIds;
	//            } else {
	//                return null;
	//            }
	//        } else {
	//            return null;
	//        }
	//    }

	private List<MetroLine> getAllMetroLine() {
		IMetroNodeService metroNodeService = null;
		try {
			metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
		} catch (ServiceException e1) {
			s_log.error("获取IMetroNodeService服务失败！");
			e1.printStackTrace();
		}

		List<MetroLine> metroLineList = metroNodeService.getAllMetroLine();

		if (metroLineList != null && !metroLineList.isEmpty()) {
			return metroLineList;
		} else {
			return null;
		}
	}

	private List<MetroStation> getAllMetroStation() {
		IMetroNodeService metroNodeService = null;
		try {
			metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
		} catch (ServiceException e1) {
			s_log.error("获取IMetroNodeService服务失败！");
			e1.printStackTrace();
		}

		List<MetroStation> metroStationList = metroNodeService.getAllMetroStation();

		if (metroStationList != null && !metroStationList.isEmpty()) {
			return metroStationList;
		} else {
			return null;
		}
	}

	private String getLineNameById(short lineId) {
		IMetroNodeService metroNodeService = null;
		try {
			metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
		} catch (ServiceException e1) {
			s_log.error("获取IMetroNodeService服务失败！");
			e1.printStackTrace();
		}

		List<MetroLine> metroLineList = metroNodeService.getAllMetroLine();

		if (metroLineList == null || metroLineList.size() == 0) {
			s_log.error("获取对应线路ID的线路名称失败！");
			return "";
		}
		for (MetroLine line : metroLineList) {
			if (line.getLineID() == lineId) {
				return line.getLineName() + "/" + String.format("0x%02x", lineId);
			}
		}

		return null;
	}

	private String getStatioNameById(int stationId) {
		IMetroNodeService metroNodeService = null;
		try {
			metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
		} catch (ServiceException e1) {
			s_log.error("获取IMetroNodeService服务失败！");
			e1.printStackTrace();
		}

		List<MetroStation> metroStationList = metroNodeService.getAllMetroStation();

		if (metroStationList == null || metroStationList.size() == 0) {
			s_log.error("获取对应线路ID的线路名称失败！");
			return "";
		}
		for (MetroStation station : metroStationList) {
			if (station.getId().getStationId() == stationId) {
				return station.getStationName() + "/" + String.format("0x%04x", stationId);
			}
		}

		return null;
	}

	private Object dialogGo(int style) {
		if (Display.getDefault().getShells().length == 0) {
			return -1;
		}
		Shell shell = Display.getDefault().getActiveShell();
		if (shell == null) {
			shell = new Shell(Display.getDefault(), SWT.APPLICATION_MODAL | SWT.ON_TOP);
		}
		ModeBroadcastCheckDialog dialog = new ModeBroadcastCheckDialog(shell, SWT.None);
		dialog.setValidationList(validationList);
		return dialog.open();
	}

	private class ModeBroadcastCheckDialogRunnable implements Runnable {
		private Object result;

		public void run() {
			result = dialogGo(0);
		}

		public Object getResult() {
			return result;
		}
	}

	public String getValidtaionUserId() {
		return validtaionUserId;
	}

	public void setValidtaionUserId(String validtaionUserId) {
		this.validtaionUserId = validtaionUserId;
	}

}
