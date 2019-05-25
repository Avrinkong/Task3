package com.back.chuilun.controller;

import com.back.chuilun.entity.Account;
import com.back.chuilun.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService as;
    private Logger logger = Logger.getLogger(AccountController.class);

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(String username, String password) {
        //logger.info(username+"AAA"+password);
        Account accountByNamePwd = as.findAccountByNamePwd(username, password);
        int accId = Math.toIntExact(accountByNamePwd.getAccId());
         //logger.info("数字是"+accId);
        //String s = JSON.toJSONString(accountByNamePwd);
        ModelAndView model = new ModelAndView();
        if (accId == 1){
            model.addObject("result","true");
            model.addObject(accountByNamePwd);
            //String s = JSON.toJSONString(model);
            model.setViewName("welcome");
            model.setView(new MappingJackson2JsonView());
            return model;
        }else {
            model.addObject("result","false");
            model.setViewName("login");
            model.addObject(accountByNamePwd);
            model.setView(new MappingJackson2JsonView());
            //String s = JSON.toJSONString(model);
            return model;
        }
       //return accId;
    }


}
