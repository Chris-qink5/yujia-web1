package com.woniu.yujiaweb.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public  class JWTUtil {
    private static String username;
    //存储用户名集合
    private static HashSet<String> usernames;
//    过期时间
    private static final Long EXPIRE_TIME=6*60*60*1000L;
//    签名
    private static  final String SIGN="qwertyuiopasdf";
    //    创建jwttoken
    public static String createToken(HashMap<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        builder.withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME));
        String token = builder.sign(Algorithm.HMAC256(SIGN));
        return token;
    }
//    分解jwt工具
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
    /** Base64URL 解码 */
    public static String decode(String input) {
        return new String(Base64.getUrlDecoder().decode(input));
    }

    public static void setUsername(String username) {
        JWTUtil.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static HashSet<String> getUsernames() {
        return usernames;
    }

    public static void setUsernames(HashSet<String> usernames) {
        JWTUtil.usernames = usernames;
    }
}
