package com.jk.service;

import com.jk.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface UserService {


    @RequestMapping("select")
    List<UserEntity> select();

    @RequestMapping("save")
    void save(@RequestBody UserEntity userEntity);
}
