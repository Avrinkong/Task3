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
        List<Message> all = ms.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",all);
        mav.setView(new MappingJackson2JsonView());
        //mav.setView(message);
        return mav;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findMessage(String worksName, Integer mstatus){
        List<Message> all = ms.findAll(worksName,mstatus);
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("list",all);
        //mav.setView(message);
        return new Result(0,"success",all);
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public Result editMessage(Long messageId){
        Result result = ms.editMessage(messageId);
        return result;
    }

   @RequestMapping(value = "editstatus",method = RequestMethod.POST)
   @ResponseBody
   public Result editstatus(Long messageId,Integer mstatus){
       Result result = ms.updateMstatus(messageId, mstatus);
       return result;
   }

   @RequestMapping(value = "delete",method = RequestMethod.POST)
   @ResponseBody
   public Result delete(Long messageId){
       Result delete = ms.delete(messageId);
       return delete;
   }

   @RequestMapping(value = "save",method = RequestMethod.POST)
   @ResponseBody
   public Result save(Long messageId,String spare){
       Result save = ms.save(messageId, spare);
       return save;
   }
}
