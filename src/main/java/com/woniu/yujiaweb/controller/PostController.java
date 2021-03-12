package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.Advert;
import com.woniu.yujiaweb.domain.Post;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.AdvertService;
import com.woniu.yujiaweb.service.PostService;
import com.woniu.yujiaweb.vo.PageVO;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;
    //查询所有的帖子信息
    @GetMapping("allPost")
    public Result getAllPost(PageVO pageVo){
        IPage<Post> allPost = postService.getAllPost(pageVo);
        return new Result(true, StatusCode.OK,"查询帖子内容成功",allPost);
    }

    //删除指定的广告信息
    @PostMapping("del/{id}")
    public Result delPostById(@PathVariable Integer id){
        boolean b = postService.delPost(id);
        if(b){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    //修改指定的广告信息
    @PostMapping("update")
    public Result updatePost(@RequestBody Post post){
        boolean b = postService.updatePost(post);
        if(b){
            return new Result(true,StatusCode.OK,"修改成功");
        }
        return new Result(false,StatusCode.ERROR,"修改失败");
    }

    //新增广告信息
    @PostMapping("addPost")
    public Result addPost(@RequestBody Post post){
        boolean b = postService.addPost(post);
        if(b){
            return new Result(true,StatusCode.OK,"新增成功");
        }
        return new Result(false,StatusCode.ERROR,"新增失败");
    }

    //取消置顶
    @PostMapping("removeTop")
    public Result removeTop(@RequestBody Post post){
        boolean b = postService.removeTop(post);
        if(b) {
            return new Result(true, StatusCode.OK, "取消置顶成功");
        }
        return new Result(false,StatusCode.ERROR,"取消失败");
    }
    //取消置顶t
    @PostMapping("top")
    public Result Top(@RequestBody Post post){
        boolean b = postService.top(post);
        if(b) {
            return new Result(true, StatusCode.OK, "置顶成功");
        }
        return new Result(false,StatusCode.ERROR,"置顶失败");
    }
}

