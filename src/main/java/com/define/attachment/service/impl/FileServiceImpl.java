package com.define.attachment.service.impl;

import com.define.attachment.dao.FileDao;
import com.define.attachment.domain.FileDO;
import com.define.attachment.service.FileService;
import com.define.common.config.UploadConfig;
import com.define.common.exception.business.BusinessException;
import com.define.common.utils.FileType;
import com.define.common.utils.FileUtil;
import com.define.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class FileServiceImpl implements FileService {
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
    public R uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        String filesize = this.formetFileSize(file.getSize());
        FileDO fileDO = new FileDO();
        fileDO.setType(FileType.fileType(fileName));
        fileDO.setUrl("/files/" + fileName);
        fileDO.setFileName(fileName);
        fileDO.setOriginalFileName(file.getOriginalFilename());
        fileDO.setFileSize(filesize);
        try {
            FileUtil.uploadFile(file.getBytes(), uploadConfig.getUploadPath(), fileName);
        } catch (Exception e) {
        }

        if (fileDao.save(fileDO) > 0) {
            Map map = new HashMap();
            map.put("fileId", fileDO.getId());
            map.put("fileName", fileDO.getUrl());
            return R.ok(map);
        } else {
            throw new BusinessException(500, "上传失败");
        }
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
}
