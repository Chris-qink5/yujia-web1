package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.CMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.dto.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface CMessageMapper extends BaseMapper<CMessage> {


    @Select("select" +
            " id, nickname, site, description, idCard, bankCard, sex, birthday" +
            " from" +
            " t_c_message" +
            " where u_id = #{id}")
    CMessage findOneById(Integer id);

    @Select("select version from t_user where id =#{id}")
    List<String> isFirstTime(Integer id);

}









