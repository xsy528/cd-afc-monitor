package com.insigma.commons.editorframework.graphic.editor.action;

import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.editor.MapComposite;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.framework.application.WindowApplication;
import com.insigma.commons.ui.widgets.Shell;

/**
 * 
 * @author DLF
 *
 */
public class FullScreenAction extends Action {

	public class FullScrnActionHandler extends ActionHandlerAdapter {

		@Override
		public void perform(ActionContext context) {
			final EditorFrameWork frameWork = context.getFrameWork();
			if (frameWork == null) {
				return;
			}
			final MapEditorView view = (MapEditorView) frameWork.getView("MONITOR_MAPEDITORVIEW");
			if (view == null) {
				return;
			}
			final MapComposite map = view.getMap();
			if (map == null) {
				return;
			}
			final Shell window = new Shell(Display.getDefault(), SWT.APPLICATION_MODAL);

			final Composite parent = view.getParent();
			view.setParent(window);
			view.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			window.setLayout(new GridLayout(1, true));
			window.setFullScreen(true);
			int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
			window.setSize(screenWidth, screenHeight);
			map.addKeyListener(new KeyAdapter() {

				@Override
				public void keyReleased(KeyEvent event) {
					if (window.isDisposed()) {
						return;
					}
					if (event.character == SWT.ESC) {
						view.setParent(parent);
						map.removeKeyListener(this);
						parent.layout();
						window.close();
						WindowApplication.getFrameworkWindow().setVisible(true);
						WindowApplication.getFrameworkWindow().forceActive();
					} else if (event.character == SWT.BS) {
						MapTreeView mapTreeView = (MapTreeView) frameWork.getView(MapTreeView.class);
						if (mapTreeView == null) {
							return;
						}
						mapTreeView.navUp();

					}

				}

			});

			map.addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					//1 左键
					if (window.isDisposed()) {
						return;
					}
					if (e.button == 1) {
						view.setParent(parent);
						map.removeMouseListener(this);
						parent.layout();
						window.close();
						WindowApplication.getFrameworkWindow().setVisible(true);
						WindowApplication.getFrameworkWindow().forceActive();
					}
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
				}
			});
			WindowApplication.getFrameworkWindow().setVisible(false);
			window.open();
		}
	}

	public FullScreenAction() {
		super("全屏");
		setImage("/com/insigma/commons/ui/function/fullscreen.png");
		setHandler(new FullScrnActionHandler());
	}

}
