package com.back.chuilun.service.impl;

import com.back.chuilun.dao.RoleMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Role;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result add(Object object) {
        Role role = (Role) object;
        Date date=new Date();
        long timestamp=date.getTime();
        role.setRoleCreatetime(timestamp);
        int i = roleMapper.insert(role);
        if(i>0){
            return new Result(0,"添加成功",i);
        }else {
            throw  new BusinessException("添加失败");
        }
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
        List<Role> roles = roleMapper.selectAll();
        if(roles.size()>0){
            return  new Result(0,"进入页面成功",roles);
        }else {

            throw  new BusinessException("进入页面失败");
        }
    }

    public List findAll(String roleName) {
        List<Role> roles = roleMapper.selectByRoleName(roleName);
        //logger.info(messages+"1111111111111111");
        return roles;
    }

    public Result updateById(Role role) {
        Date date=new Date();
        long timestamp=date.getTime();
        role.setRoleCreatetime(timestamp);
        int i = roleMapper.updateByPrimaryKey(role);
        if (i>0){
            return new Result(0,"角色编辑成功",i);
        }else {
            throw  new BusinessException("角色编辑失败");
        }
    }

    public Result deleteById(Integer roleId) {
        int i = roleMapper.deleteByPrimaryKey(roleId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            throw  new BusinessException("角色删除失败");
        }
    }

    public PageInfo<Role> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Role> bannercontrols = roleMapper.selectAll();
        PageInfo<Role> pageInfo =new PageInfo<>(bannercontrols);
        return pageInfo;
    }
}
