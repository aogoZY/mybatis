package cn.aogo.test;

import cn.aogo.dao.IAccountDao;
import cn.aogo.dao.IUserDao;
import cn.aogo.domain.Account;
import cn.aogo.domain.AccountUser;
import cn.aogo.domain.QueryVo;
import cn.aogo.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mybatisTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
//        1、读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
//        2、创建sqlSessionFactory的构建对象
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
//        3、使用构造者创建工厂对象 sqlSessionFactory
        SqlSessionFactory factory = factoryBuilder.build(in);
//        4、使用selSessionFactory生产 sqlSession 对象
        sqlSession = factory.openSession(true);
//        5、使用sqlSession创建dao接口的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
        accountDao = sqlSession.getMapper(IAccountDao.class);

    }

    @After
    public void destroy() throws Exception {
        sqlSession.close();
        in.close();
    }


    @Test
    public void queryAll() throws IOException {
        List<User> users = userDao.queryAll();
        for (User user : users) {
            System.out.println(user);
        }

    }

    @Test
    public void addTest() throws IOException {
        User user = new User();
        user.setUserName("aogo");
        user.setGender(1);
        user.setBirthday(new Date());
        user.setAddress("广州");

        Integer affected = userDao.insertUser(user);
        System.out.println("affected:" + affected);

    }

    //    这里相当于是全量覆盖 如果某列没有set值是会被置为null的
    @Test
    public void updateTest() throws IOException {
        User user = new User();
        user.setUserName("amao");
        user.setAddress("北京");
        user.setBirthday(new Date());
        user.setId(23L);
        user.setGender(1);

        Integer affected = userDao.updateUser(user);
        System.out.println(affected);
    }

    @Test
    public void deleteTest() throws IOException {
        Integer affected = userDao.deleteUser(22);
        System.out.println(affected);
    }


    //    根据名称模糊查询
    @Test
    public void findByNameTest() {
        List<User> userList = userDao.findByName("%星%");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    //    根据queryVo中查询条件查询
    @Test
    public void queryByVo() {
        User user = new User();
        user.setUserName("%五%");
        QueryVo vo = new QueryVo();
        vo.setUser(user);
        List<User> userList = userDao.queryByVo(vo);
        for (User u : userList) {
            System.out.println(u);
        }
    }

    //    根据if标签做条件查询
    @Test
    public void queryByIfCondition() {
        User user = new User();
        user.setUserName("aogou");

        List<User> userList = userDao.findByCondition(user);
        for (User u : userList) {
            System.out.println(u);
        }
    }

    //    根据where 标签做条件查询
    @Test
    public void queryByWhereCondition() {
        User user = new User();
        user.setUserName("星期五");
        List<User> userList = userDao.findByWhereCondition(user);
        for (User u : userList) {
            System.out.println(u);
        }
    }

    //    根据foreach 标签做条件查询
    @Test
    public void queryByForeachCondition() {
        QueryVo vo = new QueryVo();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        vo.setIds(ids);
        List<User> users = userDao.findByForeachCondition(vo);
        for (User user : users) {
            System.out.println(user);
        }
    }

    //    查询所有account信息及其对应的用户名和地址
    @Test
    public void queryAccountAllIncludeUser() {
        List<AccountUser> accountUserList = accountDao.findAll();
        for (AccountUser accountUser : accountUserList) {
            System.out.println(accountUser);
        }
    }

    //    查询所有account信息及其对应用户地址 使用account类包含user类的形式
    @Test
    public void findAllByBean() {
        List<Account> accountList = accountDao.findAllByBean();
        for (Account account : accountList) {
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }

    //    查询所有用户及其关联的账户信息 一对多
    @Test
    public void findUserLeftJoinAccount() {
        List<User> userList = userDao.findAllByUser();
        for (User user : userList) {
            System.out.println(user);
            System.out.println(user.getAccounts()+"");
            System.out.println("-----------");
        }
    }
}

