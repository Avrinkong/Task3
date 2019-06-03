package com.back.chuilun.service.impl;

import com.back.chuilun.dao.RoleMapper;
import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Role;
import com.back.chuilun.service.RoleService;
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
            return new Result(-1,"添加失败",i);
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

            return new Result(-1,"进入页面失败",roles);
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
        if (role.getRoleMessagestatus()==null||(role.getRoleMessagestatus()<=0&&role.getRoleMessagestatus()>3)){
            role.setRoleMessagestatus(1);
        }
        if (role.getRolePortfolio()==null||role.getRolePortfolio()<=0&&role.getRolePortfolio()>3){
            role.setRolePortfolio(1);
        }
        if (role.getRoleSecpotstatus()==null||role.getRoleSecpotstatus()<=0&&role.getRoleSecpotstatus()>3){
            role.setRoleSecpotstatus(1);
        }
        if (role.getRoleBannerstatus()==null||role.getRoleBannerstatus()<=0&&role.getRoleBannerstatus()>3){
            role.setRoleBannerstatus(1);
        }
        if (role.getRoleWroksstatus()==null||role.getRoleWroksstatus()<=0&&role.getRoleWroksstatus()>3){
            role.setRoleWroksstatus(1);
        }
        if (role.getRoleStudiostatus()==null||role.getRoleStudiostatus()<=0&&role.getRoleStudiostatus()>3){
            role.setRoleStudiostatus(1);
        }
        if (role.getRoleRoadstatus()==null||role.getRoleRoadstatus()<=0&&role.getRoleRoadstatus()>3){
            role.setRoleRoadstatus(1);
        }
        int i = roleMapper.updateByPrimaryKey(role);
        if (i>0){
            return new Result(0,"添加成功",i);
        }else {
            return new Result(-1,"添加失败",i);
        }
    }

    public Result deleteById(Integer roleId) {
        int i = roleMapper.deleteByPrimaryKey(roleId);
        if(i>0){
            return new Result(0,"删除成功",i);
        }else {
            return new Result(-1,"删除失败",i);
        }
    }
}
