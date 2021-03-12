package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.yujiaweb.domain.YujiaDetail;
import com.woniu.yujiaweb.mapper.YujiaDetailMapper;
import com.woniu.yujiaweb.service.YujiaDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.YujiaDetailVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
@Service
public class YujiaDetailServiceImpl extends ServiceImpl<YujiaDetailMapper, YujiaDetail> implements YujiaDetailService {

    @Resource
    private YujiaDetailMapper yujiaDetailMapper;

    //查看众筹项目的详情
    @Override
    public YujiaDetailVo queryYijiaDetail(Integer id) {
        QueryWrapper<YujiaDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("detail_id",id);
        YujiaDetail yujiaDetail = yujiaDetailMapper.selectOne(queryWrapper);

        //查询众筹项目的场馆名称
        String gymName = yujiaDetailMapper.findGymName(yujiaDetail.getPlId());
        //查询众筹项目的教练名称
        String couName = yujiaDetailMapper.findCouName(yujiaDetail.getCId());
        //一个项目的学员有多个 定义一个集合来存储uid的集合
        ArrayList<Integer> uid = new ArrayList<>();
        //定义一个集合 来存储用户姓名
        ArrayList<String> strings = new ArrayList<>();
        uid.add(yujiaDetail.getUId());
        uid.forEach(uid1->{
            //查询众筹项目的学员名称q
            String stuName = yujiaDetailMapper.findStuName(yujiaDetail.getUId());
            strings.add(stuName);
        });

        //存储项目详情信息
        YujiaDetailVo yujiaDetailVo = new YujiaDetailVo();
        yujiaDetailVo.setCName(couName);
        yujiaDetailVo.setPName(gymName);
        yujiaDetailVo.setSName(strings);
        yujiaDetailVo.setCourseDescribe(yujiaDetail.getCourseDescribe());
        yujiaDetailVo.setCoursePrice(yujiaDetail.getCoursePrice());

        return yujiaDetailVo;
    }
}
