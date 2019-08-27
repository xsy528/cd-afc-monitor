package com.insigma.afc.monitor.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author: xingshaoya
 * @time: 2019-07-31 16:27
 */
public class ResultContent<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface Base{}

    @JsonView(ResultContent.Base.class)
    private int pageNum;
    @JsonView(ResultContent.Base.class)
    private int pageSize;
    @JsonView(ResultContent.Base.class)
    private Long totalCount;
    @JsonView(ResultContent.Base.class)
    private T content;

    private ResultContent(PageRequest page, T data){
        this.pageNum = page.getPageNumber();
        this.pageSize = page.getPageSize();
        this.content = data;
    }
    private ResultContent(PageRequest page, T data, Long totalCount){
        this.pageNum = page.getPageNumber();
        this.pageSize = page.getPageSize();
        this.content = data;
        this.totalCount = totalCount;
    }
    public static <T> ResultContent<T> content(PageRequest page,T content){
        return new ResultContent(page,content);
    }

    public static <T> ResultContent<T> content(PageRequest page,Long totalCount,T data){
        return new ResultContent(page,data,totalCount);
    }


    public static <T> ResultContent<T> setNull(PageRequest page){
        return new ResultContent(page,null);
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
