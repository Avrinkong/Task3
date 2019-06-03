package com.back.chuilun.service.impl;

import com.back.chuilun.dao.SecondPorMapper;
import com.back.chuilun.dao.WorksCllectionMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.SecondPor;
import com.back.chuilun.entity.WorksCllection;
import com.back.chuilun.service.WorksCllectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorksCllectionServiceImpl implements WorksCllectionService {

    @Autowired
    private WorksCllectionMapper wcm;
    @Autowired
    private SecondPorMapper secondPorMapper;

    @Override
    public Result add(Object object) {
        List<WorksCllection> list = wcm.selectAll();
        if (list.size()>7){
            return new Result(-1,"作品集数量已达上限");
        }else {
            WorksCllection wc= new WorksCllection();
            wc.setPortfolioName((String) object);
            wc.setPstatus(2);
            Date date=new Date();
            long timestamp=date.getTime();
            wc.setPcreateTime(timestamp);
            wc.setPupdateTime(timestamp);
            int insert = wcm.insert(wc);
            if (insert==0) {
                return new Result(-1,"添加失败",insert);
            }else if(insert>0){
                return new Result(0,"添加成功",insert);
            }else if (insert<0){
                return new Result(1,"添加失败",insert);
            }
            return new Result(2,"添加异常");
        }
    }

    @Override
    public Result delete(Long number) {
        return null;
    }

    @Override
    public Result update(Object object) {
        return new Result();
    }

    @Override
    public Result findAll() {
        return null;
    }


    /**
     *
     * @return
     */

    public Result findWorks(){
        List<WorksCllection> worksCllections = wcm.selectAll();
        if(worksCllections!=null){
            return  new Result(0,"进入页面成功",worksCllections);
        }else {

            return new Result(-1,"进入页面失败",worksCllections);
        }

    }

    /**
     * 先查找全部数据，然后通过比较作品名称和上架状态来筛选出相关对象，然后加入list集合中。
     * @param portfolioName
     * @param pstatus
     * @return list
     */
    public List<WorksCllection> findAll(String portfolioName,Integer pstatus){
        List<WorksCllection> list = new ArrayList<>();
        List<WorksCllection> worksCllections = wcm.selectAll();
        for (WorksCllection w:worksCllections){
            if(pstatus==null){
                if(w.getPortfolioName().contains(portfolioName)){
                    list.add(w);
                }
            }else {
                if(w.getPortfolioName().contains(portfolioName)){
                    if (w.getPstatus()==pstatus){
                        list.add(w);
                        // logger.info(message+"22222222222222");
                    }
                }
            }
        }
        return list;
    }

    /**
     * 通过传入的id,名称和状态，来判断是否处于上下架状态，同时修改状态
     * @param portfolioId
     * @param portfolioName
     * @param pstutsa
     * @return
     */
    public Result updatePortfolio(Long portfolioId,String portfolioName,Integer pstutsa){
        if (portfolioId==null||portfolioId<=0){
            return new Result(-1,"id不能为空");
        }
        WorksCllection worksCllection = wcm.selectByPrimaryKey(portfolioId);
        List<SecondPor> secondPors = secondPorMapper.selectAll();
        if (worksCllection.getPortfolioName().equals(portfolioName)){
            if (worksCllection.getPortfolioName()!=null&&secondPors.size()>0) {
                for (SecondPor secondPor:secondPors){
                    if (secondPor.getPortfolioName().equals(portfolioName)){
                        return  new Result(-1,"作品集有有下级作品,请先清空下级标题" );
                    }
                }
            }
        }else {
            return new Result(-1,"id与作品名称不匹配");
        }

        if (worksCllection.getPstatus()!=null&&!worksCllection.getPstatus().equals(pstutsa)){
                if(worksCllection.getPstatus()==1){
                    worksCllection.setPstatus(pstutsa);
                    Date date=new Date();
                    long timestamp=date.getTime();
                    worksCllection.setPupdateTime(timestamp);

                    int i = wcm.updateByPrimaryKey(worksCllection);
                    if (i>0){
                        return new Result(0,"下架成功",i);
                    }else {
                        return new Result(-1,"下架失败",i);
                    }
                }else{
                    worksCllection.setPstatus(pstutsa);
                    int i = wcm.updateByPrimaryKey(worksCllection);
                    if (i>0){
                        return new Result(0,"上架成功",i);
                    }else {
                        return new Result(-1,"上架失败",i);
                    }
                }
            }else{
                if(pstutsa==1){
                    return new Result(-1,"作品集已上架，不需要上架");
                }else {
                    return new Result(-1,"作品集已下架，不需要下架");
                }
            }
    }

    /**
     *通过id和状态对象形成排序
     * @param worksCllection
     * @return
     */
    public Result sortPortfolio(List<WorksCllection> worksCllection){
        int i = wcm.updateSpareByKey(worksCllection);
        if(i>0){
            return new Result(0,"设置成功",i);
        }else {
            return new Result(-1,"设置失败",i);
        }
    }

    /**
     * 通过ID和修改作品集名称
     * @param portfolioId
     * @param portfolioName
     * @return
     */
    public Result updateById(Long portfolioId,String portfolioName){
        WorksCllection worksCllection = new WorksCllection();
        worksCllection.setPortfolioId(portfolioId);
        worksCllection.setPortfolioName(portfolioName);
        int i = wcm.updateByPrimaryKey(worksCllection);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            return new Result(-1,"编辑失败",i);
        }
    }

    public Result deleteById(Long portfolioId){
        List<SecondPor> secondPors = secondPorMapper.selectAll();
        WorksCllection worksCllection = wcm.selectByPrimaryKey(portfolioId);
        for (SecondPor secondPor:secondPors){
            if (secondPor.getPortfolioName().equals(worksCllection.getPortfolioName())){
                return new Result(-1,"作品集下有下级作品,无法删除");
            }else {
                if (worksCllection.getPstatus()==1){
                    return new Result(-1,"作品集处于上架状态,无法删除");
                }
            }
        }
        int i = wcm.deleteByPrimaryKey(portfolioId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }

    /*public List<WorksCllectSecondPor> findByPName() {
        int i = wcm.selectByPName();
        return i;
    }*/

}
