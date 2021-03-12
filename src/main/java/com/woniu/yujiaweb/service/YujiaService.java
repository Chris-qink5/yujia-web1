package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yujia;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.MyYuJiaVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
public interface YujiaService extends IService<Yujia> {
    public Page<Yujia> getPage(int current,int size);
    public List<MyYuJiaVO> findMyYuJia(int uid);
}
