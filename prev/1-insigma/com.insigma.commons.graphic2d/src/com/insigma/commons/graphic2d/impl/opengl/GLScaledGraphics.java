/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.opengl;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GLScaledGraphics extends Graphics {

	public void setViewPort(int width, int height) {
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_PROJECTION); // select the projection matrix
		GL11.glLoadIdentity(); // reset the projection matrix
		float fAspect = (float) width / (float) height;
		GLU.gluPerspective(170.0f, fAspect, 0.5f, 1200.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // select the modelview matrix
		GL11.glLoadIdentity();
	}

	/**
	 * @param xpos
	 * @param ypos
	 * @param radius
	 *            根据圆心点和半径画圆
	 */
	public void drawCircle(float xpos, float ypos, float radius) {
		int slice = 16;
		GL11.glPushMatrix();
		GL11.glTranslatef(xpos, ypos, 0.0f);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2f(0, 0);
		for (float sector = (new Double(2 * Math.PI)).floatValue(); sector > 0; sector -= (Math.PI / slice)) {
			GL11.glVertex2f((new Double(radius * Math.sin(sector))).floatValue(),
					(new Double(radius * Math.cos(sector))).floatValue());
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	/**
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param point4
	 *            根据传入的四个点画矩形
	 */
	public void drawQuads(Point point1, Point point2, Point point3, Point point4) {
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(point1.x, point1.y, 0.0f);
		GL11.glVertex3f(point2.x, point2.y, 0.0f);
		GL11.glVertex3f(point3.x, point3.y, 0.0f);
		GL11.glVertex3f(point4.x, point4.y, 0.0f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	/**
	 * @param point1
	 * @param point2
	 * @param point3
	 *            画三角
	 */
	public void drawTriangle(Point point1, Point point2, Point point3) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex3f(point1.x, point1.y, 0.0f); // 设置顶点位置
		GL11.glVertex3f(point2.x, point2.y, 0.0f);
		GL11.glVertex3f(point3.x, point3.y, 0.0f);
		GL11.glEnd();
	}

	/**
	 * 灰色
	 */
	public void paintGray() {
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
	}

	/**
	 * 暗灰色
	 */
	public void paintGrayDark() {
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
	}

	/**
	 * 黄色
	 */
	public void paintYellow() {
		GL11.glColor3f(1.0f, 1.0f, 0.0f);
	}

	/**
	 * 暗黄色
	 */
	public void paintYellowDark() {
		GL11.glColor3f(0.5f, 0.5f, 0.0f);
	}

	/**
	 * 红色
	 */
	public void paintRed() {
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
	}

	/**
	 * 暗红色
	 */
	public void paintRedDark() {
		GL11.glColor3f(0.5f, 0.0f, 0.0f);
	}

	/**
	 * 青色
	 */
	public void paintCray() {
		GL11.glColor3f(0.0f, 0.8f, 0.8f);
	}

	/**
	 * 暗青色
	 */
	public void paintCrayDark() {
		GL11.glColor3f(0.0f, 0.3f, 0.3f);
	}

	/**
	 * 蓝色
	 */
	public void paintBlue() {
		GL11.glColor3f(0.0f, 0.0f, 1.0f);
	}

	/**
	 * 绿色
	 */
	public void paintGreen() {
		GL11.glColor3f(0.0f, 1.0f, 0.0f);
	}

	/**
	 * 暗绿色
	 */
	public void paintGreenDark() {
		GL11.glColor3f(0.0f, 0.5f, 0.0f);
	}

	/**
	 * 白色
	 */
	public void paintWhite() {
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}

	@Override
	public void clipRect(Rectangle arg0) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {

	}

	@Override
	public void drawFocus(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void drawImage(Image arg0, int arg1, int arg2) {

	}

	@Override
	public void drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8) {

	}

	@Override
	public void drawLine(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void drawOval(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void drawPolygon(PointList arg0) {

	}

	@Override
	public void drawPolyline(PointList arg0) {

	}

	@Override
	public void drawRectangle(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void drawRoundRectangle(Rectangle arg0, int arg1, int arg2) {

	}

	@Override
	public void drawString(String arg0, int arg1, int arg2) {

	}

	@Override
	public void drawText(String arg0, int arg1, int arg2) {

	}

	@Override
	public void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {

	}

	@Override
	public void fillGradient(int arg0, int arg1, int arg2, int arg3, boolean arg4) {

	}

	@Override
	public void fillOval(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void fillPolygon(PointList arg0) {

	}

	@Override
	public void fillRectangle(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void fillRoundRectangle(Rectangle arg0, int arg1, int arg2) {

	}

	@Override
	public void fillString(String arg0, int arg1, int arg2) {

	}

	@Override
	public void fillText(String arg0, int arg1, int arg2) {

	}

	@Override
	public Color getBackgroundColor() {
		return null;
	}

	@Override
	public Rectangle getClip(Rectangle arg0) {
		return null;
	}

	@Override
	public Font getFont() {
		return null;
	}

	@Override
	public FontMetrics getFontMetrics() {
		return null;
	}

	@Override
	public Color getForegroundColor() {
		return null;
	}

	@Override
	public int getLineStyle() {
		return 0;
	}

	@Override
	public int getLineWidth() {
		return 0;
	}

	@Override
	public boolean getXORMode() {
		return false;
	}

	@Override
	public void popState() {

	}

	@Override
	public void pushState() {

	}

	@Override
	public void restoreState() {

	}

	@Override
	public void scale(double arg0) {

	}

	@Override
	public void setBackgroundColor(Color arg0) {

	}

	@Override
	public void setClip(Rectangle arg0) {

	}

	@Override
	public void setFont(Font arg0) {

	}

	@Override
	public void setForegroundColor(Color arg0) {

	}

	@Override
	public void setLineStyle(int arg0) {

	}

	@Override
	public void setLineWidth(int arg0) {

	}

	@Override
	public void setXORMode(boolean arg0) {

	}

	@Override
	public void translate(int arg0, int arg1) {

	}

}
