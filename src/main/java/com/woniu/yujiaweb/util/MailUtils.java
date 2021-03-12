package com.woniu.yujiaweb.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	//email:邮件发给谁  subject:主题  emailMsg：邮件的内容
	public static void sendMail(String email, String subject, String emailMsg)
			throws AddressException, MessagingException {

		// 1.创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");//发邮件的协议
		props.setProperty("mail.host", "smtp.163.com");//发送邮件的服务器地址
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//密码如果不是登录密码就是授权密码(wyc123)
				return new PasswordAuthentication("yangym023", "OGWWMJGGJJVRPRME");//发邮件的账号的验证
			}
		};
		Session session = Session.getInstance(props, auth);
		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("yangym023@163.com")); // 设置发送者

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject(subject);//邮件的主题

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);
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
	public static void main(String[] args) {
		try {
			MailUtils.sendMail("1348417673@qq.com", "验证信息", "310735");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
