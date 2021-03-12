package com.woniu.yujiaweb.controller;


import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.UserService;
import com.woniu.yujiaweb.service.impl.UserServiceImpl;
import com.woniu.yujiaweb.util.JWTUtil;
import com.woniu.yujiaweb.vo.PageGymVo;
import com.woniu.yujiaweb.vo.PageUserVo;
import com.woniu.yujiaweb.vo.PageVo;
import com.woniu.yujiaweb.vo.UserVO;
import com.woniu.yujiaweb.vo.YuJiaVO;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserService userService;
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

//      创建jwt,并将通过验证的用户保存到后端session中
        HashMap<String, String> map = new HashMap<>();

        map.put("username",userVO.getUsername());
        String jwtToken = JWTUtil.createToken(map);
        JWTUtil.getUsernames().add(userVO.getUsername());
        System.out.println(jwtToken);
        System.out.println("结束login");

        return new Result(true, StatusCode.OK,"登陆成功",jwtToken);

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
    //退出登录
    @GetMapping("/logout")
    public Result logout(){
        return new Result(true, StatusCode.OK,"登陆成功");
    }

    @PostMapping("/show")
    public Result show(){
        System.out.println("进入show");
        redisTemplate.opsForValue().set("name","tom");
        return new Result(true, StatusCode.OK,"登陆成功");

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

