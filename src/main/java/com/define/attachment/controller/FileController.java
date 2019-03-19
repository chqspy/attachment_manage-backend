package com.define.attachment.controller;

import com.define.attachment.service.FileService;
import com.define.ueditor.domain.UeditorImageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理
 *
 * @author caiww
 * @email caiwwh@gzdefine.com
 * @date 2019-03-15 10:44:41
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/attachment")
public class FileController {

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping("/upload")
    public UeditorImageDO upload(@RequestParam("upfile") MultipartFile file) throws Exception {
        return fileService.uploadFile(file);
    }

}
