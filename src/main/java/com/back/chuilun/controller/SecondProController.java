package com.back.chuilun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.SecondPor;
import com.back.chuilun.exception.BusinessException;
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
        if (secName!=null&&!secName.trim().equals("")){
            if (secStatus!=null){
                List<SecondPor> all = secondProService.findAll(secName,secStatus);
                if (all.size()>0) {
                    return new Result(0, "success", all);
                }else {
                    throw  new BusinessException("false");
                }
            }else {
                throw  new BusinessException("二级标题状态为空");
            }
        }else {
            throw  new BusinessException("二级标题名不能为空");
        }
    }

    @RequestMapping(value = "sort",method = RequestMethod.POST)
    @ResponseBody
    public Result sortSecondPro(@RequestBody String jsonObject) {
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        List<SecondPor> list = JSON.parseArray(jsonObject1.getString("secondPor"), SecondPor.class);
        if (list.size()>0){
            Result result = secondProService.sortSecondPor(list);
            return result;
        }else {
            throw  new BusinessException("参数不能为空");
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addPortfolio(String secName,String portfolioName){
        if(secName!=null){
            if (portfolioName!=null&&!portfolioName.trim().equals("")){
                Result add = secondProService.add(secName,portfolioName);
                return add;
            }else {
                throw  new BusinessException("导航不能为空");
            }
        }else {
            throw  new BusinessException("二级标题不能为空");
        }
    }

    @RequestMapping(value = "secstatus",method = RequestMethod.POST)
    @ResponseBody
    public Result updatePortfolio(Integer secondPorId,String secName,Integer secStatus){
        if (secondPorId!=null){
            if (secName!=null&&!secName.trim().equals("")){
                if(secStatus!=null&&secStatus>0&&secStatus<3){
                    Result result = secondProService.updateSecondPro(secondPorId, secName, secStatus);
                    return result;
                }else {
                    throw  new BusinessException("二级标题上下架状态错误");
                }
            }else {
                throw  new BusinessException("二级标题名称不能为空");
            }
        }else {
            throw  new BusinessException("二级标题ID不能为空");
        }
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Integer secondPorId,String secName){
        if(secondPorId!=null){
            if (secName!=null&&!secName.trim().equals("")){
                Result result = secondProService.updateById(secondPorId, secName);
                return result;
            }else {
                throw  new BusinessException("二级标题名称不能为空");
            }
        }else {
            throw  new BusinessException("二级标题ID不能为空");
        }
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer secondPorId){
        if (secondPorId!=null){
            Result result = secondProService.deleteById(secondPorId);
            return result;
        }else {
            throw  new BusinessException("二级标题不能为空");
        }
    }
}


