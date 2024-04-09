# mybatis
实现的简单mybatis查询和对象映射功能



##### 功能

###### 查询功能

根据@Select注解进行sql查询，支持单条记录和多条记录查询

```java
public interface UserMapper {

    @Select(value = "select * from user where name=#{name}")
    List<User> getUser(@Param("name") String name);


    @Select(value = "select * from user where id=#{id}")
    User queryById(@Param("id") Long id);

}
```

