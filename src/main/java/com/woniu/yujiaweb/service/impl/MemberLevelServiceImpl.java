package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.MemberLevel;
import com.woniu.yujiaweb.mapper.MemberLevelMapper;
import com.woniu.yujiaweb.service.MemberLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.MemberUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-10
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

    @Resource
    private MemberLevelMapper memberLevelMapper;
    //查询所有的订单信息
    @Override
    public List<MemberLevel> findAllLevel() {
        List<MemberLevel> memberLevels = memberLevelMapper.selectList(null);
        return memberLevels ;
    }

    //修改指定的订单信息
    public boolean updateLevel(MemberLevel memberLevel){
        int i = memberLevelMapper.updateById(memberLevel);
        if(i > 0){
            return true;
        }
        return false;
    }

    //删除指定的用户信息
    @Override
    public boolean delLevelByid(Integer id) {
        int i = memberLevelMapper.deleteById(id);
        if(i > 0){
         return true;
        }
        return false;
    }

    //添加新的等级信息
    @Override
    public boolean addLevel(MemberLevel memberLevel) {
        int insert = memberLevelMapper.insert(memberLevel);
        if(insert > 0){
            return true;
        }
        return false;
    }

    //查询所有的用户会员等级信息
    @Override
    public IPage<MemberUserVo> findALlMember(MemberUserVo memberUserVo) {
        Page<MemberUserVo> memberUserVoPage = new Page<>(memberUserVo.getCurrent(),memberUserVo.getSize());

        QueryWrapper<MemberUserVo> memberUserVoQueryWrapper = new QueryWrapper<>();
        List<MemberUserVo> allMemberInfo = memberLevelMapper.findAllMemberInfo(memberUserVoPage, memberUserVoQueryWrapper);
        memberUserVoPage.setRecords(allMemberInfo);
        return memberUserVoPage;
    }


}
