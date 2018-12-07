/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.control;

import java.util.Date;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.swtdesigner.SWTResourceManager;

public class ImageClock extends EnhanceComposite {

	private Image[] digitalImage;

	private Image sep;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public ImageClock(Composite parent, int style) {

		super(parent, style);

		digitalImage = new Image[10];

		for (int i = 0; i < 10; i++) {
			digitalImage[i] = SWTResourceManager.getImage(ImageClock.class,
					"/com/insigma/commons/ui/images/" + i + ".png");

		}
		sep = SWTResourceManager.getImage(ImageClock.class, "/com/insigma/commons/ui/images/s.png");
		this.addPaintListener(new PaintListener() {
			@SuppressWarnings("deprecation")
			public void paintControl(PaintEvent arg0) {
				GC gc = arg0.gc;
				Date date = new Date();
				String time = "";
				if (date.getHours() <= 9) {
					time = "0" + date.getHours();
				} else {
					time = Integer.toString(date.getHours());
				}

				if (date.getMinutes() <= 9) {
					time = time + ":0" + date.getMinutes();
				} else {
					time = time + ":" + date.getMinutes();
				}

				int width = 50;
				for (int i = 0; i < time.length(); i++) {
					char value = time.charAt(i);
					if (value == ':') {
						gc.drawImage(sep, width * i, 0);
					} else {
						int d = value - '0';
						gc.drawImage(digitalImage[d], width * i, 0);
					}

				}
				gc.dispose();
			}
		});

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (!isDisposed()) {
					redraw();
					Display.getDefault().timerExec(10000, this);
				}

			}
		});
	}
}
