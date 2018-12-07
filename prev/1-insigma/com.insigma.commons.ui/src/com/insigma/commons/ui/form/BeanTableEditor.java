/* 
 * 日期：2010-7-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.form.listener.ITableEditorLisener;
import com.insigma.commons.ui.layout.LayoutFactory;
import com.insigma.commons.ui.layout.LayoutFactory.LayoutType;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.insigma.commons.ui.widgets.ktable.KTable;
import com.insigma.commons.ui.widgets.ktable.SWTX;
import com.swtdesigner.SWTResourceManager;

public class BeanTableEditor extends EnhanceComposite implements ITableEditor {

	private Log logger = LogFactory.getLog(BeanTableEditor.class);

	private IEditorChangedListener changedListener;

	private KTable ktable;

	private BeanEditorTableModel beanModel;

	private Field parentField;

	public BeanTableEditor(final Composite parent, int style, ITableEditorLisener... toolBarLiseners) {
		super(parent, style);
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		setLayout(LayoutFactory.getInstance().create(LayoutType.NO_BORDER));
		setLayoutData(new GridData(GridData.FILL_BOTH));

		// 构建功能栏
		initToolBar(toolBarLiseners);

		// table
		ktable = new KTable(this, SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWTX.FILL_WITH_LASTCOL
				| SWTX.EDIT_ON_KEY | SWTX.MARK_FOCUS_HEADERS);
		ktable.setLayout(new GridLayout());
		ktable.setLayoutData(new GridData(GridData.FILL_BOTH));

		initLisener(toolBarLiseners);
	}

	/**
	 * 设置table的标题
	 * 
	 * @param cls
	 */
	public void setHeader(Field dataField) {
		this.parentField = dataField;

		final ListType listType = dataField.getAnnotation(ListType.class);
		boolean userDefinedClass = BeanUtil.isUserDefinedClass(listType.type());
		if (userDefinedClass) {
			beanModel = new BeanEditorTableModel(listType.type());
		} else {
			beanModel = new BeanEditorTableModel(dataField, listType.type());
		}
		beanModel.setChangedListener(changedListener);
		ktable.setModel(beanModel);
	}

	public void setCompareObjectList(Object compareList) {
		if (null != compareList) {
			List<Object> list = null;
			if (compareList.getClass().isArray()) {
				list = Arrays.asList((Object[]) compareList);
			} else if (compareList instanceof List) {
				list = (List<Object>) compareList;
			}
			beanModel.setCampareList(list);
		}
	}

	/**
	 * 设置table的内容
	 * 
	 * @param dataList
	 */
	public void setObjectList(Object dataList) {
		if (dataList == null) {
			return;
		}
		List<Object> list = null;
		if (dataList.getClass().isArray()) {
			list = Arrays.asList((Object[]) dataList);
		} else if (dataList instanceof List) {
			list = (List<Object>) dataList;
		}
		if (list.size() > 0) {
			if (beanModel == null) {
				beanModel = new BeanEditorTableModel(list.get(0).getClass());
				beanModel.setChangedListener(changedListener);
			}
		}
		beanModel.setObjectList(list);
		ktable.setModel(beanModel);
	}

	public IEditorChangedListener getChangedListener() {
		return changedListener;
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
		if (beanModel != null)
			this.beanModel.setChangedListener(changedListener);
	}

	private void notifyChangedListener(boolean isChanged) {
		if (null != changedListener) {
			Event event = new Event();
			changedListener.editorChanged(event, isChanged);
		}
	}

	public boolean addRow(Object obj) {
		try {
			beanModel.addRow(obj);
			ktable.redraw();
			notifyChangedListener(true);
			return true;
		} catch (Exception e) {
			logger.error("table添加行失败", e);
			return false;
		}
	}

	public void updateRow(int index, Object obj) throws Exception {
		beanModel.updateRow(index, obj);
		ktable.redraw();
	}

	@Override
	public Object getCompareRow(int index) {
		return beanModel.getCompareRow(index);
	}

	public Object getRow(int index) {
		return beanModel.getRow(index);
	}

	public Object copyRow(int index) {
		try {
			return beanModel.copyRow(index);
		} catch (Exception e) {
			logger.error("table复制行失败", e);
			return null;
		}
	}

	public boolean removeRow(int[] indexes) {
		try {
			Arrays.sort(indexes);
			int length = indexes.length;
			for (int i = length - 1; i >= 0; i--) {
				beanModel.remove(indexes[i]);
			}
			ktable.clearSelection();
			ktable.redraw();
			if (length > 0) {
				int row = indexes[length - 1] - length;
				if (row < 1) {
					row = indexes[0];
				}
				ktable.setSelection(0, row, true);
			}
			notifyChangedListener(true);
			return true;
		} catch (Exception e) {
			logger.error("table删除行失败", e);
			return false;
		}
	}

	private int[] getRowSelect() {
		Point[] ps = ktable.getCellSelection();
		if (ps == null) {
			return new int[0];
		}
		List<Integer> ss = new ArrayList<Integer>();
		for (Point p : ps) {
			if (!ss.contains(p.y)) {
				ss.add(p.y);
			}
		}
		int[] a = new int[ss.size()];
		for (int i = 0; i < ss.size(); i++) {
			a[i] = ss.get(i);
		}
		return a;
	}

	public Field getField() {
		return parentField;
	}

	public Class getDataClass() {
		return beanModel.getDataClass();
	}

	/**
	 * 创建功能栏
	 */
	private void initToolBar(ITableEditorLisener... toolBarLiseners) {
		// 构建功能钮
		if (null != toolBarLiseners && toolBarLiseners.length > 0) {
			ToolBar bar = null;

			for (final ITableEditorLisener lisener : toolBarLiseners) {
				if (lisener.getType() == ITableEditorLisener.LisenerType.TOOL_BAR) {
					lisener.setParentTableEditor(this);

					bar = createToolItem(bar, lisener);
				}
			}
		}
	}

	private ToolBar createToolItem(ToolBar bar, final ITableEditorLisener lisener) {
		if (null == bar) {
			bar = new ToolBar(this, SWT.NONE);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			bar.setLayoutData(gridData);
			bar.setBackground(getBackground());
		}

		ToolItem item = new ToolItem(bar, SWT.NONE);
		item.setText(lisener.getName());
		item.setImage(SWTResourceManager.getImage(TableEditor.class, lisener.getImagePath()));
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					int[] rowNums = ktable.getRowSelection();

					lisener.widgetSelected(rowNums);
				} catch (Exception e) {
					logger.error("table" + lisener.getName() + "失败", e);
				}
			}
		});

		return bar;
	}

	/**
	 * 创建功能栏
	 */
	private void initLisener(ITableEditorLisener... toolBarLiseners) {
		// 构建功能钮
		if (null != toolBarLiseners && toolBarLiseners.length > 0) {

			for (final ITableEditorLisener lisener : toolBarLiseners) {
				if (lisener.getType() == ITableEditorLisener.LisenerType.LISENER) {
					lisener.setParentTableEditor(this);
					addLisener(lisener);
				}
			}

		}
	}

	private void addLisener(final ITableEditorLisener lisener) {

		ktable.addListener(lisener.getStyle(), new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					int[] rowNums = ktable.getRowSelection();

					lisener.widgetSelected(rowNums);
				} catch (Exception e) {
					logger.error("table" + lisener.getName() + "失败", e);
				}
			}
		});
	}

}
