package com.woniu.yujiaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yujia;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.PageGymVo;
import com.woniu.yujiaweb.vo.PageUserVo;
import com.woniu.yujiaweb.vo.YujiaVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    //查询所有的众筹项目
    @Select("SELECT y.id,y.course_name,y.joiner_num,y.joiner_tar,y.star_time,y.end_time,y.status,u.username\n" +
            "FROM t_yujia as y " +
            "JOIN t_user AS u " +
            "on u.id = y.sponsor_id " +
            "${ew.customSqlSegment}")
    List<YujiaVo> queryAllCJ(Page<YujiaVo> page, @Param(Constants.WRAPPER) QueryWrapper<YujiaVo> queryWrapper);

    //对状态未0的项目进行审核
    @Update("UPDATE t_yujia AS y SET y.status = 1 WHERE y.id = #{id}")
    public Integer aud(Integer id);
}
