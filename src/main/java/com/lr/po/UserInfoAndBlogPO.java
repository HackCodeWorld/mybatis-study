package com.lr.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoAndBlogPO {
    // a user publishes what blog, userId, username, blogTitle, blogContent
    private Long userId;
    private String username;
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
