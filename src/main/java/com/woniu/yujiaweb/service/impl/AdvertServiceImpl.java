package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Advert;
import com.woniu.yujiaweb.mapper.AdvertMapper;
import com.woniu.yujiaweb.service.AdvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.PageVO;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
@Service
public class AdvertServiceImpl extends ServiceImpl<AdvertMapper, Advert> implements AdvertService {

    @Resource
    private AdvertMapper advertMapper;
    //查询所有的广告信息
    @Override
    public IPage<Advert> getAllAdver(PageVO pageVo) {
        Page<Advert> advertPage = new Page<>(pageVo.getCurrent(),pageVo.getSize());
        IPage<Advert> allAdvert = advertMapper.selectPage(advertPage, null);
        return allAdvert;
    }

    //删除指定的广告信息
    @Override
    public boolean delAdvert(Integer id) {
        int i = advertMapper.deleteById(id);
        if(i > 0){
            return true;
        }
        return false;
    }

    //修改指定的广告信息
    @Override
    public boolean updateAdvert(Advert advert) {
        int i = advertMapper.updateById(advert);
        if(i > 0){
            return true;
        }
        return false;
    }

    //新增广告信息
    @Override
    public boolean addAdvert(Advert advert) {
        int insert = advertMapper.insert(advert);
        if(insert > 0){
            return true;
        }
        return false;
    }
}
