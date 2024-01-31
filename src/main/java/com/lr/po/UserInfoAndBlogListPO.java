package com.lr.po;

import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * 多表一对多
 */
@Data
public class UserInfoAndBlogListPO {
    // a user publishes multiple blogs, userId, username, blogTitle, blogContent
    private String username;

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

    private List<Blog> blogList;
}
