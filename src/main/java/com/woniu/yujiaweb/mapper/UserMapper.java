package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into t_user_role (uid,rid) values (#{uid},#{rid})")
    void saveUserAndRole(String uid, String rid);

}
