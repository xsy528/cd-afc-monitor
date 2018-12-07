package com.insigma.acc.wz.monitor;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import com.insigma.acc.wz.module.WZBaseLogModule;
import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.acc.wz.monitor.action.WZMapSyncAction;
import com.insigma.acc.wz.provider.WZDeviceEventTreeProvider;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.map.action.SaveMapAction;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.editor.IGraphicMapGenerator;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.editorframework.graphic.editor.action.GroupAction;
import com.insigma.commons.editorframework.graphic.editor.action.MoveAction;
import com.insigma.commons.editorframework.graphic.editor.action.MoveAction.MoveMode;
import com.insigma.commons.editorframework.graphic.editor.action.RedoAction;
import com.insigma.commons.editorframework.graphic.editor.action.RotateAction;
import com.insigma.commons.editorframework.graphic.editor.action.RotateAction.RotateMode;
import com.insigma.commons.editorframework.graphic.editor.action.UndoAction;
import com.insigma.commons.editorframework.view.PropertyView;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.ModuleEventAdapter;
import com.insigma.commons.framework.application.WindowApplication;
import com.insigma.commons.framework.application.WindowCloseListener;
import com.insigma.commons.framework.config.IModuleBuilder;
import com.insigma.commons.framework.extend.UserModule;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * Ticket: 节点模块管理
 * @author  yang
 *
 */
public class ACCMapEditorModule extends WZBaseLogModule implements IModuleBuilder {

	protected Log logger = LogFactory.getLog(getClass());

	private IGraphicMapGenerator graphicMapGenerator = null;

	public Module getModule() {
		UserModule module = new UserModule();
		getLogService(WZACCLogModuleCode.MODULE_MAP_EDITOR.toString());
		module.setModuleListener(new ModuleEventAdapter() {

			public void create(Object object) {
				ArrayList<Action> actions = getActions();

				final EditorFrameWork compositeFrameWork = (EditorFrameWork) object;
				compositeFrameWork.setActions(actions);

				final MapTreeView mapTreeView = new MapTreeView(compositeFrameWork, SWT.NONE);
				mapTreeView.setText("编辑模块");
				WindowApplication.addWindowCloseListener(new WindowCloseListener() {

					@Override
					public String getName() {
						return "节点管理没有保存，确定要保存吗？";
					}

					@Override
					public void beforeClose() {
						SaveMapAction action = new SaveMapAction();
						ActionContext context = new ActionContext(action);
						context.setFrameWork(compositeFrameWork);
						action.getHandler().perform(context);
					}

					@Override
					public void afterClose() {
						MapEditorView view = (MapEditorView) compositeFrameWork.getView(MapEditorView.class);
						while (view.getMap().getActionExecutor().getUndoList().size() != 0) {
							view.getMap().getActionExecutor().undo();
						}
					}

					@Override
					public boolean prepare() {
						return mapTreeView.isChanged();
					}

					@Override
					public Image getImage() {
						return SWTResourceManager.getImage(mapTreeView.getClass(), mapTreeView.getIcon());
					}
				});

				compositeFrameWork.addView("EDITOR_MAP_TREE_VIEW", mapTreeView, SWT.LEFT);

				MapEditorView mapEditorView = new MapEditorView(compositeFrameWork, SWT.NONE);
				mapEditorView.setEditable(true);

				compositeFrameWork.addView("EDITOR_MAP_EDITOR_VIEW", mapEditorView, SWT.CENTER);

				{
					PropertyView view = new PropertyView(compositeFrameWork, 3, SWT.NONE);
					view.setText("设备属性列表 ");
					compositeFrameWork.addView("EDITOR_MAP_PROPERTY_VIEW", view, SWT.BOTTOM);
					//					view.setObject(new DeviceInfoForm(FormMode.VIEW));
				}
				mapTreeView.setGraphicMapGenerator(graphicMapGenerator);
				mapTreeView.refresh();
			}
		});
		module.setText("   节点管理   ");
		module.setComposite(EditorFrameWork.class);

		return module;
	}

	private ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();

		WZDeviceEventTreeProvider treeProvider = new WZDeviceEventTreeProvider();
		treeProvider.setRootId(1);
		treeProvider.setDepth(2);
		//		NetworkConfig networkConfig = (NetworkConfig) Application.getConfig(NetworkConfig.class);
		//		Short[] lines = { networkConfig.getLineNo().shortValue() };
		//		treeProvider.setLineIDs(lines);

		{
			//分离
			GroupAction action = new GroupAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//上移
			MoveAction action = new MoveAction(MoveMode.TOP);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//下移
			MoveAction action = new MoveAction(MoveMode.BOTTOM);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//左移
			MoveAction action = new MoveAction(MoveMode.LEFT);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//右移
			MoveAction action = new MoveAction(MoveMode.RIGHT);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//顺时针
			RotateAction action = new RotateAction(RotateMode.H);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//逆时针
			RotateAction action = new RotateAction(RotateMode.V);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE.toString());
			actions.add(action);
		}

		{
			//保存
			SaveMapAction action = new SaveMapAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_SAVE.toString());
			actions.add(action);
		}

		//		{
		//			// 地图导入
		//			StationConfigFileImportAction action = new StationConfigFileImportAction();
		//			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_NODE_STAION_MAP_IMPORT.toString());
		//			actions.add(action);
		//		}

		//		{
		//			//导出地图
		//			MapExportAction action = new MapExportAction();
		//			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_EXPORT_MAP.toString());
		//			actions.add(action);
		//		}

		{
			//地图同步
			WZMapSyncAction action = new WZMapSyncAction();
			CommonTreeProvider deviceTree = new CommonTreeProvider();
			deviceTree.setDepth(AFCNodeLevel.LC);
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_AYNCHRONIZATION_MAP.toString());
			action.setTreeProvider(deviceTree);
			actions.add(action);
		}

		{
			//撤销
			Action action = new UndoAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_DO.toString());
			actions.add(action);
		}

		{
			//重做
			Action action = new RedoAction();
			action.setID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_LINE_UNDO.toString());
			actions.add(action);
		}

		{
			//			NBRefreshAction action = new NBRefreshAction();
			//			actions.add(action);
		}
		return actions;
	}

	public void setGraphicMapGenerator(IGraphicMapGenerator graphicMapGenerator) {
		this.graphicMapGenerator = graphicMapGenerator;
	}

}