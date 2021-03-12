package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    @Select("SELECT u.* FROM t_user u INNER JOIN t_user_role ur ON u.`id`=ur.`uid` AND ur.`rid`=3")
    public List<User> findPlace();

    @Select("SELECT u.* FROM t_user u INNER JOIN t_yujia_place yp ON u.`id`=yp.`pid` WHERE yp.`yid`=#{yid}")
    public List<User> findYPlace(int yid);
}
