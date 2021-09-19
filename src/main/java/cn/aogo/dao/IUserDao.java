package cn.aogo.dao;

import cn.aogo.domain.AccountUser;
import cn.aogo.domain.QueryVo;
import cn.aogo.domain.User;

import java.util.List;

public interface IUserDao {
    //    查询所有
    List<User> queryAll();

    //    插入数据
    Integer insertUser(User user);

    //    更新数据
    Integer updateUser(User user);

    //    删除数据
    Integer deleteUser(Integer id);

    //    根据名字模糊查询
    List<User> findByName(String name);

    //    根据vo查询
    List<User> queryByVo(QueryVo vo);

    //    根据if标签查询
    List<User> findByCondition(User user);

    //    根据where标签查询
    List<User> findByWhereCondition(User user);

    //    根据foreach标签查询
    List<User> findByForeachCondition(QueryVo vo);

    //    查询所有user并获取对应的account信息
    List<AccountUser> findAll();

    //    一对多查询 获取所有用户及对应的account信息
    List<User> findAllByUser();
}

