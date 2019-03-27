package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.afc.monitor.model.vo.Location;

import java.io.Serializable;
import java.util.List;

/**
 * 图片位置
 */
public class ImageLocation extends Location implements Serializable {

    private static final long serialVersionUID = 1L;

    public ImageLocation() {
    }

    public ImageLocation(List<String> imgs, int x, int y, int angle) {
        this.imgs = imgs;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public ImageLocation(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @JsonView(NodeItem.monitor.class)
    private List<String> imgs;

    public List<String> getImgs() {
        return imgs;
    }

    public ImageLocation setImgs(List<String> imgs) {
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
