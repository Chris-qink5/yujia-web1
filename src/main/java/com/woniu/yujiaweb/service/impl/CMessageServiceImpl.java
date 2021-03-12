package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.CMessage;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.mapper.CMessageMapper;
import com.woniu.yujiaweb.service.CMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class CMessageServiceImpl extends ServiceImpl<CMessageMapper, CMessage> implements CMessageService {

    private static final String DEFAULTVERSION = "version_1";
    @Resource
    private CMessageMapper cmessageMapper;




    @Override
    public CMessage findOneById(Integer id){
        return cmessageMapper.findOneById(id);
    }

    @Override
    public int updateCMessage(CMessage cMessage) {
        return cmessageMapper.updateById(cMessage);
    }








    @Override
    public Boolean isFirstTime(Integer id) {
        final List<String> list = cmessageMapper.isFirstTime(id);
        if(list.size()>1){
            throw new RuntimeException("数据重复错误");
        }

        return DEFAULTVERSION.equals(list.get(0));
    }

}
