package com.insigma.afc.log.handler;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;

public class ViewLogDetailActionHandler extends ActionHandlerAdapter {

	public Response perform(Request request) {
		SearchRequest searchRequest = (SearchRequest) request;
		SearchResponse searchResponse = new SearchResponse();
		List<Object> objects = searchRequest.getSelection();
		searchResponse.setValue(objects);
		getLogger().debug("获取表格内容 ：" + ReflectionToStringBuilder.toString(objects.get(0)));
		return searchResponse;
	}

}
