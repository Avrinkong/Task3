package com.back.chuilun.service.impl;

import com.back.chuilun.dao.BannercontrolMapper;
import com.back.chuilun.entity.Bannercontrol;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
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
            throw  new BusinessException("进入页面失败");
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
                    if(b.getBannerEditor().contains(bannerEditor)){
                        list.add(b);
                    }
                }else {
                    if(b.getBannerEditor().contains(bannerEditor)){
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
            throw  new BusinessException("设置失败");
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
        bannercontrol.setBannerEditor("1");
        Date date=new Date();
        long timestamp=date.getTime();
        bannercontrol.setBannerCreatetime(timestamp);
        int insert = bannerMapper.insert(bannercontrol);
        if (insert>0){
            return new Result(0,"添加成功",insert);
        }else {
            throw  new BusinessException("添加失败");
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
            throw  new BusinessException("id不能为空");
        }
        Bannercontrol bannercontrol2 = bannerMapper.selectByPrimaryKey(bannerId);
        if (bannercontrol2==null){
            throw new BusinessException("该banner图不存在！");
        }
        if (bannerStatus==1){
            List<Bannercontrol> bannercontrols = bannerMapper.selectByStatus(bannerStatus);
            if (bannercontrols.size()>5){
                throw  new BusinessException("最多上架6个banner图");
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
                    int j =1;
                    bannerStatus = 1;
                    List<Bannercontrol> bannercontrols = bannerMapper.selectByStatus(bannerStatus);
                    for (Bannercontrol bannercontrol1:bannercontrols){
                        bannercontrol1.setBannerSor(j);
                        j++;
                    }
                    bannerMapper.updateSorByKey(bannercontrols);
                    return new Result(0,"下架成功",i);
                }else {
                    throw  new BusinessException("下架失败");
                }
            }else{
                bannercontrol.setBannerStatus(bannerStatus);
                int i = bannerMapper.updateByPrimaryKey(bannercontrol);
                if (i>0){
                    List<Bannercontrol> bannercontrols = bannerMapper.selectByStatus(bannerStatus);
                    bannercontrol.setBannerSor(bannercontrols.size()+1);
                    bannerMapper.updateByPrimaryKey(bannercontrol);
                    return new Result(0,"上架成功",i);
                }else {
                    throw  new BusinessException("上架失败");
                }
            }
        }else{
            if(bannerStatus==1){
                throw  new BusinessException("作品集已上架，不需要上架");
            }else {
                throw  new BusinessException("作品集已下架，不需要下架");
            }
        }
    }


    public Result deleteById(Integer bannerId) {
        Bannercontrol bannercontrol = bannerMapper.selectByPrimaryKey(bannerId);
        if (bannercontrol==null){
            throw new BusinessException("该banner图不存在！");
        }
        int i = bannerMapper.deleteByPrimaryKey(bannerId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            throw  new BusinessException("删除失败");
        }
    }

    public Result updateById(Integer bannerId, String bannerUrl, String bannerPic) {
        Bannercontrol bannercontro2 = bannerMapper.selectByPrimaryKey(bannerId);
        if (bannercontro2==null){
            throw new BusinessException("该banner图不存在！");
        }
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
            throw  new BusinessException("编辑失败");
        }
    }

    public PageInfo<Bannercontrol> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Bannercontrol> bannercontrols = bannerMapper.selectAll();
        PageInfo<Bannercontrol> pageInfo =new PageInfo<>(bannercontrols);
        return pageInfo;
    }

    public List<Bannercontrol> findByBS(Integer bannerStatus){
        List<Bannercontrol> bannercontrols = bannerMapper.selectByStatus(bannerStatus);

        return bannercontrols;
    }


}
