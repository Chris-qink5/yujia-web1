package com.woniu.yujiaweb.controller;


import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.woniu.yujiaweb.domain.AlipayBean;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;

//import com.woniu.yujiaweb.service.AlipayService;
import com.woniu.yujiaweb.service.AlipayService;
import com.woniu.yujiaweb.service.UserService;
import com.woniu.yujiaweb.util.AliyunSmsUtils;
import com.woniu.yujiaweb.util.JWTUtil;

import com.woniu.yujiaweb.util.MailUtils;
import com.woniu.yujiaweb.util.SaltUtil;
import com.woniu.yujiaweb.vo.CoachVo;
import com.woniu.yujiaweb.vo.GymVo;

import com.woniu.yujiaweb.vo.UserVO;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.sound.midi.Soundbank;

import java.io.File;
import java.io.IOException;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
@Api(value = "文件上传，下载相关功能")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private AlipayService alipayService;
    @Resource
    private UserService userService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private  Integer gym;
    boolean  b=true;
    //查寻平台教练教练
    @GetMapping("/showcoach")
    public Result showcocach( CoachVo coachVo){
        QueryWrapper<CoachVo> QueryWrapper = new QueryWrapper<>();
            QueryWrapper.eq("r.id",2);
        if(StringUtils.hasLength(coachVo.getTel())){
            System.out.println("tel");
            QueryWrapper.like("u.tel",coachVo.getTel());
        }
        if(StringUtils.hasLength(coachVo.getNickname())){
            System.out.println("nickname");
            QueryWrapper.like("nickname",coachVo.getNickname());
        }
        if(!StringUtils.isEmpty(coachVo.getSex())){
            System.out.println("sex");
            QueryWrapper.eq("c.sex",coachVo.getSex());
        }
      Page<CoachVo> allcoach = userService.getAllcoach(coachVo, QueryWrapper);
        return new Result(true, StatusCode.OK,"查询成功",allcoach);
    }
    //查看学员
    @GetMapping("/showstudent")
    public Result showstudent(CoachVo studentVo){
        QueryWrapper<CoachVo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("r.id",1);
        if(!StringUtils.isEmpty(studentVo.getSex())){
            QueryWrapper.eq("ui.sex",studentVo.getSex());
        }
        if(StringUtils.hasLength(studentVo.getTel())){
            QueryWrapper.like("u.tel",studentVo.getTel());
        }
        if(StringUtils.hasLength(studentVo.getNickname())){
            QueryWrapper.like("nickname",studentVo.getNickname());
        }
        Page<CoachVo> allstudent = userService.getAllstudent(studentVo, QueryWrapper);
        return new Result(true, StatusCode.OK,"查询成功",allstudent);
    }
    //查询学员签约情况
    @GetMapping("/querystudent/{tel}")
    public Result querystudent(@PathVariable String tel){
        //通过学员电话查询出签约教练id
      List<Integer> coachIds= userService.querycoachBystudentTel(tel);
        //集合保存教练信息
        ArrayList<CoachVo> coachnames= new ArrayList<>();
        coachIds.forEach(coachId->{
            CoachVo coachname = userService.querycoachBycoachId(coachId);
            coachnames.add(coachname);
        });
        return  new Result(true, StatusCode.OK,"查询学员签约教练信息成功",coachnames);
    }
    //查询教练签约情况
   @GetMapping("/signc/{tel}")
   public Result signcoach(@PathVariable String tel){
        //通过电话查询出教练id
       Integer coachid = userService.selectcoachid(tel);
       //通过教练id查询出签约场馆id
    List<Integer> gymIds=userService.querygymBycoachId(coachid);
    //集合保存签约场馆信息
       ArrayList<GymVo> gymnames= new ArrayList<>();
            gymIds.forEach(gymId->{
                //遍历查询场馆信息
                GymVo gymname = userService.Querygym(gymId);
                gymnames.add(gymname);
            });
       return  new Result(true, StatusCode.OK,"查询成功",gymnames);
   }
   //申请签约教练
    @GetMapping("insertgym")
    public Result insertgym(Integer gymid,String tel){
        //通过电话查询出教练id
        Integer coachid1 = userService.selectcoachid(tel);
        //通过教练id查询出签约场馆id
        List<Integer> gymIds=userService.querygymBycoachId(coachid1);
        //签约的场馆不能再次签约
        if(!ObjectUtils.isEmpty(gymIds)){
            gymIds.forEach(gymid1 -> {
                if (gymid1 == gymid) {
                    b = false;
                } else {
                    b = true;
                }
            });
        }else {
            b=true;
        }
        if(b){
            Integer coachid = userService.selectcoachid(tel);
            userService.insertcoach(coachid, gymid);
            return  new Result(false, StatusCode.ACCOUNTEXISTS,"教练签约成功");
        }
        return  new Result(true, StatusCode.OK,"不能重复签约");
    }
   //查询场馆签约信息
    @PostMapping("/querygym/{tel}")
    public Result querygym(@PathVariable String tel){
            GymVo gymVos =userService.findgym(tel);
            return new Result(true, StatusCode.OK,"查询场馆信息成功",gymVos);
    }
    //文件上传
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        //文件上传
        //2、存放在本地文件系统中
        String path="D:/vue-cli/gymproject/static/img/".replace("/",File.separator);
            String paths="/static/img/";
        File upload=new File(path);
        if (!upload.exists()) {
            upload.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        //避免文件同名，使用UUID+系统时间对文件名进行处理
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long currentTimeMillis = System.currentTimeMillis();
        String uploadFileName=uuid+currentTimeMillis+fileName;
        //执行上传操作
        try {
            //执行上传操作
            file.transferTo(new File(path,uploadFileName));
            System.out.println("上传成功"+uploadFileName);
            return new Result(true, StatusCode.OK,"上传成功",paths+uploadFileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return new Result(false, StatusCode.ERROR,"上传失败");
        }
    }
    //修改场馆信息//通过教练id查询签约教练的个人信息
    @PostMapping("/updatagym")
    public Result updatagym(@RequestBody GymVo gymVo )  {
        userService.updateByTel(gymVo);
        return new Result(true, StatusCode.OK,"修改成功");
    }
    //查询签约教练的id
    @GetMapping("selectcoachid/{tel}")
    public Result selectcoachid(@PathVariable String tel,CoachVo coachVo){
         gym = userService.selectgymid(tel);
        QueryWrapper<CoachVo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("u.tel",tel);
        Page<CoachVo> allcoachs = userService.selectcoachinfo(coachVo, QueryWrapper);
        return new Result(true, StatusCode.OK,"查询我的签约教练的个人信息成功",allcoachs);
    }
    //解约教练
    @PostMapping("deletecid/{tel}")
    public Result querycid(@PathVariable String tel){
        System.out.println(gym+"场馆id");
      Integer coachid=  userService.selectcoachid(tel);
        //  根据教练id和场馆id解约教练
      userService.deletecgymBycIDandgId(coachid,gym);
        return new Result(true, StatusCode.OK,"解除签约成功");
    }
    //通过场馆电话查询出签约教练，通过签约教练查询出场馆的学员
    @GetMapping("findmystudent/{tel}")
    public Result findmystudent(CoachVo coachVo,@PathVariable String tel){
        QueryWrapper<CoachVo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("u.tel",tel);
        Page<CoachVo> allmystudent = userService.selectStudentinfo(coachVo, QueryWrapper);
        return new Result(true, StatusCode.OK,"修改成功",allmystudent);
    }
    //通过场馆电话查询出课程信息
    @GetMapping("selectclass/{tel}")
    public Result findclass(CoachVo coachVo,@PathVariable String tel){
        QueryWrapper<CoachVo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("u.tel",tel);
        Page<CoachVo> allclass = userService.findclassinfo(coachVo, QueryWrapper);
        return new Result(true, StatusCode.OK,"修改成功",allclass);
    }
    //下架课程
    @GetMapping("classdel")
    public Result deleteclass( CoachVo coachVo){
        //通过教练电话查询他的id
        Integer coachid=  userService.selectcoachid(coachVo.getTel());
        //通过课程名字查询课程id
      Integer classId=userService.queryclassIdByclassName(coachVo.getCourseName());
      //通过教练id和课程id下架课程
        userService.deleteclass(coachid,classId);
        return new Result(true, StatusCode.OK,"下架成功");
    }

    //查询关注我的教练的信息
    @GetMapping("attentiongym/{tel}")
        public Result attentiongym(CoachVo coachVo,@PathVariable String tel){
        QueryWrapper<CoachVo> QueryWrapper = new QueryWrapper<>();
        Integer selectgymid = userService.selectgymid(tel);
        QueryWrapper.eq("cg.yogagym_id",selectgymid);
        Page<CoachVo> allattentiongym = userService.findattentiongym(coachVo, QueryWrapper);

        return  new Result(true, StatusCode.OK,"查询成功",allattentiongym);
    }

    //实现支付功能
        @PostMapping("alipay")
        public Result toAlipay(@RequestBody AlipayBean alipayBean) throws IOException {
            String result = alipayService.toAlipay(alipayBean);
            return new Result(true, StatusCode.OK,"修改成功",result);
        }
}

