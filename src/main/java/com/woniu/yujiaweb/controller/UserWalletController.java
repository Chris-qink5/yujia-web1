package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.woniu.yujiaweb.domain.UserWallet;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.UserWalletService;
import com.woniu.yujiaweb.vo.WalletVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yym
 * @since 2021-03-11
 */
@Controller
@RequestMapping("/userWallet")
public class UserWalletController {
    @Resource
    private UserWalletService userWalletService;
    @GetMapping("/getMyWallet")
    @ResponseBody
    public Result getMyWallet(String username){
        return new Result(true, StatusCode.OK,"查询个人信息成功",userWalletService.getMyWallet(username));
    }
    @PostMapping("/recharge")
    @ResponseBody
    public Result recharge(@RequestBody WalletVO walletVO){
        //充值就是更改t_user_wallet数据库
        QueryWrapper<UserWallet> wrapper = new QueryWrapper<>();
        wrapper.eq("username",walletVO.getUsername());
        UserWallet userWallet = userWalletService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(userWallet)){
            //数据不为空就修改数据
            Double walletBalance = userWallet.getBalance();
            userWallet=new UserWallet();
            userWallet.setBalance(walletBalance+walletVO.getBalance());
            userWalletService.update(userWallet,wrapper);
        }
        return new Result(true, StatusCode.OK,"充值成功");
    }
}

