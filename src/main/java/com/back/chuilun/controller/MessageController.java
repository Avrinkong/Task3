package com.back.chuilun.controller;

import com.back.chuilun.entity.Message;
import com.back.chuilun.entity.Result;
import com.back.chuilun.service.impl.MessageServiceImpl;
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
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageServiceImpl ms;
    Logger logger = Logger.getLogger(MessageController.class);

    @RequestMapping(value = "find",method = RequestMethod.GET)
    public ModelAndView findMessage(){
        List<Message> all = (List<Message>) ms.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",all);
        mav.setView(new MappingJackson2JsonView());
        //mav.setView(message);
        return mav;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findMessage(String worksName, Integer mstatus){
        if(worksName!=null&&!worksName.trim().equals("")){
            List<Message> all = ms.findAll(worksName,mstatus);
            if (all.size()>0) {
                return new Result(0, "success", all);
            }else {
                return new Result(-1,"false");
            }
        }
        return new Result(-1,"做品名称不能为空或者为空格");
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public Result editMessage(Long messageId){
        if(messageId!=null){
            Result result = ms.editMessage(messageId);
            return result;
        }
        Result result = new Result(-1,"信息ID错误");
        return result;
    }

   @RequestMapping(value = "editstatus",method = RequestMethod.POST)
   @ResponseBody
   public Result editstatus(Long messageId,Integer mstatus){
        if(messageId!=null){
            if (mstatus!=null){
                Result result = ms.updateMstatus(messageId, mstatus);
                return result;
            }
            Result result = new Result(-1,"状态不能为空");
            return result;
        }
       Result result = new Result(-1,"消息id不能为空");
       return result;

   }

   @RequestMapping(value = "delete",method = RequestMethod.POST)
   @ResponseBody
   public Result delete(Long messageId){
        if (messageId!=null){
            Result delete = ms.delete(messageId);
            return delete;
        }
       Result result = new Result(-1,"消息id不能为空");
       return result;
   }

    /**
     * 保存留言回复
     * @param messageId
     * @param spare
     * @return
     */
   @RequestMapping(value = "save",method = RequestMethod.POST)
   @ResponseBody
   public Result save(Long messageId,String spare){
        if (messageId!=null){
            if (spare!=null&&!spare.trim().equals("")){
                Result save = ms.save(messageId, spare);
                return save;
            }
            return new Result(-1,"回复不能为空");
        }
        return new Result(-1,"留言ID不能为空");
   }
}
