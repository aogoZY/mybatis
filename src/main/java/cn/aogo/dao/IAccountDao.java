package cn.aogo.dao;

import cn.aogo.domain.Account;
import cn.aogo.domain.AccountUser;

import java.util.List;

public interface IAccountDao {
    //    查询所有用账户信息，并带有用户名称和地址
    List<AccountUser> findAll();

    //查询所有账户信息及其用户名称和地址 使用实体类的方式 account类包含了user类
    List<Account> findAllByBean();
}
