package com.wxj.SimpleMybatis.IO;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 */

public class ResourceURL implements Resource{
    // 存放文件地址或网络地址
    private final URL url;

    public ResourceURL(URL url) {
        this.url = url;
    }


    @Override
    public InputStream getInputStream() throws Exception {
        // 获取连接对象
        URLConnection urlConnection = url.openConnection();
        // 建立连接,获取输入流
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
