package com.insigma.acc.wz.web.controller;

import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.commons.lang.PairValue;

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
        Map<String, List<PairValue<Object,String>>> modeTree = new HashMap<>();
        modeTree.put("正常运行模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL));
        modeTree.put("降级运行模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_DESCEND));
        modeTree.put("紧急运营模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_URGENCY));
        modeTree.put("列车故障模式",AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN));
        return Result.success(modeTree);
    }

    //获取设备状态列表
    public Result getDeviceStatusList(){
        return Result.success(DeviceStatus.getInstance().getByGroup("1"));
    }
}
