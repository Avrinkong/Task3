package com.back.chuilun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.SecondPor;
import com.back.chuilun.service.impl.SecondProServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("secportfolio")
public class SecondProController {

    @Autowired
    private SecondProServiceImpl secondProService;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findAll(){
        Result all = secondProService.findAll();
        return all;
    }


    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findSecondPro(String secName, Integer secStatus){
        List<SecondPor> all = secondProService.findAll(secName,secStatus);
        return new Result(0,"success",all);
    }

    @RequestMapping(value = "sort",method = RequestMethod.POST)
    @ResponseBody
    public Result sortSecondPro(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        List<SecondPor> list = JSON.parseArray(jsonObject1.getString("secondPor"), SecondPor.class);
        Result result = secondProService.sortSecondPor(list);


        return result;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addPortfolio(String secName,String portfolioName){
        Result add = secondProService.add(secName,portfolioName);
        return add;
    }

    @RequestMapping(value = "secstatus",method = RequestMethod.POST)
    @ResponseBody
    public Result updatePortfolio(Integer secondPorId,String secName,Integer secStatus){
        Result result = secondProService.updateSecondPro(secondPorId, secName, secStatus);
        return result;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Integer secondPorId,String secName){
        Result result = secondProService.updateById(secondPorId, secName);
        return result;
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer secondPorId){
        Result result = secondProService.deleteById(secondPorId);
        return result;
    }
}


