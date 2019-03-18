package com.define.attachment.dao;

import com.define.attachment.domain.FileDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文件管理
 *
 * @author caiww
 * @email caiwwh@gzdefine.com
 * @date 2019-03-15 10:38:23
 */
@Mapper
public interface FileDao {

    FileDO get(Long id);

    List<FileDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(FileDO file);

    int update(FileDO file);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<FileDO> getFileList(Long[] ids);
}