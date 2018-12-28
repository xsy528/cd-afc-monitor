package com.insigma.commons.framework.view.tabletree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.convert.ColumnConvertor;
import com.insigma.commons.ui.tree.TreeNode;
import com.insigma.commons.ui.widgets.Tree;

public class TreeView extends Tree {

	public final String ITEM_DATA_KEY_PREFIX = "DATA_";

	public final String ITEM_FIELD_KEY_PREFIX = "FIELD_";

	public final String ITEM_VIEW_KEY_PREFIX = "VIEW_";

	private Class<?> dataClass;

	public String NONE_VALUE = "";

	public TreeView(Composite arg0, int arg1) {
		super(arg0, arg1 | SWT.FULL_SELECTION);
		setLinesVisible(true);
		setHeaderVisible(true);
	}

	@Override
	protected void checkSubclass() {
		// super.checkSubclass();
	}

	/**
	 * 格式化显示内容
	 * 
	 * @param value
	 *            显示内容
	 * @param view
	 *            格式化信息
	 * @return
	 */
	protected String formatValue(Object value, ColumnView view) {
		String result = null;
		if (view != null) {
			Class<? extends ColumnConvertor> convertor = view.convertor();
			if (convertor != null) {
				try {
					result = convertor.newInstance().getText(null, value, 0, 0, null);
					if (result == null && value != null) {
						result = value.toString();
					}
				} catch (InstantiationException e) {

					e.printStackTrace();
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				}
			}
			if (result == null) {
				return NONE_VALUE;
			}
			return result;
		}
		return NONE_VALUE;
	}

	private void setObjectItem(TreeItem supitem, Object objectItem) {
		if (supitem == null || objectItem == null) {
			return;
		}
		Field[] fs = objectItem.getClass().getDeclaredFields();
		int columnNo = 0;
		for (Field field : fs) {
			Object value = null;
			try {
				field.setAccessible(true);
				value = field.get(objectItem);
				if (BeanUtil.isUserDefinedClass(field.getType())) {
					setObjectItem(supitem, value);
					continue;
				}
				ColumnView view = field.getAnnotation(ColumnView.class);
				if (view != null) {
					String text = formatValue(value, view);
					supitem.setText(columnNo, text);
					supitem.setData(ITEM_FIELD_KEY_PREFIX + columnNo, field);
					supitem.setData(ITEM_DATA_KEY_PREFIX + columnNo, value);
					supitem.setData(ITEM_VIEW_KEY_PREFIX + columnNo, view);
					columnNo++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 构建表头
	 * 
	 * @param cls
	 */
	public void newHeader(Class<?> cls) {
		if (cls != null && !cls.equals(dataClass)) {
			TreeColumn[] treeColumns = getColumns();
			List<Field> viewFields = collectFields(cls);

			int i = 0;
			for (Field field : viewFields) {
				TreeColumn treeColumn = null;
				View view = field.getAnnotation(View.class);
				if (i < getColumnCount()) {
					treeColumn = treeColumns[i];
				} else {
					treeColumn = new TreeColumn(this, SWT.NONE);
				}
				treeColumn.setText(view.label());
				int length = view.label().length();
				if (length > 6) {
					treeColumn.setWidth((length - 6) * 10 + 100);
				} else {
					treeColumn.setWidth(100);
				}
				i++;
			}
			if (getColumnCount() > viewFields.size()) {
				for (int j = i; j < treeColumns.length; j++) {
					treeColumns[j].dispose();
				}
			}
			dataClass = cls;
		}
	}

	private List<Field> collectFields(Class<?> cls) {
		Field[] fields = cls.getDeclaredFields();
		List<Field> viewFields = new ArrayList<Field>();
		for (Field field : fields) {
			View view = field.getAnnotation(View.class);
			if (null != view) {
				if (BeanUtil.isUserDefinedClass(field.getType())) {
					viewFields.addAll(collectFields(field.getType()));
				} else {
					viewFields.add(field);
				}
			}
		}
		return viewFields;
	}

	public void fillTree(List<TreeNode> data) {
		this.removeAll();
		for (TreeNode treeNode : data) {
			TreeItem supitem = new TreeItem(this, SWT.NONE);
			// supitem.setText(toStringArray(treeNode.getKey(), NONE_VALUE));
			setObjectItem(supitem, treeNode.getKey());
			supitem.setData(treeNode.getKey());
			if (treeNode.getChilds() != null) {
				fillSubTree(supitem, treeNode.getChilds());
			}
		}
	}

	public void fillObjectList(List<?> data) {
		this.removeAll();
		if (data == null || data.size() == 0) {
			return;
		}
		Object object = data.get(0);
		newHeader(object.getClass());
		for (Object obj : data) {
			TreeItem supitem = new TreeItem(this, SWT.NONE);
			// supitem.setText(toStringArray(obj, NONE_VALUE));
			setObjectItem(supitem, obj);
			supitem.setData(obj);
		}
	}

	public void fillArrays(List<?> data) {
		this.removeAll();
		if (data.size() > 0 && data.get(0).getClass().isArray()) {
			for (Object rowdata : data) {
				Object[] row = (Object[]) rowdata;
				TreeItem supitem = new TreeItem(this, SWT.NONE);
				supitem.setData(rowdata);
				int i = 0;
				for (Object object : row) {
					supitem.setText(i++, object == null ? NONE_VALUE : object.toString());
				}
			}
		}
	}

	private void fillSubTree(TreeItem supitem, List<TreeNode> data) {
		for (TreeNode treeNode : data) {
			TreeItem subitem = new TreeItem(supitem, SWT.NONE);
			// subitem.setText(toStringArray(treeNode.getKey(), NONE_VALUE));
			setObjectItem(supitem, treeNode.getKey());
			subitem.setData(treeNode.getKey());
			if (treeNode.getChilds() != null) {
				fillSubTree(subitem, treeNode.getChilds());
			}
		}
	}

	public List<Object> getSelectionIndicesObject() {
		TreeItem[] selectItems = getSelection();
		List<Object> result = new ArrayList<Object>();
		if (null != selectItems && selectItems.length > 0) {
			for (TreeItem treeItem : selectItems) {
				Object object = treeItem.getData();
				result.add(object);
			}
		}
		return result;
	}

	/**
	 * 设置列宽
	 * 
	 * @param widths
	 */
	public void setWidths(int[] widths) {
		TreeColumn[] columns = getColumns();
		int size = columns.length;
		if (size >= widths.length) {
			size = widths.length;
		}
		for (int i = 0; i < size; i++) {
			columns[i].setWidth(widths[i]);
		}
	}

	@Override
	public void reset() {
		removeAll();
	}
}
