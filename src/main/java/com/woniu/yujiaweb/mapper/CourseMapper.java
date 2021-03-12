package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface CourseMapper extends BaseMapper<Course> {

    @Insert("insert into t_course(u_id,course_name,course_money) values (#{uId},#{courseName},#{courseMoney})")
    int insertCourse(Course course);

}
