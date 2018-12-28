package com.insigma.afc.monitor.map.action;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.action.IMapImporter;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.FileDialog;

public class MapImportAction extends Action {

	private IMapImporter importer;

	public IMapImporter getImporter() {
		return importer;
	}

	public void setImporter(IMapImporter importer) {
		this.importer = importer;
	}

	public MapImportAction() {
		super("导入地图");
		setImage("/com/insigma/afc/monitor/images/toolbar/broadcast-mode.png");
		setHandler(new MapImportActionHandler());
	}

	private ILogService logService;

	public class MapImportActionHandler extends ActionHandlerAdapter {

		public void perform(final ActionContext context) {
			logger.info("打开目录文件");
			FileDialog dialog = new FileDialog(context.getFrameWork().getShell(), SWT.OPEN);
			String file = dialog.open();
			if (file == null) {
				return;
			} else {
				try {
					boolean importMap = importer.importMap(file);
					if (logService != null) {
						if (importMap) {
							logService.doBizLog("导入地图" + file + "成功。");
						} else {
							logService.doBizErrorLog("导入地图" + file + "失败。");
						}
					}
					MessageForm.Message("导入地图" + (importMap ? "成功。" : "失败。"));
				} catch (Exception e) {
					MessageForm.Message(e);
				}
			}

		}
	}

	//	public MapImportAction() {
	//		setName("车站地图导入");
	//		setImage("/com/insigma/afc/monitor/images/toolbar/map-sync.png");
	//		setHandler(new MapImportActionHandler());
	//
	//	}

	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

}
