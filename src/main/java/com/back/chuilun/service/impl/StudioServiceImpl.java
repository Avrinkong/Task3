package com.back.chuilun.service.impl;

import com.back.chuilun.dao.StudioMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Studio;
import com.back.chuilun.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioMapper studioMapper;

    @Override
    public Result add(Object object) {
        Studio studio = (Studio) object;
        Date date=new Date();
        long timestamp=date.getTime();
        studio.setStudioCreatetime(timestamp);
        studio.setStudioUpdatetime(timestamp);
        int insert = studioMapper.insert(studio);
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
        return null;
    }

    @Override
    public Result findAll() {
        List<Studio> studios = studioMapper.selectAll();
        if(studios!=null){
            return  new Result(0,"进入页面成功",studios);
        }else {

            return new Result(-1,"进入页面失败",studios);
        }
    }

    /**
     * 根据状态和名称查找工作室
     * @param studioName
     * @param studioStatus
     * @return
     */
    public List<Studio> findAll(String studioName, Integer studioStatus) {
        List<Studio> list = new ArrayList<>();
        List<Studio> studios = studioMapper.selectAll();
        //logger.info(messages+"1111111111111111");
        for (Studio s:studios){
            if(studioStatus==null){
                if(s.getStudioName().equals(studioName)){
                    list.add(s);
                }
            }else {
                if (s.getStudioName()!=null) {
                    if (s.getStudioName().equals(studioName)) {
                        if (s.getStudioStatus() == studioStatus) {
                            list.add(s);
                            // logger.info(message+"22222222222222");
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 根据ID来编辑工作室模块
     * @param studioId
     * @param studioName
     * @param studioPicture
     * @param studioText
     * @param studioEditor
     * @return
     */

    public Result updateById(Integer studioId,String studioName,String studioPicture,String studioText,String studioEditor) {
        Studio studio = studioMapper.selectByPrimaryKey(studioId);
        studio.setStudioId(studioId);
        if (studioName!=null){
            studio.setStudioName(studioName);
        }
        if (studioPicture!=null){
            studio.setStudioPicture(studioPicture);
        }
        if (studioText!=null){
            studio.setStudioText(studioText);
        }
        if (studioEditor!=null){
            studio.setStudioEditor(studioEditor);
        }
        Date date=new Date();
        long timestamp=date.getTime();
        studio.setStudioUpdatetime(timestamp);
        int i = studioMapper.updateByPrimaryKey(studio);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            return new Result(-1,"编辑失败",i);
        }
    }

    /**
     * 通过id和当前状态进行上下架修改
     * @param studioId
     * @param studioStutsa
     * @return
     */

    public Result updateStudioStatus(Integer studioId, Integer studioStutsa) {
        if (studioId == null || studioId <= 0) {
            return new Result(-1, "id不能为空");
        }
        Studio studio = studioMapper.selectByPrimaryKey(studioId);


        if (studio.getStudioStatus() != null && studio.getStudioStatus() != studioStutsa) {
            if (studio.getStudioStatus() == 1) {
                studio.setStudioStatus(studioStutsa);
                Date date = new Date();
                long timestamp = date.getTime();
                studio.setStudioUpdatetime(timestamp);

                int i = studioMapper.updateByPrimaryKey(studio);
                if (i > 0) {
                    return new Result(0, "下架成功", i);
                } else {
                    return new Result(-1, "下架失败", i);
                }
            } else {
                studio.setStudioStatus(studioStutsa);
                int i = studioMapper.updateByPrimaryKey(studio);
                if (i > 0) {
                    return new Result(0, "上架成功", i);
                } else {
                    return new Result(-1, "上架失败", i);
                }
            }
        } else {
            if (studio.getStudioStatus() == null) {
                studio.setStudioStatus(studioStutsa);
                Date date = new Date();
                long timestamp = date.getTime();
                studio.setStudioUpdatetime(timestamp);
                int i = studioMapper.updateByPrimaryKey(studio);
                if (i > 0) {
                    return new Result(0, "设置成功", i);
                } else {
                    return new Result(-1, "设置失败", i);
                }
            }else {
                if (studioStutsa == 1) {
                    return new Result(-1, "作品集已上架，不需要上架");
                } else if (studioStutsa == 2) {
                    return new Result(-1, "作品集已下架，不需要下架");
                }
            }
        }
        return new Result(-1,"未知错误");
    }
}
