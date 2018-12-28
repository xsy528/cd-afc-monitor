package com.insigma.acc.wz.web.util;

import com.insigma.afc.application.AFCNodeLevel;

/**
 * Ticket: 节点工具
 *
 * @author xuzhemin
 * 2018-12-28:10:50
 */
public class NodeUtils {

    /**
     * 将十进制节点id转化为十六进制字符串
     * @param id 十进制id
     * @param afcNodeLevel 节点等级
     * @return 十六进制字符串
     */
    public static String getNodeId(long id, AFCNodeLevel afcNodeLevel){
        switch (afcNodeLevel){
            case ACC:{
                return "0x00000000";
            }
            case LC: {
                return String.format("0x%02x000000",id);
            }
            case SC:{
                return String.format("0x%04x0000",id);
            }
            case SLE:{
                return String.format("0x%08x",id);
            }
            default: return "";
        }
    }
}
