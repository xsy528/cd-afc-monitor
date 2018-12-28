/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.graphic.editor.action.MoveAction;
import com.insigma.commons.editorframework.graphic.editor.action.MoveAction.MoveMode;

/**
 * @author 邱昌进(qiuchangjin@zdwxgd.com)
 *
 */
public class MapKeyListener implements KeyListener {

	MoveAction moveAction = new MoveAction(MoveMode.LEFT);

	MapComposite mapComposite;

	MapActionExecutor actionExecutor;

	public MapKeyListener(MapComposite mapComposite) {
		this.mapComposite = mapComposite;
		this.actionExecutor = mapComposite.getActionExecutor();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ActionContext context = new ActionContext(moveAction);
		context.setFrameWork(mapComposite.getFrameWork());
		if ((e.stateMask & SWT.CTRL) == 0) {
			if (e.keyCode == SWT.ARROW_LEFT) {
				moveAction.setAlignMode(MoveMode.LEFT);
				moveAction.getHandler().perform(context);
			} else if (e.keyCode == SWT.ARROW_RIGHT) {
				moveAction.setAlignMode(MoveMode.RIGHT);
				moveAction.getHandler().perform(context);
			} else if (e.keyCode == SWT.ARROW_DOWN) {
				moveAction.setAlignMode(MoveMode.BOTTOM);
				moveAction.getHandler().perform(context);
			} else if (e.keyCode == SWT.ARROW_UP) {
				moveAction.setAlignMode(MoveMode.TOP);
				moveAction.getHandler().perform(context);
			}
			mapComposite.redraw();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.stateMask & SWT.CTRL) != 0) {
			if (e.keyCode == 122) {//z
				actionExecutor.undo();
				mapComposite.redraw();
			} else if (e.keyCode == 121) {//y
				actionExecutor.redo();
				mapComposite.redraw();
			} else if (e.keyCode == 118) {//v

			} else if (e.keyCode == 97) {//C

			}
		}

	}

}
