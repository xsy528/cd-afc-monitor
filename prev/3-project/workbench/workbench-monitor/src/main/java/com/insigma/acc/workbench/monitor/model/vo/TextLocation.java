package com.insigma.acc.workbench.monitor.model.vo;

import io.swagger.annotations.ApiModel;

@ApiModel("文字位置")
public class TextLocation extends Location {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
