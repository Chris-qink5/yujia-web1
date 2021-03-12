package com.woniu.yujiaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.PageGymVo;
import com.woniu.yujiaweb.vo.PageUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.jws.soap.SOAPBinding;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
public interface UserMapper extends BaseMapper<User> {

    //获取所有的学员信息
    @Select("SELECT u.id,u.username,u.tel,u.email,ur.rid,ui.score,ui.spend,ui.sex,ui.nickname,r.id " +
            "FROM t_user as u " +
            "JOIN t_user_role AS ur " +
            "on ur.uid = u.id " +
            "JOIN t_role as r " +
            "on r.id = ur.rid " +
            "JOIN t_user_info AS ui " +
            "ON ui.u_id = u.id " +
            "${ew.customSqlSegment}")
    List<PageUserVo> selectUserListPage(Page<PageUserVo> page,@Param(Constants.WRAPPER) QueryWrapper<PageUserVo> queryWrapper);

    //查询所有的场馆信息
    @Select("SELECT y.id,u.username,y.gym_tel,u.email,y.gym_name,y.gym_address,y.gym_description,y.gym_photo " +
            "FROM t_user as u " +
            "JOIN t_user_role AS ur " +
            "on ur.uid = u.id " +
            "JOIN t_role as r " +
            "on r.id = ur.rid " +
            "JOIN t_yogagyminfo as y " +
            "ON y.u_id = u.id " +
            "${ew.customSqlSegment}")
    public List<PageGymVo> queryAllGym(Page<PageGymVo> pageGymVoPage,@Param(Constants.WRAPPER) QueryWrapper<PageGymVo> queryWrapper);
}


