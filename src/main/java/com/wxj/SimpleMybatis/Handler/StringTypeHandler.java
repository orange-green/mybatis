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
 * @since 2024/4/8 15:48:16
 */

public class StringTypeHandler implements TypeHandler<String>{
    @Override
    public void setParameter(PreparedStatement statement, int index,  String value) throws SQLException {
        statement.setString(index, value);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {

        return (String) rs.getString(columnName);
    }
}
