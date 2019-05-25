package com.back.chuilun.service;

import com.back.chuilun.entity.Result;

import java.util.List;

public interface BaseService {
    Result add(Object object);

    Result delete(Long number);

    Result update(Object object);

     List findAll();

}
