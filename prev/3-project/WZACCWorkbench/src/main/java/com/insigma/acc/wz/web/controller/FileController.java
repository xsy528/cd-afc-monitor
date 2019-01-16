package com.insigma.acc.wz.web.controller;

import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2018-12-25:10:12
 */
public class FileController extends BaseMultiActionController{

    static{
        methodMapping.put("/file/ref","getFileLink");
        methodMapping.put("/file/images","getImageList");
    }

    @Autowired
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    public void getFileLink(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("image/png");
        String filename = request.getParameter("filename");
        try(OutputStream outputStream = response.getOutputStream()){
            outputStream.write(fileService.getFileBytes(filename));
        }
    }

    public Result<String[]> getImageList(){
        return fileService.getResourceList();
    }
}
