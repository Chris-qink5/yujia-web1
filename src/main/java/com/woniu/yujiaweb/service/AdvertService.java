package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Advert;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface AdvertService extends IService<Advert> {

    //分页查询所有的广告信息
    public IPage<Advert> getAllAdver(PageVO pageVo);

    //删除指定的广告信息
    public boolean delAdvert(Integer id);

    //修改指定的广告信息
    public boolean updateAdvert(Advert advert);

    //新增广告信息
    public boolean addAdvert(Advert advert);

}
