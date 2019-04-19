package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("命令执行结果")
public class CommandResultDTO {

	@ApiModelProperty("目标节点")
	private String id;

	@ApiModelProperty("操作员id")
	@JsonProperty("operator_id")
	private String operatorId;

	@ApiModelProperty("命令名称")
	@JsonProperty("cmd_name")
	private String cmdName;

	@ApiModelProperty("命令参数")
	private String arg;

	@ApiModelProperty("命令结果码")
	private short result;

	@ApiModelProperty("命令执行结果")
	@JsonProperty("cmd_result")
	private String cmdResult;

	@ApiModelProperty("发生时间")
	@JsonProperty("occur_time")
	private Date occurTime;

	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getCmdResult() {
		return cmdResult;
	}

	public void setCmdResult(String cmdResult) {
		this.cmdResult = cmdResult;
	}

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public short getResult() {
		return result;
	}

	public void setResult(short result) {
		this.result = result;
	}

}
