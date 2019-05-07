package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.afc.monitor.model.dto.NodeItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文字、图片位置
 */
@ApiModel("位置信息")
public class Location {

    @JsonView(NodeItem.monitor.class)
    @ApiModelProperty("x坐标")
    protected int x;

    @JsonView(NodeItem.monitor.class)
    @ApiModelProperty("y坐标")
    protected int y;

    @JsonView(NodeItem.monitor.class)
    @ApiModelProperty("旋转角度")
    protected int angle;

    public Location(){

    }

    public Location(int x, int y, int angle){
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
