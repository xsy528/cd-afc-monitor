package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.*;
import com.insigma.afc.monitor.model.dto.condition.BarAndPieCondition;
import com.insigma.commons.model.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ticket: 字典接口，获取程序中各类字典参数
 *
 * @author xuzhemin
 * 2019-01-14 16:18
 */
@Api(tags="字典接口")
//@RestController
@RequestMapping("/monitor/dic")
public class DicController {

    @ApiOperation("获取运营模式列表")
    @PostMapping("/modeTypeList")
    public Result getModeTypeList(){
        List<Map> data = new ArrayList<>(4);
        Map<String,Object> groupMode1 = new HashMap<>();
        groupMode1.put("groupName","正常运行模式");
        groupMode1.put("modes", AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL));
        Map<String,Object> groupMode2 = new HashMap<>();
        groupMode2.put("groupName","降级运行模式");
        groupMode2.put("modes",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_DESCEND));
        Map<String,Object> groupMode3 = new HashMap<>();
        groupMode3.put("groupName","紧急模式");
        groupMode3.put("modes",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_URGENCY));
        data.add(groupMode1);
        data.add(groupMode2);
        data.add(groupMode3);
        return Result.success(data);
    }

    @ApiOperation("获取设备状态列表")
    @PostMapping("/deviceStatusList")
    public Result getDeviceStatusList(){
        return Result.success(DeviceStatus.getInstance().getByGroup("monitor"));
    }

    @ApiOperation("获取日志类型列表")
    @PostMapping("/CmdTypeList")
    public Result getCmdTypeList(){
        return Result.success(AFCCmdLogType.getInstance().getByGroup(""));
    }

    @ApiOperation("获取命令结果列表")
    @PostMapping("/modeCmdResultList")
    public Result getCmdResultList(){
        return Result.success(AFCMackCode.getInstance().getByGroup(""));
    }

    @ApiOperation("获取票种类型列表")
    @PostMapping("/ticketTypeList")
    public Result getTicketTypeList(){
        return Result.success(AFCTicketFamily.getInstance().getByGroup(""));
    }

    @ApiOperation("获取交易类型列表")
    @PostMapping("/transTypeList")
    public Result getTransTypeList(){
        String[] legend = BarAndPieCondition.LEGEND;
        List<Map> data = new ArrayList<>(legend.length);
        for (int i=0;i<legend.length;i++) {
            Map<String, Object> groupMode = new HashMap<>(2);
            groupMode.put("key", i);
            groupMode.put("value", legend[i]);
            data.add(groupMode);
        }
        return Result.success(data);
    }

    @ApiOperation("获取曲线图时间间隔类型列表")
    @PostMapping("/timeIntervalList")
    public Result getTimeInterval(){
        //时间间隔的选择个数
        return Result.success(Stream.of(1,2,5,6,8,10,15,20,25,30)
                .map(DicController::getMap)
                .collect(Collectors.toList()));
    }

    private static Map<String, Object> getMap(Integer k) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("key", k);
        map.put("value", k.toString());
        return map;
    }

}
