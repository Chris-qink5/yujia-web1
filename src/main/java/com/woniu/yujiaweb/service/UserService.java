package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.UserInfoVO;

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
    Page<User> findByCondition(UserInfoVO userInfoVO);

    void saveUserAndRole(String uid,String rid);

    void update(User user);

//    void updateById(Integer id);
}
