package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.domain.AlipayBean;

public interface AlipayService extends IService<AlipayBean> {
    public String toAlipay(AlipayBean alipayBean);
}
