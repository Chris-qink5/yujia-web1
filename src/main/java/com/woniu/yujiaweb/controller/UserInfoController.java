package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.yujiaweb.domain.UserInfo;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.mapper.UserInfoMapper;
import com.woniu.yujiaweb.service.UserInfoService;
import com.woniu.yujiaweb.vo.InfoVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yym
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserInfoService userInfoService;
    @GetMapping("/getUsersInfoByUsername")
    @ResponseBody
    public Result getUsersInfoByUsername(String username){
        return new Result(true, StatusCode.OK,"查询个人信息成功",userInfoService.getUsersInfoByUsername(username));
    }
    @PostMapping("/updateUserInfoByUsername")
    @ResponseBody
   public Result updateUserInfoByUsername(@RequestBody InfoVO infoVO){
        //先查询数据
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("username",infoVO.getUsername());
        UserInfo userInfo = userInfoService.getOne(wrapper);
        if(!ObjectUtils.isEmpty(userInfo)){
            //如果数据不为空，就修改t_user_info
            userInfo=new UserInfo();
            userInfo.setNickname(infoVO.getNickname());
            userInfo.setBankCard(infoVO.getBankCard());
            userInfo.setSex(infoVO.getSex());
            userInfo.setSecret(infoVO.getSecret());
            userInfo.setOpen(infoVO.getOpen());
            userInfoService.update(userInfo,wrapper);
        }
        return new Result(true,StatusCode.OK,"修改成功");
   }
    //文件上传
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file){
        //文件上传
        //2、存放在本地文件系统中
        String path="D:/idea-project/yujia-web1/target/img/".replace("/", File.separator);
        File upload=new File(path);
        if (!upload.exists()) {
            upload.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        //避免文件同名，使用UUID+系统时间对文件名进行处理
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long currentTimeMillis = System.currentTimeMillis();
        String uploadFileName=uuid+currentTimeMillis+fileName;
        //执行上传操作
        try {
            //执行上传操作
            file.transferTo(new File(path,uploadFileName));
            System.out.println("上传成功"+uploadFileName);
            return new Result(true, StatusCode.OK,"上传成功",path+uploadFileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return new Result(false, StatusCode.ERROR,"上传失败");
        }
    }

}

