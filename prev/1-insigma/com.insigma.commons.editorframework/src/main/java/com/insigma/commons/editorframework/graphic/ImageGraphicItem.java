/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;

import com.insigma.commons.editorframework.graphic.editor.MapItem;

public class ImageGraphicItem extends GraphicItem {

	// jfq，当前要绘制的图片（images是一个数组，表示不同的图片。ImageGraphicItem可以切换显示不同的图片，来表示不同的设备状态。
	private int imageIndex;

	private int editorImg;
	private ArrayList<Integer> monitorImgs = new ArrayList<>();

	private ArrayList<Image> images;

	private ArrayList<String> imagesPath;

	public ArrayList<Integer> getMonitorImgs(){
		return monitorImgs;
	}

	@JsonIgnore
	public ArrayList<String> getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(ArrayList<String> imagesPath) {
		this.imagesPath = imagesPath;
	}

	public int getEditorImg(){
		return editorImg;
	}

	public void setEditorImg(int value){
		this.editorImg = value;
	}
	@JsonIgnore
	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	@JsonIgnore
	public int getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public ImageGraphicItem(MapItem parent, int x, int y, float angle) {
		super(parent, x, y, angle);
	}

	@Override
	public void paint(GC gc) {

		if (images != null && imageIndex >= 0 && imageIndex < images.size()) {

			final Image image = images.get(imageIndex);
			setWidth(image.getBounds().width);
			setHeight(image.getBounds().height);

			Transform transform = new Transform(gc.getDevice());

			transform.translate(getX(), getY());

			transform.rotate(getAngle());

			gc.setTransform(transform);

			int alpha = gc.getAlpha();

			if (isSelected()) {
				gc.setAlpha(200);
			} else {
				gc.setAlpha(255);
			}

			gc.drawImage(image, -getWidth() / 2, -getHeight() / 2);
			//调整为以左上角为原点进行绘图-yangy 20180622
			//			gc.drawImage(image, 0, 0);

			transform.rotate(-getAngle());

			transform.dispose();

			gc.setAlpha(alpha);

			gc.setTransform(null);

		}
	};

	public void addImage(String image) {
		if (images == null) {
			images = new ArrayList<Image>();
		}
		//images.add(SWTResourceManager.getImage(ImageGraphicItem.class, image));
images.add(null);
		if (imagesPath == null) {
			imagesPath = new ArrayList<String>();
		}
		imagesPath.add(image);
	}

	public void addImage(String path, String... images) {
		if (images != null) {
			for (int i = 0; i < images.length; i++) {
				addImage(path + images[i]);
			}
		}
	}
}
