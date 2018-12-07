package com.insigma.afc.monitor.dialog.config;

import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

public class ConfigActionHandler extends ActionHandlerAdapter {

	@Autowire
	ICommonDAO commonDAO;

}
