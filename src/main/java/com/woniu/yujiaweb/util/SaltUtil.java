package com.woniu.yujiaweb.util;

import java.util.Random;

public class SaltUtil {
    private String salt;


    public SaltUtil(int num) {
        String words="qwertyuiopasdfghjkl;'zxcvbnm,./1234567890！@#￥%……&*（）";
        if(num>0){
            int len=words.length();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=1;i<=num;i++){
                char singleword=words.charAt(new Random().nextInt(len));
                stringBuffer.append(singleword);
            }
            System.out.println(String.valueOf(stringBuffer));
            this.salt=String.valueOf(stringBuffer);
        }

    }

    public String getSalt() {
        return salt;
    }


}
