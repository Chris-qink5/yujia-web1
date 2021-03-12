package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yujia;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.AYujiaVo;
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

    //分页查询所有的众筹项目
    public Page<AYujiaVo> findAllCJ(AYujiaVo yujiaVo);

    //对相应的众筹项目信息作出修改
    public boolean updateCj(AYujiaVo yujiaVo);

    //对所选中的项目进行删除
    public boolean delCJ(Integer id);

    //对达到要求的众筹项目进行审核
    public boolean aud(Integer id);
}
