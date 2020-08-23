package com.jk.controller;

import com.jk.mapper.UserMapper;
import com.jk.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RefreshScope
@Controller
public class UserController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("aaa")
    @ResponseBody
    public Object aaa(){
        Object obj = redisUtil.get("aaa");
        List<Map> userlist = userMapper.userlist();
        return obj+userlist.toString();
    }

}
