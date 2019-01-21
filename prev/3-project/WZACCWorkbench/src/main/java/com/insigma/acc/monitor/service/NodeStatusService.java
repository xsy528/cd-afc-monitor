package com.insigma.acc.monitor.service;

import com.insigma.acc.monitor.model.vo.Result;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-03:10:39
 */
public interface NodeStatusService {

    //模式上传信息
    List<TmoModeUploadInfo> getModeUpload(long nodeId);

    //模式广播信息
    List<TmoModeBroadcast> getModeBroadcast();

    //监视设备信息
    Result getDeviceDetail(long id);

    //票箱钱箱信息
    Result getBoxDetail(long id);

}
