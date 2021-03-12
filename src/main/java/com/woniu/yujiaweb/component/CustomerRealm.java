package com.woniu.yujiaweb.component;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.PermissionService;
import com.woniu.yujiaweb.service.RoleService;
import com.woniu.yujiaweb.service.UserService;
import com.woniu.yujiaweb.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    //进行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权开始");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取token中的数据
        String token =(String) principalCollection.getPrimaryPrincipal();
        //解析token
        DecodedJWT verify = JWTUtil.verify(token);
        //获取token中存入的用户名 要通过asString()进行转换
        String username = verify.getClaim("username").asString();

//        //根据用户名去查询数据库中的权限
//        Integer uid = userService.findUserIdByName(username);
//        List<String> hasPermissionElements = permissionService.getHasPermissionElements(uid);
//        //循环遍历该用户所拥有的权限  并进行授权
//        hasPermissionElements.forEach(element->{
//            simpleAuthorizationInfo.addRole(element);
//        });

        //根据用户名去查询其所属的用户角色
        String rolename= roleService.findRolenameByUserName(username);
        System.out.println("角色名称");
        simpleAuthorizationInfo.addRole(rolename);

        return simpleAuthorizationInfo;
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
