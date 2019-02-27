package com.insigma.afc.monitor.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket: 分页参数
 *
 * @author xuzhemin
 * 2019-02-27 11:53
 */
public class PageBean {

    @ApiModelProperty("页码")
    protected Integer pageNumber;

    @ApiModelProperty("页大小")
    protected Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
