package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yujia;
import com.woniu.yujiaweb.mapper.YujiaMapper;
import com.woniu.yujiaweb.service.YujiaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.MyYuJiaVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
@Service
public class YujiaServiceImpl extends ServiceImpl<YujiaMapper, Yujia> implements YujiaService {
    @Resource
    private YujiaMapper yujiaMapper;
    @Override
    public Page<Yujia> getPage(int current,int size) {
        Page<Yujia> yujiaPage = new Page<>(current, size);
        QueryWrapper<Yujia> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("joiner_num");
        yujiaMapper.selectPage(yujiaPage, wrapper);
        return yujiaPage;
    }

    @Override
    public List<MyYuJiaVO> findMyYuJia(int uid) {
        List<MyYuJiaVO> myYuJia = yujiaMapper.findMyYuJia(uid);
        return myYuJia;
    }
}
