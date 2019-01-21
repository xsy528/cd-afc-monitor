package com.insigma.acc.monitor.util;

public class StringHelper {

    public static String upcaseFirst(String str){
        char[] chars = str.toCharArray();
        char c = chars[0];
        if (c>='a'&&c<='z'){
            chars[0]-=32;
        }
        return new String(chars);
    }
}
