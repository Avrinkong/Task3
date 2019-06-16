package com.back.chuilun.controller;

import com.back.chuilun.entity.Account;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.AccountServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountServiceImpl as;
    private Logger logger = Logger.getLogger(AccountController.class);

    /**
     * 根据用户名和密码进行登录验证操作
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
   // @ResponseBody
    public ModelAndView login(String username, String password) {
        ModelAndView model = new ModelAndView();
        if (username!=null&&!username.trim().equals("")){
            if (password!=null&&!password.trim().equals("")){
                Account accountByNamePwd = as.findAccountByNamePwd(username, password);
                if (accountByNamePwd==null){
                    model.addObject("message", "用户名或密码错误");
                    model.setViewName("login");
                    model.setView(new MappingJackson2JsonView());
                    return model;
                }
                int num = Math.toIntExact(accountByNamePwd.getAccId());
                if (num == 1) {
                    model.addObject("message", "登录成功");
                    //model.addObject(accountByNamePwd);
                    //String s = JSON.toJSONString(model);
                    model.setViewName("login");
                    model.setView(new MappingJackson2JsonView());
                    return model;
                }else {
                    model.addObject("message","登录失败");
                    model.setViewName("login");
                    model.addObject(accountByNamePwd);
                    model.setView(new MappingJackson2JsonView());
                    //String s = JSON.toJSONString(model);
                    return model;
                }
            }else {
                model.addObject("message","密码错误");
                model.setViewName("login");
                model.setView(new MappingJackson2JsonView());
            }
        }else {
            model.addObject("message","用户名不存在");
            model.setViewName("login");
            model.setView(new MappingJackson2JsonView());
        }
        return model;
    }

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result find(){
        Result all = as.findAll();
        return all;
    }


    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result find(String accName, String roleName){
        if (accName!=null&&!accName.trim().equals("")){
            List<Account> all = as.findAll(accName,roleName);
            if (all.size()>0) {
                return new Result(0, "查询成功", all);
            }else {
                throw  new BusinessException("没有符合要求的账户");
            }
        }else {

            Result all = as.findAll();
            List<Account> list = new ArrayList<>();
            if (roleName!=null&&!roleName.trim().equals("")){
                List<Account> data = (List<Account>) all.getData();
                for (Account studio:data){
                    if (studio.getRoleName().equals(roleName)){
                        list.add(studio);
                    }
                }
                if (list.size()<=0){
                    throw new BusinessException("查询错误");
                }
                all.setData(list);
            }
            return all;
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addAccount(Account account){
        if (account.getAccCreateman()!=null&&!account.getAccCreateman().trim().equals("")) {
            if (account.getAccName() != null && !account.getAccName().trim().equals("")) {
                if (account.getAccPassword() != null && !account.getAccPassword().trim().equals("")&&account.getAccPassword().length()>=6) {
                    if (account.getRoleName() != null && !account.getRoleName().trim().equals("")) {
                        Result result = as.add(account);
                        return result;
                    } else {
                        throw new BusinessException("角色名不能为空");
                    }
                } else {
                    throw new BusinessException("密码不能为空");
                }
            } else {
                throw new BusinessException("用户名不能为空");
            }
        }else {
            throw new BusinessException("超级管理员名称未输入");
        }
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Account account){
        if (account.getAccId()!=null) {
            if (account.getAccName()!=null&&!account.getAccName().trim().equals("")){
                if (account.getAccPassword()!=null&&!account.getAccPassword().trim().equals("")&&account.getAccPassword().length()>=6) {
                    if (account.getRoleName()!=null&&!account.getRoleName().trim().equals("")) {
                        if (account.getAccCreateman()!=null&&!account.getAccCreateman().trim().equals("")){
                            Result result = as.updateById(account);
                            return result;
                        }
                        throw new BusinessException("超级管理员名称未输入");
                    }
                    throw  new BusinessException("角色名不能为空");
                }
                throw  new BusinessException("密码不能为空");
            }
            throw  new BusinessException("用户名不能为空");
        }
        throw  new BusinessException("ID不能为空");
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Long accId){
        if (accId!=null){
            Result result = as.deleteById(accId);
            return result;
        }else {
            throw  new BusinessException("角色ID不能为空");
        }
    }

    @RequestMapping(value = "password",method = RequestMethod.POST)
    @ResponseBody
    public Result changePassword(Long accId,String oldpassword,String newpassword ){
        if (accId!=null){
            if (oldpassword!=null&&!oldpassword.trim().equals("")){
                if (newpassword!=null&&!newpassword.trim().equals("")&&newpassword.length()>=6){
                    Result result = as.changePassword(accId,oldpassword,newpassword);
                    return result;
                }
                throw  new BusinessException("新密码不能为空或密码长度小于6位数");
            }
            throw  new BusinessException("旧密码不能为空");
        }
        throw  new BusinessException("用户id不能为空");
    }

    @RequestMapping(value = "pageinfo",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Account> findByPage(int currentPage, int pageSize){
        PageInfo<Account> info =as.findByPage(currentPage,pageSize);
        return info;
    }
}
