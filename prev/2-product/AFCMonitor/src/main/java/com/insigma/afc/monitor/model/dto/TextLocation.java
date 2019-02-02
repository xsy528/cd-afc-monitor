package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.afc.monitor.model.vo.NodeItem;

import java.io.Serializable;

public class TextLocation extends Location implements Serializable {

    private static final long serialVersionUID = 1L;

    public TextLocation(String text, int x, int y, int angle) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public TextLocation(int x, int y, int angle) {
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

    public TextLocation setText(String text) {
        this.text = text;
        return this;
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
