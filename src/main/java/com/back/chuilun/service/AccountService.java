package com.back.chuilun.service;

import com.back.chuilun.entity.Account;

import java.util.List;


public interface AccountService {
     int addAccount(Account account);

     int deleteAccount(Account account);

     int updateAccount(Account account);

     List<Account> findAccountAll();

     Account findAccountById(Long accId);

     Account findAccountByNamePwd(String username,String password);
}
