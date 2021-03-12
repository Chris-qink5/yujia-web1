package com.woniu.yujiaweb.util;

import java.util.Random;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 
 * 阿里云短信服务：
 * 
 * 注意：需要 签名名称、模版CODE 以及 RAM访问控制中的 AccessKeyID 和 AccessKeySecret
 * 
 */
public class AliyunSmsUtils {
	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";
	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = "LTAI0i8gzakniAxp";
	// TODO 修改成自己的
	static final String accessKeySecret = "vUlAvJHwKqyhtHgzEQHCaBKt19pZQX";

	public static SendSmsResponse sendSms(String telephone, String code) throws ClientException {
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化acsClient,暂不支持region化

		IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);

		DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", product, domain);

		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();

		// 必填:待发送手机号
		request.setPhoneNumbers(telephone);
		// 必填:短信签名-可在短信控制台-签名管理中找到
		request.setSignName("龙猫仔"); // TODO 修改成自己的
		// 必填:短信模板-可在短信控制台-模板管理中找到
		request.setTemplateCode("SMS_168827525"); // TODO 修改成自己的
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
		request.setTemplateParam("{\"code\":\"" + code + "\"}");
		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			System.out.println("短信发送成功！");
		} else {
			System.out.println("短信发送失败！");
		}
		return sendSmsResponse;
	}

	// 以下为测试代码，随机生成验证码
	public static String newcode="";

	public static String getNewcode() {
		return newcode;
	}

	public static void setNewcode() {
		Random ran=new Random();
		
		 for(int i=1;i<=4;i++){
			 switch (ran.nextInt(3)) {
				case 0://97~122
					newcode+=(char)(ran.nextInt(122-97+1)+97)+"";
					break;
				case 1://65~90
					newcode+=(char)(ran.nextInt(90-65+1)+65)+"";
					break;
				case 2://48~57
					newcode+=(char)(ran.nextInt(57-48+1)+48)+"";
					break;
				}
		 }
		 
	}

	public static void main(String[] args) throws Exception {
		newcode="";
		setNewcode();
		String code = getNewcode();//
		System.out.println("发送的验证码为：" + code);
		// 发短信
		SendSmsResponse response = sendSms("15823336374", code); // TODO 填写你需要测试的手机号码
		System.out.println("短信接口返回的数据----------------");
		System.out.println("Code=" + response.getCode());
		System.out.println("Message=" + response.getMessage());
		System.out.println("RequestId=" + response.getRequestId());
		System.out.println("BizId=" + response.getBizId());
		
	}
}
