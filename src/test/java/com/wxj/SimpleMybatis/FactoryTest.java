package com.wxj.SimpleMybatis;

import com.wxj.SimpleMybatis.Factory.MapperProxyFactory;
import org.junit.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/8 10:13:21
 */

public class FactoryTest {
    @Test
    public void test1() {
        UserMapper mapper = MapperProxyFactory.getMapper(UserMapper.class);
        User user = mapper.queryById(1l);
    }

}
