package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.*;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.*;
import com.woniu.yujiaweb.vo.AYujiaVo;
import com.woniu.yujiaweb.vo.LauchVO;
import com.woniu.yujiaweb.vo.MyYuJiaVO;
import com.woniu.yujiaweb.vo.YuJiaVO;
import io.swagger.annotations.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import com.woniu.yujiaweb.domain.Yujia;

import com.woniu.yujiaweb.service.YujiaService;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/yujia")
public class YujiaController {
    @Resource
    private YujiaService yujiaService;
    @Resource
    private YujiaDetailService yujiaDetailService;
    @Resource
    private UserYujiaService userYujiaService;
    @Resource
    private UserService userService;
    @Resource
    private YujiaPlaceService yujiaPlaceService;
    @Resource
    private RedisTemplate redisTemplate;
    @ApiOperation(value = "查找课程",notes = "<span style='color:red;'>查找众筹的课程并排序分页</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "分页成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "current",value = "当前页码",dataType = "int",example = "1"),
            @ApiImplicitParam(name = "size",value = "页码大小",dataType = "int",example = "1"),

    })
    @GetMapping("/findCourse")
    @ResponseBody
    public Result findCourse(int current,int size){
        System.out.println(current+","+size);
        Page<Yujia> page = yujiaService.getPage(current, size);
        return new Result(true, StatusCode.OK,"分页成功",page);
    }
    @ApiOperation(value = "加入众筹课程",notes = "<span style='color:red;'>点击加入后，课程人数增加，并保存用户增加的众筹课程</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "加入成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "yuJiaVO",value = "瑜伽vo",dataType = "YuJiaVO",example = "{yid：1，uname:xxx}"),


    })
//    用户加入到众筹课程时调用的接口
    @PostMapping ("/join")
    @ResponseBody
    public Result join(@RequestBody YuJiaVO yuJiaVO){
        System.out.println(yuJiaVO.getYid()+","+yuJiaVO.getUname());
//        用户——众筹项目中间表添加数据，保存用户所参加的众筹项目
        UserYujia t = new UserYujia();
        int uid =(int) redisTemplate.opsForValue().get(yuJiaVO.getUname());
        t.setUid(uid);
        t.setYid(yuJiaVO.getYid());
        userYujiaService.save(t);
//        众筹项目表参加人数自增
        Yujia byId = yujiaService.getById(yuJiaVO.getYid());
        Yujia yujia = new Yujia();
        yujia.setId(yuJiaVO.getYid());
        yujia.setJoinerNum(byId.getJoinerNum()+1);
        UpdateWrapper<Yujia> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",yuJiaVO.getYid());
        yujiaService.update(yujia, wrapper);

        return new Result(true, StatusCode.OK,"加入成功");
    }

    //    用户加入到众筹课程时调用的接口
    @GetMapping ("/findMyYuJia")
    @ResponseBody
    public Result findMyYuJia(String username){
        System.out.println(username+"进入查找我的瑜伽众筹课程");
        int uid =(int) redisTemplate.opsForValue().get(username);
        List<MyYuJiaVO> myYuJia = yujiaService.findMyYuJia(uid);
        myYuJia.forEach(one->{
            System.out.println(one);
        });
        return new Result(true, StatusCode.OK,"加入成功",myYuJia);
    }
//    用户退出众筹课程时调用的接口
    @PostMapping ("/exit")
    @ResponseBody
    public Result exit(@RequestBody YuJiaVO yuJiaVO){
        System.out.println(yuJiaVO.getYid()+","+yuJiaVO.getUname());
//        用户——众筹项目中间表删除数据
        int uid =(int) redisTemplate.opsForValue().get(yuJiaVO.getUname());
        UpdateWrapper<UserYujia> wrapper = new UpdateWrapper<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("uid",uid);
        params.put("yid",yuJiaVO.getYid());
        wrapper.allEq(params);
        userYujiaService.remove(wrapper);
        return new Result(true, StatusCode.OK,"退出成功");
    }

    @PostMapping ("/lunchCf")
    @ResponseBody
    public Result lunchCf(@RequestBody LauchVO lauchVO){
        System.out.println(lauchVO.toString());
        Yujia yujia = new Yujia();
        yujia.setCourseName(lauchVO.getCourseName());
        yujia.setJoinerTar(lauchVO.getJoinerTar());
        yujia.setStarTime(lauchVO.getDate1());
        yujia.setEndTime(lauchVO.getDate1a());
        yujia.setSponsor(lauchVO.getSponsor());
        yujiaService.save(yujia);
        System.out.println(yujia);
        YujiaDetail yujiaDetail = new YujiaDetail();
        yujiaDetail.setCoach(lauchVO.getSponsor());
        yujiaDetail.setCourseDescribe(lauchVO.getDesc());
        yujiaDetail.setCoursePrice(lauchVO.getCoursePrice());
        yujiaDetail.setYid(yujia.getId());
        yujiaDetailService.save(yujiaDetail);
        ArrayList<YujiaPlace> plYujia = new ArrayList<YujiaPlace>();
        for(String pl:lauchVO.getCities()){
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username",pl);
            User place = userService.getOne(wrapper);
            YujiaPlace yujiaPlace = new YujiaPlace();
            yujiaPlace.setPid(place.getId());
            yujiaPlace.setYid(yujia.getId());
            plYujia.add(yujiaPlace);
        }
        yujiaPlaceService.saveBatch(plYujia);
        return new Result(true, StatusCode.OK,"发起成功");
    }
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam("cname") String cname) {
        QueryWrapper<Yujia> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", cname);
        Yujia one = yujiaService.getOne(queryWrapper);
        int yid = one.getId();
        //文件上传
        //2、存放在本地文件系统中
        String path = "E:/nodejs/vue-cli/shirojwt/static/picture".replace("/", File.separator);
        File upload = new File(path);
        if (!upload.exists()) {
            upload.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        //避免文件同名，使用UUID+系统时间对文件名进行处理
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long currentTimeMillis = System.currentTimeMillis();
        String uploadFileName = uuid + currentTimeMillis + fileName;
        //执行上传操作
        try {
            //执行上传操作
            file.transferTo(new File(path, uploadFileName));
            System.out.println("上传成功" + uploadFileName);
            String picturePath = "/static/picture/" + uploadFileName;
            YujiaDetail t = new YujiaDetail();
            t.setPicture(picturePath);
            UpdateWrapper<YujiaDetail> wrapper = new UpdateWrapper<>();
            wrapper.eq("yid", yid);
            yujiaDetailService.update(t, wrapper);
            return new Result(true, StatusCode.OK, "上传成功", path + uploadFileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return new Result(false, StatusCode.ERROR, "上传失败");
        }
    }


    //管理员对众筹项目进行管理
    //查询所有的瑜伽项目
    @GetMapping("queryAllCF")
    public Result findAllCj(AYujiaVo yujiaVo) {
        Page<AYujiaVo> allCJ = yujiaService.findAllCJ(yujiaVo);
        return new Result(true, StatusCode.OK, "成功查询所有的众筹项目", allCJ);
    }

    //对所选中的项目进行修改
    @PostMapping("update")
    public Result updateCF(@RequestBody AYujiaVo yujiaVo) {
        boolean b = yujiaService.updateCj(yujiaVo);
        if (b) {
            return new Result(true, StatusCode.OK, "修改项目信息成功");
        }
        return new Result(false, StatusCode.ERROR, "修改项目信息失败");
    }

    //对所选中的项目进行删除
    @PostMapping("delCF/{id}")
    public Result delCF(@PathVariable Integer id) {
        boolean b = yujiaService.delCJ(id);
        if (b) {
            return new Result(true, StatusCode.OK, "成功删除订单");
        }
        return new Result(false, StatusCode.ERROR, "删除订单失败");
    }

    //对未审核的众筹项目进行审核 将0变为1
    @PostMapping("aud/{id}")
    public Result aud(@PathVariable Integer id) {
        boolean aud = yujiaService.aud(id);
        if (aud) {
            return new Result(true, StatusCode.OK, "审核成功");
        }
        return new Result(false, StatusCode.ERROR, "审核失败");
    }
}

