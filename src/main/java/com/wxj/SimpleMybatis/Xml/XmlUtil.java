package com.wxj.SimpleMybatis.Xml;

import com.wxj.SimpleMybatis.IO.ResourceLoader;
import com.wxj.SimpleMybatis.IO.ResourceURL;
import com.wxj.SimpleMybatis.model.Database;
import com.wxj.SimpleMybatis.model.SqlMapperItem;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  xml解析
 * </p>
 *
 * @author wuxj
 * @since 2024/4/15 15:31:53
 */

public class XmlUtil {


    private static final String configLocation = null;

    public static Map<Class, Document> classDocumentMap = null;

    static {
        try {
            classDocumentMap = getMapperDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
        //<dataSource></dataSource>里面子标签
        NodeList dataSourceNodeList = document.getElementsByTagName("dataSource");

        if (dataSourceNodeList == null || dataSourceNodeList.getLength() == 0) {
            throw new Exception("xml文件dataSource属性缺失");
        }

        // 这里按照最简单的设计,认为xml中只有一个dataSource节点
        Element dataSourceNode = (Element) dataSourceNodeList.item(0);

        NodeList propertyList = dataSourceNode.getElementsByTagName("property");

        HashMap<String, String> mataMap = new HashMap<>();

        for (int i =0; i < propertyList.getLength(); i++) {
            Node item = propertyList.item(i);
            NamedNodeMap attributes = item.getAttributes();
            Node name = attributes.getNamedItem("name");
            Node value = attributes.getNamedItem("value");
            mataMap.put(name.getNodeValue(), value.getNodeValue());
        }

        String driver = mataMap.get("driver");
        String url = mataMap.get("url");
        String username = mataMap.get("username");
        String password = mataMap.get("password");

        if (driver == null || url == null || username==null || password==null) {
            throw new Exception("xml属性缺失");
        }
        return new Database(null, url, null, username, password, driver);
    }
    /***
     * <p> 解析xml文件中<mappers></mappers>里面的所有mapper文件地址 </p>
     */
    public static List<String> parseXmlMappers(Document document) {
        ArrayList<String> mapperLocationList = new ArrayList<>();
        // 获取根节点
        Element root = document.getDocumentElement();
        NodeList mapperList = root.getElementsByTagName("mapper");

        for (int i=0; i<mapperList.getLength(); i++) {
            Element item = (Element)mapperList.item(i);
            String resource = item.getAttribute("resource");
            mapperLocationList.add(resource);
        }
        return mapperLocationList;
    }

    public static Map<Class, Document> getMapperDocument() throws Exception {
        Document document = XmlReader(configLocation);
        List<String> mappesLocation = parseXmlMappers(document);
        HashMap<Class, Document> classDocumentHashMap = new HashMap<>();
        for (String location: mappesLocation
             ) {
            Document doc = XmlReader(location);
            Element element = doc.getDocumentElement();
            NodeList mapperList = element.getElementsByTagName("mapper");
            Element node =  (Element)mapperList.item(0);
            String namespace = node.getAttribute("namespace");
            Class<?> mapperClass = Class.forName(namespace);
            classDocumentHashMap.put(mapperClass, doc);
        }

        return classDocumentHashMap;
    }

    public static SqlMapperItem getSql(Class aclass, String funName) throws Exception {
        SqlMapperItem sqlMapperItem = null;
        String sql = null;
        Document document = classDocumentMap.get(aclass);
        Element documentElement = document.getDocumentElement();
        NodeList nodes = documentElement.getChildNodes();
        for (int i =0; i < nodes.getLength(); i++) {
            Element elem =  (Element)nodes.item(i);
            String id = elem.getAttribute("id");
            if (funName.equals(id)) {
                sql = elem.getTextContent();
                String resultType = elem.getAttribute("resultType");
                sqlMapperItem = new SqlMapperItem();
                sqlMapperItem.setSql(sql);
                sqlMapperItem.setId(id);
                sqlMapperItem.setResultType(resultType);
                break;
            }
        }

        return sqlMapperItem;

    }
}
