package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.InfoVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yym
 * @since 2021-03-09
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("select ui.* " +
            "from t_user u " +
            "join t_user_info ui " +
            "on u.id=ui.uid " +
            "where u.username=#{username}")
    List<UserInfo> getUsersInfoByUsername(String username);
    @Update("update t_user_info ui " +
            "set ui.nickname=#{nickname},ui.bankCard=#{bankCard},ui.sex=#{sex},ui.secret=#{secret},ui.open=#{open}" +
            "where ui.username=#{username}")
    int updateUserInfoByUsername(String username);

}
