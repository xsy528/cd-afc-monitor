package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.constant.dic.*;
import com.insigma.afc.monitor.constant.dic.cd.CDTicketFamily;
import com.insigma.afc.monitor.model.dto.condition.BarAndPieCondition;
import com.insigma.commons.dic.DicitemEntry;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.model.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/monitor/dic")
public class DicController {

    @Value("${refreshcycle}")
    private String refreshCycle;

    @ApiOperation("获取刷新周期")
    @PostMapping("/flushTime")
    public Result getFlushTime(){

        return Result.success(refreshCycle);
    }

    @ApiOperation("设置刷新周期")
    @PostMapping("/putFlushTime")
    public Result putFlushTime(@RequestParam String refresh){


        return Result.success(true);
    }


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
        List<Map> lists = new ArrayList<>();
        String[] nameList = CDTicketFamily.getInstance().getNameList();
        int index = 0;
        Map<String, DicitemEntry> dicItecEntryMap = CDTicketFamily.getInstance().getDicItecEntryMap();
        for (DicitemEntry v : dicItecEntryMap.values()) {
            DicItem dicItem = v.dicitem;
            lists.add(getMap(Integer.valueOf(dicItem.desc() + String.format("%02d", v.value)),nameList[index]));
            index++;
        }
        return Result.success(lists);
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

    @ApiOperation("获取客流查询-统计类型列表")
    @PostMapping("/countType")
    public Result getCountType(){

        List<Map> data = new ArrayList<>(2);
        data.add(getMap(0,"按车站和票种"));
        data.add(getMap(1,"按车站"));
        //时间间隔的选择个数
        return Result.success(data);
    }

    @ApiOperation("获取分时查询-时间间隔列表")
    @PostMapping("/timeInterval")
    public Result getTimeInterval2(){

        List<Map> data = new ArrayList<>(2);
        data.add(getMap(5,"5"));
        data.add(getMap(10,"10"));
        data.add(getMap(15,"15"));
        data.add(getMap(20,"20"));
        data.add(getMap(25,"25"));
        data.add(getMap(30,"30"));
        data.add(getMap(35,"35"));
        data.add(getMap(40,"40"));
        data.add(getMap(45,"45"));
        data.add(getMap(50,"50"));
        data.add(getMap(55,"55"));
        data.add(getMap(60,"60"));
        //时间间隔的选择个数
        return Result.success(data);
    }

    private static Map<String, Object> getMap(Integer k) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("key", k);
        map.put("value", k.toString());
        return map;
    }

    private Map getMap(Object key,Object value){
        Map<String,Object> map = new HashMap<>(1);
        map.put("key",key);
        map.put("value",value);
        return map;
    }

}
