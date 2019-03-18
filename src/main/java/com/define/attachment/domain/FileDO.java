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
    @Column(columnDefinition = "int(10) COMMENT '附件类型'")
    private Integer type;

    @Column(columnDefinition = "varchar(255) COMMENT '附件地址'")
    private String url;

    @Column(columnDefinition = "varchar(255) COMMENT '附件名称'")
    private String fileName;

    @Column(columnDefinition = "varchar(255) COMMENT '原始附件名称'")
    private String originalFileName;

    @Column(columnDefinition = "varchar(45) COMMENT '附件大小'")
    private String fileSize;
}
