package com.back.chuilun.controller;

import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Role;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.RoleServiceImpl;
import com.github.pagehelper.PageInfo;
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
                return new Result(0, "查询成功", all);
            }else {
                throw  new BusinessException("用户不存在");
            }
        }else {
            Result all = roleService.findAll();
            return all;
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addRole(Role role){
        if(role.getRoleName()!=null&&!role.getRoleName().trim().equals("")){
            if(role.getRoleMessagestatus()!=null&&role.getRoleMessagestatus()>0&&role.getRoleMessagestatus()<3){
                if(role.getRoleWroksstatus()!=null&&role.getRoleWroksstatus()>0&&role.getRoleWroksstatus()<3){
                    if (role.getRoleBannerstatus()!=null&&role.getRoleBannerstatus()>0&&role.getRoleBannerstatus()<3){
                        if (role.getRoleStudiostatus()!=null&&role.getRoleStudiostatus()>0&&role.getRoleStudiostatus()<3){
                            if(role.getRoleRoadstatus()!=null&&role.getRoleRoadstatus()>0&&role.getRoleRoadstatus()<3){
                                Result add = roleService.add(role);
                                return add;
                            }else {
                                throw  new BusinessException("轨迹管理状态错误");
                            }
                        }else {
                            throw  new BusinessException("工作室管理状态错误");
                        }
                    }else {
                        throw  new BusinessException("banner管理状态错误");
                    }
                }else {
                    throw  new BusinessException("作品管理状态错误");
                }
            }else {
                throw  new BusinessException("留言管理状态错误");
            }
        }else {
            throw  new BusinessException("角色名错误");
        }
    }


    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Role role){
        if (role.getRoleId()!=null){
            if(role.getRoleName()!=null&&!role.getRoleName().trim().equals("")){
                if(role.getRoleMessagestatus()!=null&&role.getRoleMessagestatus()>0&&role.getRoleMessagestatus()<3) {
                    if (role.getRolePortfolio() != null && role.getRolePortfolio() >0 && role.getRolePortfolio()<3) {
                        if (role.getRoleSecpotstatus()!=null&&role.getRoleSecpotstatus()>0&&role.getRoleSecpotstatus()<3) {
                            if (role.getRoleWroksstatus() != null && role.getRoleWroksstatus() > 0 && role.getRoleWroksstatus() < 3) {
                                if (role.getRoleBannerstatus() != null && role.getRoleBannerstatus() > 0&& role.getRoleBannerstatus() < 3) {
                                    if (role.getRoleStudiostatus() != null && role.getRoleStudiostatus() > 0 && role.getRoleStudiostatus() < 3) {
                                        if (role.getRoleRoadstatus() != null && role.getRoleRoadstatus() > 0 && role.getRoleRoadstatus() < 3) {
                                            Result result = roleService.updateById(role);
                                            return result;
                                        } else {
                                            throw new BusinessException("轨迹管理状态错误");
                                        }
                                    } else {
                                        throw new BusinessException("工作室管理状态错误");
                                    }
                                } else {
                                    throw new BusinessException("banner管理状态错误");
                                }
                            } else {
                                throw new BusinessException("作品管理状态错误");
                            }
                        }else {
                            throw new BusinessException("作品集二级导航管理状态错误");
                        }
                    }else {
                        throw new BusinessException("作品集导航管理状态错误");
                    }
               } else {
                throw new BusinessException("留言管理状态错误");
               }
            }else {
                throw  new BusinessException("角色名错误");
            }
        }else {
            throw  new BusinessException("角色ID错误");
        }
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer roleId){
        if (roleId!=null){
            Result result = roleService.deleteById(roleId);
            return result;
        }else {
            throw  new BusinessException("角色ID不能为空");
        }
    }

    @RequestMapping(value = "pageinfo",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Role> findByPage(int currentPage, int pageSize){
        PageInfo<Role> info =roleService.findByPage(currentPage,pageSize);
        return info;
    }

}
