package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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



}
