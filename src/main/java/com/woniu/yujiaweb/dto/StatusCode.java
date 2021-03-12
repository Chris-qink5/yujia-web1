package com.woniu.yujiaweb.dto;

public class StatusCode {
    public static final int OK=20000;
    public static final int ERROR=20001;
    public static final int LOGINERROR=20002;
    public static final int ACCESSERROR=20003;
    public static final int ArithemticException=20004;

    public static final int IOException=20006;
    public static final int MessagingException=20007;
    public static final int ACCOUNTEXISTS = 20008;//用户未注册,转换大小写快捷键ctrl+shift+u
    public static final int AUTHCODE=20009;//验证码错误

    public static final int RepeatException=20005;



}
