/* 
 * 日期：2016-4-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.form.editor.DefaultEditor;
import com.insigma.commons.editorframework.form.editor.IComponentEditor;
import com.insigma.commons.ui.form.IEditorChangedListener;
import com.insigma.commons.ui.widgets.EnhanceComposite;

public class EditorManagerComposite extends FrameWorkView implements IComponentEditor {
	protected boolean isEditable = true;

	protected Object value;

	protected Object compareValue;

	protected IEditorChangedListener changedListener;

	protected final EnhanceComposite mainComposite;

	protected final StackLayout stackLayout;

	private EnhanceComposite defaultEditor;

	private Map<Class, Class> entityCompositeClassMap = new HashMap<Class, Class>();

	private Map<Class, Composite> editorCompositeMap = new HashMap<Class, Composite>();

	public EditorManagerComposite(Composite arg0, int arg1) {
		super(arg0, arg1);
		setText("参数编辑器");
		mainComposite = new EnhanceComposite(this, SWT.NONE);
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stackLayout = new StackLayout();
		mainComposite.setLayout(stackLayout);

		defaultEditor = new DefaultEditor(mainComposite, SWT.NONE);
		stackLayout.topControl = defaultEditor;
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public void addEntityEditorComposite(Class entity, Class editorClass) {
		try {
			if (null == editorCompositeMap.get(editorClass)) {
				Composite composite = (Composite) editorClass.getConstructor(Composite.class, int.class)
						.newInstance(mainComposite, SWT.NONE);
				editorCompositeMap.put(editorClass, composite);
			}

			entityCompositeClassMap.put(entity, editorClass);
		} catch (Exception e) {
			logger.error("编辑界面创建失败：" + editorClass, e);
		}
	}

	public void setCompareValue(Object compareValue) {
		this.compareValue = compareValue;
	}

	@Override
	public Object getValue() {
		if (null != stackLayout.topControl) {
			IComponentEditor editor = (IComponentEditor) stackLayout.topControl;
			return editor.getValue();
		}
		return null;
	}

	@Override
	public void setValue(Object value) {
		Composite editorComposite = getEditorComposite(value);
		IComponentEditor editor = (IComponentEditor) editorComposite;
		editor.setEditable(isEditable);
		editor.setChangedListener(changedListener);

		if (null != compareValue && null != value && compareValue.getClass().equals(value.getClass())) {
			editor.setCompareValue(compareValue);
		} else {
			editor.setCompareValue(null);
		}

		editor.setValue(value);

		stackLayout.topControl = editorComposite;
		mainComposite.layout();
	}

	public void reset() {
		stackLayout.topControl = defaultEditor;
		defaultEditor.clear();
		mainComposite.layout();
	}

	/**
	 * 根据对象获取编辑界面
	 * @param value
	 * @return
	 */
	private Composite getEditorComposite(Object value) {
		Composite result = defaultEditor;
		if (null != value) {
			Class entity = value.getClass();
			Class editorCompositeClass = entityCompositeClassMap.get(entity);
			if (null != editorCompositeClass) {
				result = (Composite) editorCompositeMap.get(editorCompositeClass);
			}
		}

		return result;
	}
}
