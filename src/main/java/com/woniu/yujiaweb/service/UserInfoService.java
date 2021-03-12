package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.InfoVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yym
 * @since 2021-03-09
 */
public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> getUsersInfoByUsername(String username);
}
