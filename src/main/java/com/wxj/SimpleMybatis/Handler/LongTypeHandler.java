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
 * @since 2024/4/8 16:09:31
 */

public class LongTypeHandler implements TypeHandler<Long>{


    @Override
    public void setParameter(PreparedStatement statement, int index, Long value) throws SQLException {
        statement.setLong(index, value);
    }

    @Override
    public Long getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getLong(columnName);
    }
}
