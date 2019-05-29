package com.back.chuilun.controller;

import com.back.chuilun.entity.Account;
import com.back.chuilun.entity.Result;
import com.back.chuilun.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService as;
    private Logger logger = Logger.getLogger(AccountController.class);

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(String username, String password) {
        ModelAndView model = new ModelAndView();
        if(username!=null&&password!=null){
            //logger.info(username+"AAA"+password);
            Account accountByNamePwd = as.findAccountByNamePwd(username, password);
            int accId = Math.toIntExact(accountByNamePwd.getAccId());
             //logger.info("数字是"+accId);
            //String s = JSON.toJSONString(accountByNamePwd);
            if (accId == 1){
                model.addObject("message","添加成功");
                model.addObject(accountByNamePwd);
                //String s = JSON.toJSONString(model);
                model.setViewName("welcome");
                model.setView(new MappingJackson2JsonView());
                return model;
            }else {
                model.addObject("message","添加失败");
                model.setViewName("login");
                model.addObject(accountByNamePwd);
                model.setView(new MappingJackson2JsonView());
                //String s = JSON.toJSONString(model);
                return model;
            }
        }else {
            model.addObject("message","用户名或者密码为空");
        }
        return model;
       //return accId;
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
                return new Result(0, "success", all);
            }else {
                return new Result(-1,"false");
            }
        }else {
            return new Result(-1,"用户名不能为空");
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addAccount(Account account){
        if (account.getAccId()!=null) {
            if (!account.getAccName().trim().equals("")&&account.getAccName()!=null){
                if (!account.getAccPassword().trim().equals("")&&account.getAccPassword()!=null) {
                    if (!account.getRoleName().trim().equals("")&&account.getRoleName()!=null) {
                        Result result = as.add(account);
                        return result;
                    }else {
                        Result result = new Result(-1,"角色名不能为空");
                        return result;
                    }
                }else {
                    Result result = new Result(-1,"密码不能为空");
                    return result;
                }
            }else {
                Result result = new Result(-1,"用户名不能为空");
                return result;
            }
        }else {
            Result result = new Result(-1,"ID不能为空");
            return result;
        }

    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Account account){
        if (account.getAccId()!=null) {
            if (account.getAccName()!=null&&!account.getAccName().trim().equals("")){
                if (account.getAccPassword()!=null&&!account.getAccPassword().trim().equals("")) {
                    if (account.getRoleName()!=null&&!account.getRoleName().trim().equals("")) {
                        Result result = as.updateById(account);
                        return result;
                    }
                    Result result = new Result(-1,"角色名不能为空");
                    return result;
                }
                Result result = new Result(-1,"密码不能为空");
                return result;
            }
            Result result = new Result(-1,"用户名不能为空");
            return result;
        }
        Result result = new Result(-1,"ID不能为空");
        return result;
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Long accId){
        if (accId!=null){
            Result result = as.deleteById(accId);
        }
        Result result = new Result(-1,"角色ID不能为空");
        return result;
    }

    @RequestMapping(value = "password",method = RequestMethod.POST)
    @ResponseBody
    public Result changePassword(Long accId,String oldpassword,String newpassword ){
        if (accId!=null){
            if (oldpassword!=null&&!oldpassword.trim().equals("")){
                if (newpassword!=null&&!newpassword.trim().equals("")){
                    Result result = as.changePassword(accId,oldpassword,newpassword);
                    return result;
                }
                Result result = new Result(-1,"新密码不能为空");
                return result;
            }
            Result result = new Result(-1,"旧密码不能为空");
            return result;
        }
        Result result = new Result(-1,"用户id不能为空");
        return result;
    }
}
