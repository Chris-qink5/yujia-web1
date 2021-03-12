package com.woniu.yujiaweb.service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yym
 * @since 2021-03-08
 */
public interface RedisService {

    //存储数据
    void set(String key, String value);
    //获得数据
    String get(String key);
    //设置超时时间
    boolean expire(String key, long expire);
    //删除数据
    void remove(String key);
    //自增操作
    Long increment(String key, long delta);
}
