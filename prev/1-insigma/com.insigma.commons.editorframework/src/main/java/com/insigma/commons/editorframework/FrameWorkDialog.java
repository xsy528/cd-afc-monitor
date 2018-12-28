package com.insigma.commons.editorframework;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.StandardDialog;

public class FrameWorkDialog extends StandardDialog {

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

	private EditorFrameWork frameWork;

	private List<Action> actions;

	public FrameWorkDialog(EditorFrameWork parent, int style) {
		super(parent.getShell(), style);
		this.frameWork = parent;
		actions = new ArrayList<Action>();
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

		createAction(actions);

		if (actions.size() > 0) {
			gridLayout_1.numColumns = actions.size();
			for (final Action action : actions) {
				final Button exitBtn = new Button(btnComposite, SWT.NONE);
				exitBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						try {
							ActionContext context = new ActionContext(action);
							context.setFrameWork(frameWork);
							if (!(action instanceof CloseAction)) {
								context.setData(ActionContext.RESULT_DATA, getResult());
							}
							action.getHandler().perform(context);
						} catch (Exception e) {
							MessageForm.Message(e);
						}
					}
				});
				exitBtn.setLayoutData(new GridData(70, 30));
				exitBtn.setText(action.getName());
			}
		}
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	protected void createAction(List<Action> actions) {
	}

	public EditorFrameWork getFrameWork() {
		return frameWork;
	}

	public void setFrameWork(EditorFrameWork frameWork) {
		this.frameWork = frameWork;
	};

	public void addCloseAction() {
		actions.add(new CloseAction());
	}
}
