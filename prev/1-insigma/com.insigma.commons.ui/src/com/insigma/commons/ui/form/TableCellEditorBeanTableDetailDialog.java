package com.insigma.commons.ui.form;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.dialog.StandardDialog;
import com.insigma.commons.ui.widgets.ktable.KTable;
import com.insigma.commons.ui.widgets.ktable.KTableCellEditor;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class TableCellEditorBeanTableDetailDialog extends KTableCellEditor {
	private DetailsDialog m_Dialog;

	public void open(KTable table, int col, int row, Rectangle rect) {
		m_Table = table;
		m_Model = table.getModel();
		m_Rect = rect;
		m_Row = row;
		m_Col = col;
		m_Dialog = getDialog(table.getShell());
		m_Dialog.open();
		close(true);
	}

	/**
	 * @return Returns the dialog that should be shown on
	 * editor activation.
	 */
	public DetailsDialog getDialog(Shell shell) {
		BeanEditorTableModel model = (BeanEditorTableModel) m_Model;
		BeanField cellValue = model.doGetContentDefine(m_Col, m_Row);
		DetailsDialog diloag = new DetailsDialog(shell, cellValue.fieldValue, cellValue.field);
		return diloag;
	}

	/**
	 * Called when the open-method returns.
	 */
	public void close(boolean save) {
		super.close(save);
		m_Dialog = null;
	}

	/**
	 * Sets the bounds of the dialog to the cell bounds.
	 * DEFAULT: Ignored. Set the required shell properties by
	 * overwriting the method <code>setupShellProperties(Shell)</code>.
	 */
	public void setBounds(Rectangle rect) {
		// ignored.
	}

	/**
	 * Ignored.
	 * @see de.kupzog.ktable.KTableCellEditor#setContent(java.lang.String)
	 */
	public void setContent(Object content) {
	}

	/**
	 * Ignored, since it is no longer in use. We use a dialog instead 
	 * of a control!
	 */
	protected Control createControl() {
		return null;
	}

	private class DetailsDialog extends Dialog {

		protected Shell shell;

		/**
		 * Create the dialog
		 * 
		 * @param parent
		 * @param style
		 */
		public DetailsDialog(Shell parent, Object value, Field field) {
			super(parent, SWT.NONE);
			shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.verticalSpacing = 0;
			gridLayout.marginWidth = 0;
			gridLayout.marginHeight = 0;
			gridLayout.horizontalSpacing = 0;
			shell.setLayout(gridLayout);
			int width = 400;
			int height = 280;
			shell.setSize(700, 400);
			shell.setLocation(FormUtil.getCenterLocation(width, height));
			View view = field.getAnnotation(View.class);
			if (view != null && view.label() != null) {
				shell.setText(view.label());
			}
			createContents(value, field);

		}

		/**
		 * Open the dialog
		 * 
		 * @return the result
		 */
		public void open() {
			shell.setImage(SWTResourceManager.getImage(StandardDialog.class, "/com/insigma/afc/images/logo.png"));
			shell.open();
			shell.layout();
			Display display = getParent().getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}

		/**
		 * Create contents of the dialog
		 */
		protected void createContents(Object value, Field field) {
			boolean isNeedToolBar = true;
			View annotation = field.getAnnotation(View.class);
			if (value.getClass().isArray() || !annotation.editable()) {
				isNeedToolBar = false;
			}

			// TODO:参数
			final BeanTableEditor beanEditor = new BeanTableEditor(shell, SWT.NONE);
			beanEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
			BeanEditorTableModel model = (BeanEditorTableModel) m_Model;
			beanEditor.setChangedListener(model.changedListener);
			try {
				beanEditor.setHeader(field);
				beanEditor.setObjectList(value);
			} catch (Exception e) {
				MessageForm.Message("弹出框内容设置异常", e);
			}

		}
	}
}
