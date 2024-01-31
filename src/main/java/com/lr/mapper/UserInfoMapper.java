package com.lr.mapper;

import com.lr.po.UserInfo;
import com.lr.po.UserInfoAndBlogListPO;
import com.lr.po.UserInfoAndBlogPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 根据主键id获取userinfo
 */
public interface UserInfoMapper {
    /**
     * 通过主键获取只能有一条
     * 方法名selectUserInfoById要和UserInfoMapper的<select id="selectUserInfoById" resultMap="userInfoResult">
     * select * from user_info where user_id = #{id}
     * </select> 的id "selectUserInfoById" 对应
     * UserInfo selectUserInfoById(Long id);
     *
     * 根据主键id获取userinfo
     * @return userinfo
     */
    UserInfo selectUserInfoById(Long id);

    /**
     * 查询所有用户信息
     */
    List<UserInfo> selectAllUsers();

    /**
     * 新增一条数据/用户信息
     */
    Integer insertUserInfo(UserInfo userInfo);

    /**
     * 根据主键id修改用户名
     * 多个参数需要mapping加注解，那不然不知道name和id参数对应的是xml里sql语句的哪个实参
     */
    Integer updateUsernameById(@Param("Name") String name, @Param("ID") Long id);

    /**
     * 根据主键id删除用户信息
     * @param id
     * @return
     */
    Integer deleteUserById(Long id);

    /**
     * 根据用户名和密码查询
     */
    UserInfo queryUserInfoByUsernameAndPwd(@Param("name") String name, @Param("pass") String password);

    /**
     * 根据用户名或者密码查询
     */
    List<UserInfo> queryUserInfoByUsernameOrPwd(UserInfo userInfo);

    /**
     * 根据用户名者密码（参数）任意一/几个或者全部进行查询
     */
    List<UserInfo> queryUserInfoByParameters(UserInfo userInfo);

    /**
     * 根据任意传参更新用户信息
     * @param userInfo
     * @return
     */
    Integer updateUserInfoByPara(UserInfo userInfo);

    /**
     * 一对一 多表查询， 搜索某用户发了什么博客
     * @param userId
     * @return
     */
    List<UserInfoAndBlogPO> queryUserInfoAndBlogByUserId(Long userId);

    /**
     * 一对多 多表查询， 搜索某用户发了多个博客是什么
     * @param userId
     * @return
     */
    UserInfoAndBlogListPO queryUserInfoAndBlogListByUserId(Long userId);

    /**
     * 多对多 多表查询， 搜索多名用户发了多个博客是什么
     * @return
     */
    List<UserInfoAndBlogListPO> queryUserInfoAndBlogList();

    List<UserInfoAndBlogPO> queryBlogsByIds(Long userId);
}
