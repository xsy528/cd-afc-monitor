/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor.action;

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ImagePrinter {

	public void print(Image image) {

		Shell shell = Display.getDefault().getActiveShell();

		try {

			if (image.getImageData() != null) {
				// Show the Choose Printer dialog
				PrintDialog dialog = new PrintDialog(shell, SWT.NULL);
				PrinterData printerData = dialog.open();

				if (printerData != null) {
					// Create the printer object
					Printer printer = new Printer(printerData);

					// Calculate the scale factor between the screen resolution
					// and printer
					// resolution in order to correctly size the image for the
					// printer
					Point screenDPI = Display.getDefault().getDPI();
					Point printerDPI = printer.getDPI();
					int scaleFactor = printerDPI.x / screenDPI.x;

					// Determine the bounds of the entire area of the printer
					Rectangle trim = printer.computeTrim(0, 0, 0, 0);

					// Start the print job
					if (printer.startJob("打印图片")) {
						if (printer.startPage()) {
							GC gc = new GC(printer);
							// Draw the image
							gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, -trim.x,
									-trim.y, scaleFactor * image.getBounds().width,
									scaleFactor * image.getBounds().height);
							gc.dispose();
							printer.endPage();
						}
					}
					// End the job and dispose the printer
					printer.endJob();
					printer.dispose();
				}
			}
		} catch (Exception e) {
		}
	}
}
