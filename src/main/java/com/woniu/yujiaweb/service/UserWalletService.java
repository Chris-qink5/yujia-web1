package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.UserWallet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yym
 * @since 2021-03-11
 */
public interface UserWalletService extends IService<UserWallet> {
    List<UserWallet> getMyWallet(String username);
}
