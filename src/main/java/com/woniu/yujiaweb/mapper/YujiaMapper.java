package com.woniu.yujiaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yujia;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.AYujiaVo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    //查询所有的众筹项目
    @Select("SELECT y.id,y.course_name,y.joiner_num,y.joiner_tar,y.star_time,y.end_time,y.status,u.username\n" +
            "FROM t_yujia as y " +
            "JOIN t_user AS u " +
            "on u.id = y.sponsor_id " +
            "${ew.customSqlSegment}")
    List<AYujiaVo> queryAllCJ(Page<AYujiaVo> page, @Param(Constants.WRAPPER) QueryWrapper<AYujiaVo> queryWrapper);

    //对状态未0的项目进行审核
    @Update("UPDATE t_yujia AS y SET y.status = 1 WHERE y.id = #{id}")
    public Integer aud(Integer id);
}
