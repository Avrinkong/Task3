package com.back.chuilun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.WorksCllection;
import com.back.chuilun.service.impl.WorksCllectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("portfolio")
public class WorkCllectionController {

    @Autowired
    private WorksCllectionServiceImpl wcmi;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findWorks(){
        Result works = wcmi.findWorks();
        return works;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findportfolio(String portfolioName, Integer pstatus){
        List<WorksCllection> all = wcmi.findAll(portfolioName,pstatus);
        return new Result(0,"success",all);
    }


    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addPortfolio(String portfolioName){
        Result add = wcmi.add(portfolioName);
        return add;
    }

    @RequestMapping(value = "/pastatus/update",method = RequestMethod.POST)
    @ResponseBody
    public Result updatePortfolio(Long portfolioId,String portfolioName,Integer pstutsa){
        Result result = wcmi.updatePortfolio(portfolioId, portfolioName, pstutsa);
        return result;
    }



    @RequestMapping(value = "sort",method = RequestMethod.POST)
    @ResponseBody
    public Result sortPortfolio(@RequestBody String jsonObject){// List<Integer> portfolioSpares, List<Long> portfolioIds
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        List<WorksCllection> list = JSON.parseArray(jsonObject1.getString("workscllection"),WorksCllection.class);
        Result result = wcmi.sortPortfolio(list);
        return result;
    }


    public Result updateById(Integer portfolio_id,String portfolio_name){
        wcmi.updateById(portfolio_id,portfolio_name);
    }
}
