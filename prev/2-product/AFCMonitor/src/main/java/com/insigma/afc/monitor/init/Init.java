package com.insigma.afc.monitor.init;

import com.insigma.commons.dic.loader.IDicClassListProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Ticket:初始化类
 *
 * @author xuzhemin
 * 2019-01-24:11:44
 */
@Component
public class Init implements CommandLineRunner {

    @Autowired
    private IDicClassListProvider dicClassListProvider;

    @Override
    public void run(String... args){
        //字典初始化
        DicOverwriteInitor.init(dicClassListProvider);
    }
}
