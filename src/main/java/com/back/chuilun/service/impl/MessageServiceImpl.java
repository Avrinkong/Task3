package com.back.chuilun.service.impl;

import com.back.chuilun.dao.MessageMapper;
import com.back.chuilun.entity.Message;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    private Logger logger = Logger.getLogger(MessageServiceImpl.class);

    @Override
    public Result add(Object object) {
        return  new Result();
    }

    @Override
    public Result delete(Long number) {
        int i = messageMapper.deleteByPrimaryKey(number);
        if (i==1){//删除行数为1行，代表删除成功
            return  new Result(0,"删除成功");
        }else {
            throw  new BusinessException("删除失败");
        }
    }

    @Override
    public Result update(Object object) {
        return new Result();
    }

    @Override
    public Result findAll() {
        List<Message> messages = messageMapper.selectAll();
        return new Result(0,"查找成功",messages);
    }

    public Result find(String name){
        List<Message> list = messageMapper.selectByWorksName(name);
        return new Result(0,"根据名称搜索",list);
    }
    /**
     * 先查找全部数据，然后通过比较作品名称和上架状态来筛选出相关对象，然后加入list集合中。
     * @param worksName
     * @param mstatus
     * @return list
     */
    public List findAll(String worksName,Integer mstatus){
        List<Message> list = new ArrayList<>();
        List<Message> messages = messageMapper.selectAll();
        //logger.info(messages+"1111111111111111");
        for (Message message:messages){
            if(mstatus==null){
                if(message.getWorksName().contains(worksName)){
                    list.add(message);
                }
            }else {
                if(message.getWorksName().contains(worksName)){
                    if (message.getMstatus()==mstatus){
                        list.add(message);
                       // logger.info(message+"22222222222222");
                    }
                }
            }
        }
       return list;
    }

    /**
     * 传入messageId然后通过ID查询出该条留言对象，然后获取该对象的留言
     * @param messageId
     * @return
     */
    public Result editMessage(Long messageId){
        Message message = messageMapper.selectByPrimaryKey(messageId);
        //String msg = message.getMessage();
        if(message==null){//如果留言为空
            throw  new BusinessException("没有回复");
        }else {
            return  new Result(0,"回复显示正常",message);
        }

    }

    /**
     * 设置精选留言，若传入mstatus与原数据库相同，返回-1，否则返回0设置成功。
     * @param messageId
     * @param mstatus
     * @return
     */
    public Result updateMstatus(Long messageId, Integer mstatus){
        /*Message ms = new Message();
        ms.setMessageId(messageId);
        ms.setWorksName("测试啊，大佬");*/
        List<Message> list = messageMapper.selectAll();
        if (list.size()<messageId){
            throw  new BusinessException("该用户不存在！");
        }
        Message message = messageMapper.selectByPrimaryKey(messageId);
        if(message.getMstatus()==mstatus){//根据ID查询出的对象状态码和传入对象一致
            if (mstatus==1){//已经为精选留言状态
                throw  new BusinessException("目前为精选留言状态，请勿重复设置");
            }else {//已经为非精选留言状态
                throw  new BusinessException("目前未非精选留言状态，请勿重复设置");
            }
        }else {//查询出对象和传入对象状态码不一致
            message.setMstatus(mstatus); //将将要设置的对象码传入对象
            Date date=new Date();
            long timestamp=date.getTime();
            message.setUpdateTime(timestamp);
        }
        int i = messageMapper.updateByPrimaryKey(message);
        if (i==1) {//更新成功
            return new Result(0, "设置成功");
        }else {//更新失败
            throw  new BusinessException("设置异常");
        }
    }



    public Result save(Long messageId,String spare){
        Message message = messageMapper.selectByPrimaryKey(messageId);
        message.setSpare(spare);
        Date date=new Date();
        long timestamp=date.getTime();
        message.setUpdateTime(timestamp);
        int i = messageMapper.updateByPrimaryKey(message);
        if (i==1) {//更新成功
            return new Result(0, "更新成功");
        }else {//更新失败
            throw  new BusinessException("更新失败");
        }
    }
}
