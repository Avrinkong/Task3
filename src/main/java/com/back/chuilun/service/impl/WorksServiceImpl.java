package com.back.chuilun.service.impl;

import com.back.chuilun.dao.WorksMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Works;
import com.back.chuilun.service.WorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    private WorksMapper worksMapper;

    @Override
    public Result add(Object object) {
        Works works = (Works) object;
        //works.setWorksName((String)object);
        works.setWstatus(2);
        Date date=new Date();
        long timestamp=date.getTime();
      /*  SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //long类型转换为string 类型字符串
        String timeText=format.format(timestamp);*/
        works.setWcreateTime(timestamp);
        works.setChangeTime(timestamp);
        int insert = worksMapper.insert(works);
        if (insert==0) {
            return new Result(-1,"添加失败",insert);
        }else if(insert>0){
            return new Result(0,"添加成功",insert);
        }else if (insert<0){
            return new Result(1,"添加失败",insert);
        }
        return new Result(2,"添加异常");
    }

    @Override
    public Result delete(Long number) {
        return null;
    }

    public Result delete(Integer number) {
        int i = worksMapper.deleteByPrimaryKey(number);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }

    @Override
    public Result update(Object object) {
        Works works = (Works) object;
        Date date=new Date();
        long timestamp=date.getTime();
        works.setChangeTime(timestamp);
        int i = worksMapper.updateByPrimaryKey(works);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            return new Result(-1,"编辑失败",i);
        }
    }

    @Override
    public Result findAll() {
        return null;
    }


    public Result findWorks(){
        List<Works> works = worksMapper.selectAll();
        if(works!=null){
            return  new Result(0,"进入页面成功",works);
        }else {

            return new Result(-1,"进入页面失败",works);
        }
    }

    public List findAll(String worksName, Integer wstatus){
        List<Works> list = new ArrayList<>();
        List<Works> works = worksMapper.selectAll();
        //logger.info(messages+"1111111111111111");
        for (Works w:works){
            if(wstatus==null){
                if(w.getWorksName().equals(worksName)){
                    list.add(w);
                }
            }else {
                if(w.getWorksName().equals(worksName)){
                    if (w.getWstatus()==wstatus){
                        list.add(w);
                        // logger.info(message+"22222222222222");
                    }
                }
            }
        }
        return list;
    }

    public Result updateWstatus(Integer worksId,String worksName,Integer wstatus){
        if (worksId==null||worksId<=0){
            return new Result(-1,"id不能为空");
        }
        Works works = worksMapper.selectByPrimaryKey(worksId);

        if(worksName!=null) {
            works.setWorksName(worksName);
        }
        if (works.getWstatus()!=null&&works.getWstatus()!=wstatus){
            if(works.getWstatus()==1){
                works.setWstatus(wstatus);
                Date date=new Date();
                long timestamp=date.getTime();
                works.setChangeTime(timestamp);

                int i = worksMapper.updateByPrimaryKey(works);
                if (i>0){
                    return new Result(0,"下架成功",i);
                }else {
                    return new Result(-1,"下架失败",i);
                }
            }else{
                works.setWstatus(wstatus);
                int i = worksMapper.updateByPrimaryKey(works);
                if (i>0){
                    return new Result(0,"上架成功",i);
                }else {
                    return new Result(-1,"上架失败",i);
                }
            }
        }else{
            if(wstatus==1){
                return new Result(-1,"作品集已上架，不需要上架");
            }else {
                return new Result(-1,"作品集已下架，不需要下架");
            }
        }
    }

    public Works findById(Integer worksId) {
        Works works = worksMapper.selectByPrimaryKey(worksId);
        return works;
    }

    public PageInfo<Works> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Works> works = worksMapper.selectAll();
        PageInfo<Works> pageInfo =new PageInfo<>(works);
        return pageInfo;
    }
}
