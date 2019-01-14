package com.insigma.acc.wz.web.model.vo;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class TextLocation extends Location implements Serializable {

    private static final long serialVersionUID = 1L;

    public TextLocation(String text, int x, int y, int angle) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public TextLocation() {
    }

    @JsonView(NodeItem.monitor.class)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextLocation{" +
                "text='" + text + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                '}';
    }
}
