package com.wxj.SimpleMybatis.Handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/8 15:03:21
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PrepareSql {

    // 预编译sql
    private String sql;

    // sql语句里面的参数列表
    private List<String> paramList;


    private Map<String, Object> paramValueMap;

    private Map<String, Object> paramTypeMap;
}
