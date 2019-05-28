package com.back.chuilun.service;

import com.back.chuilun.entity.Result;

public interface BaseService {
    Result add(Object object);

    Result delete(Long number);

    Result update(Object object);

     Result findAll();

}
