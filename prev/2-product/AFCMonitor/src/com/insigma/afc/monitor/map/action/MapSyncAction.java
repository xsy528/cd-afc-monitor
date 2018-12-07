package com.insigma.afc.monitor.map.action;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.tree.ITreeProvider;

public class MapSyncAction extends Action {

	private ILogService logSysService;

	private ITreeProvider treeProvider;

	private Class nodeType = MetroStation.class;

	private int logModuleCode = ModuleCode.MODULE_OTHER;

	private Command activeCommand;

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public class MapSyncActionHandler extends ActionHandlerAdapter {

		@Override
		public void perform(final ActionContext context) {
			MapSyncDialog dlg = new MapSyncDialog(context.getFrameWork(), SWT.NONE);
			dlg.setTreeProvider(getTreeProvider());
			dlg.setNodeClass(nodeType);
			dlg.setActiveCommand(activeCommand);
			if (this.logService != null) {
				dlg.setSynLogService(logService);
			}
			dlg.open();
		}
	}

	public MapSyncAction() {
		super("地图同步");
		setImage("/com/insigma/afc/monitor/images/toolbar/map-sync.png");
		setHandler(new MapSyncActionHandler());
	}

	public Class getNodeType() {
		return nodeType;
	}

	public void setNodeType(Class nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return the activeCommand
	 */
	public Command getActiveCommand() {
		return activeCommand;
	}

	/**
	 * @param activeCommand
	 *            the activeCommand to set
	 */
	public void setActiveCommand(Command activeCommand) {
		this.activeCommand = activeCommand;
	}

	/**
	 * @return the logModuleCode
	 */
	public int getLogModuleCode() {
		return logModuleCode;
	}

	/**
	 * @param logModuleCode
	 *            the logModuleCode to set
	 */
	public void setLogModuleCode(int logModuleCode) {
		this.logModuleCode = logModuleCode;
	}

	public ILogService getLogSysService() {
		return logSysService;
	}

	public void setLogSysService(ILogService logSysService) {
		this.logSysService = logSysService;
	}

}
