package com.woniu.yujiaweb.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
         return true;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //进入认证
        System.out.println("进入认证");
        String username = (String)authenticationToken.getPrincipal();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User userDB = userMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(userDB)) {
            redisTemplate.opsForValue().set(userDB.getUsername(),userDB.getId());
            return new SimpleAuthenticationInfo(userDB,
                userDB.getPassword(),
                ByteSource.Util.bytes(userDB.getSalt()),
                this.getName());
        }
        return null;
    }
}
