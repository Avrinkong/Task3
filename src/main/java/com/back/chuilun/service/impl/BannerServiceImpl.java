package com.back.chuilun.service.impl;

import com.back.chuilun.dao.BannercontrolMapper;
import com.back.chuilun.entity.Bannercontrol;
import com.back.chuilun.entity.Result;
import com.back.chuilun.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannercontrolMapper bannerMapper;


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

    /**
     * 查找所有BANNER图
     * @return
     */
    @Override
    public Result findAll() {
        List<Bannercontrol> bannercontrols = bannerMapper.selectAll();
        if(bannercontrols!=null){
            return  new Result(0,"进入页面成功",bannercontrols);
        }else {
            return new Result(-1,"进入页面失败",bannercontrols);
        }
    }

    /**
     * 根据传入的上架状态和编辑人搜索banner
     * @param bannerStatus
     * @param bannerEditor
     * @return
     */
    public List findAll(Integer bannerStatus, String bannerEditor){
        List<Bannercontrol> list = new ArrayList<>();
        List<Bannercontrol> bannercontrols = bannerMapper.selectByBanner(bannerStatus);
        //logger.info(messages+"1111111111111111");
        if (bannercontrols!=null){
            for (Bannercontrol b:bannercontrols){
                if(bannerStatus==null){
                    if(b.getBannerEditor().equals(bannerEditor)){
                        list.add(b);
                    }
                }else {
                    if(b.getBannerEditor().equals(bannerEditor)){
                        if (b.getBannerStatus()==bannerStatus){
                            list.add(b);
                            // logger.info(message+"22222222222222");
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 排序
     * @param list
     * @return
     */
    public Result sortBanner(List<Bannercontrol> list) {
        int i = bannerMapper.updateSorByKey(list);
        if(i>0){
            return new Result(0,"设置成功",i);
        }else {
            return new Result(-1,"设置失败",i);
        }
    }

    /**
     * 通过bannerUrl，bannerPic添加文件
     * @param bannerUrl
     * @param bannerPic
     * @return
     */
    public Result add(String bannerUrl, String bannerPic) {
        Bannercontrol bannercontrol = new Bannercontrol();
        bannercontrol.setBannerUrl(bannerUrl);
        bannercontrol.setBannerPic(bannerPic);
        bannercontrol.setBannerStatus(2);
        Date date=new Date();
        long timestamp=date.getTime();
        bannercontrol.setBannerCreatetime(timestamp);
        int insert = bannerMapper.insert(bannercontrol);
        if (insert>0){
            return new Result(0,"添加成功",insert);
        }else {
            return new Result(-1,"添加失败",insert);
        }
    }

    /**
     * 根据bannerID和banner状态 修改banner上下架状态
     * @param bannerId
     * @param bannerStatus
     * @return
     */
    public Result updateBannerStatus(Integer bannerId, Integer bannerStatus) {
        if (bannerId==null||bannerId<=0){
            return new Result(-1,"id不能为空");
        }
        //bannerMapper.selectAll(bannerStatus);
        if (bannerStatus==1){
            List<Bannercontrol> bannercontrols = bannerMapper.selectByStatus(bannerStatus);
            if (bannercontrols.size()>5){
                return new Result(-1,"最多上架6个banner图");
            }
        }
        Bannercontrol bannercontrol = bannerMapper.selectByPrimaryKey(bannerId);

        if (bannercontrol.getBannerStatus()!=null&&bannercontrol.getBannerStatus()!=bannerStatus){
            if(bannercontrol.getBannerStatus()==1){
                bannercontrol.setBannerStatus(bannerStatus);
                Date date=new Date();
                long timestamp=date.getTime();
                bannercontrol.setBannerUpdatetime(timestamp);

                int i = bannerMapper.updateByPrimaryKey(bannercontrol);
                if (i>0){
                    return new Result(0,"下架成功",i);
                }else {
                    return new Result(-1,"下架失败",i);
                }
            }else{
                bannercontrol.setBannerStatus(bannerStatus);
                int i = bannerMapper.updateByPrimaryKey(bannercontrol);
                if (i>0){
                    return new Result(0,"上架成功",i);
                }else {
                    return new Result(-1,"上架失败",i);
                }
            }
        }else{
            if(bannerStatus==1){
                return new Result(-1,"作品集已上架，不需要上架");
            }else {
                return new Result(-1,"作品集已下架，不需要下架");
            }
        }
    }


    public Result deleteById(Integer bannerId) {
        int i = bannerMapper.deleteByPrimaryKey(bannerId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }

    public Result updateById(Integer bannerId, String bannerUrl, String bannerPic) {
        Bannercontrol bannercontrol = new Bannercontrol();
        Date date=new Date();
        long timestamp=date.getTime();
        bannercontrol.setBannerUpdatetime(timestamp);
        bannercontrol.setBannerId(bannerId);
        bannercontrol.setBannerUrl(bannerUrl);
        bannercontrol.setBannerPic(bannerPic);
        int i = bannerMapper.updateByPrimaryKey(bannercontrol);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            return new Result(-1,"编辑失败",i);
        }
    }

    public PageInfo<Bannercontrol> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Bannercontrol> bannercontrols = bannerMapper.selectAll();
        PageInfo<Bannercontrol> pageInfo =new PageInfo<>(bannercontrols);
        return pageInfo;
    }

}
