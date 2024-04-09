package com.wxj.SimpleMybatis;

import com.wxj.SimpleMybatis.Factory.MapperProxyFactory;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/8 10:35:46
 */

public class MyApplication {

  public static void main(String[] args) {

    UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class);
    List<User> list = userMapper.getUser("wxj");

    System.out.println(list);
  }
}
