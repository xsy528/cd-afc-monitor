package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import com.insigma.afc.monitor.model.vo.CommandLogInfo;
import com.insigma.afc.monitor.service.CommandLogService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import com.insigma.commons.util.NodeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.function.Function;

/**
 * 命令日志查询接口
 * @author  xingshaoya
 */
@Service
public class CommandLogServiceImpl implements CommandLogService {

    private TmoCmdResultDao tmoCmdResultDao;
    private TopologyService topologyService;

    @Override
    public Page<CommandLogInfo> getCommandLogSearch(CommandLogCondition condition) {
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

        Page<TmoCmdResult> tmoCmdResultPage = tmoCmdResultDao.findAll((root,query,builder)->{
            List<Predicate> predicates = new ArrayList<>();
            if (nodeIds!=null&&!nodeIds.isEmpty()){
                List<Long> ids = new ArrayList<>();
                for (Long nodeId:nodeIds){
                    ids.add(NodeIdUtils.nodeIdStrategy.getNodeNo(nodeId));
                }
                predicates.add(root.get("nodeId").in(ids));
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
                predicates.add(builder.equal(root.get("cmdType"),logType));
                //日志类型
            }
            if (commandResult!=null){
                predicates.add(builder.equal(root.get("cmdResult"),Integer.parseInt(commandResult)));
                //命令结果
            }
            query.orderBy(builder.desc(root.get("occurTime")));
            //以occurTime降序
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(condition.getPageNumber(),condition.getPageSize()));

        Set<Long> ids = new HashSet<>();
        Map<Long,String> textMap = null;
        if (!tmoCmdResultPage.isEmpty()){
            for (TmoCmdResult tmoCmdResult:tmoCmdResultPage.getContent()){
                ids.add(tmoCmdResult.getNodeId());
            }
            textMap = topologyService.getNodeTexts(ids).getData();
        }

        return tmoCmdResultPage.map(new TmoCmdResultToCommandLogInfo(textMap));
    }

    private class TmoCmdResultToCommandLogInfo implements Function<TmoCmdResult,CommandLogInfo> {
        private Map<Long,String> textMap;

        TmoCmdResultToCommandLogInfo(Map<Long,String> textMap){
            this.textMap = textMap;
        }

        @Override
        public CommandLogInfo apply(TmoCmdResult tmoCmdResult) {
            //返回结果集合显示，显示实体类
            //节点名称/编码，命令名称,操作员名称/编号，发送时间，命令结果/应答码
            CommandLogInfo commandLogInfo = new CommandLogInfo();

            commandLogInfo.setNodeName(textMap.get(tmoCmdResult.getNodeId()));
            commandLogInfo.setNodeId(tmoCmdResult.getNodeId());
            commandLogInfo.setCmdName(tmoCmdResult.getCmdName());
            commandLogInfo.setOperatorId(tmoCmdResult.getOperatorId());
            commandLogInfo.setUploadTime(DateTimeUtil.formatDate(tmoCmdResult.getOccurTime()));
            commandLogInfo.setCmdResult(tmoCmdResult.getCmdResult().toString());

            return commandLogInfo;
        }
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    @Autowired
    public void setTmoCmdResultDao(TmoCmdResultDao tmoCmdResultDao) {
        this.tmoCmdResultDao = tmoCmdResultDao;
    }
}
