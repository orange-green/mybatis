package com.wxj.SimpleMybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.PrimitiveIterator;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/16 17:21:08
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SqlMapperItem {

    private String id;

    private String resultType;

    private String sql;
}
