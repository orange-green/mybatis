package com.wxj.SimpleMybatis.Xml;

import com.wxj.SimpleMybatis.IO.Resource;
import com.wxj.SimpleMybatis.IO.ResourceLoader;
import com.wxj.SimpleMybatis.IO.ResourceURL;
import com.wxj.SimpleMybatis.model.Database;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleConsumer;

/**
 * <p>
 *  xml解析
 * </p>
 *
 * @author wuxj
 * @since 2024/4/15 15:31:53
 */

public class XmlUtil {

    /**
     * <p> 读取xml文件 </p>
     * * @param path:
     * @return void
     * @author wuxj
     * @since 2024/4/15 15:33
     */
    public static Document XmlReader(String path) throws Exception {
        // 获取xml文件的输入流
        ResourceLoader resourceLoader = new ResourceLoader();
        ResourceURL resourceURL = resourceLoader.getResourceURL(path);
        InputStream inputStream = resourceURL.getInputStream();

        // 获取文档建造者的工厂实例
        DocumentBuilderFactory xmlDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = xmlDocumentBuilderFactory.newDocumentBuilder();

        // 将输入流解析为xml文档对象
        Document xmlDocument = documentBuilder.parse(inputStream);


        inputStream.close();

        return xmlDocument;
    }

    /**
     * <p> 解析xml中关于数据库相关的属性 </p>
     *
     */
    public static Database parseXmlDataBaseProperty(Document document) throws Exception {
        // 获取xml文件的根节点
        Element rootElement = document.getDocumentElement();
        //<dataSource></dataSource>里面子标签
        NodeList dataSourceNodeList = rootElement.getElementsByTagName("dataSource");
        // 这里按照最简单的设计,认为xml中只有一个dataSource节点
        Element dataSourceNode = (Element) dataSourceNodeList.item(0);
        String driver = dataSourceNode.getAttribute("driver");
        String url = dataSourceNode.getAttribute("url");
        String username = dataSourceNode.getAttribute("username");
        String password = dataSourceNode.getAttribute("password");

        if (driver == null || url == null || username==null || password==null) {
            throw new Exception("xml属性缺失");
        }
        return new Database(null, url, null, username, password, driver);
    }
    /***
     * <p> 解析xml文件中<mappers></mappers>里面的所有mapper文件地址 </p>
     */
    public static List<String> parseXmlMapperURL(Document document) {
        // 获取xml文件的根节点
        Element rootElement = document.getDocumentElement();
        //<dataSource></dataSource>里面子标签
        NodeList mappersNodeList = rootElement.getElementsByTagName("mappers");
        Element mappersNode = (Element) mappersNodeList.item(0);
        NodeList childNodes = mappersNode.getChildNodes();
        ArrayList<String> mapperLocationList = new ArrayList<>();
        for (int i=0; i<childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            mapperLocationList.add(item.getNodeValue());
        }
        return mapperLocationList;
    }
}
