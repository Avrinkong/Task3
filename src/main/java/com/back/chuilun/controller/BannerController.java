package com.back.chuilun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.chuilun.entity.Bannercontrol;
import com.back.chuilun.entity.Result;
import com.back.chuilun.exception.BusinessException;
import com.back.chuilun.service.impl.BannerServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "banner")
public class BannerController {

    @Autowired
    private BannerServiceImpl bannerService;

    @RequestMapping(value = "find",method = RequestMethod.GET)
    @ResponseBody
    public Result findWorks(){
        //PageHelper.startPage(1,5);
        Result all = bannerService.findAll();
        return all;
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    @ResponseBody
    public Result findBanner(Integer bannerStatus, String bannerEditor){
        if (bannerEditor!=null&&!bannerEditor.trim().equals("")){
            List all = bannerService.findAll(bannerStatus, bannerEditor);
            if (all.size()>0) {
                return new Result(0, "查询成功", all);
            }else {
                throw  new BusinessException("没有此banner图");
            }
        }
        Result all = bannerService.findAll();
        List<Bannercontrol> list = new ArrayList<>();
        if (bannerStatus!=null){
            if (bannerStatus==2){
                List<Bannercontrol> data = (List<Bannercontrol>) all.getData();
                for (Bannercontrol studio:data){
                    if (studio.getBannerStatus().equals(bannerStatus)){
                        list.add(studio);
                    }
                }
                if (list.size()<=0){
                    throw new BusinessException("查询错误");
                }
                all.setData(list);
            }else if (bannerStatus==1){
                List<Bannercontrol> byBS = bannerService.findByBS(bannerStatus);
                all.setData(byBS);
            }
        }
        return all;
    }

    @RequestMapping(value = "sort",method = RequestMethod.POST)
    @ResponseBody
    public Result sortPortfolio(@RequestBody String jsonObject){
        if (jsonObject!=null){
            JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
            List<Bannercontrol> list = JSON.parseArray(jsonObject1.getString("bannercontrol"),Bannercontrol.class);
            Result result = bannerService.sortBanner(list);
            return result;
        }else {
            throw  new BusinessException("输入错误");
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addBanner(String bannerUrl,String bannerPic){
        Result all = bannerService.findAll();
        List<Bannercontrol> data = (List<Bannercontrol>) all.getData();
        if (bannerUrl!=null&&!bannerUrl.trim().equals("")){
            if (bannerPic!=null&&!bannerPic.trim().equals("")){
                    Result add = bannerService.add(bannerUrl, bannerPic);
                    return add;
            }
            throw  new BusinessException("图片不能为空");
        }
        throw  new BusinessException("连接不能为空");
    }

    @RequestMapping(value = "status",method = RequestMethod.POST)
    @ResponseBody
    public Result updateBannerStatus(Integer bannerId,Integer bannerStatus){
        if (bannerId!=null){
            if (bannerStatus!=null&&bannerStatus>0&&bannerStatus<3){
                Result result = bannerService.updateBannerStatus(bannerId, bannerStatus);
                return result;
            }
            throw  new BusinessException("上下架状态错误");
        }
        throw  new BusinessException("Id不能为空");
    }

    @RequestMapping(value = "delete",method =RequestMethod.POST)
    @ResponseBody
    public Result deleteById(Integer bannerId){
        if (bannerId!=null){
            Result result =bannerService.deleteById(bannerId);
            return result;
        }else {
            throw new BusinessException("Id不能为空");
        }
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateById(Integer bannerId,String bannerUrl,String bannerPic){
        if (bannerId!=null){
            if (!bannerUrl.trim().equals("")){
                if (!bannerPic.trim().equals("")){
                    Result result = bannerService.updateById(bannerId, bannerUrl,bannerPic);
                    return result;
                }
                throw  new BusinessException("图片不能为空");
            }
            throw  new BusinessException("连接不能为空");
        }
        throw  new BusinessException("Id不能为空");
    }

    @RequestMapping(value = "pageinfo",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Bannercontrol> findByPage(int currentPage,int pageSize){
        PageInfo<Bannercontrol> info = bannerService.findByPage(currentPage,pageSize);
        return info;
    }

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
                    path=realPath+/*System.getProperty("file.separator")+*/trueFileName;

                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    result.setMessage("文件成功上传到指定目录下1");
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
}
