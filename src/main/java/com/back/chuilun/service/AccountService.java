package com.back.chuilun.service;

import com.back.chuilun.entity.Account;
import com.back.chuilun.entity.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface AccountService {
     int addAccount(Account account);

     int deleteAccount(Account account);

     int updateAccount(Account account);

     List<Account> findAccountAll();

     Account findAccountById(Long accId);

     Account findAccountByNamePwd(String username,String password);

     Result findAll();

     List<Account> findAll(String accName, String roleName);

     Result add(Account account);

     Result updateById(Account account);

     Result deleteById(Long accId);

     Result changePassword(Long accId, String oldpassword, String newpassword);

    PageInfo<Account> findByPage(int currentPage, int pageSize);
}
