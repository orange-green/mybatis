package com.wxj.SimpleMybatis.Handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/8 15:50:15
 */

public class IntegerTypeHandler implements TypeHandler<Integer>{
    @Override
    public void setParameter(PreparedStatement statement, int index, Integer value) throws SQLException {
        statement.setInt(index, value);
    }

    @Override
    public Integer getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }
}
