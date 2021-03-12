package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.Advert;
import com.woniu.yujiaweb.domain.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.PageVO;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface PostService extends IService<Post> {
    //分页查询所有的帖子信息
    public IPage<Post> getAllPost(PageVO pageVo);

    //删除指定的帖子信息
    public boolean delPost(Integer id);

    //修改指定的帖子信息
    public boolean updatePost(Post psot);

    //新增帖子信息
    public boolean addPost(Post post);

    public boolean removeTop(Post post);

    public boolean top(Post post);
}
