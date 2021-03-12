package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.CMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.dto.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
public interface CMessageService extends IService<CMessage> {



    CMessage findOneById(Integer id);

    int updateCMessage(CMessage cMessage);


    /**
     * 判断传入的用户id是否是首次登陆，若是首次登陆则返回true，若不是则返回false
     * 若抛出RuntimeException，则是传入了非法的参数
     * @param id
     * @return
     */
    Boolean isFirstTime(Integer id);


}
