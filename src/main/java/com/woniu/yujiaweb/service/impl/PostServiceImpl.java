package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Advert;
import com.woniu.yujiaweb.domain.Post;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.PostMapper;
import com.woniu.yujiaweb.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.PageVo;
import io.lettuce.core.RedisClient;
import javafx.geometry.Pos;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
 * @since 2021-03-09
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Resource
    private PostMapper postMapper;
    @Resource
    private RedisTemplate<String,Post> redisTemplate;

    //分页查询所有的帖子信息
    @Override
    public IPage<Post> getAllPost(PageVo pageVo) {
//        //查询数据的流程 先从redis缓存中去拿取数据  如果没有 再去数据库中查询
//
//        //判断查询出来的数据是否为空
//        if (ObjectUtils.isEmpty(post)) {
//            Page<Post> postPage = new Page<>(pageVo.getCurrent(),pageVo.getSize());
//            IPage<Post> allPost = postMapper.selectPage(postPage, null);
//        }
        Page<Post> postPage = new Page<>(pageVo.getCurrent(),pageVo.getSize());
        IPage<Post> allPost = postMapper.selectPage(postPage, null);
//
//        redisTemplate.opsForZSet().add(pageVo.getCurrent().toString(),allPost,1);
//        //将查询出来的数据放在redis中
//        redisTemplate.opsForList().leftPush("post", allPost);

//        Page<Post> postPage = new Page<>(pageVo.getCurrent(),pageVo.getSize());
//        System.out.println("总数" + postPage.getTotal());
//        //查询所有的帖子信息
//        List<Post> posts = postMapper.selectList(null);
//        //将数据存储到redis中
//        posts.forEach(post->{
//            redisTemplate.opsForList().rightPush("post", post);
//        });
//        Integer left = pageVo.getCurrent() * pageVo.getSize();
//        Integer right = left + pageVo.getSize() -1;
//        List<Post> post = redisTemplate.opsForList().range("post", left, right);
        return allPost;
    }

    //删除指定的帖子信息
    @Override
    public boolean delPost(Integer id) {
        int i = postMapper.deleteById(id);
        if(i > 0){
            return true;
        }
        return false;
    }

    //修改帖子的内容
    @Override
    public boolean updatePost(Post post) {
        int update = postMapper.updateById(post);
        if(update > 0){
            return true;
        }
        return false;
    }

    //新增帖子内容
    @Override
    public boolean addPost(Post post) {
        int insert = postMapper.insert(post);
        if(insert > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTop(Post post) {
        int update = postMapper.removeTop(post);
        if (update > 0) {
            return  true;
        }
        return false;
    }

    @Override
    public boolean top(Post post) {
        int update = postMapper.Top(post);
        if (update > 0) {
            return  true;
        }
        return false;
    }
}
