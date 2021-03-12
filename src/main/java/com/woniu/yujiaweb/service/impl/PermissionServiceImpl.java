package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.mapper.PermissionMapper;
import com.woniu.yujiaweb.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.PermissionVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RedisTemplate<String,Permission> redisTemplate;
    @Resource
    private PermissionMapper permissionMapper;

    //查询所有权限菜单
    @Override
    public List<Permission> getAllPermissionByUserId(Integer rid) {
//        //先在redis缓存中去查询所有权限
//        List<Permission> allPermissions = redisTemplate.opsForList().range("allPermission", 0, -1);
//        System.out.println(allPermissions);
//        //判断查询的所有权限菜单是否为空
//        if (ObjectUtils.isEmpty(allPermissions)) {
//            System.out.println("去数据库中查询");
//            //如果为空的就去数据库中进行查询
//            allPermissions = permissionMapper.getAllPermissionByUserId(id);
//            //将查询到的数据存入到redis缓存中
//            allPermissions.forEach(allpermissionDb->{
//                System.out.println(allpermissionDb);
//
//            });
//        }

        List<Permission> allPermissions= permissionMapper.getAllPermissionByUserId(rid);
        //存放所有菜单目录,对应的一级菜单有自己对应的二级菜单
            ArrayList<Permission> firstPermissions = new ArrayList<>();

            //找出所有的一级菜单
            allPermissions.forEach(allPermission->{
                //如果对应的权限等级为1,那么就是一级菜单
                if(allPermission.getPerssionLevel()==1){
                    //先将该一级菜单设置一个二级菜单
                    allPermission.setChildren(new ArrayList<Permission>());
                    //将一级菜单放入到菜单目录中
                    firstPermissions.add(allPermission);
                }
            });
        allPermissions.forEach(allPermission->{
            firstPermissions.forEach(firstPermission->{
                //判断一级菜单的id和二级菜单的父级菜单id即pid是否相等
                if(allPermission.getPid()==firstPermission.getId()){
                    //相等 则表示该二级菜单就是该一级菜单下面的 放入到一级菜单的子菜单
                    firstPermission.getChildren().add(allPermission);
                }
            });
        });

            return firstPermissions;
        }

    //查询后台的所有权限菜单
    @Override
    public List<Permission> getAllPermission() {
        System.out.println("查询后台的所有权限");
        List<Permission> allPermissions= permissionMapper.getAllPermission();
        //存放所有菜单目录,对应的一级菜单有自己对应的二级菜单
        ArrayList<Permission> firstPermissions = new ArrayList<>();

        //找出所有的一级菜单
        allPermissions.forEach(allPermission->{
            //如果对应的权限等级为1,那么就是一级菜单
            if(allPermission.getPerssionLevel()==1){
                //先将该一级菜单设置一个二级菜单
                allPermission.setChildren(new ArrayList<Permission>());
                //将一级菜单放入到菜单目录中
                firstPermissions.add(allPermission);
            }
        });
        allPermissions.forEach(allPermission->{
            firstPermissions.forEach(firstPermission->{
                //判断一级菜单的id和二级菜单的父级菜单id即pid是否相等
                if(allPermission.getPid()==firstPermission.getId()){
                    //相等 则表示该二级菜单就是该一级菜单下面的 放入到一级菜单的子菜单
                    firstPermission.getChildren().add(allPermission);
                }
            });
        });

        return firstPermissions;
    }

    //查询对应id已经拥有的权限菜单id
    public List<Integer> getHasPermissionIds(Integer id){
        List<Permission> aLlPermissionIds = permissionMapper.getHasPermissionIds(id);
        List<Integer> allPerIds = new ArrayList<>();
        aLlPermissionIds.forEach(pid->{
            allPerIds.add(pid.getId());
        });
        return allPerIds;
    }
    //查询对应id已经拥有的权限菜单名称
    @Override
    public List<String> getHasPermissionElements(Integer id) {
        List<Permission> aLlPermissionIds = permissionMapper.getHasPermissionIds(id);
        List<String> allPerElement= new ArrayList<>();
        aLlPermissionIds.forEach(pid->{
            allPerElement.add(pid.getElement());
        });
        return allPerElement;
    }

    //删除管理员拥有的权限
    @Override
    public void delPermissionByid(Integer id) {
        permissionMapper.delPermissionByid(id);
    }

    //添加管理员选择的权限
    @Override
    public void addPermissionByid(PermissionVo permissionVo) {
        //存储一级菜单 不能有重复的数据 使用set集合 不能使用list
        HashSet<Integer> firPermi = new HashSet<>();
        //存储二级菜单
        HashSet<Integer> secPermi = new HashSet<>();
        //将传回来的pid集合进行遍历 来判断其是属于一级菜单还是二级菜单
        permissionVo.getPid().forEach(pid->{
            Permission permission = permissionMapper.queryPermissionByPid(pid);
            if(permission.getPerssionLevel()==1){//判断 如果是一级菜单  就将一级菜单保存
                firPermi.add(pid);
            }else if(permission.getPerssionLevel()==2){
                //如果是二级菜单  先将其对应的以及菜单存到一级菜单里面
                firPermi.add(permission.getPid());
                //再将其id存入到二级集合中
                secPermi.add(pid);
            }
        });
        //对一级权限进行授权  即添加
        firPermi.forEach(pid->{
            permissionMapper.addPermissionByrid(permissionVo.getRid(),pid);
        });
        //对二级权限进行授权  即添加
        secPermi.forEach(pid->{
            permissionMapper.addPermissionByrid(permissionVo.getRid(),pid);
        });
    }
}
