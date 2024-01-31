package com.lr;

import com.lr.mapper.BlogDao;
import com.lr.mapper.UserInfoMapper;
import com.lr.po.Blog;
import com.lr.po.UserInfo;
import com.lr.po.UserInfoAndBlogListPO;
import com.lr.po.UserInfoAndBlogPO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用Mapper绑定映射接口实现增删改查
 * 1.写映射器接口
 * 2.mybatis xml配置数据库用户名和密码 然后映射到mapping xml
 * 3.写对应的mapping xml（mapper包含sql增删改查语句）
 * All in all, 主方法调用mybatis xml得到数据库data，然后通过sqlsession写实例映射mapper的接口方法，
 * 这些接口方法又和实例映射xml里的sql增删改查语句一一对应，从而起到数据库的一系列操作功能。
 */
public class Application {
    public static void main(String[] args) throws Exception {
//        updateUserInfo();
//        insertUserInfo();
//        deleteUserById();
//        userLogin();
//        queryUserInfoByUsernameOrPwd();
        getAllUserInfo();
//        updateUserInfoByParameters();
//        getAllUserInfo();
//        searchBlogById();
//        searchMultipleTablesOneMapOne();
//       searchMultipleTablesOneMapMul();
//        searchMultipleTablesMulMapMul();
//        searchMultipleTablesMulMapMul2();
    }

    /**
     * 运用之前写好的id查询所有user_info里的userId，然后再用stream合成list map一个个按照id查询blogs
     * blogs再整合成一个集合出来
     * @throws IOException
     */
    private static void searchMultipleTablesMulMapMul2() throws IOException {
        SqlSession sqlSession = getSqlSession();
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfo> userInfos = userInfoMapper.selectAllUsers();
        List<Long> idList = userInfos.stream()
                .map(UserInfo::getUserId)
                .collect(Collectors.toList());

        List<List<UserInfoAndBlogPO>> allBlogsByIds = idList.stream()
                .map((id) -> userInfoMapper.queryBlogsByIds(id))
                .collect(Collectors.toList());

        System.out.println(allBlogsByIds);
    }

    /**
     * 多对多多表查询， 和一对多很像， 不同就在于不指定用id去查了
     * 多个用户发了很多博客 整个平台全要查询
     * @throws IOException
     */
    private static void searchMultipleTablesMulMapMul() throws IOException {
        SqlSession sqlSession = getSqlSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfoAndBlogListPO> userInfoAndBlogListPOS = mapper.queryUserInfoAndBlogList();
        userInfoAndBlogListPOS.forEach( e -> System.out.println("User: "+ e) );
    }

    /**
     * 一对多多表查询, 一个用户发了很多博客 都要查询
     * @throws IOException
     */
    private static void searchMultipleTablesOneMapMul() throws IOException {
        //搜索某用户发了什么博客
        SqlSession sqlSession = getSqlSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        System.out.println(mapper.queryUserInfoAndBlogListByUserId(1L));
    }

    /**
     * 搜索某用户发了什么博客 多表查询一对一（一个用户查出一个博客）
     * 如果把pojo类的博客改成List type就可以进行查询，但是有重复信息（如：名字，id等），所以多表查询更加customized
     * @throws IOException
     */
    private static void searchMultipleTablesOneMapOne() throws IOException {
        //搜索某用户发了什么博客
        SqlSession sqlSession = getSqlSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        System.out.println(mapper.queryUserInfoAndBlogByUserId(1L).get(0)); //need to fix if there is only one blog for
        //the user
    }

    /**
     * 根据ID搜索博客信息
     * @throws IOException
     */
    private static void searchBlogById() throws IOException {
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        BlogDao mapper = sqlSession.getMapper(BlogDao.class);
        Blog blog = new Blog();
        blog.setBlogId(1L);
        blog.setUserId(1L);
        blog.setBlogContent("I have a dream.");
        mapper.insert(blog);
        System.out.println(mapper.queryById(1L));
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 根据变量更新用户信息
     * @throws IOException
     */
    private static void updateUserInfoByParameters() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Athena");
        userInfo.setUserPassword("123456");
        userInfo.setUserId(1L);
        mapper.updateUserInfoByPara(userInfo);
        sqlSession.commit();
        sqlSession.close();
//        InputStream is = new ByteArrayInputStream("dekm".getBytes());
    }

    /**
     * 根据用户名或者密码查询
     *
     * @throws IOException
     */
    private static void queryUserInfoByUsernameOrPwd() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Alex");
        userInfo.setUserPassword("root");
        List<UserInfo> userInfos = mapper.queryUserInfoByUsernameOrPwd(userInfo);
        userInfos.forEach(e -> System.out.println("user info:" + e));
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 根据用户名和密码查询
     */
    private static void userLogin() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo = mapper.queryUserInfoByUsernameAndPwd("Tom", "ns123456sodj.!");
        System.out.println(userInfo);
    }

    /**
     * 新增一条数据/用户信息
     */
    private static void insertUserInfo() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(8L);
        userInfo.setUsername("Alex");
        userInfo.setUserPassword("jig989089");
        mapper.insertUserInfo(userInfo);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 通过主键id删除用户信息
     *
     * @throws IOException
     */
    private static void deleteUserById() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        mapper.deleteUserById(13L);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 通过主键id更改用户名
     *
     * @throws IOException
     */
    private static void updateUserInfo() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        mapper.updateUsernameById("Franky", 2L); //执行完毕后要记得提交
        //最后一定要commit这个事务那不然不会更新数据成功的
        sqlSession.commit();
        sqlSession.close();
    }


    /**
     * 搜索展示所有用户信息
     *
     * @throws IOException
     */
    private static void getAllUserInfo() throws IOException {
        //映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的.
        SqlSession sqlSession = getSqlSession();
        //获取自定义interface映射器对象
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        //然后直接可以使用定义的那个方法了
        //mybatis有自动机制帮我们handle了底层，想看的话idea里搜索plugin里的freeMyBatis插件重启后可以看到绑定mapping的箭头
//        mapper.insertUserInfo(new UserInfo("Christopher", "we3889hj"));
        List<UserInfo> userInfo = mapper.selectAllUsers();
        userInfo.forEach(u -> System.out.println("user information:" + u));
        sqlSession.close();
    }

    /**
     * SQLSession请求方式简单但是线程不安全也不能够被共享。最佳作用域是请求或方法作用域，绝对不能将其实例放在一个类的静态domain
     * 关闭操作很重要，要放在finally block里
     *
     * @throws IOException
     */
    private static void getMysqlSimple() throws IOException {
        SqlSession sqlSession = getSqlSession();
        UserInfo user = sqlSession.selectOne("selectUser", 1);
        System.out.println(user);
        sqlSession.close();
    }

    /**
     * 通过得到配置文件中的用户名和密码
     *
     * @return 一个SQLSession对象
     * @throws IOException
     */
    private static SqlSession getSqlSession() throws IOException {
        String resource = "mybatis.config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }
}
