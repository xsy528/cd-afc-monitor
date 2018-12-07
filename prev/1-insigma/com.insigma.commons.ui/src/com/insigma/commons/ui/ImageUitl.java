package com.insigma.commons.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class ImageUitl {

	public static Image getImage(Image image, Rectangle rect) {
		Image subImage = new Image(Display.getDefault(), rect.width, rect.height);
		GC gc = new GC(subImage);
		gc.drawImage(image, -rect.x, -rect.y);
		gc.dispose();
		return subImage;
	}
}
