package com.jk.serviceImpl;

import com.jk.entity.UserEntity;
import com.jk.mapper.UserMapper;
import com.jk.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    @RequestMapping("select")
    public List<UserEntity> select() {
        return userMapper.select();
    }

    @Override
    @RequestMapping("save")
    public void save(@RequestBody UserEntity userEntity) {
        userMapper.save(userEntity);
    }
}
