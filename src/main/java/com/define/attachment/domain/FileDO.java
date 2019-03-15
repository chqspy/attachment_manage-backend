package com.define.attachment.domain;


import com.define.base.BaseDAO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "DY_ATTACHMENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class FileDO extends BaseDAO {
    @Column(columnDefinition = "varchar(10) COMMENT '附件类型'")
    private String type;

    @Column(columnDefinition = "varchar(255) COMMENT '附件地址'")
    private String url;

    @Column(columnDefinition = "varchar(255) COMMENT '附件名称'")
    private String fileName;

    @Column(columnDefinition = "varchar(45) COMMENT '附件大小'")
    private String fileSize;
}
