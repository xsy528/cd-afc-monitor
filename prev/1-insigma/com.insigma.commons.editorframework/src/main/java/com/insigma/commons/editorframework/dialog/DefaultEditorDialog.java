/* 
 * 日期：2010-11-7
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.dialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.form.editor.DefaultEditor;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.dialog.StandardDialog;

public class DefaultEditorDialog extends StandardDialog {

	private DefaultEditor editor;

	private Field field;

	private Object value;

	private Object compareValue;

	private boolean isEditable = true;

	private String title;

	private String description;

	private List<Action> actions = new ArrayList<Action>();

	public DefaultEditorDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void setCompareValue(Object compareValue) {
		this.compareValue = compareValue;
	}

	protected void createBar() {

		final Composite btnComposite = new Composite(getShell(), SWT.NONE);
		final GridData gdBtnComposite = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		gdBtnComposite.heightHint = 60;
		btnComposite.setLayoutData(gdBtnComposite);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.marginTop = 15;
		gridLayout_1.marginWidth = 15;
		gridLayout_1.marginHeight = 0;
		gridLayout_1.horizontalSpacing = 15;
		gridLayout_1.numColumns = 2;
		btnComposite.setLayout(gridLayout_1);

		addCloseAction();

		gridLayout_1.numColumns = actions.size();

		for (final Action action : actions) {
			final Button btn = new Button(btnComposite, SWT.NONE);
			btn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent arg0) {
					try {
						ActionContext context = new ActionContext(action);
						if (!(action instanceof CloseAction)) {
							context.setData(editor.getValue());
						}
						action.getHandler().perform(context);

						setResult(context.getData(ActionContext.RESULT_DATA));
					} catch (Exception e) {
						MessageForm.Message(e);
					}
				}
			});
			btn.setLayoutData(new GridData(70, 30));
			btn.setText(action.getName());
		}
	}

	@Override
	protected void createContents(Composite parent) {
		// 设置提示信息
		super.setTitle(title);
		super.setDescription(description);

		parent.setLayout(new GridLayout());
		editor = new DefaultEditor(parent, SWT.BORDER);
		editor.setLayoutData(new GridData(GridData.FILL_BOTH));
		editor.setEditable(isEditable);
		editor.setCompareValue(compareValue);
		if (BeanUtil.isBaseType(value.getClass())) {
			editor.setSimpleValue(field, value);
		} else {
			editor.setValue(value);
		}
		parent.layout();
	}

	public DefaultEditor getEditor() {
		return editor;
	}

	public void setField(Field field) {
		this.field = field;
		if (null != field) {
			View view = field.getAnnotation(View.class);
			if (null != view) {
				setTitle(view.label());
				setDescription(view.label());
			}
		}
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	private void addCloseAction() {
		actions.add(new CloseAction());
	}

	public class CloseAction extends Action {
		public CloseAction() {
			super("关闭 (&X)");
			this.setImage("/com/insigma/commons/editorframework/images/export.png");
			IActionHandler handler = new ActionHandlerAdapter() {
				public void perform(ActionContext context) {
					getShell().close();
				}
			};
			setHandler(handler);
		}
	}
}
