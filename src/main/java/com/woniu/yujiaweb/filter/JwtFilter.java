package com.woniu.yujiaweb.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.woniu.yujiaweb.util.JWTUtil;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;

@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
//    解决跨域问题
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse response1 = WebUtils.toHttp(response);
        response1.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        response1.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response1.setHeader("Access-Control-Allow-Headers",httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response1.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request,response);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        //如果没带token，意味着匿名访问，直接放行，由shiro的鉴权操作进行处理
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest =(HttpServletRequest) request;
        String token = httpRequest.getHeader("Token");
        return token!=null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        int isExist=0;
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String token = httpRequest.getHeader("Token");
        System.out.println("令牌"+token);
        DecodedJWT jwt= JWTUtil.verify(token);
        JSONObject jsonObject = JSONObject.parseObject(JWTUtil.decode(jwt.getPayload()));
        String usernameJwt = jsonObject.getString("username");
        Long exp = jsonObject.getLong("exp");
//        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        HashSet<String> usernames = JWTUtil.getUsernames();
        for(String s:usernames){
            System.out.println(s);
            if(s.equals(usernameJwt)){
                isExist++;
            }
        }

        System.out.println("usernameJwt");
        if (isExist>0) {
            System.out.println("用户验证通过");
            if (System.currentTimeMillis()/1000<=exp) {
                System.out.println("时效验证通过");
                return true;
            }else {return false;}
        }else {
        return false;}


    }


}
