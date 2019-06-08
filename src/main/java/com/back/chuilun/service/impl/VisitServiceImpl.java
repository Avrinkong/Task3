package com.back.chuilun.service.impl;

import com.back.chuilun.dao.VisitMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Visit;
import com.back.chuilun.service.VisitService;
import com.back.chuilun.utils.RandomName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitMapper visitMapper;

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
        return null;
    }

    public Visit addByIp(String visitIp) {
       // List<Visit> visitList = new ArrayList<>();
        Date date = new Date();
        long time = date.getTime();
        Random random = new Random();
        int i = random.nextInt(3);
        i=i+2;
        RandomName randomName1 = new RandomName();
        //String s="于屠";
        String s = randomName1.randomName(i);
        List<Visit> list = visitMapper.selectAll();
        if (list.size()>0){
            for (Visit visit:list){
                if (visit.getVisitIp().equals(visitIp)){
                   return visit;
                }
                if (visit.getVisitName().equals(s)){
                    int b = random.nextInt(100);
                    s=s+b;
                }
            }
        }
        Visit visit = new Visit();
        visit.setVisitTime(time);
        visit.setVisitIp(visitIp);
        visit.setVisitName(s);
        visitMapper.insert(visit);
        return visit;
    }
}

