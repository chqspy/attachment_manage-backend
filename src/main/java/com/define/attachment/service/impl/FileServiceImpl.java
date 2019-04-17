package com.define.attachment.service.impl;

import com.define.attachment.dao.FileDao;
import com.define.attachment.domain.FileDO;
import com.define.attachment.service.FileService;
import com.define.common.config.UploadConfig;
import com.define.common.utils.FileType;
import com.define.common.utils.FileUtil;
import com.define.ueditor.domain.UeditorImageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Value("${server.port}")
    private String port;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public FileDO get(Long id) {
        return fileDao.get(id);
    }

    @Override
    public List<FileDO> list(Map<String, Object> map) {
        return fileDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return fileDao.count(map);
    }

    @Override
    public int save(FileDO sysFile) {
        return fileDao.save(sysFile);
    }

    @Override
    public int update(FileDO sysFile) {
        return fileDao.update(sysFile);
    }

    @Override
    public int remove(Long id) {
        return fileDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return fileDao.batchRemove(ids);
    }

    @Override
    public Boolean isExist(String url) {
        Boolean isExist = false;
        if (!StringUtils.isEmpty(url)) {
            String filePath = url.replace("/files/", "");
            filePath = uploadConfig.getUploadPath() + filePath;
            File file = new File(filePath);
            if (file.exists()) {
                isExist = true;
            }
        }
        return isExist;
    }

    @Override
    public UeditorImageDO uploadFile(MultipartFile file) throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();
        String urlPrefix = "http://" + ip + ":" + port;
        UeditorImageDO ueditorImageDO = new UeditorImageDO();
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        String filesize = this.formetFileSize(file.getSize());
        int fileType = FileType.fileType(fileName);
        FileDO fileDO = new FileDO();
        fileDO.setType(fileType);
        fileDO.setUrl("/files/" + this.getUploadFileUrl(fileType) + fileName);
        fileDO.setFileName(fileName);
        fileDO.setOriginalFileName(file.getOriginalFilename());
        fileDO.setFileSize(filesize);
        FileUtil.uploadFile(file.getBytes(), uploadConfig.getUploadPath() + this.getUploadFileUrl(fileType), fileName);
        if (fileDao.save(fileDO) > 0) {
            ueditorImageDO.setState("SUCCESS");
            ueditorImageDO.setUrl(urlPrefix + fileDO.getUrl());
            ueditorImageDO.setTitle(fileDO.getOriginalFileName());
            ueditorImageDO.setOriginal(fileDO.getOriginalFileName());
        } else {
            ueditorImageDO.setState("FAIL");
        }
        return ueditorImageDO;
    }

    @Override
    public UeditorImageDO uploadScrawl(String file) throws Exception {
        System.out.println("######:" + file + ":######");
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();
        String urlPrefix = "http://" + ip + ":" + port;
        UeditorImageDO ueditorImageDO = new UeditorImageDO();
        Long fileSize = FileUtil.imageSize(file);
        String originalFileName = "scrawl.png";
        String fileName = FileUtil.renameToUUID(originalFileName);
        String filesize = this.formetFileSize(fileSize);
        int fileType = 0;
        boolean image = FileUtil.generateImage(file, uploadConfig.getUploadPath() + this.getUploadFileUrl(fileType), fileName);
        if (image) {
            FileDO fileDO = new FileDO();
            fileDO.setType(fileType);
            fileDO.setUrl("/files/" + this.getUploadFileUrl(fileType) + fileName);
            fileDO.setFileName(fileName);
            fileDO.setOriginalFileName(originalFileName);
            fileDO.setFileSize(filesize);
            if (fileDao.save(fileDO) > 0) {
                ueditorImageDO.setState("SUCCESS");
                ueditorImageDO.setUrl(urlPrefix + fileDO.getUrl());
                ueditorImageDO.setTitle(fileDO.getOriginalFileName());
                ueditorImageDO.setOriginal(fileDO.getOriginalFileName());
            } else {
                ueditorImageDO.setState("FAIL");
            }
        } else {
            ueditorImageDO.setState("FAIL");
        }
        return ueditorImageDO;
    }

    @Override
    public String formetFileSize(long filesize) {
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (filesize < 1024) {
            fileSizeString = df.format((double) filesize) + "B";
        } else if (filesize < 1048576) {
            fileSizeString = df.format((double) filesize / 1024) + "Kb";
        } else if (filesize < 1073741824) {
            fileSizeString = df.format((double) filesize / 1048576) + "Mb";
        } else {
            fileSizeString = df.format((double) filesize / 1073741824) + "Gb";
        }
        return fileSizeString;
    }

    @Override
    public String getUploadFileUrl(int fileType) {
        String filePath = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        String curDate = df.format(new Date());
        switch (fileType) {
            case 0:
                filePath = "image/";
                break;
            case 1:
                filePath = "document/";
                break;
            case 2:
                filePath = "video/";
                break;
            case 3:
                filePath = "music/";
                break;
            default:
                filePath = "other/";
                break;
        }
        return filePath + curDate + "/";
    }
}
