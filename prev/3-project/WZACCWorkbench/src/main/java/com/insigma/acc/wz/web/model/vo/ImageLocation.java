package com.insigma.acc.wz.web.model.vo;

import java.io.Serializable;
import java.util.List;

public class ImageLocation extends Location implements Serializable {

    private static final long serialVersionUID = 1L;

    public ImageLocation(List<Integer> imgs, int x, int y, int angle) {
        this.imgs = imgs;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    private List<Integer> imgs;

    public List<Integer> getImgs() {
        return imgs;
    }

    public void setImgs(List<Integer> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "ImageLocation{" +
                "imgs=" + imgs +
                ", x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                '}';
    }
}
