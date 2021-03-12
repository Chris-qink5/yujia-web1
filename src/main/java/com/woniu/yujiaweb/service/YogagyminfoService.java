package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface YogagyminfoService extends IService<Yogagyminfo> {

    //修改场馆的详细信息
    public boolean updateGym(Yogagyminfo yogagyminfo);
}
