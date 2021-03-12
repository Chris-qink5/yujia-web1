package com.woniu.yujiaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.MemberLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.MemberUserVo;
import com.woniu.yujiaweb.vo.PageUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-10
 */
public interface MemberLevelMapper extends BaseMapper<MemberLevel> {

    //查询所有的会员等级信息
    @Select("SELECT u.id,u.username,u.tel,ui.spend,ui.score,ml.level_name " +
            "FROM t_user AS u " +
            "JOIN t_user_info AS ui " +
            "ON u.id = ui.u_id " +
            "JOIN t_member_level as ml " +
            "ON ui.score BETWEEN ml.`start` AND ml.`end` " +
            "${ew.customSqlSegment}")
    public List<MemberUserVo> findAllMemberInfo(Page<MemberUserVo> page, @Param(Constants.WRAPPER) QueryWrapper<MemberUserVo> queryWrapper);
}
