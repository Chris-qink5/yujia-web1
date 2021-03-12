package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.MemberUserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-10
 */
public interface MemberLevelService extends IService<MemberLevel> {

    //查询所有的会员等级信息
    public List<MemberLevel> findAllLevel();

    //修改指定的等级信息
    public boolean updateLevel(MemberLevel memberLevel);

    //删除指定的等级信息
    public boolean delLevelByid(Integer id);

    //添加新的等级信息
    public boolean addLevel(MemberLevel memberLevel);

    //分页查询所有的会员等级信息
    public IPage<MemberUserVo> findALlMember(MemberUserVo memberUserVo);
}
