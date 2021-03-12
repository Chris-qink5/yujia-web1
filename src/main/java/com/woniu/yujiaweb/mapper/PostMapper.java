package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface PostMapper extends BaseMapper<Post> {

    @Update("update t_post set top = '否' where id =#{id}")
    public Integer removeTop(Post post);

    @Update("update t_post set top = '是' where id =#{id}")
    public Integer Top(Post post);
}
