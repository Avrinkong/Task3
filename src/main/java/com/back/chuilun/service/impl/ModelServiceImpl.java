package com.back.chuilun.service.impl;

import com.back.chuilun.dao.ModelMapper;
import com.back.chuilun.entity.Model;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.ModelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result add(Object object) {
        Model model = (Model)object;
        List<Model> models = modelMapper.selectAll();
        for (Model model1:models){
            if (model1.getModelName().equals(model.getModelName())){
                throw new BusinessException("该模块已存在");
            }
        }
        int insert = modelMapper.insert((Model) object);
        if (insert<=0) {
            throw  new BusinessException("添加失败");
        }else if(insert>0){
            return new Result(0,"添加成功",insert);
        }
        throw  new BusinessException("添加异常");
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
        List<Model> models = modelMapper.selectAll();
        if(models!=null){
            return  new Result(0,"进入页面成功",models);
        }else {

            throw  new BusinessException("进入页面失败");
        }
    }

    public List findAll(String modelName) {
        List<Model> list = new ArrayList<>();
        List<Model> models = modelMapper.selectByModelName(modelName);
        //logger.info(messages+"1111111111111111");
        return models;
    }

    public Result updateById(Integer modelId ,String modelName, Integer fatherId, String modelUrl) {
        Model model1 = modelMapper.selectByPrimaryKey(modelId);
        if (model1==null){
            throw new BusinessException("该模块不存在");
        }
        List<Model> models = modelMapper.selectAll();
        for (Model model2:models){
            if (model2.getModelName().equals(modelName)){
                throw new BusinessException("该模块已存在");
            }
        }
        Model model = new Model();
        model.setModelName(modelName);
        model.setFatherid(fatherId);
        model.setModelUrl(modelUrl);
        model.setModelId(modelId);
        int i = modelMapper.updateByPrimaryKey(model);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            throw  new BusinessException("编辑失败");
        }
    }

    public Result deleteModel(Integer modelId) {
        Model model = modelMapper.selectByPrimaryKey(modelId);
        if (model==null){
            throw new BusinessException("该模块不存在");
        }
        int i = modelMapper.deleteByPrimaryKey(modelId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            throw  new BusinessException("删除失败");
        }
    }

    public PageInfo<Model> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Model> bannercontrols = modelMapper.selectAll();
        PageInfo<Model> pageInfo =new PageInfo<>(bannercontrols);
        return pageInfo;
    }
}
