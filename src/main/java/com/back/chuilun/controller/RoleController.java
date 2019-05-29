package com.back.chuilun.controller;

import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Role;
import com.back.chuilun.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findAll(){
        Result all = roleService.findAll();
        return all;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findAll(String roleName){
        if(roleName!=null&&!roleName.trim().equals("")){
            List all = roleService.findAll(roleName);
            if (all.size()>0) {
                return new Result(0, "success", all);
            }else {
                return new Result(-1,"false");
            }
        }else {
            return new Result(-1,"角色名不能为空");
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addRole(Role role){
        if(role.getRoleName()!=null&&!role.getRoleName().trim().equals("")){
            if(role.getRoleMessagestatus()!=null){
                if(role.getRoleWroksstatus()!=null){
                    if (role.getRoleBannerstatus()!=null){
                        if (role.getRoleStudiostatus()!=null){
                            if(role.getRoleRoadstatus()!=null){
                                Result add = roleService.add(role);
                                return add;
                            }else {
                                return new Result(-1,"轨迹管理状态不能为空");
                            }
                        }else {
                            return new Result(-1,"工作室管理状态不能为空");
                        }
                    }else {
                        return new Result(-1,"banner管理状态不能为空");
                    }
                }else {
                    return new Result(-1,"作品管理状态不能为空");
                }
            }else {
                return new Result(-1,"留言管理状态不能为空");
            }
        }else {
            return new Result(-1,"角色名不能为空");
        }
    }


    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Role role){
        if (role.getRoleId()!=null){
            if(role.getRoleName()!=null&&!role.getRoleName().trim().equals("")){
                if(role.getRoleMessagestatus()!=null){
                    if(role.getRoleWroksstatus()!=null){
                        if (role.getRoleBannerstatus()!=null){
                            if (role.getRoleStudiostatus()!=null){
                                if(role.getRoleRoadstatus()!=null){
                                    Result result = roleService.updateById(role);
                                    return result;
                                }else {
                                    return new Result(-1,"轨迹管理状态不能为空");
                                }
                            }else {
                                return new Result(-1,"工作室管理状态不能为空");
                            }
                        }else {
                            return new Result(-1,"banner管理状态不能为空");
                        }
                    }else {
                        return new Result(-1,"作品管理状态不能为空");
                    }
                }else {
                    return new Result(-1,"留言管理状态不能为空");
                }
            }else {
                return new Result(-1,"角色名不能为空");
            }
        }else {
            return new Result(-1,"角色ID不能为空");
        }
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer roleId){
        if (roleId!=null){
            Result result = roleService.deleteById(roleId);
            return result;
        }else {
            return new Result(-1,"角色ID不能为空");
        }
    }

}
