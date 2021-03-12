package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.YujiaDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.YujiaDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface YujiaDetailService extends IService<YujiaDetail> {

    //查看项目的详细信息
    public YujiaDetailVo queryYijiaDetail(Integer id);
}
