package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
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
