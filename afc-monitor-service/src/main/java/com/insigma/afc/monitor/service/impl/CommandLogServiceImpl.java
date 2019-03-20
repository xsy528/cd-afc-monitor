package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import com.insigma.afc.monitor.service.CommandLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 命令日志查询接口
 * @author  xingshaoya
 */
@Service
public class CommandLogServiceImpl implements CommandLogService {
    @Autowired
    private TmoCmdResultDao tmoCmdResultDao;
    @Override
    public Page<TmoCmdResult> getCommandLogSearch(CommandLogCondition condition) {
        //路线、站点，节点并在开始时间和结束时间内，如果有操作员编号，要包含操作员编号，如果有日志类型要包含日志类型,如果有命令结果要包含命令结果。


        //节点ID
        List<Long> nodeIds = condition.getNodeIds();
        //开始时间
        Date startTime = condition.getStartTime();
        //结束时间
        Date endTime = condition.getEndTime();
        //操作员id
        String operatorId = condition.getOperatorId();
        //日志类型
        String logType = condition.getLogType();
        //命令结果
        String commandResult = condition.getCommandResult();
        //
        Integer page = condition.getPageNumber();
        //
        Integer pageSize = condition.getPageSize();

        return tmoCmdResultDao.findAll((root,query,builder)->{
            List<Predicate> predicates = new ArrayList<>();
            if (nodeIds!=null){
                predicates.add(root.get("nodeId").in(nodeIds));
                //将站点ID存入root中
            }
            if (startTime!=null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("occurTime"),startTime));
                //获取开始时间之后
            }
            if (endTime!=null){
                predicates.add(builder.lessThanOrEqualTo(root.get("occurTime"),endTime));
                //获取结束时间之前
            }
            if (operatorId!=null){
                predicates.add(builder.equal(root.get("operatorId"),operatorId));
                //操作员ID
            }
            if (logType!=null){
                predicates.add(builder.equal(root.get("logType"),logType));
                //日志类型
            }
            if (commandResult!=null){
                predicates.add(builder.equal(root.get("commandResult"),commandResult));
                //命令结果
            }
            query.orderBy(builder.desc(root.get("occurTime")));
            //以occurTime降序
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page,pageSize));
    }
}
