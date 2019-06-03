package com.back.chuilun.service.impl;

import com.back.chuilun.dao.ModelMapper;
import com.back.chuilun.entity.Model;
import com.back.chuilun.entity.Result;
import com.back.chuilun.service.ModelService;
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
        int insert = modelMapper.insert((Model) object);
        if (insert==0) {
            return new Result(-1,"添加失败",insert);
        }else if(insert>0){
            return new Result(0,"添加成功",insert);
        }else if (insert<0){
            return new Result(1,"添加失败",insert);
        }
        return new Result(-1,"添加异常",insert);
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

            return new Result(-1,"进入页面失败",models);
        }
    }

    public List findAll(String modelName) {
        List<Model> list = new ArrayList<>();
        List<Model> models = modelMapper.selectByModelName(modelName);
        //logger.info(messages+"1111111111111111");
        return models;
    }

    public Result updateById(Integer modelId ,String modelName, Integer fatherId, String modelUrl) {
        Model model = new Model();
        model.setModelName(modelName);
        model.setFatherid(fatherId);
        model.setModelUrl(modelUrl);
        model.setModelId(modelId);
        int i = modelMapper.updateByPrimaryKey(model);
        if(i>0){
            return new Result(0,"编辑成功",i);
        }else {
            return new Result(-1,"编辑失败",i);
        }
    }

    public Result deleteModel(Integer modelId) {
        int i = modelMapper.deleteByPrimaryKey(modelId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }
}
