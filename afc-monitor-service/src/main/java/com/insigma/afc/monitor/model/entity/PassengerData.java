package com.insigma.afc.monitor.model.entity;

import org.springframework.stereotype.Repository;

/**
 * Ticket:客流查询接口接收结果实体
 *
 * @author: xingshaoya
 * create time: 2019-03-22 17:00
 */
@Repository
public class PassengerData {

    private Long line_id;

    private String ticket_family;

    private int od_id;

    private int od_out;

    private int od_buy;

    private int od_add;

    public Long getLine_id() {
        return line_id;
    }

    public void setLine_id(Long line_id) {
        this.line_id = line_id;
    }

    public String getTicket_family() {
        return ticket_family;
    }

    public void setTicket_family(String ticket_family) {
        this.ticket_family = ticket_family;
    }

    public int getOd_id() {
        return od_id;
    }

    public void setOd_id(int od_id) {
        this.od_id = od_id;
    }

    public int getOd_out() {
        return od_out;
    }

    public void setOd_out(int od_out) {
        this.od_out = od_out;
    }

    public int getOd_buy() {
        return od_buy;
    }

    public void setOd_buy(int od_buy) {
        this.od_buy = od_buy;
    }

    public int getOd_add() {
        return od_add;
    }

    public void setOd_add(int od_add) {
        this.od_add = od_add;
    }
}
