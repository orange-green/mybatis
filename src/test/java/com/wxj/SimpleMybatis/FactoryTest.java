package com.wxj.SimpleMybatis;

import com.wxj.SimpleMybatis.Factory.MapperProxyFactory;
import com.wxj.SimpleMybatis.Xml.XmlUtil;
import com.wxj.SimpleMybatis.model.Database;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.List;

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

    @Test
    public void test2() throws Exception {
        Document document = XmlUtil.XmlReader("resource/xml/mybatis-config.xml");
        Database database = XmlUtil.parseXmlDataBaseProperty(document);
        List<String> strings = XmlUtil.parseXmlMapperURL(document);
    }

}
