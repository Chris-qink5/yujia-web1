package com.woniu.yujiaweb.controller;


import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.woniu.yujiaweb.domain.AlipayBean;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.domain.UserInfo;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserInfoService;
import com.woniu.yujiaweb.service.UserService;
import com.woniu.yujiaweb.service.impl.UserServiceImpl;
import com.woniu.yujiaweb.util.AliyunSmsUtils;
import com.woniu.yujiaweb.util.JWTUtil;
import com.woniu.yujiaweb.util.MailUtils;
import com.woniu.yujiaweb.util.SaltUtil;
import com.woniu.yujiaweb.vo.*;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserInfoService userInfoService;
    @PostMapping ("/register")
    //@ApiOperation用于描述接口方法，作用于方法上
    @ApiOperation(value = "用户注册",notes = "<span style='color:red;'>用来用户注册的接口</span>")
    public Result register(@RequestBody UserVO userVO){
        //先查询数据库是否有数据
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVO.getUsername());
        User userDB = userService.getOne(queryWrapper);
        //如果没有数据
        if (ObjectUtils.isEmpty(userDB)) {
            //加密注册
            userDB=new User();
            SaltUtil saltUtil = new SaltUtil(8);
            String salt = saltUtil.getSalt();
            Md5Hash hash = new Md5Hash(userVO.getPassword(), salt,2048);
            userDB.setUsername(userVO.getUsername());
            userDB.setPassword(hash.toHex());
            userDB.setSalt(salt);
            //创建时间
            userDB.setGmtCreate(new Date());
            if(userVO.getContact().contains(".com")){
                //邮箱注册
                String email = userVO.getContact();
                userDB.setEmail(email);
            }else {
                //电话注册
                String tel = userVO.getContact();
                userDB.setTel(tel);
            }
            //前台跟后台验证码进行对比
            if (userVO.getAuthCode().equals(redisTemplate.opsForValue().get("authCode"))){
                //往数据库里存数据t_user
                userService.save(userDB);
            }else {
                return new Result(false, StatusCode.AUTHCODE,"验证码错误");
            }
            //缓存里存注册的昵称、银行卡号、性别
            redisTemplate.opsForValue().set("nickname",userVO.getNickname());
            redisTemplate.opsForValue().set("bankCard",userVO.getBankCard());
            redisTemplate.opsForValue().set("sex",userVO.getSex());
            //往t_user_role里存数据(userVO.getRadio())
            redisTemplate.opsForValue().set("radio",userVO.getRadio());
            return new Result(true, StatusCode.OK,"注册成功");
        }else {
            return new Result(false, StatusCode.ACCOUNTEXISTS,"账户已存在");
        }
    }
    @PostMapping ("/login")
    //@ApiOperation用于描述接口方法，作用于方法上
    @ApiOperation(value = "用户登陆",notes = "<span style='color:red;'>用来登陆所有用户的接口</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20002,message = "密码错误"),
            @ApiResponse(code=20003,message = "账户不存在")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "userVO",value = "用户名于密码组成的用户",dataType = "UserVO",example = "{username:'tom',password:'1234'}"),

    })
    public Result login(@RequestBody UserVO userVO){
        System.out.println("进入login");
        UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUsername(), userVO.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        //先查询新注册时的角色id
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVO.getUsername());
        User userDB = userService.getOne(queryWrapper);
        if(!ObjectUtils.isEmpty(userDB)){
            //保存缓存
            redisTemplate.opsForValue().set("username",userVO.getUsername());
            //数据不为空
            String radio = (String) redisTemplate.opsForValue().get("radio");
            //userDB.getId()可以得到uid
            userService.saveUserAndRole(Integer.toString(userDB.getId()),radio);
            //把注册时的昵称、银行卡号、性别存入t_user_info数据库中
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            //从缓存中取出用户id
            wrapper.eq("username",userVO.getUsername());
            UserInfo userInfo = userInfoService.getOne(wrapper);
            if(ObjectUtils.isEmpty(userInfo)){
                //从缓存中取出数据
                String nickname = (String) redisTemplate.opsForValue().get("nickname");
                String bankCard = (String) redisTemplate.opsForValue().get("bankCard");
                String sex = (String) redisTemplate.opsForValue().get("sex");
                //如果为空就存入数据
                UserInfo userInfo1 = new UserInfo();
                userInfo1.setUsername(userVO.getUsername());
                userInfo1.setUid(userDB.getId());
                userInfo1.setNickname(nickname);
                userInfo1.setBankCard(bankCard);
                userInfo1.setSex(sex);
                userInfoService.save(userInfo1);
            }
        }
//      创建jwt,并将通过验证的用户保存到后端session中
        HashMap<String, String> map = new HashMap<>();
        map.put("username",userVO.getUsername());

        map.put("username", userVO.getUsername());
        String jwtToken = JWTUtil.createToken(map);
        JWTUtil.getUsernames().add(userVO.getUsername());
        System.out.println(jwtToken);
        System.out.println("结束login");

        return new Result(true, StatusCode.OK,"登陆成功",jwtToken);
    }
    @PostMapping ("/getAuthCode")
    //@ApiOperation用于描述接口方法，作用于方法上
    @ApiOperation(value = "获取验证码",notes = "<span style='color:red;'>用来登陆所有用户的接口</span>")
    public Result getAuthCode(@RequestBody UserVO userVO){
        //先判断是手机还是邮箱
        if(userVO.getContact().contains(".com")){
            //邮箱注册
            try {
                MailUtils.newcode="";
                MailUtils.setNewcode();
                String code = MailUtils.getNewcode();
                //验证码存入缓存
                redisTemplate.opsForValue().set("authCode",code);
                MailUtils.sendMail(userVO.getContact(),"验证码",code);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }else{
            //手机注册
            try {
                AliyunSmsUtils.newcode="";
                AliyunSmsUtils.setNewcode();
                String code = AliyunSmsUtils.getNewcode();
                //验证码存入缓存
                redisTemplate.opsForValue().set("authCode",code);
                AliyunSmsUtils.sendSms(userVO.getContact(),code);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return new Result(true,StatusCode.OK,"验证码发送成功");
    }
    @PostMapping ("/getpassword")
    //@ApiOperation用于描述接口方法，作用于方法上
    @ApiOperation(value = "找回密码",notes = "<span style='color:red;'>用来找回密码的接口</span>")
    public Result getpassword(@RequestBody UserVO userVO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVO.getUsername());
        User userDB = userService.getOne(queryWrapper);
        //如果可以查到数据
        if (!ObjectUtils.isEmpty(userDB)) {
            //密码加密
            userDB=new User();
            SaltUtil saltUtil = new SaltUtil(8);
            String salt = saltUtil.getSalt();
            Md5Hash hash = new Md5Hash(userVO.getPassword(), salt,2048);
            userDB.setPassword(hash.toHex());
            userDB.setSalt(salt);
            //修改时间
            userDB.setGmtModifified(new Date());
            if(userVO.getContact().contains(".com")){
                //邮箱找回
                try {
                    MailUtils.newcode="";
                    MailUtils.setNewcode();
                    String code = MailUtils.getNewcode();
                    //验证码存入缓存
                    redisTemplate.opsForValue().set("newAuthCode",code);
                    MailUtils.sendMail(userVO.getContact(),"验证码",code);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                userDB.setEmail(userVO.getContact());
            }else {
                //电话找回
                try {
                    AliyunSmsUtils.newcode="";
                    AliyunSmsUtils.setNewcode();
                    String code = AliyunSmsUtils.getNewcode();
                    //验证码存入缓存
                    redisTemplate.opsForValue().set("newAuthCode",code);
                    AliyunSmsUtils.sendSms(userVO.getContact(),code);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                userDB.setTel(userVO.getContact());
            }
            //数据库修改数据
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("username",userVO.getUsername());
            userService.update(userDB,wrapper);
            //前台跟后台验证码进行对比
//            System.out.println("前台验证码"+userVO.getAuthCode());
//            System.out.println("后台验证码"+redisTemplate.opsForValue().get("newAuthCode"));
//            if (userVO.getAuthCode().equals(redisTemplate.opsForValue().get("newAuthCode"))){
//                //往数据库里更改数据
//                System.out.println("进入修改页面了");
//                UpdateWrapper<User> wrapper = new UpdateWrapper<>();
//                wrapper.eq("username",userVO.getUsername());
//                userService.update(userDB,wrapper);
//            }else {
//                return new Result(false, StatusCode.AUTHCODE,"验证码错误");
//            }
        }
        return new Result(true, StatusCode.OK,"发送验证码成功，请注意查收");

    }
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
    //@ApiOperation用于描述接口方法，作用于方法上
    @ApiOperation(value = "查找一级列表",notes = "<span style='color:red;'>用来查找一级列表</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "一级列表查找成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "userVO",value = "用户名于密码组成的用户",dataType = "UserVO",example = "{username:'tom',password:'xxx'}"),

    })
    @RequestMapping("findManue")
    @ResponseBody
    public Result findManue(@RequestBody UserVO userVO){
        System.out.println("进入find"+userVO.getUsername());
        List<Permission> rootManue = userService.findManue(userVO.getUsername());
        return new Result(true, StatusCode.OK,"一级列表查找成功",rootManue);

    }
    @ApiOperation(value = "查找二级列表",notes = "<span style='color:red;'>用来查找二级列表</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "二级列表查找成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "userVO",value = "用户名于密码组成的用户",dataType = "UserVO",example = "{username:'tom',password:'xxx'}"),

    })
    @RequestMapping("findManue2")
    @ResponseBody
    public Result findManue2(@RequestBody UserVO userVO){
        System.out.println("进入find"+userVO.getUsername());
        List<Permission> rootManue = userService.findManue2(userVO.getUsername());
        return new Result(true, StatusCode.OK," ",rootManue);
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
    @ApiOperation(value = "退出登陆",notes = "<span style='color:red;'>用来退出登陆</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "注销成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "userVO",value = "用户名于密码组成的用户",dataType = "UserVO",example = "{username:'tom',password:'xxx'}"),

    })
    @PostMapping("/logout")
    public Result show(@RequestBody UserVO userVO){
        System.out.println("进入logout");
       redisTemplate.delete(userVO.getUsername());
        return new Result(true, StatusCode.OK,"注销成功");

    }

    @ApiOperation(value = "退出登陆",notes = "<span style='color:red;'>用来退出登陆</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "注销成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "userVO",value = "用户名于密码组成的用户",dataType = "UserVO",example = "{username:'tom',password:'xxx'}"),

    })
    @GetMapping("/findPlace")
    public Result findPlace(){
        List<User> place = userService.findPlace();
        ArrayList<String> places = new ArrayList<>();
        place.forEach(p->{
            places.add(p.getUsername());
        });
        return new Result(true, StatusCode.OK,"查询场馆成功",places);
    }

    @PostMapping("/findYPlace")
    @ResponseBody
    public Result findYPlace(@RequestBody YuJiaVO yuJiaVO){
        List<User> yPlace = userService.findYPlace(yuJiaVO.getYid());
        return new Result(true, StatusCode.OK,"查询发起众筹的场馆成功",yPlace);
    }

    //获取所有的学员信息
    @GetMapping("/findAllStudent")
    public Result findAllStudent(PageUserVo pageUserVo){
        //获取学员的对应的角色id(可根据学院姓名去数据库中查找)
        System.out.println("当前页" + pageUserVo.getCurrent());
        Integer rid = 1;
        Page<PageUserVo> allStudent = userService.findAllUser(pageUserVo);
        return new Result(true, StatusCode.OK,"查询所有学员信息成功",allStudent);
    }
    //获取所有的教练信息
    @GetMapping("/findAllCoach")
    public Result findAllCoach(PageUserVo pageUserVo){
        //获取学员的对应的角色id(可根据学院姓名去数据库中查找)
        Integer rid = 2;
        Page<PageUserVo> allCoach = userService.findAllUser(pageUserVo);
        return new Result(true, StatusCode.OK,"查询所有学员信息成功",allCoach);
    }
    //获取所有的场馆信息
    @GetMapping("/findAllGym")
    public Result findAllGym(PageGymVo pageGymVo){
        //获取学员的对应的角色id(可根据学院姓名去数据库中查找)
        Integer rid = 3;
        Page<PageGymVo> allGym = userService.findAllGym(pageGymVo);
        return new Result(true, StatusCode.OK,"查询所有学员信息成功",allGym);
    }

    //删除学员的信息
    @PostMapping("/delStudent/{id}")
    public Result delStudent(@PathVariable Integer id){
        System.out.println("删除id" + id);
        Boolean b = userService.delStudent(id);
        if(b){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    //修改学员的信息
    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody User user){
        System.out.println("要删除的信息" + user);
        Boolean f = userService.updateStudent(user);
        if(f){
            return new Result(true,StatusCode.OK,"修改成功");
        }else{
            return new Result(false,StatusCode.ERROR,"修改失败");
        }
    }

    //修改场馆的信息
    @PostMapping("updateGym")
    public Result updateGym(@RequestBody PageGymVo pageGymVo){
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //获取所有的管理员信息
    @GetMapping("/findAllAdmin")
    public Result findAllAdmin(PageUserVo pageUserVo){
        //获取学员的对应的角色id(可根据学院姓名去数据库中查找)
        Integer rid = 4;
        Page<PageUserVo> allAdmin = userService.findAllAdmin(pageUserVo);
        return new Result(true, StatusCode.OK,"查询所有学员信息成功",allAdmin);
    }


}

