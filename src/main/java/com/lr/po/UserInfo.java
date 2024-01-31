package com.lr.po;
import lombok.Data;
import java.util.Date;

/**
 * create table user_info
 * (
 * 		user_id             bigint auto_increment comment '用户ID' primary key,
 *     username         varchar(50)      null default '' comment  '用户名',
 *     user_password    varchar(50)    null default '' comment '用户密码',
 * 		create_time  timestamp default CURRENT_TIMESTAMP null comment '创建时间',
 * 		update_time  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
 * )charset = utf8;
 */
@Data //可以省略get and set方法的注解
public class UserInfo {
    private Long userId;
    private String username;
    private String userPassword;
    private Date createTime;
    private Date updateTime;
}
