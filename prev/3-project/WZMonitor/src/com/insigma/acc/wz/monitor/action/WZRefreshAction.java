package com.insigma.acc.wz.monitor.action;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.service.ITsyResourceService;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.ui.ResourceUtil;
import com.swtdesigner.SWTResourceManager;

public class WZRefreshAction extends Action {

	IActionHandler handler = new ActionHandlerAdapter() {
		@Override
		public void perform(final ActionContext context) {
			//AFCApplication.init();
			try {
				ITsyResourceService resourceService = AFCApplication.getService(ITsyResourceService.class);
				resourceService.syncResouce();
				String picName = AFCApplication.getAFCNode().getPicName();
				String path = ResourceUtil.ROOT_RESOURCE_PATH + "/" + picName;
				SWTResourceManager.disposeImage(path);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			AFCApplication.refresh();
			EditorFrameWork editorFrameWork = context.getFrameWork();
			for (FrameWorkView view : editorFrameWork.getViews()) {

				//				if(view.)
				view.refresh();
			}
		}
	};

	public WZRefreshAction() {
		super("刷新");
		setImage("/com/insigma/commons/ui/function/refresh.png");
		setHandler(handler);
	}

}
