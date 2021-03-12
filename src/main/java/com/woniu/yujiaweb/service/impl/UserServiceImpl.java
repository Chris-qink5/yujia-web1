package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.UserMapper;
import com.woniu.yujiaweb.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.UserInfoVO;
import org.apache.shiro.util.StringUtils;
import com.woniu.yujiaweb.vo.PageGymVo;
import com.woniu.yujiaweb.vo.PageUserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import com.woniu.yujiaweb.vo.CoachVo;
import com.woniu.yujiaweb.vo.GymVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Page<User> findByCondition(UserInfoVO userInfoVO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(StringUtils.hasLength(userInfoVO.getEmail())){
            wrapper.like("email",userInfoVO.getEmail());
        }
        if(StringUtils.hasLength(userInfoVO.getScore())){
            wrapper.like("score",userInfoVO.getScore());
        }
        if(StringUtils.hasLength(userInfoVO.getSex())){
            wrapper.like("sex",userInfoVO.getSex());
        }
        if(StringUtils.hasLength(userInfoVO.getTel())){
            wrapper.like("tel",userInfoVO.getTel());
        }
        if(StringUtils.hasLength(userInfoVO.getUsername())){
            wrapper.like("username",userInfoVO.getUsername());
        }
        if(!ObjectUtils.isEmpty(userInfoVO.getMinLength())){
            wrapper.ge("length",userInfoVO.getMinLength());
        }
        if(!ObjectUtils.isEmpty(userInfoVO.getMaxLength())){
            wrapper.le("length",userInfoVO.getMaxLength());
        }


        Page<User> filmPage = new Page<>(userInfoVO.getCurrent(), userInfoVO.getSize());


        return (Page<User>) userMapper.selectPage(filmPage, wrapper);
    }

    @Override
    public void saveUserAndRole(String uid, String rid) {
        userMapper.saveUserAndRole(uid,rid);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }
    @Override
    public List<Permission> findManue(String username) {
        System.out.println(username+"进入服务层");
        int id =(int) redisTemplate.opsForValue().get(username);

        List<Permission> permissions = userMapper.findManue(id);
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {

                rootManue.add(permission);

        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        System.out.println(rootManue);
        return rootManuelist;

    }
    public List<Permission> findManue2(String username) {
        System.out.println(username + "进入服务层");
        int id = (int) redisTemplate.opsForValue().get(username);

        List<Permission> permissions = userMapper.findManue2(id);
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {

            rootManue.add(permission);

        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        System.out.println(rootManue);
        return rootManuelist;
    }

    //根据角色id查询对应的教练和学员的分页信息
    @Override
    public Page<PageUserVo> findAllUser(PageUserVo pageUserVo) {
        Page<PageUserVo> userPage = new Page<>(pageUserVo.getCurrent(),pageUserVo.getSize());

        //进行条件的筛选
        QueryWrapper<PageUserVo> pageUserVoQueryWrapper = new QueryWrapper<>();
        pageUserVoQueryWrapper.eq("rid",1);
        pageUserVoQueryWrapper.eq("u.deleted",0);
        if(!ObjectUtils.isEmpty(pageUserVo.getUsername())){
            pageUserVoQueryWrapper.like("username",pageUserVo.getUsername());
        }
        if(!ObjectUtils.isEmpty(pageUserVo.getTel())){
            pageUserVoQueryWrapper.like("tel",pageUserVo.getTel());
        }
        if (!ObjectUtils.isEmpty(pageUserVo.getSex())) {
            pageUserVoQueryWrapper.eq("sex",pageUserVo.getSex());
        }
        if (!ObjectUtils.isEmpty(pageUserVo.getStartScore())&&!ObjectUtils.isEmpty(pageUserVo.getEndScore())) {
            if(Integer.parseInt(pageUserVo.getStartScore()) <  Integer.parseInt(pageUserVo.getEndScore())){
                pageUserVoQueryWrapper.between("score",Integer.parseInt(pageUserVo.getStartScore()),Integer.parseInt(pageUserVo.getEndScore()));
            }
        }
        List<PageUserVo> pageUserVos = userMapper.selectUserListPage(userPage,pageUserVoQueryWrapper);
        userPage.setRecords(pageUserVos);//将查询到的数据添加page中的recods中
        return userPage;
    }

    //删除学员的信息
    @Override
    public Boolean delStudent(Integer userId) {
        int i = userMapper.deleteById(userId);
        System.out.println(i);
        if(i > 0){
            return true;
        }
        return false;
    }

    //修改学员的信息
    @Override
    public Boolean updateStudent(User user) {
        int i = userMapper.updateById(user);
        if(i > 0){
            return true;
        }
        return false;
    }

    //查询所有的场馆信息
    @Override
    public Page<PageGymVo> findAllGym(PageGymVo pageGymVo) {
        Page<PageGymVo> allGym = new Page<>(pageGymVo.getCurrent(),pageGymVo.getSize());
        //进行条件筛选查询
        QueryWrapper<PageGymVo> pageGymVoQueryWrapper = new QueryWrapper<>();
        //查询角色id为3的场馆
        pageGymVoQueryWrapper.eq("r.id",3);
        //对场馆的地址进行模糊查询
        if (!ObjectUtils.isEmpty(pageGymVo.getGymAddress())) {
            pageGymVoQueryWrapper.like("gym_address",pageGymVo.getGymAddress());
        }
        if(!ObjectUtils.isEmpty(pageGymVo.getGymName())){
            //对场馆的名称进行模糊查询
            pageGymVoQueryWrapper.like("gym_name",pageGymVo.getGymName());
        }

        //将条件带进去进行多表联合分页查询
        List<PageGymVo> pageGymVos = userMapper.queryAllGym(allGym, pageGymVoQueryWrapper);
        //将查询到的数据放入到allgym
         allGym.setRecords(pageGymVos);
        return allGym;
    }

    //联合修改
    @Override
    public boolean updateGym(PageGymVo pageGymVo) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        return false;
    }

    //查询所有的管理员信息
    @Override
    public Page<PageUserVo> findAllAdmin(PageUserVo pageUserVo) {
        Page<PageUserVo> userPage = new Page<>(pageUserVo.getCurrent(), pageUserVo.getSize());
        //进行条件的筛选
        QueryWrapper<PageUserVo> pageUserVoQueryWrapper = new QueryWrapper<>();
        pageUserVoQueryWrapper.eq("u.deleted",0);
        List<PageUserVo> pageUserVos = userMapper.selectUserListPage(userPage,pageUserVoQueryWrapper);
        userPage.setRecords(pageUserVos);//将查询到的数据添加page中的recods中
        return userPage;
    }
    //查询所有教练
    @Override
    public Page<CoachVo> getAllcoach(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {

        Page<CoachVo> coachVoPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        Page<CoachVo> allcoach = userMapper.getAllcoach(coachVoPage, queryWrapper);

        return allcoach;
    }
    //查询所有学员
    @Override
    public Page<CoachVo> getAllstudent(CoachVo studentVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> coachVoPage = new Page<>(studentVo.getCurrent(),studentVo.getSize());
        Page<CoachVo> allstudent = userMapper.getAllstudent(coachVoPage, queryWrapper);

        return allstudent;
    }



    @Override
    public GymVo findgym(String tel) {
        return userMapper.findgym(tel);
    }

    @Override
    public void updateByTel(GymVo gymVo) {
            userMapper.UpdateByTel(gymVo);
    }


    @Override
    public Page<CoachVo> selectcoachinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> coachVoPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        Page<CoachVo> allcoach = userMapper.selectcocahinfo(coachVoPage, queryWrapper);
        return allcoach;
    }


    @Override
    public Integer selectcoachid(String tel) {
        return userMapper.selectcoachid(tel);
    }

    @Override
    public Integer selectgymid(String tel) {
        return userMapper.selectgymid(tel);
    }

    @Override
    public void deletecgymBycIDandgId(Integer coachid, Integer gym) {
        userMapper.deletecgymBycIDandgId(coachid,gym);
    }

    @Override
    public void insertcoach(Integer coachid,Integer gymid) {
        userMapper.insertcoach(coachid,gymid);
    }

    @Override
    public List<Integer> querygymBycoachId(Integer coachid) {
        return userMapper.querygymBycoachId(coachid);


    }

    @Override
    public GymVo Querygym(Integer gymid) {
        return userMapper.Querygym(gymid);
    }

    @Override
    public List<Integer> querycoachBystudentTel(String tel) {
        return userMapper.querycoachBystudentTel(tel);
    }

    @Override
    public CoachVo querycoachBycoachId(Integer id) {
        return userMapper.querycoachBycoachId(id);
    }



    @Override
    public Page<CoachVo> selectStudentinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {

        Page<CoachVo> coachVoPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        Page<CoachVo> allstudent = userMapper.selectStudentinfo(coachVoPage, queryWrapper);
        return allstudent;
    }

    @Override
    public Page<CoachVo> findclassinfo(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> classPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        return userMapper.findclassinfo(classPage,queryWrapper);
    }

    @Override
    public Integer queryclassIdByclassName(String courseName) {
        return userMapper.queryclassIdByclassName(courseName);
    }

    @Override
    public void deleteclass(Integer coachid, Integer classId) {
        userMapper.deleteclass(coachid,classId);
    }

    @Override
    public Page<CoachVo> findattentiongym(CoachVo coachVo, QueryWrapper<CoachVo> queryWrapper) {
        Page<CoachVo> attentiongymPage = new Page<>(coachVo.getCurrent(),coachVo.getSize());
        return userMapper.findattentiongym(attentiongymPage,queryWrapper);
    }

    @Override
    public void saveUserAndRole(String uid, String rid) {
        //向数据库保存数据
        userMapper.saveUserAndRole(uid,rid);
    }

    /**
     * 去重
     */
    public  List<Permission> removeDuplicate(List<Permission> list){
        List<Permission> listTemp = new ArrayList<Permission>();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;

    }
    public List<User> findPlace() {
        List<User> place = userMapper.findPlace();
        return place;
    }
    @Override
    public List<User> findYPlace(int yid) {
        List<User> yPlace = userMapper.findYPlace(yid);
        return yPlace;
    }
}
