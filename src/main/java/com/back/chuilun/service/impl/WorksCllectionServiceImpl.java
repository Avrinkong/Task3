package com.back.chuilun.service.impl;

import com.back.chuilun.dao.WorksCllectionMapper;
import com.back.chuilun.entity.Result;
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

    @Override
    public Result add(Object object) {
        WorksCllection wc= new WorksCllection();
        wc.setPortfolioName((String) object);
        wc.setPstatus(2);
        Date date=new Date();
        long timestamp=date.getTime();
      /*  SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //long类型转换为string 类型字符串
        String timeText=format.format(timestamp);*/
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
    public List findAll(String portfolioName,Integer pstatus){
        List<WorksCllection> list = new ArrayList<>();
        List<WorksCllection> worksCllections = wcm.selectAll();
        //logger.info(messages+"1111111111111111");
        for (WorksCllection w:worksCllections){
            if(pstatus==null){
                if(w.getPortfolioName().equals(portfolioName)){
                    list.add(w);
                }
            }else {
                if(w.getPortfolioName().equals(portfolioName)){
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

        if(portfolioName!=null) {
            worksCllection.setPortfolioName(portfolioName);
        }
        if (worksCllection.getPstatus()!=null&&worksCllection.getPstatus()!=pstutsa){
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
        int i = wcm.deleteByPrimaryKey(portfolioId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }
}
