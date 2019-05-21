package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.CommandResultDTO;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeBroadcastCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeCmdCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeUploadCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.model.vo.ModeBroadcastInfo;
import com.insigma.afc.monitor.model.vo.ModeCmdInfo;
import com.insigma.afc.monitor.model.vo.ModeUploadInfo;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * Ticket: 模式服务
 * @author  邱昌进(qiuchangjin@zdwxjd.com)
 *
 */
public interface ModeService {

	/**
	 * 获取当前设备事件
	 * @param condition 查询条件
	 * @return
	 * PageHolder<TmoEquStatusCur>
	 *
	 */
	List<TmoEquStatusCur> getEquStatusList(DeviceEventCondition condition);

	/**
	 * 查询模式上传
	 * @param condition 查询条件
	 * @return 模式上传分页数据
	 */
	Page<ModeUploadInfo> getModeUploadInfo(ModeUploadCondition condition);

	/**
	 * 获取模式日志信息
	 * 注意：使用此接口时，如果开始时间与结束时间相同，请将结束时间加一秒
     * 因为数据库中时间精确到了毫秒
	 * @param condition 模式日志查询条件实体类
	 * @return 分页数据
	 */
    Page<ModeCmdInfo> getModeCmdSearch(ModeCmdCondition condition);

	/**
	 * 各类查询模式广播查询
	 * @param condition 查询条件
	 * @return 分页数据
	 */
	Page<ModeBroadcastInfo> getModeBroadcastInfo(ModeBroadcastCondition condition);

	/**
	 * 模式上传信息
	 * @param nodeId 节点id
	 * @return 模式上传信息
	 */
	List<TmoModeUploadInfo> getModeUpload(long nodeId);

	/**
	 * 模式广播信息
	 * @return 模式广播信息
	 */
	List<TmoModeBroadcast> getModeBroadcast();

	/**
	 * 重新发送模式广播
	 * @param resultIds 广播信息id
	 * @return 结果
	 */
	List<CommandResultDTO> resendModeBroadcast(List<Long> resultIds);

}
