package com.woniu.yujiaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.config.AlipayConfig;
import com.woniu.yujiaweb.domain.AlipayBean;

import com.woniu.yujiaweb.mapper.AlipayBeanMapper;

import com.woniu.yujiaweb.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayServiceImpl extends ServiceImpl<AlipayBeanMapper, AlipayBean> implements AlipayService {
    @Autowired
    AlipayConfig alipayConfig;
    @Override
    public String toAlipay(AlipayBean alipayBean)  {
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),//支付宝网关
                alipayConfig.getApp_id(),//appid
                alipayConfig.getMerchant_private_key(),//商户私钥
                alipayConfig.getFormate(),
                alipayConfig.getCharset(),//字符编码格式
                alipayConfig.getAlipay_public_key(),//支付宝公钥
                alipayConfig.getSign_type());//签名方式
        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(alipayConfig.getReturn_url());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());
        //封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        String result="";
        try {
            //3、请求支付宝进行付款，并获取支付结果
            result  = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //返回付款信息
        return result;
    }
}



