package com.insigma.afc.monitor.action;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.service.IModeService;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.spring.Autowire;

public class ModeBroadcastAction extends NodeAction {

	public class ModeBroadcastActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IModeService modeService;

		@Override
		public void perform(ActionContext context) {

			List<TmoModeUploadInfo> data = null;
			try {
				data = modeService.getModeUploadInfoList(null, null, null, null, null, null, null, 1, 1000);
			} catch (Exception e) {
				logger.error("获取模式广播信息失败。", e);
				return;
			}

			ModeBroadcastDialog dlg = new ModeBroadcastDialog(context.getFrameWork(), SWT.NONE);
			dlg.setModes(data);
			dlg.setTreeProvider(getTreeProvider());
			dlg.setTreeDepth(getTreeDepth());
			dlg.setSelections((List) getNodes());
			dlg.open();
		}
	}

	public ModeBroadcastAction() {
		setName("模式广播");
		setImage("/com/insigma/afc/monitor/images/toolbar/broadcast-mode.png");
		setTargetType(null);
		setHandler(new ModeBroadcastActionHandler());
	}
}
