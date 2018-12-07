package com.insigma.afc.monitor.dialog.sle;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.dic.AFCUpLoadDataFileType;
import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionHandlerAdapter;

public class UploadDataAction extends NodeAction {

	public class UploadDataActionHandler extends ActionHandlerAdapter {
		@SuppressWarnings("unchecked")
		public void perform(Action action) {
			DefinitionCommandDialog dlg = new DefinitionCommandDialog(action.getFrameWork(), SWT.NONE);
			dlg.setSize(500, 550);
			dlg.setText("发送文件上传命令");
			dlg.setTitle("发送文件上传命令");
			dlg.setDescription("描述：向设备发送文件上传命令");
			dlg.setDefinition(AFCUpLoadDataFileType.getInstance());
			dlg.setSelections((List) getNodes());
			dlg.setTreeDepth(getTreeDepth());
			dlg.open();
		}
	}

	public UploadDataAction() {
		setName("上传当前数据");
		setImage("/com/insigma/afc/monitor/images/toolbar/upload-file.png");
		setHandler(new UploadDataActionHandler());
	}
}
