package com.wxj.SimpleMybatis.IO;

import java.io.InputStream;

public interface Resource {
    /**
     * <p> 获取输入流 </p>
     *
     */
    InputStream getInputStream() throws Exception;
}
