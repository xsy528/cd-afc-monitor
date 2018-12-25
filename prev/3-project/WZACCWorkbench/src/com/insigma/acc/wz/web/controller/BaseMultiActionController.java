package com.insigma.acc.wz.web.controller;

import com.insigma.acc.wz.web.controller.resolver.CustomMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2018-12-24:10:34
 */
public class BaseMultiActionController extends MultiActionController {

    protected Map<String,String> methodMapping;

    public BaseMultiActionController(Map<String,String> methodMapping){
        this.methodMapping = methodMapping;
        this.setMethodNameResolver(new CustomMethodNameResolver(methodMapping));
    }

}
