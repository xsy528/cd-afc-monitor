package com.insigma.commons.ui;

import java.awt.Toolkit;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Monitor;

public class FormUtil {

	/**
	 * 得到Form的中心位置
	 * 
	 * @param width
	 *            Form宽度
	 * @param height
	 *            Form高度
	 * @return
	 */
	static public Point getCenterLocation(int width, int height) {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		int left = (screenWidth - width) / 2;
		int right = (screenHeight - height) / 2;

		return new Point(left, right);
	}

	/**
	 * 得到Composite的中心位置
	 * 
	 * @param composite
	 * @param width
	 *            composite宽度
	 * @param height
	 *            composite高度
	 * @return
	 */
	static public Point getCenterLocation(Composite composite, int width, int height) {
		int compositeWidth = composite.getClientArea().width;
		int compositeHeight = composite.getClientArea().height;

		int left = (compositeWidth - width) / 2;
		int right = (compositeHeight - height) / 2;

		return new Point(left, right);
	}

	static public Point getCenterLocation(Composite composite) {
		int compositeWidth = composite.getClientArea().width;
		int compositeHeight = composite.getClientArea().height;

		int left = (compositeWidth - composite.getSize().x) / 2;
		int right = (compositeHeight - composite.getSize().y) / 2;

		return new Point(left, right);
	}

	static public Point getCenterLocation(Monitor monitor, Composite composite) {

		int screenWidth = monitor.getBounds().width;
		int screenHeight = monitor.getBounds().height;

		int compositeWidth = composite.getClientArea().width;
		int compositeHeight = composite.getClientArea().height;

		int left = (screenWidth - compositeWidth) / 2 + monitor.getBounds().x;
		int right = (screenHeight - compositeHeight) / 2 + monitor.getBounds().y;

		return new Point(left, right);
	}

	static public Point getCenterLocation(Monitor monitor, int width, int height) {

		int screenWidth = monitor.getBounds().width;
		int screenHeight = monitor.getBounds().height;

		int left = (screenWidth - width) / 2 + monitor.getBounds().x;
		int right = (screenHeight - height) / 2 + monitor.getBounds().y;

		return new Point(left, right);
	}
}
