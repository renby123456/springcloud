package com.jk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Value("${my.name}")
    private String name;

    @RequestMapping("aaa")
    @ResponseBody
    public String aaa(){
        return name+"你好";
    }
}
