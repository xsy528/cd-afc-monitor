package com.insigma.afc.topology.exception;

import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.commons.exception.ServiceException;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-02-01:09:33
 */
public class NodeNotFoundException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public NodeNotFoundException(Long nodeId) {
        super("节点"+nodeId+"不存在");
    }

    public NodeNotFoundException(Long nodeId, AFCNodeLevel level) {
        super(level+"节点"+nodeId+"不存在");
    }
}
