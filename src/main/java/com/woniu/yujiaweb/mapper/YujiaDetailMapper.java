package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.YujiaDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface YujiaDetailMapper extends BaseMapper<YujiaDetail> {

    //查询场馆的名称
    @Select("SELECT u.username FROM t_user AS u WHERE u.id = #{id}")
    public String findGymName(Integer id);
    //查询教练的名称
    @Select("SELECT u.username FROM t_user AS u WHERE u.id = #{id}")
    public String findCouName(Integer id);
    //查询用户的名称集合
    @Select("SELECT u.username FROM t_user AS u WHERE u.id = #{id}")
    public String findStuName(Integer id);
}
