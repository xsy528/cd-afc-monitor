/**
 * iFrameWork 框架
 *
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description
 * @date          2009-4-4
 * @copyright    浙江浙大网新众合轨道交通工程有限公司
 * @history      1.2009-5-20: add 'newInstance' operation to the method getForm(), Yunzhi Lin
 *                2.2009-7-15: modify the LayoutData of Label(new GridData(GridData.FILL_HORIZONTAL)-->new GridData(SWT.LEFT, SWT.CENTER, false, false)),Yunzhi Lin
 */
package com.insigma.commons.framework.view.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

import com.insigma.commons.application.Application;
import com.insigma.commons.collection.IndexHashMap;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.FormRequest;
import com.insigma.commons.framework.function.form.Form.FormMode;
import com.insigma.commons.lang.FourValue;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.anotation.ControlCascade;
import com.insigma.commons.ui.anotation.ControlCascades;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.casecade.CasecadeListener;
import com.insigma.commons.ui.casecade.ICasecadeControl;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.IEditorChangedListener;
import com.insigma.commons.ui.form.WidgetsFactory;
import com.insigma.commons.ui.widgets.Button;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Label;

@SuppressWarnings("rawtypes")
public class FormEditor<T> extends EnhanceComposite implements IRequestGenerator, IInputControl {

	private final String CONTROL_PREFIX_VALUE = "control_prefix_value";// 控件的上一个值

	protected Log logger = LogFactory.getLog(getClass());

	protected Form<T> form;

	protected Class<?> formClass = null;

	protected List<FourValue<BeanField, ViewData, Button, IInputControl>> fieldlist;

	protected HashMap<String, IInputControl> namehashMap;//

	protected List<BeanField> casecadeFields;

	private GridLayout gridLayout;

	protected IEditorChangedListener changedListener;

	private IFormFieldsCollector formFieldsCollector = new FormFieldsCollector();

	public FormEditor(Composite parent, int style) {
		super(parent, style);
		fieldlist = new ArrayList<FourValue<BeanField, ViewData, Button, IInputControl>>();
		namehashMap = new HashMap<String, IInputControl>();
		casecadeFields = new ArrayList<BeanField>();
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		setLayout(gridLayout);
	}

	public Form<T> getForm() {
		for (FourValue<BeanField, ViewData, Button, IInputControl> item : fieldlist) {
			try {
				if (item.getElement2().checkable()) {
					IInputControl control = item.getElement4();
					if (item.getElement3().getSelection()) {
						control = item.getElement4();
						Object value = control.getObjectValue();
						doSetValue(item, value);
					} else {
						doSetValue(item, null);
					}
				} else if (!item.getElement2().checkable()) {
					IInputControl control = item.getElement4();
					Object value = control.getObjectValue();
					doSetValue(item, value);
				}
			} catch (Exception e) {
				String msg = item.getElement2().label() + "取值错误。";
				logger.error(msg, e);
				throw new ApplicationException(msg);
			}
		}
		return form;
	}

	/**
	 * @param item
	 * @param value
	 *            控件的值
	 */
	private void doSetValue(FourValue<BeanField, ViewData, Button, IInputControl> item, Object value) {
		BeanField e1 = item.getElement1();
		BeanField parent = e1.parent;
		if (parent == null) {
			BeanUtil.setValue(form.getEntity(), e1.field, value);
		} else {
			BeanUtil.setValue(parent.fieldValue, e1.field, value);// 设置控件所在的复合对象的值
			BeanField parentTemp = parent;
			while ((parentTemp = parentTemp.parent) != null) {
				BeanUtil.setValue(parentTemp.fieldValue, parent.field, parent.fieldValue);
				parent = parentTemp;
			}
		}
	}

	protected void createContents(Object data, Class clz) {
		logger.debug("开始收集字段...");
		IndexHashMap<String, List<BeanField>> groupFieldsMap = formFieldsCollector.collectFields(data, clz);
		List<String> groupList = groupFieldsMap.getKeyList();

		logger.debug("收集字段完成，准备创建控件");
		for (String groupName : groupList) {
			List<BeanField> fields = groupFieldsMap.get(groupName);
			// 设置labelwidth
			for (BeanField formField : fields) {
				ViewData viewData = formField.getAnnotationData(ViewData.class);
				String labelText = viewData.label() + ": ";
				int labelWidth = calcLabelWidth(labelText);
				if (form.labelWidth < labelWidth) {
					form.labelWidth = labelWidth;
				}
			}
			if (groupName.equals(IFormFieldsCollector.NONE_GROUP)) {// 不需要分组的直接创建到this中
				for (BeanField pairValue : fields) {
					ViewData viewData = pairValue.getAnnotationData(ViewData.class);
					createControl(pairValue, this, viewData);
				}
			} else {
				createGroup(groupName, fields);
			}
		}

	}

	/**
	 * @param groupName
	 * @param list
	 * @return
	 */
	private Group createGroup(String groupName, List<BeanField> list) {
		Group group = new Group(this, SWT.NONE);
		group.setText(groupName);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = this.form.getColums();
		gridLayout.makeColumnsEqualWidth = this.form.isColumnsEqualWidth;
		gridLayout.marginTop = 0;
		gridLayout.marginBottom = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		// 控制一列中两个网络间组件的宽度,像素为单位.
		gridLayout.verticalSpacing = 5;
		// 控制左边和右边组件离边缘的距离空间,以像素为单位.
		gridLayout.marginWidth = 2;
		group.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = this.form.getColums();
		group.setLayoutData(gridData);
		group.setBackground(getBackground());

		for (BeanField pairValue : list) {
			ViewData viewData = pairValue.getAnnotationData(ViewData.class);
			createControl(pairValue, group, viewData);
		}

		return group;
	}

	private void createControl(final BeanField formField, Composite parent, ViewData view) {
		Field field = formField.field;
		Object value = formField.fieldValue;
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginTop = 0;
		gridLayout.marginBottom = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		GridData compositeGridData = new GridData(GridData.FILL_HORIZONTAL);
		compositeGridData.horizontalSpan = view.coloumnspan();
		compositeGridData.verticalSpan = view.rowspan();
		// compositeGridData.heightHint = view.heightHint();
		composite.setLayoutData(compositeGridData);
		composite.setLayout(gridLayout);
		composite.setBackground(getBackground());

		// 显示的label信息
		Button button = null;
		if (!view.label().equals("")) {
			String labelText = view.label() + ": ";
			if (view.checkable()) {
				button = new Button(composite, SWT.CHECK);
				GridData gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
				gridData.widthHint = form.labelWidth;
				gridData.verticalSpan = view.rowspan();
				button.setText(labelText);
				button.setBackground(getBackground());
				button.setLayoutData(gridData);

			} else {
				Label label = new Label(composite, SWT.NONE);
				GridData gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
				gridData.widthHint = form.labelWidth;
				gridData.verticalSpan = view.rowspan();
				label.setText(labelText);
				label.setBackground(getBackground());
				label.setLayoutData(gridData);// 2.modify
			}
		}

		final IInputControl inputControl = WidgetsFactory.getInstance().create(composite, formField);
		if (inputControl == null) {
			throw new IllegalArgumentException("无效的widget类型：" + view.type() + "。");
		}

		ControlCascade cascade = field.getAnnotation(ControlCascade.class);
		if (cascade != null) {
			casecadeFields.add(formField);
		}
		ControlCascades cascades = field.getAnnotation(ControlCascades.class);
		if (cascades != null) {
			casecadeFields.add(formField);
		}
		final Control editControl = (Control) inputControl;
		GridData editControlGridData = new GridData(GridData.FILL_HORIZONTAL);
		editControlGridData.heightHint = view.heightHint();
		editControl.setLayoutData(editControlGridData);
		editControl.setBackground(getBackground());
		// modify 设置控件可用状态
		setControlEnable(view, editControl);
		if (view.checkable() && button != null) {
			button.setSelection(view.checked());
			editControl.setEnabled(view.checked());
			editControl.setData(button);
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					try {
						editControl.setEnabled(((Button) arg0.widget).getSelection());
					} catch (Exception e) {
						MessageForm.Message(e);
					}
				}
			});
		}
		if (changedListener != null) {
			editControl.setData(CONTROL_PREFIX_VALUE, value);// 记录初始值
			editControl.addListener(SWT.FocusOut, new Listener() { // change监听
				public void handleEvent(Event arg0) {
					Object inputValue = inputControl.getObjectValue();
					Object pre_value = editControl.getData(CONTROL_PREFIX_VALUE);

					boolean hasChange = true;
					if (null != inputValue) {
						hasChange = !inputValue.equals(pre_value);
					} else if (pre_value != null) {
						hasChange = !pre_value.equals(inputValue);
					}
					if (hasChange) {
						Event event = new Event();
						event.item = editControl;
						event.data = pre_value;
						changedListener.editorChanged(event, true);
					}
					editControl.setData(CONTROL_PREFIX_VALUE, inputValue);
				}
			});
		}
		if (view.checkable() && value != null) {
			button.setSelection(true);
		}

		FourValue<BeanField, ViewData, Button, IInputControl> item = new FourValue<BeanField, ViewData, Button, IInputControl>();
		item.setElement1(formField);
		item.setElement2(view);
		item.setElement3(button);
		item.setElement4(inputControl);
		fieldlist.add(item);
		namehashMap.put(field.getName(), inputControl);

	}

	private final int calcLabelWidth(String label) {
		GC gc = new GC(getShell());
		int y = gc.stringExtent(label).x;
		gc.dispose();
		return y;
	}

	/**
	 * 创建时间 2011-2-21 下午07:11:55<br>
	 * 描述：设置控件可用状态
	 * 
	 * @param view
	 * @param editControl
	 */
	private final void setControlEnable(ViewData view, final Control editControl) {
		if (form.getFormMode() == FormMode.MODIFY) {
			editControl.setEnabled(view.modify());
		}
		if (form.getFormMode() == FormMode.VIEW) {
			editControl.setEnabled(false);
			// setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		}
	}

	public void setForm(final Form<T> inputForm) {
		this.form = inputForm;
		this.changedListener = this.form.changedListener;
		Object entity = this.form.getEntity();
		if (formClass == null || !formClass.equals(entity.getClass())) {
			clear();
			gridLayout.numColumns = this.form.getColums();
			gridLayout.makeColumnsEqualWidth = this.form.isColumnsEqualWidth;
			formClass = entity.getClass();
			createContents(entity, formClass);
			// FIXME 级联处理
			doCasecade();
		} else {
			for (FourValue<BeanField, ViewData, Button, IInputControl> item : fieldlist) {
				if (item.getElement2().checkable() && item.getElement3().getSelection()) {
					Object value = BeanUtil.getValue(this.form.getEntity(), item.getElement1().field.getName());
					item.getElement4().setObjectValue(value);
				} else if (!item.getElement2().checkable()) {
					Object value = BeanUtil.getValue(this.form.getEntity(), item.getElement1().field.getName());
					logger.debug(value);
					item.getElement4().setObjectValue(value);
				}
			}
		}
		layout();
	}

	protected final void doCasecade() {
		for (final BeanField field : casecadeFields) {
			final ControlCascade cascade = field.field.getAnnotation(ControlCascade.class);
			if (cascade != null) {
				registCasecadeListener(field, cascade);
			}
			final ControlCascades cascades = field.field.getAnnotation(ControlCascades.class);
			if (cascades != null) {
				for (ControlCascade controlCascade : cascades.value()) {
					registCasecadeListener(field, controlCascade);
				}
			}
		}
	}

	/**
	 * @param field
	 * @param cascade
	 */
	private final void registCasecadeListener(final BeanField field, final ControlCascade cascade) {

		IInputControl listenerControl = namehashMap.get(field.field.getName());

		if (listenerControl instanceof ICasecadeControl) {
			Map<String, IInputControl> changedCtrolList = getChangedCtrol(cascade.changedField());
			// 初始化监听接口
			CasecadeListener casecadeListener = getCasecadeListener(cascade);

			// 监听控件增加监听事件
			final ICasecadeControl listenerCasecadeCtrl = (ICasecadeControl) listenerControl;
			listenerCasecadeCtrl.addCasecadeListener(casecadeListener);

			// 被监听者中增加需要监控的控件
			Iterator<String> keyIterator = changedCtrolList.keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				final ICasecadeControl changedCasecadeCtrl = (ICasecadeControl) changedCtrolList.get(key);
				changedCasecadeCtrl.setFieldName(key);
				changedCasecadeCtrl.addCasecadeControl(listenerCasecadeCtrl);
			}
		}
	}

	private Map<String, IInputControl> getChangedCtrol(String changedFieldInfo) {
		Map<String, IInputControl> result = new HashMap<String, IInputControl>();
		if (null != changedFieldInfo) {
			if (changedFieldInfo.contains(",")) {
				String[] changedFields = changedFieldInfo.split(",");
				for (String fieldName : changedFields) {
					IInputControl changedCtrol = namehashMap.get(fieldName);
					if (changedCtrol instanceof ICasecadeControl) {
						result.put(fieldName, changedCtrol);
					}
				}
			} else {
				IInputControl changedCtrol = namehashMap.get(changedFieldInfo);
				if (changedCtrol instanceof ICasecadeControl) {
					result.put(changedFieldInfo, changedCtrol);
				}
			}
		}

		return result;
	}

	/**
	 * 初始化监听接口
	 * 
	 * @param cascade
	 * @return
	 */
	private final CasecadeListener getCasecadeListener(final ControlCascade cascade) {
		try {
			if (cascade.type().equals("byName")) {
				return (CasecadeListener) Application.getClassByName(cascade.listenerName());
			} else {
				return (CasecadeListener) Application.getClassBean(cascade.casecadeListener());
			}
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@Override
	public void reset() {
		for (FourValue<BeanField, ViewData, Button, IInputControl> item : fieldlist) {
			IInputControl control = item.getElement4();
			ViewData view = item.getElement2();
			if (view.checkable()) {
				item.getElement3().setSelection(view.checked());
				((Control) control).setEnabled(view.checked());
			}
			control.reset();
		}
	}

	@Override
	public void clear() {
		fieldlist.clear();
		namehashMap.clear();
		super.clear();
	}

	public Request getRequest() {
		FormRequest<T> request = new FormRequest<T>();
		request.setForm(getForm());
		return request;
	}

	public Object getObjectValue() {
		return getForm();
	}

	@SuppressWarnings("unchecked")
	public void setObjectValue(Object value) {
		if (value instanceof Form) {
			setForm((Form<T>) value);
		} else {
			Form<T> newForm = new Form<T>((T) value);
			setForm(newForm);
		}
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
	}

}
