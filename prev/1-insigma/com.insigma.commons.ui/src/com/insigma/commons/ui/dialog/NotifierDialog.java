package com.insigma.commons.ui.dialog;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.application.Application;
import com.swtdesigner.SWTResourceManager;

public class NotifierDialog {

	/**
	 * 
	 */
	private static final int MIN_HIGHT_ = 70;
	// how long the the tray popup is displayed after fading in (in milliseconds)
	private static final int DISPLAY_TIME = 4500;

	// how long each tick is when fading in (in ms)
	private static final int FADE_TIMER = 50;

	// how long each tick is when fading out (in ms)
	private static final int FADE_IN_STEP = 30;

	// how many tick steps we use when fading out
	private static final int FADE_OUT_STEP = 8;

	// how high the alpha value is when we have finished fading in
	private static final int FINAL_ALPHA = 225;

	// title foreground color
	private static Color _titleFgColor = SWTResourceManager.getColor(40, 73, 97);
	// text foreground color
	private static Color _fgColor = _titleFgColor;

	// shell gradient background color - top
	private static Color _bgFgGradient = SWTResourceManager.getColor(226, 239, 249);
	// shell gradient background color - bottom
	private static Color _bgBgGradient = SWTResourceManager.getColor(177, 211, 243);
	// shell border color
	private static Color _borderColor = SWTResourceManager.getColor(40, 73, 97);

	// contains list of all active popup shells
	private static Map<Long, Shell> _activeShells = new HashMap<Long, Shell>();

	// image used when drawing
	private static Image _oldImage;

	private static Shell _shell;

	private static int minHeight;

	/**
	 * Creates and shows a notification dialog with a specific title, message and a
	 * 
	 * @param title
	 * @param message
	 * @param type
	 */

	public static void notify(final Long did, String title, String message, Image type) {
		if (_activeShells.containsKey(did) && !_activeShells.get(did).isDisposed()) {
			_activeShells.get(did).setVisible(true);
			return;
		}
		_shell = new Shell(Display.getDefault().getActiveShell(), SWT.NO_FOCUS | SWT.DIALOG_TRIM);
		_shell.setLayout(new FillLayout());
		_shell.setForeground(_fgColor);
		_shell.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				_activeShells.remove(did);
			}
		});

		final Composite inner = new Composite(_shell, SWT.NONE);

		GridLayout gl = new GridLayout(2, false);
		gl.marginLeft = 5;
		gl.marginTop = 0;
		gl.marginRight = 5;
		gl.marginBottom = 5;

		inner.setLayout(gl);
		_shell.addListener(SWT.Resize, new Listener() {

			public void handleEvent(Event e) {
				try {
					// get the size of the drawing area
					Rectangle rect = _shell.getClientArea();
					// create a new image with that size
					Image newImage = new Image(Display.getDefault(), Math.max(1, rect.width), rect.height);
					// create a GC object we can use to draw with
					GC gc = new GC(newImage);

					// fill background
					gc.setForeground(_bgFgGradient);
					gc.setBackground(_bgBgGradient);
					gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);

					// draw shell edge
					gc.setLineWidth(2);
					gc.setForeground(_borderColor);
					gc.drawRectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
					// remember to dipose the GC object!
					gc.dispose();

					// now set the background image on the shell
					_shell.setBackgroundImage(newImage);

					// remember/dispose old used iamge
					if (_oldImage != null) {
						_oldImage.dispose();
					}
					_oldImage = newImage;
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		});

		GC gc = new GC(_shell);

		String lines[] = message.split("\n");
		Point longest = null;
		int typicalHeight = gc.stringExtent("X").y;

		for (String line : lines) {
			Point extent = gc.stringExtent(line);
			if (longest == null) {
				longest = extent;
				continue;
			}

			if (extent.x > longest.x) {
				longest = extent;
			}
		}
		gc.dispose();

		minHeight = typicalHeight * (lines.length + 4);

		CLabel imgLabel = new CLabel(inner, SWT.NONE);
		imgLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING));
		imgLabel.setImage(type);

		_shell.setText(title);
		_shell.setImage(type);

		CLabel titleLabel = new CLabel(inner, SWT.NONE);
		titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		titleLabel.setText(message);
		titleLabel.setForeground(_titleFgColor);
		Font f = titleLabel.getFont();
		titleLabel.setFont(SWTResourceManager.getBoldFont(f));

		if (minHeight < MIN_HIGHT_) {
			minHeight = MIN_HIGHT_;
		}

		int width = 200;
		_shell.setSize(width, minHeight);

		if (Display.getDefault().getActiveShell() == null
				|| Display.getDefault().getActiveShell().getMonitor() == null) {
			return;
		}
		Rectangle clientArea = Display.getDefault().getActiveShell().getMonitor().getClientArea();

		int startX = clientArea.x + clientArea.width - width;
		int startY = clientArea.y + clientArea.height - minHeight;
		// move other shells up
		// if (!_activeShells.isEmpty()) {
		// List<Shell> modifiable = new ArrayList<Shell>(_activeShells);
		// Collections.reverse(modifiable);
		// for (Shell shell : modifiable) {
		// if (shell.isDisposed()) {
		// _activeShells.remove(shell);
		// continue;
		// }
		// startX += (width) * (count / off);
		// startY -= minHeight;
		// }
		// }

		_shell.setLocation(startX, startY);
		_shell.setVisible(true);

		_activeShells.put(did, _shell);

	}

	public static boolean notify(final Long did, String title, String message, Image type, int width) {
		if (_activeShells.containsKey(did) && !_activeShells.get(did).isDisposed()) {
			_activeShells.get(did).setVisible(true);
			return false;
		}
		_shell = new Shell(Display.getDefault(), SWT.NO_FOCUS | SWT.DIALOG_TRIM);
		_shell.setLayout(new FillLayout());
		_shell.setForeground(_fgColor);
		_shell.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				_activeShells.remove(did);
			}
		});

		final Composite inner = new Composite(_shell, SWT.NONE);

		GridLayout gl = new GridLayout(2, false);
		gl.marginLeft = 5;
		gl.marginTop = 0;
		gl.marginRight = 5;
		gl.marginBottom = 5;

		inner.setLayout(gl);
		_shell.addListener(SWT.Resize, new Listener() {

			public void handleEvent(Event e) {
				try {
					// get the size of the drawing area
					Rectangle rect = _shell.getClientArea();
					// create a new image with that size
					Image newImage = new Image(Display.getDefault(), Math.max(1, rect.width), rect.height);
					// create a GC object we can use to draw with
					GC gc = new GC(newImage);

					// fill background
					gc.setForeground(_bgFgGradient);
					gc.setBackground(_bgBgGradient);
					gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);

					// draw shell edge
					gc.setLineWidth(2);
					gc.setForeground(_borderColor);
					gc.drawRectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
					// remember to dipose the GC object!
					gc.dispose();

					// now set the background image on the shell
					_shell.setBackgroundImage(newImage);

					// remember/dispose old used iamge
					if (_oldImage != null) {
						_oldImage.dispose();
					}
					_oldImage = newImage;
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		});

		GC gc = new GC(_shell);

		String lines[] = message.split("\n");
		Point longest = null;
		int typicalHeight = gc.stringExtent("X").y;

		for (String line : lines) {
			Point extent = gc.stringExtent(line);
			if (longest == null) {
				longest = extent;
				continue;
			}

			if (extent.x > longest.x) {
				longest = extent;
			}
		}
		gc.dispose();

		minHeight = typicalHeight * (lines.length + 4);

		CLabel imgLabel = new CLabel(inner, SWT.NONE);
		imgLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING));
		imgLabel.setImage(type);

		_shell.setText(title);
		_shell.setImage(type);

		CLabel titleLabel = new CLabel(inner, SWT.NONE);
		titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		titleLabel.setText(message);
		titleLabel.setForeground(_titleFgColor);
		Font f = titleLabel.getFont();
		titleLabel.setFont(SWTResourceManager.getBoldFont(f));

		if (minHeight < MIN_HIGHT_) {
			minHeight = MIN_HIGHT_;
		}

		_shell.setSize(width, minHeight);

		Shell activeShell = Display.getDefault().getActiveShell();
		if (activeShell == null || activeShell.getMonitor() == null) {

			if (Application.getShell() != null && Application.getShell().getMonitor() != null) {
				activeShell = Application.getShell();

			} else {
				return false;
			}

			// return false;
		}
		Rectangle clientArea = activeShell.getMonitor().getClientArea();

		int startX = clientArea.x + clientArea.width - width;
		int startY = clientArea.y + clientArea.height - minHeight;
		// move other shells up
		// if (!_activeShells.isEmpty()) {
		// List<Shell> modifiable = new ArrayList<Shell>(_activeShells);
		// Collections.reverse(modifiable);
		// for (Shell shell : modifiable) {
		// if (shell.isDisposed()) {
		// _activeShells.remove(shell);
		// continue;
		// }
		// startX += (width) * (count / off);
		// startY -= minHeight;
		// }
		// }

		_shell.setLocation(startX, startY);
		_shell.setVisible(true);

		_activeShells.put(did, _shell);
		return true;

	}

	/**
	 * @param _shell
	 */
	public static void close(final Shell _shell) {
		if (_oldImage != null) {
			_oldImage.dispose();
		}
		_shell.dispose();
		_activeShells.remove(_shell);
	}

	public static void close(final Long did) {
		if (_oldImage != null) {
			_oldImage.dispose();
		}
		if (_activeShells.containsKey(did)) {
			_activeShells.get(did).dispose();
			_activeShells.remove(_shell);
		}
	}

}
