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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.ui.ResourceUtil;

public class MapEditorView extends FrameWorkView {

	private MapItem activeMap;

	private MapComposite mapComposite;

	private boolean editable;

	public MapComposite getMap() {
		return mapComposite;
	}

	public void setMap(MapComposite mapComposite) {
		this.mapComposite = mapComposite;
	}

	public MapItem getActiveMap() {
		return activeMap;
	}

	public void setActiveMap(MapItem activeMap) {
		this.activeMap = activeMap;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		mapComposite.setMoveable(editable);
	}

	public MapEditorView(Composite arg0, int arg1) {
		super(arg0, arg1 | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		// text和Icon信息是在EditorFrameWork对象中addView（）时，创建CTabItem用到
		setText(" 地 图 ");
		setIcon("/com/insigma/commons/ui/shape/map.png");

		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		// 构造mapComposite对象时，addMouseListener()、addMouseMoveListener()、addFocusListener()
		mapComposite = new MapComposite(this, SWT.NONE);
		mapComposite.setBounds(50, 0, 1024, 768);
		mapComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		getVerticalBar().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				mapComposite.setBounds(mapComposite.getBounds().x, -(getVerticalBar().getSelection() * 5),
						mapComposite.getBounds().width, mapComposite.getBounds().height);
			}
		});

		getHorizontalBar().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (getHorizontalBar().getSelection() == getHorizontalBar().getMaximum()) {
					mapComposite.setBounds(-(mapComposite.getBounds().width - getClientArea().width),
							mapComposite.getBounds().y, mapComposite.getBounds().width,
							mapComposite.getBounds().height);
				} else {
					mapComposite.setBounds(-(getHorizontalBar().getSelection() * 5), mapComposite.getBounds().y,
							mapComposite.getBounds().width, mapComposite.getBounds().height);
				}
			}
		});

		addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent arg0) {
				layout();
			}
		});
	}

	public void layout() {
		getHorizontalBar().setSelection(0);
		getVerticalBar().setSelection(0);
		getHorizontalBar().setIncrement(10);
		getVerticalBar().setIncrement(10);
		int x = 0;
		if (getBounds().width > mapComposite.getBounds().width) {
			getHorizontalBar().setVisible(false);
			x = (getBounds().width - mapComposite.getBounds().width) / 2;
		} else {
			x = -(getHorizontalBar().getSelection() * 5);
			getHorizontalBar().setMaximum((mapComposite.getBounds().width - getClientArea().width + 60) / 5);
			getHorizontalBar().setVisible(true);
		}

		int y = 0;
		if (getBounds().height > mapComposite.getBounds().height) {
			y = (getBounds().height - mapComposite.getBounds().height) / 2;
			getVerticalBar().setVisible(false);
		} else {
			y = -(getVerticalBar().getSelection() * 5);
			getVerticalBar().setMaximum((mapComposite.getBounds().height - getClientArea().height + 60) / 5);
			getVerticalBar().setVisible(true);
		}
		mapComposite.setBounds(x, y, mapComposite.getBounds().width, mapComposite.getBounds().height);
		super.layout();
	}

	/**
	 * 加载监控地图
	 * 
	 * @param map
	 */
	public void load(MapItem map) {
		activeMap = map;
		if (map.getImage() != null) {
			Image image = ResourceUtil.getImage(map.getImage());
			mapComposite.setBackgroundImage(image);
			mapComposite.setBounds(image.getBounds());
			layout();

		}
		for (MapItem item : map.getMapItems()) {
			if (item.getDoubleClickAction() != null) {
				item.getDoubleClickAction().setFrameWork(getFrameWork());
				item.getDoubleClickAction().setWorkView(this);
			}
			if (item.getSelectionAction() != null) {
				item.getSelectionAction().setFrameWork(getFrameWork());
				item.getSelectionAction().setWorkView(this);
			}
			if (item.getContextAction() != null) {
				for (Action context : item.getContextAction()) {
					context.setFrameWork(getFrameWork());
					context.setWorkView(this);
				}
			}
		}
		mapComposite.setFrameWork(getFrameWork());
		mapComposite.setMap(map);

	}

	public void reDraw() {
		if (mapComposite != null) {
			mapComposite.redraw();
		}
	}

	/**
	 * 获取地图上的所有图元
	 * @return 没有返回空的List，不返回Null
	 */
	public List<MapItem> getMapItemList() {
		if (activeMap != null) {
			return activeMap.getMapItemList();
		}
		return new ArrayList<MapItem>();
	}

	public void setLayout(Layout layout) {

	}
}
