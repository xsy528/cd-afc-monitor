package com.insigma.afc.monitor.controller.resolver;

import org.springframework.web.servlet.mvc.multiaction.AbstractUrlMethodNameResolver;

import java.util.Map;

/**
 * xuzhemin
 * 路径-方法映射
 */
public class CustomMethodNameResolver extends AbstractUrlMethodNameResolver {

    private final Map<String,String> methodMapping;

    public CustomMethodNameResolver(Map<String,String> methodMapping){
        this.methodMapping = methodMapping;
    }

    @Override
    protected String getHandlerMethodNameForUrlPath(String urlPath) {
        return methodMapping.get(urlPath);
    }
}
