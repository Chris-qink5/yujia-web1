package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yym
 * @since 2021-03-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public List<Permission> findManue(String username) {
        System.out.println(username+"进入服务层");
        int id =(int) redisTemplate.opsForValue().get(username);

        List<Permission> permissions = userMapper.findManue(id);
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {

                rootManue.add(permission);

        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        System.out.println(rootManue);
        return rootManuelist;

    }
    public List<Permission> findManue2(String username) {
        System.out.println(username+"进入服务层");
        int id =(int) redisTemplate.opsForValue().get(username);

        List<Permission> permissions = userMapper.findManue2(id);
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {

            rootManue.add(permission);

        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        System.out.println(rootManue);
        return rootManuelist;
    @Override
    public List<Permission> findManue(String username) {
        System.out.println(username+"进入服务层");
        int id =(int) redisTemplate.opsForValue().get(username);

        List<Permission> permissions = userMapper.findManue(id);
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {

            rootManue.add(permission);

        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        System.out.println(rootManue);
        return rootManuelist;

    }
    public List<Permission> findManue2(String username) {
        System.out.println(username+"进入服务层");
        int id =(int) redisTemplate.opsForValue().get(username);

        List<Permission> permissions = userMapper.findManue2(id);
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {

            rootManue.add(permission);

        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        System.out.println(rootManue);
        return rootManuelist;

    }

    @Override
    public void saveUserAndRole(String uid, String rid) {
        //向数据库保存数据
        userMapper.saveUserAndRole(uid,rid);
    }

    /**
     * 去重
     */
    public  List<Permission> removeDuplicate(List<Permission> list){
        List<Permission> listTemp = new ArrayList<Permission>();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
    @Override
    public List<User> findPlace() {
        List<User> place = userMapper.findPlace();
        return place;
    }

    @Override
    public List<User> findYPlace(int yid) {
        List<User> yPlace = userMapper.findYPlace(yid);
        return yPlace;
    }

    /**
     * 去重
     */
    public  List<Permission> removeDuplicate(List<Permission> list){
        List<Permission> listTemp = new ArrayList<Permission>();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
}
