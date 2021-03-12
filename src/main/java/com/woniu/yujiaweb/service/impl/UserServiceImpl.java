package com.woniu.yujiaweb.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.CoachVo;
import com.woniu.yujiaweb.vo.GymVo;
import com.woniu.yujiaweb.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
@Resource
private UserMapper userMapper;

    //查询所有教练
    @Override
    public Page<CoachVo> getAllcoach(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {

        Page<CoachVo> coachVoPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        Page<CoachVo> allcoach = userMapper.getAllcoach(coachVoPage, queryWrapper);

        return allcoach;
    }
    //查询所有学员
    @Override
    public Page<CoachVo> getAllstudent(CoachVo studentVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> coachVoPage = new Page<>(studentVo.getCurrent(),studentVo.getSize());
        Page<CoachVo> allstudent = userMapper.getAllstudent(coachVoPage, queryWrapper);

        return allstudent;
    }



    @Override
    public GymVo findgym(String tel) {
        return userMapper.findgym(tel);
    }

    @Override
    public void updateByTel(GymVo gymVo) {
            userMapper.UpdateByTel(gymVo);
    }


    @Override
    public Page<CoachVo> selectcoachinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> coachVoPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        Page<CoachVo> allcoach = userMapper.selectcocahinfo(coachVoPage, queryWrapper);
        return allcoach;
    }

    @Override
    public Integer selectcoachid(String tel) {
        return userMapper.selectcoachid(tel);
    }

    @Override
    public Integer selectgymid(String tel) {
        return userMapper.selectgymid(tel);
    }

    @Override
    public void deletecgymBycIDandgId(Integer coachid, Integer gym) {
        userMapper.deletecgymBycIDandgId(coachid,gym);
    }

    @Override
    public void insertcoach(Integer coachid,Integer gymid) {
        userMapper.insertcoach(coachid,gymid);
    }

    @Override
    public List<Integer> querygymBycoachId(Integer coachid) {
        return userMapper.querygymBycoachId(coachid);


    }

    @Override
    public GymVo Querygym(Integer gymid) {
        return userMapper.Querygym(gymid);
    }

    @Override
    public List<Integer> querycoachBystudentTel(String tel) {
        return userMapper.querycoachBystudentTel(tel);
    }

    @Override
    public CoachVo querycoachBycoachId(Integer id) {
        return userMapper.querycoachBycoachId(id);
    }



    @Override
    public Page<CoachVo> selectStudentinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {

        Page<CoachVo> coachVoPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        Page<CoachVo> allstudent = userMapper.selectStudentinfo(coachVoPage, queryWrapper);
        return allstudent;
    }

    @Override
    public Page<CoachVo> findclassinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> classPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        return userMapper.findclassinfo(classPage,queryWrapper);
    }

    @Override
    public Integer queryclassIdByclassName(String courseName) {
        return userMapper.queryclassIdByclassName(courseName);
    }

    @Override
    public void deleteclass(Integer coachid, Integer classId) {
        userMapper.deleteclass(coachid,classId);
    }

    @Override
    public Page<CoachVo> findattentiongym(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> attentiongymPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        return userMapper.findattentiongym(attentiongymPage,queryWrapper);
    }


}
