package com.back.chuilun.controller;

import com.back.chuilun.entity.Model;
import com.back.chuilun.entity.Result;
import com.back.chuilun.service.impl.ModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("model")
public class ModelController {

    @Autowired
    private ModelServiceImpl modelService;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findModel(){
        Result all = modelService.findAll();
        return all;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findModel(String modelName){
        if (modelName!=null&&!modelName.trim().equals("")){
            List all = modelService.findAll(modelName);
            if (all.size()>0) {
                return new Result(0, "查询成功", all);
            }else {
                return new Result(-1,"查询失败");
            }
        }
       return new Result(-1,"模块名称不能为空");
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addModel(String modelName,Integer fatherId,String modelUrl){

        Model model = new Model();
        if (modelName!=null&&!modelName.trim().equals("")){
            model.setModelName(modelName);
        }else {
            return new Result(-1,"模块名称不能为空");
        }
        if (fatherId!=null){
            model.setFatherid(fatherId);
        }else {
            return new Result(-1,"父节点不能为空");
        }
        if (modelUrl!=null&&!modelUrl.trim().equals("")){
            model.setModelUrl(modelUrl);
        }else {
            return new Result(-1,"模块连接不能为空");
        }
        Result add = modelService.add(model);
        return add;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Integer modelId ,String modelName,Integer fatherId,String modelUrl){
        if (modelId!=null){
            if (modelName!=null&&!modelName.trim().equals("")){
                if (fatherId!=null){
                    if (modelUrl!=null&&!modelUrl.trim().equals("")){
                        Result result = modelService.updateById(modelId,modelName,fatherId,modelUrl);
                        return result;
                    }else {
                        return new Result(-1,"模块连接不能为空");
                    }
                }else {
                    return new Result(-1,"父节点不能为空");
                }
            }else {
                return new Result(-1,"模块名称不能为空");
            }
        }else {
            return new Result(-1,"模块ID不能为空");
        }
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteModel(Integer modelId ){
        if (modelId!=null){
            Result result = modelService.deleteModel(modelId);
            return result;
        }
        return new Result(-1,"模块id不能为空");
    }

}
