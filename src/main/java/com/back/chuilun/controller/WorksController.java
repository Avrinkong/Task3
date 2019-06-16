package com.back.chuilun.controller;

import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Works;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.WorksServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "works")
public class WorksController {

    @Autowired
    private WorksServiceImpl worksService;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findWorks(){
        Result works = worksService.findWorks();
        return works;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findWorks(String worksName, Integer wstatus){
        if (worksName!=null&&!worksName.trim().equals("")){
            List<Works> all = worksService.findAll(worksName,wstatus);
            if (all.size()>0) {
                return new Result(0, "查找成功", all);
            }else {
                throw  new BusinessException("该作品不存在");
            }
        }else {
            Result all = worksService.findWorks();
            List<Works> list = new ArrayList<>();
            if (wstatus!=null){
                List<Works> data = (List<Works>) all.getData();
                for (Works studio:data){
                    if (studio.getWstatus().equals(wstatus)){
                        list.add(studio);
                    }
                }
                if (list.size()<=0){
                    throw new BusinessException("查询错误");
                }
                all.setData(list);
            }
            return all;
        }
    }

  /*  @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addPortfolio(String worksName){
        Result add = worksService.add(worksName);
        return add;
    }*/

    /**
     * 图片文件上传
     */
    @ResponseBody
    @RequestMapping(value = "/photoUpload",method = RequestMethod.POST)
    public Result photoUpload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        Result result=new Result();
        // 判断用户是否登录
        /*User user=(User) session.getAttribute("user");
        if (user==null) {
            resultData.setCode(40029);
            resultData.setMsg("用户未登录");
            return resultData;
        }*/
        if (file!=null&&file.getSize()<5242880) {// 判断上传的文件是否为空
            String path=null;// 文件路径
            String type=null;// 文件类型
            String fileName=file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    // 设置存放图片文件的路径
                    path=realPath+/*System.getProperty("file.separator")+*/"WEB-INF/files/"+trueFileName;

                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    result.setMessage("文件成功上传到指定目录下");
                    result.setData(path);
                }else {
                    throw  new BusinessException("不是我们想要的文件类型,请按要求重新上传");
                }
            }else {
                throw  new BusinessException("文件类型为空");
            }
        }else {
            throw  new BusinessException("没有找到相对应的文件或文件大小超出5M");
        }
        return result;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addWorks(String worksName,String worksIntro,String worksMpic,String worksUrl,String worksPic,String worksBintro){
        if (worksName!=null&&!worksName.trim().equals("")&&worksName.length()<=15){
            if (worksIntro!=null&&!worksIntro.trim().equals("")&&worksIntro.length()<=300){
                if (worksMpic!=null&&!worksMpic.trim().equals("")){
                    if (worksUrl!=null&&!worksUrl.trim().equals("")){
                        if (worksPic!=null&&!worksPic.trim().equals("")){
                            if (worksBintro!=null&&!worksBintro.trim().equals("")){
                                Works works = new Works();
                                works.setSecondPorName("萌萌哒默认二级标题");
                                works.setWorksName(worksName);
                                works.setWorksIntro(worksIntro);
                                works.setWorksMpic(worksMpic);
                                works.setWorksUrl(worksUrl);
                                works.setWorksPic(worksPic);
                                works.setWorksBintro(worksBintro);
                                Result update = worksService.add(works);
                                return update;
                            }else {
                                throw  new BusinessException("工作室完整簡介不能爲空");
                                        /*Result(-1,"工作室完整简介不能为空");*/
                            }
                        }else {
                            throw  new BusinessException("作品照片不能为空");
                        }
                    }else {
                        throw  new BusinessException("作品链接不能为空");
                    }
                }else {
                    throw  new BusinessException("作品缩略图不能为空");
                }
            }else {
                throw  new BusinessException("作品简介不能为空");
            }
        }else {
            throw  new BusinessException("作品名称不能为空或作品名称超过15字");
        }
      /* Date date=new Date();
       long timestamp=date.getTime();
       works.setWcreateTime(timestamp);
       works.setChangeTime(timestamp);*/
    }


    @RequestMapping(value = "wstatus",method = RequestMethod.POST)
    @ResponseBody
    public Result updateWstatus(Integer worksId,String worksName,Integer wstatus){
        if (worksId!=null){
            if (worksName!=null&&!worksName.trim().equals("")){
                if (wstatus!=null&&wstatus>0&&wstatus<3){
                    Result result = worksService.updateWstatus(worksId, worksName, wstatus);
                    return result;
                }else {
                    throw  new BusinessException("作品集上下架状态错误");
                }
            }else {
                throw  new BusinessException("作品集名称不能为空");
            }
        }else {
            throw  new BusinessException("作品集ID不能为空");
        }
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer worksId){
        if (worksId!=null){
            Result delete = worksService.delete(worksId);
            return delete;
        }else {
            throw  new BusinessException("作品集ID不能为空");
        }
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateWorks(Integer worksId,String worksName,String worksIntro,String worksMpic,String worksUrl,String worksPic,String worksBintro,String secondPorName,String portfolioName){
        if (worksId!=null){
            if (worksName!=null&&!worksName.trim().equals("")){
                if (worksIntro!=null&&!worksIntro.trim().equals("")){
                    if (worksMpic!=null&&!worksMpic.trim().equals("")){
                        if (worksUrl!=null&&!worksUrl.trim().equals("")){
                            if (worksPic!=null&&!worksPic.trim().equals("")){
                                if (worksBintro!=null&&!worksBintro.trim().equals("")){
                                    Works works = worksService.findById(worksId);
                                    if (works==null){
                                        throw new BusinessException("该作品不存在！");
                                    }
                                    works.setWorksName(worksName);
                                    works.setWorksIntro(worksIntro);
                                    works.setWorksMpic(worksMpic);
                                    works.setWorksUrl(worksUrl);
                                    works.setWorksPic(worksPic);
                                    works.setWorksBintro(worksBintro);
                                    works.setPortfolioName(portfolioName);
                                    works.setSecondPorName(secondPorName);
                                    Result update = worksService.update(works);
                                    return update;
                                }else {
                                    throw  new BusinessException("工作室完整简介不能为空");
                                }
                            }else {
                                throw  new BusinessException("作品照片不能为空");
                            }
                        }else {
                            throw  new BusinessException("作品链接不能为空");
                        }
                    }else {
                        throw  new BusinessException("作品缩略图不能为空");
                    }
                }else {
                    throw  new BusinessException("作品简介不能为空");
                }
            }else {
                throw  new BusinessException("作品集名称不能为空");
            }
        }else {
            throw  new BusinessException("工作室ID不能为空");
        }
      /* Date date=new Date();
       long timestamp=date.getTime();
       works.setWcreateTime(timestamp);
       works.setChangeTime(timestamp);*/
    }

    @RequestMapping(value = "pageinfo",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Works> findByPage(int currentPage, int pageSize){
        PageInfo<Works> info = worksService.findByPage(currentPage,pageSize);
        return info;
    }


    @RequestMapping(value = "search",method = RequestMethod.POST)
    @ResponseBody
    public Result search(@RequestParam("keyword") String keyword){
        Result result =worksService.findWorkByNI(keyword);
        return result;
    }
}
