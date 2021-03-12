package com.woniu.yujiaweb;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.service.UserService;
import com.woniu.yujiaweb.util.JWTUtil;
import com.woniu.yujiaweb.vo.CoachVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class YujiaWebApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
    }
    void JWTtest(){
//        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2Vybm1hZSI6IuWImOWkhyIsImV4cCI6MTYxNDg3NjkyM30.hsZUeMbr9pB7ZjNSXEgTnFOUFlQQcCqH72ZLJm_Z1T8";
//        DecodedJWT jwt= JWTUtil.verify(token);
//        System.out.println(jwt.getHeader());
//        System.out.println(jwt.getPayload());
//        System.out.println(jwt.getSignature());
//        System.out.println(jwt.getToken());
//        System.out.println(JWTUtil.decode(jwt.getPayload()));
//        System.out.println(JWTUtil.decode(jwt.getHeader()));
//        JSONObject jsonObject = JSONObject.parseObject(JWTUtil.decode(jwt.getPayload()));
//        String username = jsonObject.getString("usernmae");
//        System.out.println(username);
    }

    @Test
    public void test(){
        CoachVo coachVo = new CoachVo();
        coachVo.setCurrent(1);
        coachVo.setSize(5);

        QueryWrapper<CoachVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u.tel","2342");
//        queryWrapper.eq("sex","女");
        Page<CoachVo> allcoach = userService.getAllcoach(coachVo, queryWrapper);
        System.out.println(allcoach.getRecords());
    }

}
