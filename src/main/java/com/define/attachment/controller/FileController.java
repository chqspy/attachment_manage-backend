package com.define.attachment.controller;

import com.define.attachment.service.FileService;
import com.define.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件管理
 *
 * @author caiww
 * @email caiwwh@gzdefine.com
 * @date 2019-03-15 10:44:41
 */
@Controller
@RequestMapping("/attachment")
public class FileController {

    @Autowired
    private FileService fileService;

    @ResponseBody
    @GetMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return fileService.uploadFile(file);
    }

}
