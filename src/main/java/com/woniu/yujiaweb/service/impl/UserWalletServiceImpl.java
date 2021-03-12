package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.UserWallet;
import com.woniu.yujiaweb.mapper.UserWalletMapper;
import com.woniu.yujiaweb.service.UserWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yym
 * @since 2021-03-11
 */
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {
    @Resource
    private UserWalletMapper userWalletMapper;

    @Override
    public List<UserWallet> getMyWallet(String username) {
        List<UserWallet> userWallets = userWalletMapper.getMyWallet(username);
        return userWallets;
    }
}
