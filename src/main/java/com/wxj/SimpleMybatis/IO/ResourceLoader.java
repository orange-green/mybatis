package com.wxj.SimpleMybatis.IO;

import java.io.InputStream;
import java.net.URL;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/15 15:35:50
 */

public class ResourceLoader {

    /***
     * <p> 根据类加载器获取当前应用路径下的文件  </p>
     */
    public ResourceURL getResourceURL(String path) {
//        InputStream resourceAsStream = getClass().getResourceAsStream(path);
        URL url = this.getClass().getResource(path) ;
        return new ResourceURL(url);
    }
}
