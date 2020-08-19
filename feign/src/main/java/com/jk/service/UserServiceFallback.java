package com.jk.service;

import com.jk.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("err")
public class UserServiceFallback implements UserServiceFeign{
    @Override
    public List<UserEntity> select() {
        System.out.println("进入熔断。。。");
        return null;
    }

    @Override
    public void save(UserEntity userEntity) {
        System.out.println("降级");
    }
}
