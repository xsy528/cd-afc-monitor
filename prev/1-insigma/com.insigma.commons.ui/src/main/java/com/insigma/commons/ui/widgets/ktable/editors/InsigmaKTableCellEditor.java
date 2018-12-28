package com.insigma.commons.ui.widgets.ktable.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanEditorTableModel;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.TableCellEditorBeanTableDetailDialog;
import com.insigma.commons.ui.form.WidgetsFactory;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.ktable.KTable;
import com.insigma.commons.ui.widgets.ktable.KTableCellEditor;

public class InsigmaKTableCellEditor extends KTableCellEditor {

	BeanField beanField;
	ViewData view;

	public InsigmaKTableCellEditor(ViewData view, BeanField beanField) {
		super();
		this.beanField = beanField;
		this.view = view;
	}

	private KeyAdapter keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			try {
				onKeyPressed(e);
			} catch (Exception ex) {
				ex.printStackTrace();
				// Do nothing
			}
		}
	};

	private TraverseListener travListener = new TraverseListener() {
		public void keyTraversed(TraverseEvent e) {
			onTraverse(e);
		}
	};

	public void open(KTable table, int col, int row, Rectangle rect) {
		if (beanField.field.getAnnotation(ListType.class) != null
				&& !((BeanEditorTableModel) table.getModel()).isSimpleType()) {
			TableCellEditorBeanTableDetailDialog dialog = new TableCellEditorBeanTableDetailDialog();
			dialog.open(table, col, row, rect);
		} else {
			super.open(table, col, row, rect);
			m_Control.setVisible(true);
			m_Control.setFocus();
		}
	}

	/**
	 * Overwrite this method if you do not want to use the content provided by the model.
	 * 
	 * @return The content for the editor.
	 */
	protected String getEditorContent() {
		return m_Model.getContentAt(m_Col, m_Row).toString();
	}

	public void close(boolean save) {
		if (m_Control != null) {
			if (save) {
				try {
					m_Model.setContentAt(m_Col, m_Row, ((IInputControl) m_Control).getObjectValue());
				} catch (Exception e) {
					String message = "设置单元格：[" + view.label() + "]值错误";
					logger.error(message, e);
					doClose(save);
					MessageForm.Message("提示信息", message, SWT.ICON_ERROR);
					return;
				}
			}
		}
		doClose(save);
	}

	private void doClose(boolean save) {
		if (m_Control != null) {
			m_Control.removeKeyListener(keyListener);
			m_Control.removeTraverseListener(travListener);
		}
		super.close(save);
	}

	protected Control createControl() {
		m_Control = (Control) WidgetsFactory.getInstance().create(m_Table, beanField);
		if (m_Control != null) {
			m_Control.addKeyListener(keyListener);
			m_Control.addTraverseListener(travListener);
		}
		m_toolTip = view.label();
		return m_Control;
	}

	public void setContent(Object content) {
		((IInputControl) m_Control).setObjectValue(content);
	}

}