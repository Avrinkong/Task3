package com.back.chuilun.controller;

import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Visit;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.VisitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VisitController {

    @Autowired
    private VisitServiceImpl visitService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(String visitIp){
        if (visitIp!=null){
            Visit visit=visitService.addByIp(visitIp);
            return new Result(0,"载入成功",visit);
        }else {
            throw  new BusinessException("用户ip地址不能为空");
        }
    }
}
