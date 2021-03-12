package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import com.woniu.yujiaweb.vo.CoachVo;
import com.woniu.yujiaweb.vo.GymVo;
import org.apache.ibatis.annotations.*;


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
    //查询所有教练
    @Select("SELECT u.tel,c.* " +
            "FROM t_user u " +
            "JOIN t_user_role ur " +
            "ON u.id =ur.uid " +
            "JOIN t_role r " +
            "ON ur.rid=r.id " +
            "JOIN t_cmessage c " +
            "ON u.id=c.coach_id " +
            "${ew.customSqlSegment}")
    Page<CoachVo> getAllcoach(Page<CoachVo> page,@Param(Constants.WRAPPER) QueryWrapper<CoachVo> queryWrapper);

    //查询所有学员
    @Select("SELECT ui.*, u.tel " +
            "FROM t_user u " +
            "JOIN t_user_role ur " +
            "ON u.id =ur.uid " +
            "JOIN t_role r " +
            "ON ur.rid=r.id " +
            "JOIN t_user_info ui " +
            "ON u.id=ui.u_id " +
            "${ew.customSqlSegment}")
    Page<CoachVo> getAllstudent(Page<CoachVo> page,@Param(Constants.WRAPPER) QueryWrapper<CoachVo> queryWrapper);

//查询教练签约情况

    //根据教练id查询场馆id
    @Select("SELECT c.yogagym_id " +
            "FROM t_cyogagym c " +
            "WHERE c.coach_id=#{coachid}")
 List<Integer> querygymBycoachId(Integer coachid);
    //查看场馆信息
    @Select("SELECT y.* " +
            "FROM t_user u " +
            "join t_yogagyminfo y " +
            "ON u.id=y.u_id  " +
            "WHERE u.id=(SELECT u.id " +
            "FROM t_user u " +
            "WHERE u.tel=#{tel})")
    GymVo findgym(String tel);
    //修改场馆信息
    @Update("UPDATE t_yogagyminfo " +
            "SET gym_name=#{gymName} " +
            ",gym_description=#{gymDescription} " +
            ", gym_address=#{gymAddress} " +
            ",gym_photo=#{gymPhoto}" +
            "WHERE gym_tel=#{gymTel}")
    void UpdateByTel(GymVo gymVo);

   //查询签约教练
    @Select("SELECT DISTINCT c.nickname,u.tel,c.description,c.sex,c.birthday " +
            "FROM t_cmessage c " +
            "JOIN t_user u " +
            "ON u.id=c.coach_id " +
            "WHERE c.coach_id in ( " +
            "SELECT coach_id " +
            "FROM t_cyogagym  " +
            "WHERE yogagym_id=(SELECT u.id " +
            "FROM t_user u " +
            "${ew.customSqlSegment}) " +
            ") ")
    Page<CoachVo> selectcocahinfo(Page<CoachVo> page,@Param(Constants.WRAPPER) QueryWrapper<CoachVo> queryWrapper);
    //通过电话查询教练id
    @Select("SELECT u.id " +
            "FROM t_user u " +
            "WHERE u.tel=#{tel}")
    Integer selectcoachid(String tel);
    //通过场馆电话查询场馆id
    @Select("SELECT u.id " +
            "FROM t_user u " +
            "WHERE u.tel=#{tel}")
    Integer selectgymid(String tel);
        //根据场馆id和教练id删除关联，解约教练
    @Delete("DELETE FROM t_cyogagym WHERE " +
            "coach_id=#{coachid} AND yogagym_id=#{gym} ")
    void deletecgymBycIDandgId(Integer coachid, Integer gym);

    //根据教练id和场馆id签约教练
    @Insert("INSERT INTO t_cyogagym(coach_id,yogagym_id) " +
            "VALUES(#{coachid},#{gymid})")
    void insertcoach(Integer coachid, Integer gymid);
    //根据场馆id查询场馆信息
    @Select("SELECT y.gym_name " +
            "FROM t_yogagyminfo y " +
            "WHERE y.u_id=#{gymid}")
    GymVo Querygym(Integer gymid);
    //根据学生id查询签约教练id
    @Select("SELECT cs.coach_id " +
            "FROM t_cstudent cs " +
            "WHERE cs.user_id=(SELECT u.id " +
            "FROM t_user u " +
            "WHERE u.tel=#{tel} " +
            ")")
    List<Integer> querycoachBystudentTel(String tel);
    //通过教练id查询教练信息
    @Select("SELECT c.nickname " +
            "FROM t_cmessage c " +
            "WHERE c.coach_id=#{id}")
    CoachVo querycoachBycoachId(Integer id);
   //查询场馆学员
    @Select("SELECT DISTINCT ui.nickname,u.tel,ui.score,ui.sex,ui.spend,ui.user_head_portrait " +
            "FROM t_user u " +
            "JOIN t_user_info ui " +
            "ON u.id=ui.u_id " +
            "WHERE  ui.u_id  in( " +
            "SELECT c.user_id " +
            "FROM t_cstudent c " +
            "WHERE c.coach_id in ( " +
            "SELECT coach_id " +
            "FROM t_cyogagym  " +
            "WHERE yogagym_id=(SELECT u.id " +
            "FROM t_user u " +
            "${ew.customSqlSegment}) " +
            ") " +
            ")")
    Page<CoachVo> selectStudentinfo(Page<CoachVo> page,@Param(Constants.WRAPPER) QueryWrapper<CoachVo> queryWrapper);
    //查询出课程信息
    @Select("SELECT DISTINCT co.course_name,co.course_money,cm.nickname,cm.description,u.tel\n" +
            "FROM t_course co " +
            "JOIN t_cmessage cm " +
            "on cm.coach_id=co.u_id " +
            "JOIN t_user u " +
            "on u.id=cm.coach_id " +
            "WHERE co.id in ( " +
            "SELECT DISTINCT c.course_id " +
            "FROM t_ccourse c " +
            "WHERE c.coach_id in ( " +
            "SELECT coach_id " +
            "FROM t_cyogagym  " +
            "WHERE yogagym_id=(SELECT u.id " +
            "FROM t_user u " +
            "${ew.customSqlSegment}) " +
            ") " +
            ") ")
    Page<CoachVo> findclassinfo(Page<CoachVo> page,@Param(Constants.WRAPPER) QueryWrapper<CoachVo> queryWrapper);
    //课程名字查id
    @Select("SELECT cc.id " +
            "FROM t_course cc " +
            "WHERE cc.course_name=#{courseName}")
    Integer queryclassIdByclassName(String courseName);
    //下架课程
    @Delete("DELETE FROM t_ccourse  WHERE t_ccourse.coach_id=#{coachid} " +
            "and  t_ccourse.course_id=#{classId}")
    void deleteclass(Integer coachid, Integer classId);

//查询关注我的教练
    @Select("SELECT c.nickname, c.description, c.sex, c.site, u.tel,c.head_photo " +
            "FROM t_cmessage c "+
            "JOIN t_user u " +
            "ON u.id=c.coach_id " +
            "WHERE c.coach_id in ( " +
            "SELECT cg.coach_id " +
            "FROM  t_cattention  cg " +
            "${ew.customSqlSegment} " +
            ")")
    Page<CoachVo> findattentiongym(Page<CoachVo> page,@Param(Constants.WRAPPER) QueryWrapper<CoachVo> queryWrapper);
}


