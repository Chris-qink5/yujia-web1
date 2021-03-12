package com.woniu.yujiaweb.exception;


import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.io.IOException;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ArithmeticException.class)
    public Result handlerArithemticException(){
        return new Result(false, StatusCode.ArithemticException,"数学计算错误");

    }
    @ExceptionHandler(IOException.class)
    public Result handlerIOException(){
        System.out.println("IO异常处理");
        return new Result(false, StatusCode.IOException,"io错误");
    }
    @ExceptionHandler(MessagingException.class)
    public Result handlerMessagingException(){
        System.out.println("IO异常处理");
        return new Result(false, StatusCode.MessagingException,"发送信息错误");
    }
    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public Result handlerUnknownAccountException(){
        System.out.println("全局异常日志：账户不存在");
        return new Result(false, StatusCode.LOGINERROR,"账户不存在");
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public Result handlerIncorrectCredentialsException(){
        System.out.println("全局异常日志：错误密码");
        return new Result(false, StatusCode.LOGINERROR,"密码错误");
    }
//    @ExceptionHandler(Exception.class)
//    public Result handlerUnknownException(){
//        System.out.println("未知错误");
//        return new Result(false, StatusCode.UnknownException,"未知错误");
//
//    }

}
