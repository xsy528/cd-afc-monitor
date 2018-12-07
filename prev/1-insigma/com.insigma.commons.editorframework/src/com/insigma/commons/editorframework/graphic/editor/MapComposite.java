/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.editor.action.MapAction;
import com.insigma.commons.editorframework.graphic.editor.action.MouseMoveAction;
import com.swtdesigner.SWTResourceManager;

public class MapComposite extends Composite {

	private EditorFrameWork frameWork;

	private MapItem map;

	private boolean group = true;

	private boolean moveable;

	private boolean moved;

	protected Cursor defaultCursor;

	// protected IEditorChangedListener changedListener;

	private MapActionExecutor actionExecutor = new MapActionExecutor();

	public MapItem getMap() {
		return map;
	}

	public void setMap(MapItem mapItem) {
		this.map = mapItem;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
		if (isMoveable()) {
			addKeyListener(new MapKeyListener(this));
		}
	}

	public EditorFrameWork getFrameWork() {
		return frameWork;
	}

	public void setFrameWork(EditorFrameWork frameWork) {
		this.frameWork = frameWork;
	}

	protected EventAdapter eventAdapter = new EventAdapter();

	protected class EventAdapter implements MouseListener, MouseMoveListener, PaintListener, KeyListener {

		public static final int BUTTON_LEFT = 1;

		public static final int BUTTON_RIGHT = 3;

		protected Point startpoint;

		protected Point lastPoint;

		protected Point endpoint;

		protected GraphicItem dragingItem;

		protected GraphicItem hoverItem;

		protected Rectangle lastrect;

		/**
		 * 原始坐标，即未移动坐标前
		 */
		protected Point orgPosition;

		/**
		 * 原始GraphicItem，即未移动坐标前
		 */
		protected List<GraphicItem> orgItems = new ArrayList<GraphicItem>();

		protected boolean draging;

		public void mouseDoubleClick(MouseEvent mouseEvent) {
			GraphicItem item = findItem(mouseEvent.x, mouseEvent.y);
			if (item != null) {
				Action doubleClickAction = item.getParent().getDoubleClickAction();
				if (doubleClickAction != null) {
					ActionContext context = new ActionContext(doubleClickAction);
					doubleClickAction.setUndoable(false);
					doubleClickAction.setFrameWork(getFrameWork());
					context.setFrameWork(getFrameWork());
					context.setData(ActionContext.ACTION_EVENTOBJECT, mouseEvent);
					context.setData(ActionContext.SELECTED_ITEM, item);
					getActionExecutor().perform(item.getParent(), context, doubleClickAction);
					// System.err.println(">>>>>>>>>>>>>>>>>>>>>" +
					// getActionExecutor().getUndoList().size());
					redraw();
				}
				item.getParent().setSelected(true);
				for (MapItem otheritem : map.getMapItems()) {
					if (otheritem != item.getParent()) {
						otheritem.setSelected(false);
					}
				}
			} else {
				selectAll(false);
			}
		}

		public void mouseDown(MouseEvent mouseEvent) {
			setFocus();
			if (mouseEvent.count > 1) {
				return;
			}
			if ((mouseEvent.stateMask & SWT.CTRL) == 0) {
				selectAll(false);
			}

			dragingItem = findItem(mouseEvent.x, mouseEvent.y);
			if (dragingItem != null) {
				dragingItem.setSelected(true);
				orgItems.clear();
				MapItem parent = dragingItem.getParent();
				for (GraphicItem item : parent.getItems()) {
					orgItems.add(new GraphicItem(item.getParent(), item.getX(), item.getY(), item.getAngle()));

				}
				orgPosition = new Point(dragingItem.getX(), dragingItem.getY());
				redraw();
			}
			if (mouseEvent.button == BUTTON_LEFT) {
				draging = true;
				startpoint = new Point(mouseEvent.x, mouseEvent.y);
				lastPoint = startpoint;
			}
			if (mouseEvent.button == BUTTON_RIGHT) {
				Menu popMenu = new Menu(MapComposite.this);
				Action[] contextAction = map.getContextAction();
				if (dragingItem != null && dragingItem.getParent() != null) {
					Action[] subContexts = dragingItem.getParent().getContextAction();
					for (final Action action : subContexts) {
						createMenuItem(action, mouseEvent, popMenu);
					}
				} else {
					for (final Action action : contextAction) {
						createMenuItem(action, mouseEvent, popMenu);
					}
				}
				popMenu.setVisible(true);
			}

		}

		private void createMenuItem(final Action action, final MouseEvent mouseEvent, Menu popMenu) {
			action.setData(ActionContext.SELECTED_ITEM, dragingItem);
			action.setData(ActionContext.ACTION_EVENTOBJECT, mouseEvent);
			action.setFrameWork(getFrameWork());
			// action.setUndoable(false);
			if (!action.IsEnable()) {
				return;
			}
			MenuItem item = new MenuItem(popMenu, SWT.NONE);
			if (action.getImage() != null) {
				item.setImage(SWTResourceManager.getImage(MapComposite.class, action.getImage()));
			}
			item.setText(action.getName());
			item.setData(action);
			item.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent arg0) {
					ActionContext context = new ActionContext(action);
					context.setData(ActionContext.ACTION_EVENTOBJECT, mouseEvent);
					context.setData(ActionContext.SELECTED_ITEM, dragingItem);
					context.setFrameWork(getFrameWork());
					MapItem parent = null;
					if (dragingItem == null) {
						parent = getMap();
					} else {
						parent = dragingItem.getParent();
					}
					getActionExecutor().perform(parent, context, action);
					redraw();
				}

				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

		}

		public void mouseUp(MouseEvent mouseEvent) {
			if (dragingItem != null) {
				redraw();
				Action selectionAction = dragingItem.getParent().getSelectionAction();
				if (selectionAction != null) {
					ActionContext context = new ActionContext(selectionAction);
					context.setData(ActionContext.ACTION_EVENTOBJECT, mouseEvent);
					context.setData(ActionContext.SELECTED_ITEM, dragingItem);
					context.setFrameWork(getFrameWork());
					// selectionAction.perform();
					selectionAction.getHandler().perform(context);
				}
				if (draging && moved) {
					// final MapItem parent = dragingItem.getParent();
					// parent.getDataState().update();
					MapAction mouseMoveAction = new MouseMoveAction("MouseMove", orgItems, dragingItem);
					mouseMoveAction.setFrameWork(getFrameWork());
					ActionContext context = new ActionContext(mouseMoveAction);
					context.setFrameWork(getFrameWork());
					getActionExecutor().perform(dragingItem.getParent(), context, mouseMoveAction);
					redraw();
				}
			} else {
				Action selectionAction = map.getSelectionAction();
				if (selectionAction != null) {
					ActionContext context = new ActionContext(selectionAction);
					context.setData(ActionContext.ACTION_EVENTOBJECT, mouseEvent);
					context.setFrameWork(getFrameWork());
					getActionExecutor().perform(map, context, selectionAction);
					redraw();
				}
			}
			if (lastrect != null) {
				redraw(lastrect.x, lastrect.y, lastrect.width, lastrect.height, false);

			}
			if (getFrameWork() != null) {
				getFrameWork().updateToolBar();
			}
			lastrect = null;
			startpoint = null;
			endpoint = null;
			draging = false;
			moved = false;
			dragingItem = null;
		}

		protected void moveItem(GraphicItem item, int offx, int offy) {
			Rectangle oldrect = item.getBounds();
			item.setX(item.getX() + offx);
			item.setY(item.getY() + offy);
			Rectangle newrect = item.getBounds();
			redraw(oldrect.x, oldrect.y, oldrect.width, oldrect.height, false);
			redraw(newrect.x, newrect.y, newrect.width, newrect.height, false);
		}

		public void mouseMove(MouseEvent arg0) {
			if (draging) {
				if (dragingItem != null && moveable) {
					if (arg0.x <= 0 || arg0.x > getSize().x) {
						return;
					}
					if (arg0.y <= 0 || arg0.y > getSize().y) {
						return;
					}
					if (isGroup()) {
						for (GraphicItem item : dragingItem.getParent().getItems()) {
							moveItem(item, arg0.x - lastPoint.x, arg0.y - lastPoint.y);
						}
					} else {
						moveItem(dragingItem, arg0.x - lastPoint.x, arg0.y - lastPoint.y);
					}
					moved = true;

				} else {

					endpoint = new Point(arg0.x, arg0.y);
					// logger.debug(endpoint);
					if (lastrect != null) {
						redraw(lastrect.x, lastrect.y, lastrect.width, lastrect.height, false);
					}
					int startX = startpoint.x > endpoint.x ? endpoint.x : startpoint.x;
					int startY = startpoint.y > endpoint.y ? endpoint.y : startpoint.y;
					int width = Math.abs(endpoint.x - startpoint.x);
					int height = Math.abs(endpoint.y - startpoint.y);
					redraw(startX, startY, width, height, false);
					lastrect = new Rectangle(startX, startY, width, height);

					if ((arg0.stateMask & SWT.CTRL) == 0) {
						selectAll(false);
					}
					List<GraphicItem> gitems = findItems(lastrect);
					for (GraphicItem gitem : gitems) {
						gitem.setSelected(true);
					}
				}
				lastPoint = new Point(arg0.x, arg0.y);
			} else {
				GraphicItem item = findItem(arg0.x, arg0.y);
				if (item != null) {
					if (hoverItem == null) {
						hoverItem = item;
						if (item.getParent().getHint() != null) {
							setToolTipText(item.getParent().getHint());
						} else {
							setToolTipText(item.getParent().getText());
						}
						if (moveable) {
							setMouseCursor();
						}
					}
				} else if (hoverItem != null) {
					hoverItem = null;
					setToolTipText(null);
					if (moveable) {
						setCursor(defaultCursor);
					}
				}
			}
		}

		private void setMouseCursor() {
			Image crossCursor = SWTResourceManager.getImage(MapComposite.class,
					"/com/insigma/commons/ui/shape/cross_win32.gif");
			Rectangle crossBound = crossCursor.getBounds();
			Point crossSize = new Point(crossBound.width / 2, crossBound.height / 2);
			setCursor(new Cursor(getDisplay(), crossCursor.getImageData(), crossSize.x, crossSize.y));
		}

		public void paintControl(PaintEvent arg0) {
			if (map != null) {
				for (MapItem item : map.getMapItemList()) {
					if (map.isLoadSubItem()) {
						item.paint(arg0.gc);
					} else {
						for (MapItem subitem : item.getMapItemList()) {
							subitem.paint(arg0.gc);
						}
					}
				}
			}
			if (startpoint != null && endpoint != null) {
				arg0.gc.setForeground(arg0.display.getSystemColor(SWT.COLOR_BLACK));
				int startX = startpoint.x > endpoint.x ? endpoint.x : startpoint.x;
				int startY = startpoint.y > endpoint.y ? endpoint.y : startpoint.y;
				int width = Math.abs(endpoint.x - startpoint.x);
				int height = Math.abs(endpoint.y - startpoint.y);
				arg0.gc.drawRectangle(startX, startY, width - 1, height - 1);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			MapKey mapKey = new MapKey(e.stateMask, e.keyCode);
			List<MapItem> mapItemList = map.getMapItemList();
			MapItem selectItem = null;
			for (MapItem mapItem : mapItemList) {// 查找是否有选中的MapItem
				List<GraphicItem> items = mapItem.getItems();
				for (GraphicItem graphicItem : items) {
					if (graphicItem.isSelected()) {
						selectItem = mapItem;
						break;
					}
				}
			}
			if (selectItem != null) {
				Map<MapKey, Action> keyActionMap = selectItem.getKeyAction();
				executeKeyAction(selectItem, mapKey, keyActionMap);
			} else {
				Map<MapKey, Action> keyActionMap = map.getKeyAction();
				executeKeyAction(map, mapKey, keyActionMap);
			}
		}

		/**
		 * @param mapKey
		 * @param keyActionMap
		 */
		private void executeKeyAction(MapItem mapItem, MapKey mapKey, Map<MapKey, Action> keyActionMap) {
			Action action = keyActionMap.get(mapKey);
			if (action != null) {
				ActionContext context = new ActionContext(action);
				context.setFrameWork(getFrameWork());
				context.setData(ActionContext.SELECTED_ITEM, mapItem);
				getActionExecutor().perform(mapItem, context, action);
				redraw();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

	public GraphicItem findItem(int x, int y) {
		if (map == null) {
			return null;
		}
		for (MapItem item : map.getMapItems()) {
			if (map.isLoadSubItem()) {
				for (GraphicItem gitem : item.getItems()) {
					if (gitem.contains(x, y)) {
						return gitem;
					}
				}
			} else {
				for (MapItem subItem : item.getMapItems()) {
					for (GraphicItem gitem : subItem.getItems()) {
						if (gitem.contains(x, y)) {
							return gitem;
						}
					}
				}
			}
		}
		return null;
	}

	protected List<GraphicItem> findItems(Rectangle rect) {
		List<GraphicItem> gitems = new ArrayList<GraphicItem>();
		if (map != null) {
			for (MapItem item : map.getMapItems()) {
				for (GraphicItem gitem : item.getItems()) {
					if (gitem.intersects(rect)) {
						gitems.add(gitem);
					}
				}
			}
		}
		return gitems;
	}

	public void selectAll(boolean select) {
		if (map == null) {
			return;
		}
		for (MapItem item : map.getMapItems()) {
			if (item.isSelected() != select) {
				for (GraphicItem gitem : item.getItems()) {
					redraw(gitem.getBounds().x, gitem.getBounds().y, gitem.getBounds().width, gitem.getBounds().height,
							false);
				}

			}
			item.setSelected(select);
		}
	}

	public List<MapItem> getSelection() {
		ArrayList<MapItem> selections = new ArrayList<MapItem>();
		if (map != null) {
			for (MapItem item : map.getMapItems()) {
				if (item.isSelected()) {
					selections.add(item);
				}
			}
		}
		return selections;
	}

	public List<GraphicItem> getSelectionItem() {
		ArrayList<GraphicItem> selections = new ArrayList<GraphicItem>();
		if (map != null) {
			for (MapItem item : map.getMapItems()) {
				if (item.isSelected()) {
					for (GraphicItem gitem : item.getItems()) {
						if (gitem.isSelected()) {
							selections.add(gitem);
						}
					}
				}
			}
		}
		return selections;
	}

	public MapComposite(Composite parent, int style) {

		super(parent, style | SWT.DOUBLE_BUFFERED | SWT.INHERIT_DEFAULT);
		setLayout(null);
		addPaintListener(eventAdapter);

		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		addMouseListener(eventAdapter);
		addMouseMoveListener(eventAdapter);
		addKeyListener(eventAdapter);
		defaultCursor = getCursor();
	}

	@Override
	public void setBackgroundImage(Image arg0) {
		super.setBackgroundImage(arg0);
		setBounds(this.getBounds().x, this.getBounds().y, arg0.getBounds().width, arg0.getBounds().height);
		getParent().layout();
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 清空undo、redoList
	 */
	public void clear() {
		getActionExecutor().reset();
	}

	public void redo() {
		getActionExecutor().redo();
		redraw();
	}

	public void undo() {
		getActionExecutor().undo();
		redraw();
	}

	public boolean canUndo() {
		return getActionExecutor().canUndo();
	}

	public boolean canRedo() {
		return getActionExecutor().canRedo();
	}

	public MapActionExecutor getActionExecutor() {
		return actionExecutor;
	}

	public void setActionExecutor(MapActionExecutor actionExecutor) {
		this.actionExecutor = actionExecutor;
	}

}
