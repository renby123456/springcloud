/*package com.jk.controller;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jk.utils.CheckSumBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("sendcode")
public class SendCode {

        //发送验证码的请求路径URL
        private static final String SERVER_URL="https://api.netease.im/sms/sendcode.action";
        //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
        private static final String APP_KEY="f7d10475094be28983759f4eb2666fc3";
        //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
        private static final String APP_SECRET="61d440ca1874";
        //随机数
        private static final String NONCE="12345678";
        //短信模板ID
        private static final String TEMPLATEID="14889197";
        //手机号
        private static final String MOBILE="15229296058";
        //验证码长度，范围4～10，默认为4
        private static final String CODELEN="8";


    @RequestMapping("aaaa")
    @ResponseBody
    public  void   aaaa () throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);




        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();




        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nvps.add(new BasicNameValuePair("mobile", MOBILE));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);


        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));

    }

}*/

