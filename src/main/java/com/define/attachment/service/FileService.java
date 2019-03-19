package com.define.attachment.service;

import com.define.attachment.domain.FileDO;
import com.define.ueditor.domain.UeditorImageDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * 文件管理
 *
 * @author caiww
 * @email caiwwh@gzdefine.com
 * @date 2019-03-15 10:40:45
 */
public interface FileService {

    FileDO get(Long id);

    List<FileDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(FileDO sysFile);

    int update(FileDO sysFile);

    int remove(Long id);

    int batchRemove(Long[] ids);

    Boolean isExist(String url);

    UeditorImageDO uploadFile(MultipartFile file) throws Exception;

    UeditorImageDO uploadScrawl(String file) throws Exception;

    String formetFileSize(long filesize);

    String getUploadFileUrl(int fileType);
}
