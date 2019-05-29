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
        Result works = wcmi.findAll();
        return works;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findportfolio(String portfolioName, Integer pstatus){
        if (portfolioName!=null&&!portfolioName.trim().equals("")){
            if (pstatus!=null){
                List<WorksCllection> all = wcmi.findAll(portfolioName,pstatus);
                if (all.size()>0) {
                    return new Result(0, "success", all);
                }else {
                    return new Result(-1,"false");
                }
            }else {
                return new Result(-1,"作品集上下状态不能为空");
            }
        }else {
            return new Result(-1,"作品集名称不能为空");
        }
    }


    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addPortfolio(String portfolioName){
        if (portfolioName!=null&&!portfolioName.trim().equals("")){
            Result add = wcmi.add(portfolioName);
            return add;
        }else {
            return new Result(-1,"作品集名称不能为空");
        }

    }

    @RequestMapping(value = "/pastatus/update",method = RequestMethod.POST)
    @ResponseBody
    public Result updatePortfolio(Long portfolioId,String portfolioName,Integer pstutsa){
        if (portfolioId!=null){
            if (portfolioName!=null&&!portfolioName.trim().equals("")){
                if (pstutsa!=null){
                    Result result = wcmi.updatePortfolio(portfolioId, portfolioName, pstutsa);
                    return result;
                }else {
                    return new Result(-1,"工作室上下架不能为空");
                }
            }else {
                return new Result(-1,"工作室名称不能为空");
            }
        }else {
            return new Result(-1,"工作室ID不能为空");
        }
    }


    @RequestMapping(value = "sort",method = RequestMethod.POST)
    @ResponseBody
    public Result sortPortfolio(@RequestBody String jsonObject){
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        List<WorksCllection> list = JSON.parseArray(jsonObject1.getString("workscllection"),WorksCllection.class);
        if (list.size()>0){
            Result result = wcmi.sortPortfolio(list);
            return result;
        }else {
            return new Result(-1,"参数不能为空");
        }
    }


    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Long portfolioId,String portfolioName){
        if (portfolioId!=null){
            if (portfolioName!=null&&!portfolioName.trim().equals("")){
                Result result = wcmi.updateById(portfolioId, portfolioName);
                return result;
            }else {
                return new Result(-1,"工作室名称不能为空");
            }
        }else {
            return new Result(-1,"工作室ID不能为空");
        }
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Long portfolioId){
        if (portfolioId!=null){
            Result result = wcmi.deleteById(portfolioId);
            return result;
        }else {
            return new Result(-1,"工作室ID不能为空");
        }
    }
}
