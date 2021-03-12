package com.woniu.yujiaweb.controller;


import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserService;
import com.woniu.yujiaweb.service.impl.UserServiceImpl;
import com.woniu.yujiaweb.util.AliyunSmsUtils;
import com.woniu.yujiaweb.util.JWTUtil;
import com.woniu.yujiaweb.util.MailUtils;
import com.woniu.yujiaweb.util.SaltUtil;
import com.woniu.yujiaweb.vo.PageVO;
import com.woniu.yujiaweb.vo.UserInfoVO;
import com.woniu.yujiaweb.vo.UserVO;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yym
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

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
            //数据不为空
            String radio = (String) redisTemplate.opsForValue().get("radio");
            //userDB.getId()可以得到uid
            userService.saveUserAndRole(Integer.toString(userDB.getId()),radio);
        }
//      创建jwt,并将通过验证的用户保存到后端session中
        HashMap<String, String> map = new HashMap<>();
        map.put("username",userVO.getUsername());
        String jwtToken = JWTUtil.createToken(map);
        JWTUtil.getUsernames().add(userVO.getUsername());
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

//    @RequestMapping("findManue")
//    @ResponseBody
//    public Result findManue(@RequestBody UserVO userVO){
//        System.out.println("进入find"+userVO.getUsername());
//        List<Permission> rootManue = userService.findManue(userVO.getUsername());
//        return new Result(true, StatusCode.OK," ",rootManue);
//
//    }
//    @RequestMapping("findManue2")
//    @ResponseBody
//    public Result findManue2(@RequestBody UserVO userVO){
//        System.out.println("进入find"+userVO.getUsername());
//        List<Permission> rootManue = userService.findManue2(userVO.getUsername());
//        return new Result(true, StatusCode.OK," ",rootManue);
//
//    }

    @PostMapping("/logout")
    public Result show(@RequestBody UserVO userVO){
        System.out.println("进入logout");
        redisTemplate.delete(userVO.getUsername());
        return new Result(true, StatusCode.OK,"注销成功");

    }


    @GetMapping("page2")
    public Result find2Page(PageVO pageVO){
        Page<User> filmPage = new Page<>(pageVO.getCurrent(), pageVO.getSize());
        IPage<User> page = userService.page(filmPage, null);
        return new Result(true,StatusCode.OK,"分页查询成功",page);
    }

    @GetMapping("condition")
    public Result findByCondition(UserInfoVO userInfoVO){
        Page<User> page = userService.findByCondition(userInfoVO);
        return new Result(true, StatusCode.OK,"根据条件分页查询成功",page);
    }

    @GetMapping("/{filmId}")
    public Result findById(@PathVariable Integer filmId){
        User film = userService.getById(filmId);
        return new Result(true,StatusCode.OK,"根据ID查询成功",film);
    }



    @RequestMapping("page")
    @ResponseBody
    public Result findAllCourse(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("attention",1);
        List<User> users = userMapper.selectList(wrapper);
        return new Result(true, StatusCode.OK, "查询关注用户信息成功",users);
    }




    @RequestMapping("attention/{id}")
    @ResponseBody
    public Result attention(@PathVariable Integer id,User user){
        user.setAttention(1);
        userService.update(user);
        return new Result(true,StatusCode.OK,"关注成功");
    }

    @RequestMapping("deletedAttention/{id}")
    @ResponseBody
    public Result deletedAttention(@PathVariable Integer id,User user){
        user.setAttention(0);
        userService.update(user);
        return new Result(true,StatusCode.OK,"取消关注成功");
    }




}

