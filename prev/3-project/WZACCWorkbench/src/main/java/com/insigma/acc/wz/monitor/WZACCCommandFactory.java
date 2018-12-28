/* 
 * 日期：2017年8月3日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.acc.wz.define.WZAFCCmdLogType;
import com.insigma.acc.wz.define.WZCommandType;
import com.insigma.acc.wz.monitor.form.WZDeviceControlCmdForm;
import com.insigma.afc.monitor.ICommandFactory;
import com.insigma.afc.workbench.rmi.CommandType;

/**
 * Ticket:  设备控制命令列表
 * @author  wangzezhi
 *
 */
public class WZACCCommandFactory implements ICommandFactory {
	private Log log = LogFactory.getLog(WZACCCommandFactory.class);

	@Override
	public List<Command> getCommands() {
		List<Command> commands = new ArrayList<Command>();

		//		{
		//			Command command = new Command(WZCommandType.CMD_QUERY_DEVICE_SERIAL, "查询设备最新流水号");
		//			command.setSelected(true);
		//			commands.add(command);
		//		}
		//
		//		{
		//			Command command = new Command(WZCommandType.CMD_UPLOAD_FILE, "要求下级上传当前文件通知", new WZUploadCurrFileForm());
		//			command.setCmdType(WZAFCCmdLogType.LOG_FILE_INFORM.shortValue());
		//			commands.add(command);
		//		}
		//
		//		{
		//			Command command = new Command(WZCommandType.CMD_UPLOAD_SPEC_FILE, "要求下级上传指定文件通知",
		//					new WZUploadAssignFileForm());
		//			command.setCmdType(WZAFCCmdLogType.LOG_FILE_INFORM.shortValue());
		//			commands.add(command);
		//		}

		{
			Command command = new ExtCommand(CommandType.CMD_TIME_SYNC, "时钟同步");
			command.setCmdType(WZAFCCmdLogType.LOG_TIME_SYNC.shortValue());
			command.setSelected(true);
			commands.add(command);
		}

		{
			Command command = new Command(WZCommandType.COM_SLE_CONTROL_CMD, "设备控制命令", new WZDeviceControlCmdForm());
			command.setCmdType(WZAFCCmdLogType.LOG_DEVICE_CMD.shortValue());
			commands.add(command);
		}

		//		{
		//			Command command = new Command(WZCommandType.CMD_QUERY_DEVICE_FILE_VERSION, "查询设备运行文件版本信息");
		//			//			command.setSelected(true);
		//			commands.add(command);
		//		}

		return commands;
	}

}
