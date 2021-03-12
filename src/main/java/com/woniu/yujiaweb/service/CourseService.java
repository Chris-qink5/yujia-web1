package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface CourseService extends IService<Course> {

    int insertCourse(Course course);

    int updateCourse(Course course);

    int deletedCourseByid(Integer id);
}
