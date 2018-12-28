/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.control;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.swtdesigner.SWTResourceManager;

public class ImageTime extends EnhanceComposite {

	private Image[] digitalImage;

	private Image sep;

	private int hour = 10;

	private int minute = 23;

	public ImageTime(Composite parent, int style) {

		super(parent, style);

		digitalImage = new Image[10];

		for (int i = 0; i < 10; i++) {
			digitalImage[i] = SWTResourceManager.getImage(ImageTime.class,
					"/com/insigma/commons/ui/images/" + i + ".png");

		}
		sep = SWTResourceManager.getImage(ImageTime.class, "/com/insigma/commons/ui/images/s.png");
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				GC gc = arg0.gc;
				String time = "";
				if (hour <= 9) {
					time = "0" + hour;
				} else {
					time = Integer.toString(hour);
				}

				if (minute <= 9) {
					time = time + ":0" + minute;
				} else {
					time = time + ":" + minute;
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
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
}
