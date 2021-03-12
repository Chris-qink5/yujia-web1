package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.yujiaweb.domain.Course;
import com.woniu.yujiaweb.mapper.CourseMapper;
import com.woniu.yujiaweb.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseMapper courseMapper;



    @Override
    public int insertCourse(Course course) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name",course.getCourseName());
        Course course1 = courseMapper.selectOne(queryWrapper);
        if(ObjectUtils.isEmpty(course1)){
            return courseMapper.insert(course);
        }else{
            return 0;
        }
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateById(course);
    }

    @Override
    public int deletedCourseByid(Integer id) {
        return courseMapper.deleteById(id);
    }


}
