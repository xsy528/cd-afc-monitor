package com.insigma.acc.monitor.service;

import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.entity.TmoEquStatus;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.entity.TmoItemStatus;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
//import com.insigma.afc.sjz.xml.message.req.Req3101ModeNotify_t;
import com.insigma.commons.application.IService;
import com.insigma.commons.collection.PageHolder;

/**
 * Ticket: <br/>
 * @author  邱昌进(qiuchangjin@zdwxjd.com)
 *
 */
public interface IWZModeService extends IService {
	/**
	 * 模式命令
	 * <pre>
	 *  对应LC，保存该LC的数据库状态，<br/>
	 *  对应SC，保存该SC的数据库状态，<br/>
	 * </pre>
	 * @param req
	 * @return 执行结果
	 */
	public long handleModeCommand(final long senderId, long stationId, short newModeId, long operationId);

	/**
	 * 模式通知
	 * <pre>
	 *     SC将该模式命令通知到本车站SLE，同时通知LCC;;<br/>
	 *     LCC收到模式通知后，将其转发到本线路其它SC，同时通知CCHS;<br/>
	 * </pre>
	 * @param req
	 * @return
	 */
	//public long handleModeNotify(Req3101ModeNotify_t req);

	/**
	 * 创建时间 2011-1-21 下午04:47:42<br>
	 * 描述：模式广播
	 * 
	 * @param modeBroadcasts
	 * @return
	 */
	public long handleModeBroadcast(List<TmoModeBroadcast> modeBroadcasts);

	/**
	 * 获取当前模式
	 * <pre>
	 *  从数据库中查到当前模式
	 * </pre>
	 * @param id
	 * @return
	 */
	public TmoItemStatus getCurrentTmoItemMode(long stationId);

	public List<TmoItemStatus> getAllTmoItemModeList(long stationID);

	/**
	 * 只简单更新数据库中的当前模式
	 * @param nodeId
	 * @param newModeId
	 * @return
	 */
	public boolean saveOrUpdateCurrentModeId(long nodeId, short newModeId, Date changeTime);

	/**
	 * 创建时间 2010-12-21 上午11:57:02<br>
	 * 描述：获取所有降级或紧急模式的车站模式
	 * 
	 * @return
	 */
	public List<TmoModeUploadInfo> getAbnormalTmoModeUploadInfo();

	/**
	 * 模式广播查询
	 * @param modeEffectTime
	 * @return
	 */
	public List<Object> getModeSearchList(Short lineId, Short stationId, Date beginTime, Date endTime, int pageNum,
			int pageSize);

	public int getModeSearchCount(Short lineId, Short stationId, Date benginTime, Date endTime);

	public void saveOrUpdateModeBroadcastInfo(TmoModeBroadcast tmoModeBroadcast);

	/**
	 * 创建时间 2015-08-17
	 * 描述：获取所有线路车站节点信息
	 * 
	 * @return
	 */
	public List<Object[]> getMetroNodeList();

	/**
	 * 
	 */
	public void saveModeUploadInfo(TmoModeUploadInfo tmoModeUploadInfo);

	/**
	 * @param stationId
	 * @return
	 */
	public List<TmoItemStatus> getTmoItemMode(int stationId);

	/**
	 * 获取当前车站状态信息
	 * 
	 * @param stationId
	 * @return
	 */
	public List<TmoItemStatus> getCurTmoItemMode(int stationId);

	/**
	 * @param createCmdResult
	 */
	public void saveOrUpdate(Object... objects);

	public List<TmoItemStatus> getDeviceTmoItemMode(short lindID, int stationID);

	/**
	 * 保存或更新指定节点的在线状态
	 * 
	 * @param lineId
	 * @param stationId
	 * @param nodeId
	 * @param status
	 *            节点状态：4-离线；5-在线
	 * @return 更新结果
	 */
	boolean saveOrUpdateOnlineStatus(short lineId, int stationId, long nodeId, short status);

	//void updateAllSubNodeStatus(long nodeId, short status);

	void updateAllSubNodeStatus(long nodeId, boolean status);

	/**
	 * 获取设备状态
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<TmoItemStatus> getTmoItemMode(long nodeId);

	/**
	 * 获取设备事件
	 * @param filterForm
	 * @param pageIndex
	 * @return
	 */
	public PageHolder<TmoEquStatus> getEventList(EventFilterForm filterForm, int pageIndex);

	/**
	 * 获取当前设备事件
	 * @param filterForm
	 * @param pageIndex
	 * @return
	 * PageHolder<TmoEquStatusCur>
	 *
	 */
	public PageHolder<TmoEquStatusCur> getEquStatusList(EventFilterForm filterForm, int pageIndex);

}
