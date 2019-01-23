package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.dic.DeviceStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ticket: 字典接口，获取程序中各类字典参数
 *
 * @author xuzhemin
 * 2019-01-14:16:18
 */
public class DicController extends BaseMultiActionController {

    static{
        methodMapping.put("/config/devTypeList","getDeviceTypeList");
        methodMapping.put("/config/modeTypeList","getModeTypeList");
        methodMapping.put("/config/deviceStatusList","getDeviceStatusList");
    }

    //获取设备类型列表
    public Result getDeviceTypeList(){
        return Result.success(AFCDeviceType.getInstance().getByGroup("SLE"));
    }

    //获取运营模式列表
    public Result getModeTypeList(){
        List<Map> data = new ArrayList<>(4);
        Map<String,Object> groupMode1 = new HashMap<>();
        groupMode1.put("groupName","正常运行模式");
        groupMode1.put("modes",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL));
        Map<String,Object> groupMode2 = new HashMap<>();
        groupMode2.put("groupName","降级运行模式");
        groupMode2.put("modes",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_DESCEND));
        Map<String,Object> groupMode3 = new HashMap<>();
        groupMode3.put("groupName","紧急运营模式");
        groupMode3.put("modes",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_URGENCY));
        Map<String,Object> groupMode4 = new HashMap<>();
        groupMode4.put("groupName","列车故障模式");
        groupMode4.put("modes",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN));
        data.add(groupMode1);
        data.add(groupMode2);
        data.add(groupMode3);
        data.add(groupMode4);
        return Result.success(data);
    }

    //获取设备状态列表
    public Result getDeviceStatusList(){
        return Result.success(DeviceStatus.getInstance().getByGroup("1"));
    }
}
