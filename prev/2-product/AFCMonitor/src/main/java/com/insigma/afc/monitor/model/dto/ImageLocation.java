package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.List;

public class ImageLocation extends Location implements Serializable {

    private static final long serialVersionUID = 1L;

    public ImageLocation() {
    }

    public ImageLocation(List<Integer> imgs, int x, int y, int angle) {
        this.imgs = imgs;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public ImageLocation(int x, int y, int angle) {
        this.imgs = imgs;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @JsonView(NodeItem.monitor.class)
    private List<Integer> imgs;

    public List<Integer> getImgs() {
        return imgs;
    }

    public ImageLocation setImgs(List<Integer> imgs) {
        this.imgs = imgs;
        return this;
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
