package com.wxj.SimpleMybatis;

import com.wxj.SimpleMybatis.Annotations.Param;
import com.wxj.SimpleMybatis.Annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select(value = "select * from user where name=#{name}")
    List<User> getUser(@Param("name") String name);


    @Select(value = "select * from user where id=#{id}")
    User queryById(@Param("id") Long id);

    void updateNameById(@Param("name") String name, @Param("id") Long id);

}
