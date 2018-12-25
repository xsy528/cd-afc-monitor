package com.insigma.acc.workbench.monitor.model.vo;

import io.swagger.annotations.ApiModel;

@ApiModel("图片位置")
public class ImageLocation extends Location {

    private int imageUrl;

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
