package com.jk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jk.entity.UserEntity;

import com.jk.service.UserService;
import com.jk.utils.CheckSumBuilder;
import com.jk.utils.Constant;
import com.jk.utils.HttpClientUtil;
import com.jk.utils.RedisUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.jk.utils.Constant.*;

@Controller
@RequestMapping("user")
@EnableCircuitBreaker
public class UserController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("save")
    @ResponseBody
    public Boolean save(UserEntity userEntity){
        try {
            userService.save(userEntity);
            redisUtil.del("userlist");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //支付
    @RequestMapping("selectUserOne")
    public String selectUserOne(Integer id,Integer money){
        return "redirect:../pay/alipay";
    }
    @RequestMapping("toselect")
    public String toselect(){
        return "userList";
    }

    @RequestMapping("select")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "aaa")
    public List<UserEntity> select(){

        List<UserEntity> userlist = (List<UserEntity>) redisUtil.get("userlist");
        if(userlist ==null || userlist.size()<=0 || userlist.isEmpty()){
            userlist = userService.select();
            redisUtil.set("userlist",userlist);
        }
       return userlist;
    }

    private  List<UserEntity> aaa() throws Exception {
        ArrayList<UserEntity> list = new ArrayList<UserEntity>();
        UserEntity userEntity = new UserEntity();


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nvps.add(new BasicNameValuePair("mobile", MOBILE));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        return list;
    }
}
