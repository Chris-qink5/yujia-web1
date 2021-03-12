package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniu.yujiaweb.domain.CMessage;
import com.woniu.yujiaweb.domain.Course;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.CourseService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    @Resource
    private  CourseService courseService;

    @RequestMapping("findAllCourse")
    @ResponseBody
    public Result findAllCourse(){
        List<Course> list = courseService.list(null);
        return new Result(true, StatusCode.OK,
                "查询所有信息成功", list);
    }



    @ResponseBody
    @RequestMapping("insertCourse")
    public Result insertCourse(@RequestBody Course course){
        int i = courseService.insertCourse(course);
        if(i>0){
            return new Result(true,StatusCode.OK,"新增成功");
        }else{
            return new Result(true,StatusCode.ACCESSERROR,"当前课程已存在");
        }
    }



    @DeleteMapping("deletedCourse/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable Integer id){
        courseService.removeById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }




}

