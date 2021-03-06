package com.back.chuilun.service.impl;

import com.back.chuilun.dao.SecondPorMapper;
import com.back.chuilun.dao.WorksCllectionMapper;
import com.back.chuilun.dao.WorksMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.SecondPor;
import com.back.chuilun.entity.Works;
import com.back.chuilun.entity.WorksCllection;
import com.back.chuilun.exception.BusinessException;
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
    @Autowired
    private WorksCllectionMapper worksCllectionMapper;

    public Result add(String secName,String portfolioName) {
        List<SecondPor> secondPors = secondPorMapper.selectAll();
        List<WorksCllection> list=new ArrayList<>();
        for (SecondPor s:secondPors){
            if (s.getSecName().equals(secName)){
                   throw new BusinessException("该作品已经存在");
            }
        }
        List<WorksCllection> worksCllections = worksCllectionMapper.selectAll();
        for (WorksCllection w:worksCllections){
            if (w.getPortfolioName().equals(portfolioName)){
               list.add(w);
            }
        }
        if (list.size()<=0){
            throw new BusinessException("该作品集名称不存在");
        }
        if (secondPors.size()>=7){
            throw  new BusinessException("二级导航数量已达上限");
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
                throw  new BusinessException("添加失败");
            }else if(insert>0){
                return new Result(0,"添加成功",insert);
            }else {
                throw  new BusinessException("添加失败");
            }
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


    public List<SecondPor> findAll(String secName,Integer secStatus){
        List<SecondPor> list = new ArrayList<>();
        List<SecondPor> sps = secondPorMapper.selectAll();
        //logger.info(messages+"1111111111111111");
        for (SecondPor sp:sps){
            if(secStatus==null){
                if(sp.getSecName().contains(secName)){
                    list.add(sp);
                }
            }else {
                if(sp.getSecName().contains(secName)){
                    if (sp.getSecStatus().equals(secStatus)){
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
            throw  new BusinessException("设置失败");
        }
    }


    public Result updateSecondPro(Integer secondPorId,String secName,Integer secStatus){
        if (secondPorId==null||secondPorId<=0){
            throw  new BusinessException("id不能为空");
        }
        SecondPor secondPor = secondPorMapper.selectByPrimaryKey(secondPorId);
        if (secondPor==null){
            throw new BusinessException("该用户不存在");
        }
        List<Works> works = worksMapper.selectAll();
        if (secondPor.getSecName().equals(secName)) {
            if (secondPor.getSecName()!=null&&works.size()>0) {
                for (Works works1:works){
                    if (works1.getSecondPorName().equals(secName)){
                        throw  new BusinessException("作品集有有下级作品,请先清空下级标题" );
                    }
                }
            }
        }else {
            throw  new BusinessException("id与作品名称不匹配");
        }
        if (secondPor.getSecStatus()!=null&&!secondPor.getSecStatus().equals(secStatus)){
            if(secondPor.getSecStatus()==1){
                secondPor.setSecStatus(secStatus);
                Date date=new Date();
                long timestamp=date.getTime();
                secondPor.setSecUpdatetime(timestamp);

                int i = secondPorMapper.updateByPrimaryKey(secondPor);
                if (i>0){
                    int j =1;
                    secStatus = 1;
                    List<SecondPor> secondPors = secondPorMapper.selectByStatus(secStatus);
                    for (SecondPor a:secondPors){
                        a.setSecSpare(j);
                        j++;
                    }
                    secondPorMapper.updateSpareByKey(secondPors);
                    return new Result(0,"下架成功",i);
                }else {
                    throw  new BusinessException("下架失败");
                }
            }else{
                secondPor.setSecStatus(secStatus);
                Date date=new Date();
                long timestamp=date.getTime();
                secondPor.setSecUpdatetime(timestamp);
                int i = secondPorMapper.updateByPrimaryKey(secondPor);
                if (i>0){
                    List<SecondPor> secondPors = secondPorMapper.selectByStatus(secStatus);
                    secondPor.setSecSpare(secondPors.size());
                    secondPorMapper.updateByPrimaryKey(secondPor);
                    return new Result(0,"上架成功",i);
                }else {
                    throw  new BusinessException("上架失败");
                }
            }
        }else{
            if(secStatus==1){
                throw  new BusinessException("作品集已上架，不需要上架");
            }else {
                throw  new BusinessException("作品集已下架，不需要下架");
            }
        }
    }

    public Result updateById(Integer secondPorId,String secName){
        SecondPor secondPor1 = secondPorMapper.selectByPrimaryKey(secondPorId);
        if (secondPor1==null){
            throw new BusinessException("该用户不存在");
        }

        List<Works> list = worksMapper.selectBySName(secondPor1.getSecName());
        if (list.size()>0){
            for (Works w :list){
                w.setSecondPorName(secName);
                int i1 = worksMapper.updateByPrimaryKey(w);
                if(i1<0){
                    throw  new BusinessException("更新失败");
                }
            }
        }

        SecondPor secondPor = new SecondPor();
        secondPor.setSecondPorId(secondPorId);
        secondPor.setSecName(secName);
        int i = secondPorMapper.updateByPrimaryKey(secondPor);
        if(i>0){
            return new Result(0,"更新成功",i);
        }else {
            throw  new BusinessException("编辑失败");
        }
    }

    public Result deleteById(Integer secondPorId){
        SecondPor secondPor = secondPorMapper.selectByPrimaryKey(secondPorId);
        if (secondPor==null){
            throw new BusinessException("该用户不存在");
        }

        List<Works> works = worksMapper.selectAll();
        SecondPor secondPor1 = secondPorMapper.selectByPrimaryKey(secondPorId);
        for (Works works1:works){
            if (works1.getSecondPorName().equals(secondPor1.getSecName())){
                throw  new BusinessException("二级导航下有下级作品,无法删除");
            }else {
                if (secondPor1.getSecStatus()==1){
                    throw  new BusinessException("二级导航处于上架状态,无法删除");
                }
            }
        }
        int i = secondPorMapper.deleteByPrimaryKey(secondPorId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            throw  new BusinessException("删除失败");
        }
    }
}
