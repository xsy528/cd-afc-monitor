package com.insigma.commons.framework.function.handler;

import com.insigma.commons.framework.application.Function;

import java.util.HashMap;
import java.util.Map;

public class FunctionHanlderManager {

	private static Map<String, FunctionHandler> functionHandlers = new HashMap<String, FunctionHandler>();

	static {
//		addFunctionHandler(SearchFunction.class, new SearchFunctionHandler());
//		addFunctionHandler(TreeSearchFunction.class, new SearchFunctionHandler());
//		addFunctionHandler(ConsoleFunction.class, new ConsoleFunctionHandler());
//		addFunctionHandler(KTableFunction.class, new KTableViewFunctionHandler());
//		addFunctionHandler(TableViewFunction.class, new TableViewFunctionHandler());
//		addFunctionHandler(MultiTableViewFunction.class, new MultiTableViewFunctionHandler());
//		addFunctionHandler(FormFunction.class, new FormFunctionHandler());
//		addFunctionHandler(ChartFunction.class, new SearchChartFunctionHandler());
	}

	public static void addFunctionHandler(Class<?> functionClass, FunctionHandler functionHandler) {
		functionHandlers.put(functionClass.getCanonicalName(), functionHandler);
	}

	public static FunctionHandler getFunctionHandler(Function function) {
		return functionHandlers.get(function.getClass().getCanonicalName());

	}
}
