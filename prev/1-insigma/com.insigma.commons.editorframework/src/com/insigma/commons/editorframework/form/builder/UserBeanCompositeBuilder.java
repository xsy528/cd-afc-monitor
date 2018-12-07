/* 
 * 日期：2011-12-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.builder;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.form.BeanEditor;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.IEditorChangedListener;
import com.insigma.commons.ui.form.PropertyTreeViewer;

/**
 * @author qiuchangjin@zdwxgd.com<br/>
 * #Ticket
 */
public class UserBeanCompositeBuilder {
	public static final Log logger = LogFactory.getLog(UserBeanCompositeBuilder.class);
	public static final int pageSize = 5;

	/**
	 * 构造查看结构体
	 * @return
	 */
	public Composite builderViewBeanCompsite(Composite parent, final Object object) {
		PropertyTreeViewer propertyGrid = new PropertyTreeViewer(parent, SWT.NONE);
		propertyGrid.setLayoutData(new GridData(GridData.FILL_BOTH));
		propertyGrid.setObject(object);
		return propertyGrid;
	}

	/**
	 * 构造可编辑结构体
	 * @return
	 */
	public Composite builderEditableBeanCompsite(Composite parent, BeanField beanField,
			IEditorChangedListener changedListener) {
		BeanEditor beanEditor = new BeanEditor(parent, SWT.NONE);
		beanEditor.setNeedSubGroup(true);
		beanEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		beanEditor.setChangedListener(changedListener);
		beanEditor.setCompareData(beanField.historyValue);
		//		beanEditor.setObject(beanField.fieldValue);
		beanEditor.setObject(beanField);
		return beanEditor;
	}

	/**
	 * 构造可编辑结构体
	 * @return
	 */
	public Composite builderEditableSimpleBeanCompsite(Composite parent, Field field, Object subValue,
			IEditorChangedListener changedListener) {
		BeanEditor beanEditor = new BeanEditor(parent, SWT.NONE);
		beanEditor.setNeedSubGroup(true);
		beanEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		beanEditor.setChangedListener(changedListener);
		beanEditor.setSimpleTypeObject(field, subValue);
		return beanEditor;
	}

}
