package com.back.chuilun.controller;

import com.back.chuilun.entity.Message;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.MessageServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageServiceImpl ms;
    Logger logger = Logger.getLogger(MessageController.class);

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findMessage(){
        Result all = ms.findAll();
        return all;
    }

    @RequestMapping(value = "findbyname",method = RequestMethod.POST)
    @ResponseBody
    public Result findMessageByName(String name){
      //  name="%"+name+"%";
        Result all = ms.find(name);
        return all;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findMessage(String worksName, Integer mstatus){
        if(worksName!=null&&!worksName.trim().equals("")){
           // worksName="%"+worksName+"%";
            List<Message> all = ms.findAll(worksName,mstatus);
            if (all.size()>0) {
                return new Result(0, "success", all);
            }else {
                throw  new BusinessException("false");
            }
        }
        Result all = ms.findAll();
        List<Message> list = new ArrayList<>();
        if (mstatus!=null){
            List<Message> data = (List<Message>) all.getData();
            for (Message studio:data){
                if (studio.getMstatus().equals(mstatus)){
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

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public Result editMessage(Long messageId){
        if(messageId!=null){
            Result result = ms.editMessage(messageId);
            return result;
        }
        throw  new BusinessException("信息ID错误");
    }

   @RequestMapping(value = "editstatus",method = RequestMethod.POST)
   @ResponseBody
   public Result editstatus(Long messageId,Integer mstatus){
        if(messageId!=null){
            if (mstatus!=null&&mstatus>0&&mstatus<3){
                Result result = ms.updateMstatus(messageId, mstatus);
                return result;
            }
            throw  new BusinessException("状态输入错误");
        }
       throw  new BusinessException("消息id不能为空");

   }

   @RequestMapping(value = "delete",method = RequestMethod.POST)
   @ResponseBody
   public Result delete(Long messageId){
        if (messageId!=null){
            Result delete = ms.delete(messageId);
            return delete;
        }
       throw  new BusinessException("消息id不能为空");
   }

    /**
     * 保存留言回复
     * @param messageId
     * @param spare 管理员名称
     * @return
     */
   @RequestMapping(value = "save",method = RequestMethod.POST)
   @ResponseBody
   public Result save(Long messageId,String spare){
        if (messageId!=null){
            if (spare!=null&&!spare.trim().equals("")){
                if (spare.length()<=150){
                    Result save = ms.save(messageId, spare);
                    return save;
                }else {
                    throw  new BusinessException("留言不能超过150个字");
                }
            }else {
                throw  new BusinessException("回复不能为空");
            }
        }else {
            throw  new BusinessException("留言ID不能为空");
        }
   }
}
