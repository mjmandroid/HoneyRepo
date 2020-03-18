package com.streaming.better.honey.utils;

public interface ErrorCodeTO {
    final int ISSUCCESS = 200;
    final int ISERROR = 500;
    final int CANNOTFINDOBJ = 404;
    interface ValidateCode{
        //验证失败
        final int VALIDATEERROR = 2001;
        //获取验证码失败
        final int VALIDATEGETERROR = 2002;
        //验证码失效
        final int VALIDATEISNOTVALUE = 2003;
        //前后号码不一致
        final int NOTTHISNUMBER = 2004;
    }

    interface RegisterCode{
        final String REGISTERSUCCESSMSG = "注册成功！";
        //用户已存在
        final int USERHASEXIST = 2005;
        final String USERHASEXISTMSG = "该用户名已存在";
        //注册失败
        final int REGISTERFAILURE = 2006;
        final String REGISTERFAILUREMSG = "注册失败";
    }

    interface LoginCode{
        //用户不存在
        final int USERISNOTEXIST = 2007;
        final String USERISNOTEXISTMSG = "该用户不存在";
        final int PASSWORDERROR = 2008;
        final String PASSWORDERRORMSG = "帐号或密码错误";
        final String LOGINSUCCESS = "登录成功！";
    }

    interface ResearchCode{
    }
}
