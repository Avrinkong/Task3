package com.back.chuilun.controller;

import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Studio;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.StudioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("studio")
public class StudioController {

    @Autowired
    private StudioServiceImpl studioService;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findStudio(){
        Result all = studioService.findAll();
        return all;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findStudio(String studioName, Integer studioStatus){
        if (studioName!=null&&!studioName.trim().equals("")){
            List<Studio> all = studioService.findAll(studioName, studioStatus);
            if (all.size()>0) {
                return new Result(0, "查询成功", all);
            }else {
                return new Result(-1,"该工作室不存在");
            }
        }else {
            Result all = studioService.findAll();
            List<Studio> list = new ArrayList<>();
            if (studioStatus!=null){
                List<Studio> data = (List<Studio>) all.getData();
                for (Studio studio:data){
                    if (studio.getStudioStatus()==studioStatus){
                        list.add(studio);
                    }
                }
                if (list.size()<=0){
                    throw new BusinessException("该工作室不存在");
                }
                all.setData(list);
            }
            return all;
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addStudio(String studioName,String studioPicture,String studioText,String studioEditor){
        if (studioName!=null&&!studioName.trim().equals("")){
            if (studioPicture!=null&&!studioPicture.trim().equals("")){
                if(studioText!=null&&!studioText.trim().equals("")){
                    if (studioEditor!=null&&!studioEditor.trim().equals("")){
                        Studio studio = new Studio();
                        studio.setStudioName(studioName);
                        studio.setStudioPicture(studioPicture);
                        studio.setStudioText(studioText);
                        studio.setStudioEditor(studioEditor);
                        Result add = studioService.add(studio);
                        return add;
                    }else {
                        throw  new BusinessException("工作室编辑人不能为空");
                    }
                }else {
                    throw  new BusinessException("工作室文本不能为空");
                }
            }else {
                throw  new BusinessException("工作室照片不能为空");
            }
        }else {
            throw  new BusinessException("工作室名称不能为空");
        }

    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Integer studioId,String studioName,String studioPicture,String studioText,String studioEditor){
        if (studioId!=null){
            if (studioName!=null&&!studioName.trim().equals("")){
                if (studioPicture!=null&&!studioPicture.trim().equals("")){
                    if(studioText!=null&&!studioText.trim().equals("")){
                        if (studioEditor!=null&&!studioEditor.trim().equals("")){
                            Result result = studioService.updateById(studioId,studioName,studioPicture,studioText,studioEditor);
                            return result;
                        }else {
                            throw  new BusinessException("工作室编辑人不能为空");
                        }
                    }else {
                        throw  new BusinessException("工作室文本不能为空");
                    }
                }else {
                    throw  new BusinessException("工作室照片不能为空");
                }
            }else {
                throw  new BusinessException("工作室名称不能为空");
            }
        }else {
            throw  new BusinessException("工作室ID不能为空");
        }
    }

    @RequestMapping(value = "status",method = RequestMethod.POST)
    @ResponseBody
    public Result updatePortfolio(Integer studioId,Integer studioStutsa){
        if (studioId!=null){
            if (studioStutsa!=null&&studioStutsa>0&&studioStutsa<3){
                Result result = studioService.updateStudioStatus(studioId, studioStutsa);
                return result;
            }else {
                throw  new BusinessException("工作室上下架状态错误");
            }
        }else {
            throw  new BusinessException("工作室ID不能为空");
        }
    }
}
