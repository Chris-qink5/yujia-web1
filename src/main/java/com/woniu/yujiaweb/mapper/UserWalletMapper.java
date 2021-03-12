package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.UserWallet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yym
 * @since 2021-03-11
 */
public interface UserWalletMapper extends BaseMapper<UserWallet> {
    @Select("select uw.* " +
            "from t_user_wallet uw " +
            "where uw.username=#{username}")
    List<UserWallet> getMyWallet(String username);
}
