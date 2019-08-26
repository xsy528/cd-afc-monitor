package com.insigma.afc.monitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/24 22:43
 */
public interface PassengerRepository {

    /**
     * 获取柱状图、饼图数据
     * @param gatheringDate 运营日
     * @param startTimeInterval 开始时间段
     * @param endTimeInterval 结束时间段
     * @param lines 线路id
     * @param ticketFamily 票种类行
     * @return 数据
     */
    List<Tuple> findAllBarAndPie(Date gatheringDate, Integer startTimeInterval, Integer endTimeInterval,
                                 List<Short> lines, Short ticketFamily);

    /**
     * 获取曲线图数据
     * @param gatheringDate 运营日
     * @param startTimeInterval 开始时间段
     * @param endTimeInterval 结束时间段
     * @param lines 线路id
     * @param ticketFamily 票种类行
     * @return 数据
     */
    List<Tuple> findAllSeries(Date gatheringDate, Integer startTimeInterval, Integer endTimeInterval,
                              List<Short> lines, Short ticketFamily);

    /**
     * 获取表格数据
     * @param gatheringDate 运营日
     * @param startTimeInterval 开始时间段
     * @param endTimeInterval 结束时间段
     * @param lines 线路id
     * @param statType 分组类行
     * @param pageable 分页信息
     * @return 数据
     */
    Page<Tuple> findAll(Date gatheringDate, Integer startTimeInterval, Integer endTimeInterval,
                        List<Short> lines, Short statType, Pageable pageable);
}
