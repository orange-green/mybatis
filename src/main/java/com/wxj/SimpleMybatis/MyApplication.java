package com.wxj.SimpleMybatis;

import com.wxj.SimpleMybatis.Factory.MapperProxyFactory;
import com.wxj.SimpleMybatis.Xml.XmlUtil;
import com.wxj.SimpleMybatis.model.Database;
import org.w3c.dom.Document;

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

  public static void main(String[] args) throws Exception{

//    UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class);
//    List<User> list = userMapper.getUser("wxj");
//
//    System.out.println(list);
//
//    User user = userMapper.queryById(1L);
//    System.out.println(user);


    Document document = XmlUtil.XmlReader("resource/xml/mybatis-config.xml");
    Database database = XmlUtil.parseXmlDataBaseProperty(document);
    List<String> strings = XmlUtil.parseXmlMapperURL(document);
  }
}
