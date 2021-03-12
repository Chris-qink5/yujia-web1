package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Yujia;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.MyYuJiaVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
public interface YujiaMapper extends BaseMapper<Yujia> {
    @Select("SELECT yj.`id`,yj.course_name,yj.sponsor,yj.star_time,yj.end_time," +
            "yd.course_price, yd.course_describe,yd.coach " +
            "FROM t_yujia yj " +
            "INNER JOIN t_user_yujia uyj ON yj.`id`=uyj.`yid` " +
            "INNER JOIN t_user u ON uyj.`uid`=u.`id` " +
            "INNER JOIN t_yujia_detail yd ON yj.`id`=yd.`yid` " +
            "WHERE u.`id`=#{uid}")

    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="course_name", property="courseName", jdbcType=JdbcType.VARCHAR),
            @Result(column="sponsor", property="sponsor", jdbcType=JdbcType.VARCHAR),
            @Result(column="star_time", property="starTime", jdbcType=JdbcType.DATETIMEOFFSET),
            @Result(column="end_time", property="endTime", jdbcType=JdbcType.DATETIMEOFFSET),
            @Result(column="course_price", property="coursePrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="course_describe", property="courseDescribe", jdbcType=JdbcType.VARCHAR),
            @Result(column="coach", property="coach", jdbcType=JdbcType.VARCHAR)
    })
    public List<MyYuJiaVO> findMyYuJia(int uid);
}
