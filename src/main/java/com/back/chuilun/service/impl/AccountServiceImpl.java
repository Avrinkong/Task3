package com.back.chuilun.service.impl;

import com.back.chuilun.dao.AccountMapper;
import com.back.chuilun.entity.Account;
import com.back.chuilun.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int addAccount(Account account) {
        return 0;
    }

    @Override
    public int deleteAccount(Account account) {
        return 0;
    }

    @Override
    public int updateAccount(Account account) {
        return 0;
    }

    @Override
    public List<Account> findAccountAll() {
        return null;
    }

    @Override
    public Account findAccountById(Long accId) {
        return null;
    }

    @Override
    public Account findAccountByNamePwd(String username, String password) {
        Account account = accountMapper.selectByNamePwd(username, password);
        return account;
    }
}
