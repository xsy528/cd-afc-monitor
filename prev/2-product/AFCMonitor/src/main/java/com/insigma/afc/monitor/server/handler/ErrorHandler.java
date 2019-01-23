package com.insigma.afc.monitor.server.handler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-14:16:48
 */
public class ErrorHandler extends org.eclipse.jetty.server.handler.ErrorHandler {

    @Override
    protected void writeErrorPageBody(HttpServletRequest request, Writer writer, int code, String message,
                                      boolean showStacks) throws IOException {
        String uri= request.getRequestURI();

        writeErrorPageMessage(request,writer,code,message,uri);
        if (showStacks) {
            writeErrorPageStacks(request,writer);
        }
    }

}
