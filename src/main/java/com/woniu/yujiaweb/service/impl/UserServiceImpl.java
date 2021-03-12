package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.UserInfoVO;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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

    @Override
    public Page<User> findByCondition(UserInfoVO userInfoVO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(StringUtils.hasLength(userInfoVO.getEmail())){
            wrapper.like("email",userInfoVO.getEmail());
        }
        if(StringUtils.hasLength(userInfoVO.getScore())){
            wrapper.like("score",userInfoVO.getScore());
        }
        if(StringUtils.hasLength(userInfoVO.getSex())){
            wrapper.like("sex",userInfoVO.getSex());
        }
        if(StringUtils.hasLength(userInfoVO.getTel())){
            wrapper.like("tel",userInfoVO.getTel());
        }
        if(StringUtils.hasLength(userInfoVO.getUsername())){
            wrapper.like("username",userInfoVO.getUsername());
        }
        if(!ObjectUtils.isEmpty(userInfoVO.getMinLength())){
            wrapper.ge("length",userInfoVO.getMinLength());
        }
        if(!ObjectUtils.isEmpty(userInfoVO.getMaxLength())){
            wrapper.le("length",userInfoVO.getMaxLength());
        }


        Page<User> filmPage = new Page<>(userInfoVO.getCurrent(), userInfoVO.getSize());


        return (Page<User>) userMapper.selectPage(filmPage, wrapper);
    }

    @Override
    public void saveUserAndRole(String uid, String rid) {
        userMapper.saveUserAndRole(uid,rid);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }




}
