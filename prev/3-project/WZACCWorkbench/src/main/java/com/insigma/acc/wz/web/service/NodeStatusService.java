package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.web.model.vo.Result;
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

    List<TmoModeUploadInfo> getModeUpload(long nodeId);

    List<TmoModeBroadcast> getModeBroadcast();

    Result getDeviceDetail(long id);
}
