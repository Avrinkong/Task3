package com.back.chuilun.controller;

import com.back.chuilun.entity.Result;
import com.back.chuilun.entity.Works;
import com.back.chuilun.service.impl.WorksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
        List<Works> all = worksService.findAll(worksName,wstatus);
        return new Result(0,"success",all);
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
        if (file!=null) {// 判断上传的文件是否为空
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
                    path=realPath+/*System.getProperty("file.separator")+*/trueFileName;

                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    result.setMessage("文件成功上传到指定目录下");
                    result.setData(path);
                }else {
                    result.setMessage("不是我们想要的文件类型,请按要求重新上传");
                    return result;
                }
            }else {
                result.setMessage("文件类型为空");
                return result;
            }
        }else {
            result.setMessage("没有找到相对应的文件");
            return result;
        }
        return result;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addWorks(String worksName,String worksIntro,String worksMpic,String worksUrl,String worksPic,String worksBintro){
       Works works = new Works();
       works.setWorksName(worksName);
       works.setWorksIntro(worksIntro);
       works.setWorksMpic(worksMpic);
       works.setWorksUrl(worksUrl);
       works.setWorksPic(worksPic);
       works.setWorksBintro(worksBintro);
      /* Date date=new Date();
       long timestamp=date.getTime();
       works.setWcreateTime(timestamp);
       works.setChangeTime(timestamp);*/
        Result update = worksService.add(works);
        return update;
    }


    @RequestMapping(value = "wstatus",method = RequestMethod.POST)
    @ResponseBody
    public Result updateWstatus(Integer worksId,String worksName,Integer wstatus){
        Result result = worksService.updateWstatus(worksId, worksName, wstatus);
        return result;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer worksId){
        Result delete = worksService.delete(worksId);
        return delete;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateWorks(Integer worksId,String worksName,String worksIntro,String worksMpic,String worksUrl,String worksPic,String worksBintro){
        Works works = worksService.findById(worksId);
        works.setWorksName(worksName);
        works.setWorksIntro(worksIntro);
        works.setWorksMpic(worksMpic);
        works.setWorksUrl(worksUrl);
        works.setWorksPic(worksPic);
        works.setWorksBintro(worksBintro);
      /* Date date=new Date();
       long timestamp=date.getTime();
       works.setWcreateTime(timestamp);
       works.setChangeTime(timestamp);*/
        Result update = worksService.update(works);
        return update;
    }
}
