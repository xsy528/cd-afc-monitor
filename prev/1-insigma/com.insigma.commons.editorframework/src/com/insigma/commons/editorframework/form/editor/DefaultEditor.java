/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.form.builder.ListCompositeBuilder;
import com.insigma.commons.editorframework.form.builder.UserBeanCompositeBuilder;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.ValidationUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.AnnotationDataParse;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.IEditorChangedListener;

public class DefaultEditor extends FrameWorkView implements IComponentEditor {

	private Log logger = LogFactory.getLog(DefaultEditor.class);

	private Object value;

	private Object compareValue;

	private CTabFolder folder;

	private boolean isEditable = true;

	private int inputControlWidth = 200;

	private CTabItem preItem;

	private boolean isSimpleData;

	private IEditorChangedListener changedListener;

	private UserBeanCompositeBuilder userBeanCompoisteBuiler = new UserBeanCompositeBuilder();

	public DefaultEditor(Composite arg0, int arg1) {
		super(arg0, arg1);
		setText("参数编辑器");
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginTop = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.numColumns = 2;
		setLayout(gridLayout);

		folder = new CTabFolder(this, SWT.BORDER);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));

		// 添加显示内容校验
		folder.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				widgetDefaultSelected(arg0);

			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
				String validateMessage = ValidationUtil.objectValidateMessage(value);
				if (null != validateMessage) {
					MessageForm.Message("警告信息", validateMessage + "\n====== 请修改标红的字段 ======", SWT.ICON_WARNING);
					if (preItem == null && folder.getItemCount() > 0) {
						preItem = folder.getItem(0);
					}
					if (preItem != null)
						folder.setSelection(preItem);
				} else {
					preItem = folder.getSelection();
				}
			}
		});
	}

	/**
	 * 清空页面
	 */
	public void clear() {
		for (CTabItem tabItem : folder.getItems()) {
			Control control = tabItem.getControl();
			tabItem.dispose();

			if (null != control && !control.isDisposed()) {
				control.dispose();
			}
		}
	}

	public void doChange(boolean isChanged) {
		CTabItem item = (CTabItem) getData(DefaultEditor.class.getName());
		if (item != null) {
			if (isChanged) {
				item.setText("* " + getText());
			} else {
				item.setText(getText());
			}
		}
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
	}

	public void setSimpleValue(Field field, Object value) {
		isSimpleData = true;
		this.value = value;
		clear();
		if (null != field && null != value) {
			createSimpleBeanCTabItem("信息", field, value);
		}
	}

	public void setCompareValue(Object value) {
		this.compareValue = value;
	}

	public void setValue(Object value) {
		isSimpleData = false;
		this.value = value;
		clear();
		if (value != null) {
			try {

				if (null != compareValue && !compareValue.getClass().equals(value.getClass())) {
					compareValue = null;
				}

				BeanField parent = new BeanField(value);
				parent.setHistoryValue(compareValue);

				// 构建Form
				if (hasNoListData(value)) {
					createBeanCTabItem("基本参数", null, parent);
				}

				// 取得需要显示的列表
				List<BeanField> beanFieldList = getObjectList(parent);

				for (BeanField beanField : beanFieldList) {
					View listView = beanField.field.getAnnotation(View.class);
					createListCTabItem(listView.label(), beanField);
				}
			} catch (Exception e) {
				clear();
				logger.error("显示列表取得失败", e);
			}

		}

		folder.setSelection(0);
		layout();
	}

	@Override
	public Object getValue() {
		if (isSimpleData) {
			return preItem.getControl().getData();
		}
		return value;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public int getInputControlWidth() {
		return inputControlWidth;
	}

	public void setInputControlWidth(int inputControlWidth) {
		this.inputControlWidth = inputControlWidth;
	}

	private boolean hasNoListData(Object value) {
		Field[] fields = value.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			View view = field.getAnnotation(View.class);
			if (view != null) {
				ListType listType = field.getAnnotation(ListType.class);
				if (listType == null) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 取得list列表
	 * 
	 * @param value
	 * @param field
	 * @param fieldValue
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private List<BeanField> getObjectList(BeanField beanField) throws InstantiationException, IllegalAccessException {

		Object fieldValue = beanField.fieldValue;

		List<BeanField> listInfo = new ArrayList<BeanField>();
		if (null == fieldValue) {
			return listInfo;
		}

		Field[] subFields = fieldValue.getClass().getDeclaredFields();
		for (Field subField : subFields) {
			View view = subField.getAnnotation(View.class);
			if (null == view) {
				continue;
			}
			ListType fieldType = subField.getAnnotation(ListType.class);

			if (null == fieldType) {
				// 递归查找字段中含有list
				if (BeanUtil.isUserDefinedClass(subField.getType())) {
					BeanField subFieldValue = getFieldBean(beanField, subField);

					List<BeanField> subListInfo = getObjectList(subFieldValue);
					listInfo.addAll(subListInfo);
				}
			} else {
				// 添加返回列表
				BeanField subFieldValue = getFieldBean(beanField, subField);

				listInfo.add(subFieldValue);
			}

		}

		return listInfo;
	}

	private BeanField getFieldBean(BeanField parent, Field field)
			throws InstantiationException, IllegalAccessException {
		Object parentObj = parent.fieldValue;
		Object parentHistoryObj = parent.historyValue;

		Object fieldValue = BeanUtil.getValue(parentObj, field);
		if (null == fieldValue) {
			fieldValue = BeanUtil.newInstanceObject(field.getType());
			BeanUtil.setValue(parentObj, field, fieldValue);
		}

		Object fieldHistoryValue = null;
		if (null != parentHistoryObj) {
			fieldHistoryValue = BeanUtil.getValue(parentHistoryObj, field);
		}

		BeanField result = new BeanField(field, fieldValue, fieldHistoryValue, parent);

		Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
		result.setFieldAnnotations(fieldAnnotations);

		return result;
	}

	private void createSimpleBeanCTabItem(String title, Field field, Object obj) {
		Control itemControl = null;

		// 判断是否允许编辑
		if (isEditable) {
			itemControl = userBeanCompoisteBuiler.builderEditableSimpleBeanCompsite(folder, field, obj,
					changedListener);
		}

		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(title);
		item.setControl(itemControl);

		preItem = item;
	}

	private void createBeanCTabItem(String title, Field field, BeanField parent) {
		Control itemControl = null;

		if (BeanUtil.isUserDefinedClass(parent.fieldValue.getClass())) {
			// 判断是否允许编辑
			if (isEditable) {
				itemControl = userBeanCompoisteBuiler.builderEditableBeanCompsite(folder, parent, changedListener);
			} else {
				itemControl = userBeanCompoisteBuiler.builderViewBeanCompsite(folder, parent.fieldValue);
			}
		}

		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(title);
		item.setControl(itemControl);
	}

	/**
	 * list编辑界面
	 * @param title
	 * @param field
	 * @param obj
	 */
	private void createListCTabItem(String title, BeanField beanField) {
		Control itemControl = null;
		ListType listView = beanField.field.getAnnotation(ListType.class);
		if (null != listView) {
			// 判断是否可编辑
			ListCompositeBuilder listBuilder = new ListCompositeBuilder(folder, beanField);
			if (isEditable) {
				itemControl = listBuilder.builderEditableListCompsite(changedListener);
			} else {
				itemControl = listBuilder.builderViewListCompsite();
			}
		}

		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(title);
		item.setControl(itemControl);
	}
}
