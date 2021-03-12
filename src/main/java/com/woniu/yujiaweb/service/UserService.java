package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
public interface UserService extends IService<User> {
    public List<Permission> findManue2(String username);
    //保存角色id
    void saveUserAndRole(String uid,String rid);
    public List<Permission> findManue(String username);
    public List<User>  findPlace();
    public List<User>  findYPlace(int yid);

}
