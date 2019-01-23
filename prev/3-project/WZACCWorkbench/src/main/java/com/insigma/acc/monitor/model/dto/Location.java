package com.insigma.acc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 文字、图片位置
 */
public class Location {
    @JsonView(NodeItem.monitor.class)
    protected int x;
    @JsonView(NodeItem.monitor.class)
    protected int y;
    @JsonView(NodeItem.monitor.class)
    protected int angle;

    public Location(){

    }

    public Location(int x,int y,int angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
