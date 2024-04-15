package com.wxj.SimpleMybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/15 16:10:57
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Database {

    private String database;

    private String url;

    private String port;

    private String username;

    private String password;

    private String driver;

}
