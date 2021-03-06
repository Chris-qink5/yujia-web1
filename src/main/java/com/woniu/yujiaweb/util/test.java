package com.woniu.yujiaweb.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;

public class test {
    public static void main(String[] args) {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2Vybm1hZSI6IuWImOWkhyIsImV4cCI6MTYxNDg3NjkyM30.hsZUeMbr9pB7ZjNSXEgTnFOUFlQQcCqH72ZLJm_Z1T8";
        DecodedJWT jwt=JWTUtil.verify(token);
        System.out.println(jwt.getHeader());
        System.out.println(jwt.getPayload());
        System.out.println(jwt.getSignature());
        System.out.println(jwt.getToken());
        System.out.println(JWTUtil.decode(jwt.getPayload()));
        System.out.println(JWTUtil.decode(jwt.getHeader()));
        JSONObject jsonObject = JSONObject.parseObject(JWTUtil.decode(jwt.getPayload()));
        String username = jsonObject.getString("usernmae");
        System.out.println(username);
    }
}
