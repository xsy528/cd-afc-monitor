package com.insigma.commons.editorframework.graphic.editor;

import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.framework.Response;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * author: xuzhemin
 * 2018/9/29 14:24
 * 浏览器视图
 */
public class BrowserView extends FrameWorkView {

    private Browser browser;

    public BrowserView(Composite composite, int style) {
        super(composite, style);
        try {
            browser = new Browser(this, SWT.NONE);
        } catch (SWTError e) {
            e.printStackTrace();
            this.dispose();
        }

        browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        String mainUrl = System.getProperty("mainUrl");
        if (mainUrl == null) {
            mainUrl = "http://127.0.0.1:8060/index.html";
        }
        browser.setUrl(mainUrl);
    }

    //刷新浏览器内容
    public void refresh(Response response) {
        Map data = (Map) response.getValue();
        String ids = (String) data.get("lineIds");
        String selectPeriod = (String) data.get("selectPeriod");
        Date date = (Date) data.get("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        browser.execute("refresh('" + ids + "','" + sdf.format(date) + "','" + selectPeriod + "')");
    }

}
