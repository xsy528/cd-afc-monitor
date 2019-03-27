/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:24
 */
@ApiModel("断面客流查询结果")
public class SectionOdFlowStatsView {

    @ApiModelProperty("运营日期")
    private String businessday;

    @ApiModelProperty("线路/编号")
    private String line;

    @ApiModelProperty("车站1/编号")
    private String upstation;

    @ApiModelProperty("车站2/编号")
    private String downstation;

    @ApiModelProperty("上行客流")
    private String upcount;

    @ApiModelProperty("下行客流")
    private String downcount;

    @ApiModelProperty("总客流")
    private String totalcount;

    private long sectionId;

    private long downSectionId;

    private Long downFlowCount;

    /**
     * 上行车站位置X
     */
    private long upStationPosX;

    /**
     * 上行车站位置Y
     */
    private long upStationPosY;

    /**
     * 下行车站位置X
     */
    private long downStationPosX;

    /**
     * 下行车站位置
     */
    private long downStationPosY;

    /**
     * 上下行标记位(0:上行，1：下行)
     */
    private short upDownFlag;

    /**
     * 客流高中低标记位(0:低，1：中，2：高)
     */
    private short OdLevelFlag;

    /**
     * 下行客流高中低标记位(0:低，1：中，2：高)
     */
    private short downOdLevelFlag;

    public String getBusinessday() {
        return businessday;
    }

    public void setBusinessday(String businessday) {
        this.businessday = businessday;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getUpstation() {
        return upstation;
    }

    public void setUpstation(String upstation) {
        this.upstation = upstation;
    }

    public String getDownstation() {
        return downstation;
    }

    public void setDownstation(String downstation) {
        this.downstation = downstation;
    }

    public String getUpcount() {
        return upcount;
    }

    public void setUpcount(String upcount) {
        this.upcount = upcount;
    }

    public String getDowncount() {
        return downcount;
    }

    public void setDowncount(String downcount) {
        this.downcount = downcount;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getDownSectionId() {
        return downSectionId;
    }

    public void setDownSectionId(long downSectionId) {
        this.downSectionId = downSectionId;
    }

    public Long getDownFlowCount() {
        return downFlowCount;
    }

    public void setDownFlowCount(Long downFlowCount) {
        this.downFlowCount = downFlowCount;
    }

    public long getUpStationPosX() {
        return upStationPosX;
    }

    public void setUpStationPosX(long upStationPosX) {
        this.upStationPosX = upStationPosX;
    }

    public long getUpStationPosY() {
        return upStationPosY;
    }

    public void setUpStationPosY(long upStationPosY) {
        this.upStationPosY = upStationPosY;
    }

    public long getDownStationPosX() {
        return downStationPosX;
    }

    public void setDownStationPosX(long downStationPosX) {
        this.downStationPosX = downStationPosX;
    }

    public long getDownStationPosY() {
        return downStationPosY;
    }

    public void setDownStationPosY(long downStationPosY) {
        this.downStationPosY = downStationPosY;
    }

    public short getUpDownFlag() {
        return upDownFlag;
    }

    public void setUpDownFlag(short upDownFlag) {
        this.upDownFlag = upDownFlag;
    }

    public short getOdLevelFlag() {
        return OdLevelFlag;
    }

    public void setOdLevelFlag(short odLevelFlag) {
        OdLevelFlag = odLevelFlag;
    }

    public short getDownOdLevelFlag() {
        return downOdLevelFlag;
    }

    public void setDownOdLevelFlag(short downOdLevelFlag) {
        this.downOdLevelFlag = downOdLevelFlag;
    }
}
