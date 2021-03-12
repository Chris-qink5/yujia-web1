package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Course;
import com.woniu.yujiaweb.domain.OrderDetail;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.CourseService;
import com.woniu.yujiaweb.service.OrderDetailService;
import com.woniu.yujiaweb.vo.PageVO;
import com.woniu.yujiaweb.vo.RowVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yym
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private CourseService courseService;
    @Resource
    private OrderDetailService orderDetailService;
    @GetMapping("/findCoachs")
    @ResponseBody
    private Result findCoachs(PageVO pageVO){
        Page<Course> coursePage = new Page<>(pageVO.getCurrent(),pageVO.getSize());
        IPage<Course> page = courseService.page(coursePage,null);
        return new Result(true, StatusCode.OK,"查询所有教练",page);
    }
    //预约教练
    @PostMapping("/reserveCoach")
    @ResponseBody
    private Result reserveCoach(@RequestBody RowVO rowVO){
        //如果数据为空,从缓存中取出用户名
        String username = (String) redisTemplate.opsForValue().get("username");
        //预约教练就是把信息存入到订单详情t_order_detail
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        //查询是否有订单
        OrderDetail orderDetail = orderDetailService.getOne(wrapper);
        orderDetail=new OrderDetail();
        //存入教练id
        orderDetail.setUid(rowVO.getUid());
        orderDetail.setUsername(username);
        orderDetail.setCoachName(rowVO.getCoachName());
        orderDetail.setCourseName(rowVO.getCourseName());
        //订单创建时间
        orderDetail.setCreateTime(new Date());
        orderDetail.setCourseTime(rowVO.getCourseTime());
        orderDetail.setPrice(rowVO.getCourseMoney());
        //订单号UUID生成
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long currentTimeMillis = System.currentTimeMillis();
        String orderId=uuid+currentTimeMillis;
        orderDetail.setOrderId(orderId);
        orderDetailService.save(orderDetail);
        return new Result(true, StatusCode.OK,"预约成功");
    }
 * @author qk
 * @since 2021-03-09
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    @Resource
    private  CourseService courseService;

    @RequestMapping("findAllCourse")
    @ResponseBody
    public Result findAllCourse(){
        List<Course> list = courseService.list(null);
        return new Result(true, StatusCode.OK,
                "查询所有信息成功", list);
    }



    @ResponseBody
    @RequestMapping("insertCourse")
    public Result insertCourse(@RequestBody Course course){
        int i = courseService.insertCourse(course);
        if(i>0){
            return new Result(true,StatusCode.OK,"新增成功");
        }else{
            return new Result(true,StatusCode.ACCESSERROR,"当前课程已存在");
        }
    }



    @DeleteMapping("deletedCourse/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable Integer id){
        courseService.removeById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }




}

