package com.lr.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Blog)实体类
 *
 * @author makejava
 * @since 2022-10-24 00:33:38
 */
@Data
public class Blog implements Serializable {
    /**
     * 博客ID
     */
    private Long blogId;
    /**
     * 发表用户ID
     */
    private Long userId;
    /**
     * 博客标题
     */
    private String blogTitle;
    /**
     * 博客内容
     */
    private String blogContent;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

