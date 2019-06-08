package com.back.chuilun.service.impl;

import com.back.chuilun.dao.AccountMapper;
import com.back.chuilun.entity.Account;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.AccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Result findAll() {
        List<Account> accounts = accountMapper.selectAll();
        return new Result(0,"查询账号成功",accounts);
    }

    /**
     * 根据角色名称和账户名称进行查询
     * @param accName
     * @param roleName
     * @return
     */
    @Override
    public List<Account> findAll(String accName, String roleName) {
        List<Account> list = new ArrayList<>();
        List<Account> accounts = accountMapper.selectByName(accName);
        //logger.info(messages+"1111111111111111");
        for (Account a:accounts){
            if(roleName==null&&roleName.trim().equals("")){
                    list.add(a);
            }else  if (a.getRoleName().equals(roleName)){
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public Result add(Account account) {
        Date date=new Date();
        long timestamp=date.getTime();
        account.setAccCreatetime(timestamp);
        account.setAccUpdatetime(timestamp);
        int insert = accountMapper.insert(account);
        if (insert<=0) {
            throw  new BusinessException("添加失败");
        }else if(insert>0){
            return new Result(0,"添加成功",insert);
        }
        throw  new BusinessException("添加异常");
    }

    @Override
    public Result updateById(Account account) {
        Date date=new Date();
        long timestamp=date.getTime();
        account.setAccUpdatetime(timestamp);
        int i = accountMapper.updateByPrimaryKey(account);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            throw  new BusinessException("编辑失败");
        }
    }

    @Override
    public Result deleteById(Long accId) {
        int i = accountMapper.deleteByPrimaryKey(accId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            throw  new BusinessException("删除失败");
        }
    }

    @Override
    public Result changePassword(Long accId, String oldpassword, String newpassword) {
        Account account = accountMapper.selectByPrimaryKey(accId);
        if(account.getAccPassword().equals(oldpassword)){
            account.setAccPassword(newpassword);
            Date date=new Date();
            long timestamp=date.getTime();
            account.setAccUpdatetime(timestamp);
            int i = accountMapper.updateByPrimaryKey(account);
            if(i>0){
                return new Result(0,"修改成功",i);
            }else {
                throw  new BusinessException("修改失败");
            }
        }
        throw  new BusinessException("旧密码输入错误");
    }

    @Override
    public PageInfo<Account> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Account> bannercontrols = accountMapper.selectAll();
        PageInfo<Account> pageInfo =new PageInfo<>(bannercontrols);
        return pageInfo;
    }
}
