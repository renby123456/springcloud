package com.jk.utils;

public class Constant {

    public static final String SELECT_USER_LIST = "selectUserList";

    //发送验证码的请求路径URL
    public static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    public static final String
            APP_KEY="f7d10475094be28983759f4eb2666fc3";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    public static final String APP_SECRET="61d440ca1874";
    //随机数
    public static final String NONCE="123456";
    //短信模板ID
    public static final String TEMPLATEID="14880363";
    //手机号
    public static final String MOBILE="15035487147";
    //验证码长度，范围4～10，默认为4
    public static final String CODELEN="6";

}
