package com.jk.mapper;

import com.jk.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    List<UserEntity> select();

    @Insert("insert into t_user (name,age,type_id) values (#{name},#{age},#{typeId})")
    void save(UserEntity userEntity);
}

