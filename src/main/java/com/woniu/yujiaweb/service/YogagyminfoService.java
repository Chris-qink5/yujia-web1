package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.UserInfoVO;
import com.woniu.yujiaweb.vo.YogagyminfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-10
 */
public interface YogagyminfoService extends IService<Yogagyminfo> {
    Page<Yogagyminfo> findByCondition(YogagyminfoVO yogagyminfoVO);

    void update(Yogagyminfo yogagyminfo);
}
