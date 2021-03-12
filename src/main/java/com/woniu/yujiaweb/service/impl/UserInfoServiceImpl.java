package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.yujiaweb.domain.UserInfo;
import com.woniu.yujiaweb.mapper.UserInfoMapper;
import com.woniu.yujiaweb.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.InfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yym
 * @since 2021-03-09
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public List<UserInfo> getUsersInfoByUsername(String username) {
        List<UserInfo> userInfos = userInfoMapper.getUsersInfoByUsername(username);
        return userInfos;
    }



}
