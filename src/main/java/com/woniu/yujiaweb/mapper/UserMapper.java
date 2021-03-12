package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Permission;
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
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT p.* FROM t_permission p INNER JOIN t_role_permission rp ON p.`id`=rp.`pid` " +
            "            INNER JOIN t_role r ON rp.`rid`=r.`id`  " +
            "            INNER JOIN t_user_role ur ON r.`id`=ur.`rid` " +
            "            INNER JOIN `t_user` u ON ur.`uid`=u.`id` WHERE u.`id`=#{uid} AND p.`perssion_level`=2")
    public List<Permission> findManue(int uid);
    @Select("SELECT p.* FROM t_permission p INNER JOIN t_role_permission rp ON p.`id`=rp.`pid` " +
            "            INNER JOIN t_role r ON rp.`rid`=r.`id`  " +
            "            INNER JOIN t_user_role ur ON r.`id`=ur.`rid` " +
            "            INNER JOIN `t_user` u ON ur.`uid`=u.`id` WHERE u.`id`=#{uid} AND p.`perssion_level`=1")
    public List<Permission> findManue2(int uid);

    @Insert("insert into t_user_role (uid,rid) values (#{uid},#{rid})")
    void saveUserAndRole(String uid,String rid);

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
    @Select("SELECT u.* FROM t_user u INNER JOIN t_user_role ur ON u.`id`=ur.`uid` AND ur.`rid`=3")
    public List<User> findPlace();

    @Select("SELECT u.* FROM t_user u INNER JOIN t_yujia_place yp ON u.`id`=yp.`pid` WHERE yp.`yid`=#{yid}")
    public List<User> findYPlace(int yid);
}


