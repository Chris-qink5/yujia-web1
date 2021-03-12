package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.domain.Yujia;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.mapper.YujiaMapper;
import com.woniu.yujiaweb.service.YujiaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.YujiaVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    //查询所有的瑜伽项目
    @Override
    public Page<YujiaVo> findAllCJ(YujiaVo yujiaVo) {
        Page<YujiaVo> yujiaVoPage = new Page<>(yujiaVo.getCurrent(),yujiaVo.getSize());
        QueryWrapper<YujiaVo> yujiaVoQueryWrapper = new QueryWrapper<>();
        yujiaVoQueryWrapper.eq("y.deleted",0);
        System.out.println(yujiaVo.getStartNumber());
        System.out.println(yujiaVo.getEndNumber());
        //按照项目模糊查询
        if(!ObjectUtils.isEmpty(yujiaVo.getCourseName())){
            yujiaVoQueryWrapper.like("course_name",yujiaVo.getCourseName());
        }
        //按照参与人数进行查询
        if(!ObjectUtils.isEmpty(yujiaVo.getStartNumber())&&!ObjectUtils.isEmpty(yujiaVo.getEndNumber())){
            if(yujiaVo.getEndNumber() >=yujiaVo.getStartNumber()){
                System.out.println("执行参与人数查询");
                yujiaVoQueryWrapper.between("joiner_num",yujiaVo.getStartNumber(),yujiaVo.getEndNumber());
            }
        }
        //按照目标人数进行查询
        if(!ObjectUtils.isEmpty(yujiaVo.getStartNumber())&&!ObjectUtils.isEmpty(yujiaVo.getEndNumber())){
            if(yujiaVo.getEndNumber() >=yujiaVo.getStartNumber()){
                System.out.println("执行目标人数查询");
                yujiaVoQueryWrapper.between("joiner_tar",yujiaVo.getStartNumber(),yujiaVo.getEndNumber());
            }
        }
        //再去进行分页查询
        List<YujiaVo> yujiaVos = yujiaMapper.queryAllCJ(yujiaVoPage, yujiaVoQueryWrapper);
        yujiaVoPage.setRecords(yujiaVos);
        return yujiaVoPage;
    }

    //对相应的众筹项目进行修改
    @Override
    public boolean updateCj(YujiaVo yujiaVo) {
        int i = yujiaMapper.updateById(yujiaVo);
        if(i> 0){
            return true;
        }
        return false;
    }

    //对所选中的项目进行修改
    @Override
    public boolean delCJ(Integer id) {
        int i = yujiaMapper.deleteById(id);
        if(i > 0){
            return true;
        }
        return false;
    }

    //对达到要求的众筹项目进行审核
    @Override
    public boolean aud(Integer id) {
        Integer aud = yujiaMapper.aud(id);
        if(aud > 0){
            return true;
        }
        return false;
    }


}
