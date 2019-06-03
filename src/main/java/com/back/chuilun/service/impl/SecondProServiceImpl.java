package com.back.chuilun.service.impl;

import com.back.chuilun.dao.SecondPorMapper;
import com.back.chuilun.dao.WorksMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.SecondPor;
import com.back.chuilun.entity.Works;
import com.back.chuilun.service.SecondProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecondProServiceImpl implements SecondProService {

    @Autowired
    private SecondPorMapper secondPorMapper;
    @Autowired
    private WorksMapper worksMapper;

    public Result add(String secName,String portfolioName) {
        List<SecondPor> secondPors = secondPorMapper.selectAll();
        if (secondPors.size()>7){
            return new Result(-1,"二级导航数量已达上限");
        }else {

            SecondPor secondPor = new SecondPor();
            //secondPor.setPortfolioName("默认导航");
            secondPor.setSecName(secName);
            secondPor.setSecStatus(2);
            secondPor.setPortfolioName(portfolioName);
            Date date=new Date();
            long timestamp=date.getTime();

            secondPor.setSecCreatetime(timestamp);
            secondPor.setSecUpdatetime(timestamp);
            int insert = secondPorMapper.insert(secondPor);
            if (insert==0) {
                return new Result(-1,"添加失败",insert);
            }else if(insert>0){
                return new Result(0,"添加成功",insert);
            }else if (insert<0){
                return new Result(1,"添加失败",insert);
            }
            return new Result(2, "添加异常");
        }
    }

    @Override
    public Result add(Object object) {
        return null;
    }

    @Override
    public Result delete(Long number) {
        return null;
    }

    @Override
    public Result update(Object object) {
        return null;
    }

    @Override
    public Result findAll() {
        List<SecondPor> secondPors = secondPorMapper.selectAll();
        return new Result(0,"查询成功",secondPors);
    }


    public List findAll(String secName,Integer secStatus){
        List<SecondPor> list = new ArrayList<>();
        List<SecondPor> sps = secondPorMapper.selectAll();
        //logger.info(messages+"1111111111111111");
        for (SecondPor sp:sps){
            if(secStatus==null){
                if(sp.getSecName().equals(secName)){
                    list.add(sp);
                }
            }else {
                if(sp.getPortfolioName().equals(secName)){
                    if (sp.getSecStatus()==secStatus){
                        list.add(sp);
                        // logger.info(message+"22222222222222");
                    }
                }
            }
        }
        return list;
    }


    public Result sortSecondPor(List<SecondPor> secondPor){
        int i = secondPorMapper.updateSpareByKey(secondPor);
        if(i>0){
            return new Result(0,"设置成功",i);
        }else {
            return new Result(-1,"设置失败",i);
        }
    }


    public Result updateSecondPro(Integer secondPorId,String secName,Integer secStatus){
        if (secondPorId==null||secondPorId<=0){
            return new Result(-1,"id不能为空");
        }
        SecondPor secondPor = secondPorMapper.selectByPrimaryKey(secondPorId);
        List<Works> works = worksMapper.selectAll();
        if (secondPor.getSecName().equals(secName)) {
            if (secondPor.getSecName()!=null&&works.size()>0) {
                for (Works works1:works){
                    if (works1.getSecondPorName().equals(secName)){
                        return  new Result(-1,"作品集有有下级作品,请先清空下级标题" );
                    }
                }
            }
        }else {
            return new Result(-1,"id与作品名称不匹配");
        }
        if (secondPor.getSecStatus()!=null&&!secondPor.getSecStatus().equals(secStatus)){
            if(secondPor.getSecStatus()==1){
                secondPor.setSecStatus(secStatus);
                Date date=new Date();
                long timestamp=date.getTime();
                secondPor.setSecUpdatetime(timestamp);

                int i = secondPorMapper.updateByPrimaryKey(secondPor);
                if (i>0){
                    return new Result(0,"下架成功",i);
                }else {
                    return new Result(-1,"下架失败",i);
                }
            }else{
                secondPor.setSecStatus(secStatus);
                Date date=new Date();
                long timestamp=date.getTime();
                secondPor.setSecUpdatetime(timestamp);
                int i = secondPorMapper.updateByPrimaryKey(secondPor);
                if (i>0){
                    return new Result(0,"上架成功",i);
                }else {
                    return new Result(-1,"上架失败",i);
                }
            }
        }else{
            if(secStatus==1){
                return new Result(-1,"作品集已上架，不需要上架");
            }else {
                return new Result(-1,"作品集已下架，不需要下架");
            }
        }
    }

    public Result updateById(Integer secondPorId,String secName){
        SecondPor secondPor = new SecondPor();
        secondPor.setSecondPorId(secondPorId);
        secondPor.setSecName(secName);
        int i = secondPorMapper.updateByPrimaryKey(secondPor);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            return new Result(-1,"编辑失败",i);
        }
    }

    public Result deleteById(Integer secondPorId){
        List<Works> works = worksMapper.selectAll();
        SecondPor secondPor1 = secondPorMapper.selectByPrimaryKey(secondPorId);
        for (Works works1:works){
            if (works1.getSecondPorName().equals(secondPor1.getSecName())){
                return new Result(-1,"二级导航下有下级作品,无法删除");
            }else {
                if (secondPor1.getSecStatus()==1){
                    return new Result(-1,"二级导航处于上架状态,无法删除");
                }
            }
        }
        int i = secondPorMapper.deleteByPrimaryKey(secondPorId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }
}
