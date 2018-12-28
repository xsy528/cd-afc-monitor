/**
 * 
 */
package com.insigma.afc.monitor.map.cmdhandler;

import java.util.List;

import com.insigma.afc.monitor.map.IMapSyncService;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.commons.spring.Autowire;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public abstract class MapSyncCmdHandler{// extends AFCCommandHandler {
//
//	@Autowire
//	IMapSyncService mapSyncService;
//
//	public abstract int getID();
//
//	public CmdHandlerResult execute(int id, String userid, Long src, Object arg, List<MetroNode> targets) {
//		boolean hasFile = generateFiles(targets);
//		if (hasFile) {
//			return exec(targets);
//		} else {
//			CmdHandlerResult result = new CmdHandlerResult();
//			result.isOK = false;
//			result.messages.add("生成地图文件失败");
//			return result;
//		}
//	}
//
//	public abstract MessageResult sendMsg(MetroNode node);
//
//	public abstract boolean generateFiles(List<MetroNode> ids);
//
//	protected CmdHandlerResult exec(List<MetroNode> targets) {
//		CmdHandlerResult result = new CmdHandlerResult();
//		for (MetroNode node : targets) {
//			MessageResult messageResult = sendMsg(node);
//			if (messageResult.isSuccessful()) {
//				result.isOK = messageResult.isSuccessful();
//				result.messages.add("通知发送到 : [" + node.id() + "] 成功");
//			} else {
//				result.isOK = false;
//				result.messages.add("通知发送到 : [" + node.id() + "] 失败");
//			}
//		}
//		result.returnValue = (short) 0x00;
//		return result;
//	}

}
