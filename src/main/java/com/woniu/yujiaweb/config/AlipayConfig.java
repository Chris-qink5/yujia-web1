package com.woniu.yujiaweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Data
@Configuration
@ConfigurationProperties(prefix = "alipayconfig",ignoreUnknownFields = false)
@PropertySource("classpath:application.properties")//读取配置文件
@Component
public class AlipayConfig implements Serializable {
    // 应用ID
    private String app_id;
    //商户私钥
    private String merchant_private_key;
    //支付宝公钥
    private String alipay_public_key;
    //服务器异步通知页面路径
    private String notify_url;
    //页面跳转同步通知页面路径
    private String return_url;
    //签名方式
    private String sign_type;
    //字符编码格式
    private String charset;
    //支付宝网关
    private String gatewayUrl;
    /** 格式 */
    private String formate = "json";
    private String log_path;
    //get  set我这就不生成了  也可使用lombok插件
}

