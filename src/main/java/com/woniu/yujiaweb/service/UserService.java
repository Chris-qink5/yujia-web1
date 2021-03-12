package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.PageGymVo;
import com.woniu.yujiaweb.vo.PageUserVo;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import java.util.List;
import com.woniu.yujiaweb.vo.CoachVo;
import com.woniu.yujiaweb.vo.GymVo;
import com.woniu.yujiaweb.vo.PageVo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
public interface UserService extends IService<User> {
    public List<Permission> findManue(String username);
    public List<Permission> findManue2(String username);
    //保存角色id
    void saveUserAndRole(String uid,String rid);
    public List<User>  findPlace();
    public List<User>  findYPlace(int yid);


    //查询所有的学员信息
    public Page<PageUserVo> findAllUser(PageUserVo pageUserVo);

    //根据学员id删除学员信息
    public Boolean delStudent(Integer userId);

    //根据学院id修改学员信息
    public Boolean updateStudent(User user);

    //条件分页查询场馆的信息
    public Page<PageGymVo> findAllGym(PageGymVo pageGymVo);

    //修改场馆的信息
    public boolean updateGym(PageGymVo pageGymVo);

    //查询所有的管理员信息
    public Page<PageUserVo> findAllAdmin(PageUserVo pageUserVo);



        //查询所有教练
    Page<CoachVo> getAllcoach( CoachVo coachVo,QueryWrapper<CoachVo> queryWrapper);

    //查询所有学员
    Page<CoachVo> getAllstudent( CoachVo coachVo,QueryWrapper<CoachVo> queryWrapper);
    //查询教练签约情况


    GymVo findgym(String tel);

    void updateByTel(GymVo gymVo);


Page<CoachVo> selectcoachinfo( CoachVo coachVo,QueryWrapper<CoachVo> queryWrapper);

    Integer selectcoachid(String tel);

    Integer selectgymid(String tel);

    void deletecgymBycIDandgId(Integer coachid, Integer gym);

    void insertcoach(Integer coachid, Integer gymid);

    List<Integer> querygymBycoachId(Integer coachid);

        GymVo Querygym(Integer gymid);

    List<Integer> querycoachBystudentTel(String tel);

    CoachVo querycoachBycoachId(Integer id);


Page<CoachVo> selectStudentinfo( CoachVo coachVo,QueryWrapper<CoachVo> queryWrapper);

    Page<CoachVo> findclassinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper);

    Integer queryclassIdByclassName(String courseName);

    void deleteclass(Integer coachid, Integer classId);

    Page<CoachVo> findattentiongym(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper);
}
