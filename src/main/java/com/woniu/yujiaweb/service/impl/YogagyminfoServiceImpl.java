package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.woniu.yujiaweb.mapper.YogagyminfoMapper;
import com.woniu.yujiaweb.service.YogagyminfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.YogagyminfoVO;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-10
 */
@Service
public class YogagyminfoServiceImpl extends ServiceImpl<YogagyminfoMapper, Yogagyminfo> implements YogagyminfoService {

    @Resource
    private YogagyminfoMapper yogagyminfoMapper;
    @Override
    public Page<Yogagyminfo> findByCondition(YogagyminfoVO yogagyminfoVO) {
        QueryWrapper<Yogagyminfo> wrapper = new QueryWrapper<>();
        if(StringUtils.hasLength(yogagyminfoVO.getGymAddress())){
            wrapper.like("gymAddress",yogagyminfoVO.getGymAddress());
        }
        if(StringUtils.hasLength(yogagyminfoVO.getGymDescription())){
            wrapper.like("gymDescription",yogagyminfoVO.getGymDescription());
        }
        if(StringUtils.hasLength(yogagyminfoVO.getGymName())){
            wrapper.like("gymNam",yogagyminfoVO.getGymName());
        }
        if(StringUtils.hasLength(yogagyminfoVO.getGymTel())){
            wrapper.like("gymTel",yogagyminfoVO.getGymTel());
        }

        if(!ObjectUtils.isEmpty(yogagyminfoVO.getMinLength())){
            wrapper.ge("length",yogagyminfoVO.getMinLength());
        }
        if(!ObjectUtils.isEmpty(yogagyminfoVO.getMaxLength())){
            wrapper.le("length",yogagyminfoVO.getMaxLength());
        }


        Page<Yogagyminfo> filmPage = new Page<>(yogagyminfoVO.getCurrent(), yogagyminfoVO.getSize());


        return (Page<Yogagyminfo>) yogagyminfoMapper.selectPage(filmPage, wrapper);
    }

    @Override
    public void update(Yogagyminfo yogagyminfo) {
        yogagyminfoMapper.updateById(yogagyminfo);
    }
}
