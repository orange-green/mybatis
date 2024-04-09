package com.wxj.SimpleMybatis;

import org.junit.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/7 09:53:04
 */

public class EnvTest {

    @Test
    public void envTest() {
        String property = System.getProperty("java.version");
        System.out.println("java version: " + property);
    }


}
