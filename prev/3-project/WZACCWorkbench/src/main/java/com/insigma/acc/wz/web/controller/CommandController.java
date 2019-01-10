package com.insigma.acc.wz.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.insigma.acc.wz.define.WZDeviceCmdControlType;
import com.insigma.acc.wz.web.exception.ErrorCode;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.CommandService;
import com.insigma.acc.wz.web.service.FileService;
import com.insigma.acc.wz.web.util.HttpUtils;
import com.insigma.acc.wz.web.util.JsonUtils;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.action.CommandResult;
import com.insigma.commons.lang.PairValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-04:10:49
 */
public class CommandController extends BaseMultiActionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandController.class);

    static{
        methodMapping.put("/monitor/modeList","getModeList");
        methodMapping.put("/monitor/deviceControlCommandList","getDeviceControlCommandList");
        methodMapping.put("/monitor/queryMode","queryMode");
        methodMapping.put("/monitor/changeMode","changeMode");
        methodMapping.put("/monitor/timeSync","timeSync");
        methodMapping.put("/monitor/nodeControl","nodeControl");
        methodMapping.put("/monitor/refresh","refresh");
    }

    private CommandService commandService;
    private FileService fileService;

    @Autowired
    public CommandController(CommandService commandService,FileService fileService){
        this.commandService = commandService;
        this.fileService = fileService;
    }

    //获取运营模式列表
    public Result<Map> getModeList(HttpServletRequest request, HttpServletResponse response){
        Map<String, List<PairValue<Object,String>>> modeTree = new HashMap<>();
        modeTree.put("正常运行模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL));
        modeTree.put("降级运行模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_DESCEND));
        modeTree.put("紧急运营模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_URGENCY));
        modeTree.put("列车故障模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN));
        return Result.success(modeTree);
    }

    //获取设备控制命令列表
    public Result<Map> getDeviceControlCommandList(HttpServletRequest request, HttpServletResponse response){
        return Result.success(WZDeviceCmdControlType.getInstance().dicItecEntryMap);
    }

    //修改运营模式
    public Result<List<CommandResult>> changeMode(HttpServletRequest request, HttpServletResponse response){
        JsonNode jsonNode = HttpUtils.getBody(request);
        if (jsonNode==null){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }
        List<Long> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        int command = jsonNode.get("command").intValue();
        return commandService.sendChangeModeCommand(idList,command);
    }

    //模式查询
    public Result<List<CommandResult>> queryMode(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        JsonNode jsonNode = HttpUtils.getBody(request);
        if (jsonNode==null){
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }
        List<Long> idList = new ArrayList<>();
        for(JsonNode id:jsonNode.get("nodeIds")){
            idList.add(id.longValue());
        }
        return commandService.sendModeQueryCommand(idList);
    }

    //时钟同步
    public void timeSync(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        JsonNode jsonNode = HttpUtils.getBody(request);
        if (jsonNode==null){
            try(PrintWriter printWriter=response.getWriter()){
                printWriter.println(Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND));
            }catch (IOException e){
                LOGGER.error("",e);
            }
        }else{
            List<Long> idList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("nodeIds")){
                idList.add(id.longValue());
            }
            try(PrintWriter printWriter=response.getWriter()){
                printWriter.println(JsonUtils.parseObject(commandService.sendTimeSyncCommand(idList)));
            }catch (IOException e){
                LOGGER.error("",e);
            }
        }
    }

    //发送设备控制命令
    public void nodeControl(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        JsonNode jsonNode = HttpUtils.getBody(request);
        if (jsonNode==null){
            try(PrintWriter printWriter=response.getWriter()){
                printWriter.println(Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND));
            }catch (IOException e){
                LOGGER.error("",e);
            }
        }else{
            List<Long> idList = new ArrayList<>();
            for(JsonNode id:jsonNode.get("nodeIds")){
                idList.add(id.longValue());
            }
            short command = jsonNode.get("command").shortValue();
            try(PrintWriter printWriter=response.getWriter()){
                printWriter.println(JsonUtils.parseObject(commandService.sendNodeControlCommand(idList,command)));
            }catch (IOException e){
                LOGGER.error("",e);
            }
        }
    }

    //刷新数据库资源
    public Result<String> refresh(){
        fileService.synResources();
        return Result.success();
    }
}
