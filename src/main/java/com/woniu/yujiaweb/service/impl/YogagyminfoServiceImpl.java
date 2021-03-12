package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.woniu.yujiaweb.mapper.YogagyminfoMapper;
import com.woniu.yujiaweb.service.YogagyminfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class YogagyminfoServiceImpl extends ServiceImpl<YogagyminfoMapper, Yogagyminfo> implements YogagyminfoService {

    @Resource
    private YogagyminfoMapper yogagyminfoMapper;

    //修改场馆的信息
    @Override
    public boolean updateGym(Yogagyminfo yogagyminfo) {
        int i = yogagyminfoMapper.updateById(yogagyminfo);
        if(i > 0){
            return true;
        }
        return false;
    }
}
