/**
 * 
 */
package com.insigma.afc.topology;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.insigma.commons.ui.anotation.View;

/**
 * @author changjin_qiu
 *

 */
@Embeddable
public class AFCNodeLocation implements Serializable {

	private static final long serialVersionUID = -3784271692331046578L;

	@View(label = "X")
	private int x;

	@View(label = "Y")
	private int y;

	@View(label = "角度")
	private int angle;

	public AFCNodeLocation() {
		super();
	}

	public AFCNodeLocation(int x, int y, int angle) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public AFCNodeLocation(AFCNodeLocation location) {
		super();
		this.x = location.x;
		this.y = location.y;
		this.angle = location.angle;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}

}
