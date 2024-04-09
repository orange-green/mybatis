package com.wxj.SimpleMybatis.Handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/4/8 14:11:40
 */

public class SQLHandler{

    private static final String PREFIX = "#{";

    private static final String SUFFIX = "}";

       public static PrepareSql covertToPrepareStatementSql(String sql) {
           PrepareSql prepareSql = new PrepareSql();
           ArrayList<String> params = new ArrayList<>();

           // 匹配满足#{}的字段
           String regex = "#\\{([^}]+)}";
           Pattern pattern = Pattern.compile(regex);
           Matcher matcher = pattern.matcher(sql);

           while (matcher.find()) {
               String match = matcher.group(1);
//               System.out.println(match);
               params.add(match);
           }

        for (int i = 0 ; i < params.size(); i++) {
           sql = sql.replace(PREFIX+params.get(i)+SUFFIX, "?");
        }

        prepareSql.setSql(sql);
        prepareSql.setParamList(params);

        return prepareSql;
    }



    public static PreparedStatement setParamValue(PreparedStatement statement, PrepareSql prepareSql) {
        List<String> paramList = prepareSql.getParamList();
        Map<String, Object> paramValueMap = prepareSql.getParamValueMap();
        Map<String, Object> paramTypeMap = prepareSql.getParamTypeMap();

        for (int i = 0; i < paramList.size(); i++) {

           }

           return statement;

    }

    public static void setStringParamValue(PreparedStatement statement, Integer index, String value) throws SQLException {
           statement.setString(index, value);
    }


    public static void setIntegerParamValue(PreparedStatement statement, Integer index, Integer value) throws SQLException {
        statement.setInt(index, value);
    }

}
