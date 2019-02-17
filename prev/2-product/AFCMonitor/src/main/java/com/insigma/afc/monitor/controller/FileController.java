package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Ticket: 文件接口
 *
 * @author xuzhemin
 * 2018-12-25 10:12
 */
@Api(tags="文件服务接口")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation("图片链接")
    @GetMapping("/ref")
    public void getFileLink(@RequestParam String filename,HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try(OutputStream outputStream = response.getOutputStream()){
            outputStream.write(fileService.getFileBytes(filename));
        }
    }

    @ApiOperation("获取图片列表")
    @GetMapping("/images")
    public Result<String[]> getImageList(){
        return fileService.getResourceList();
    }
}
