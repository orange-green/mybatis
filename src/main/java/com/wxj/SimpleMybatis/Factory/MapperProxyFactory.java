package com.wxj.SimpleMybatis.Factory;

import com.wxj.SimpleMybatis.Annotations.Param;
import com.wxj.SimpleMybatis.Annotations.Select;
import com.wxj.SimpleMybatis.Handler.*;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

/**
 * <p>
 *  mapper代理工厂
 * </p>
 *
 * @author wuxj
 * @since 2024/4/7 11:18:31
 */

public class MapperProxyFactory {

    // sql参数类型处理器map
    private static Map<Class, TypeHandler> typeHandlerMap = new HashMap<>();

    static {

        typeHandlerMap.put(String.class, new StringTypeHandler());
        typeHandlerMap.put(Integer.class,new IntegerTypeHandler());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static <T> T getMapper(Class<T> mapper) {

    // 获取jdk的代理对象
    Object proxyInstance =
        Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(),
            new Class[] {mapper},
            new InvocationHandler() {

              @Override
              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 解析sql -> 执行sql -> 结果处理
                // mybatis底层依赖JDBC连接数据库和执行sql

                // JDBC
                // 1.创建数据库连接
                Connection connection = getConnection();

                // 通过反射拿到Select注解的value值
                Select annotation = method.getAnnotation(Select.class);
                String sql = annotation.value();
                System.out.println(sql);

                  HashMap<String, Object> parameterValueMap = new HashMap<>();
                  HashMap<String, Object> parameterTypeMap = new HashMap<>();

                  ArrayList<String> list = new ArrayList<>();
                  // 拿方法入参的参数名、参数值、参数类型
                  Parameter[] parameters = method.getParameters();
                  for (int i = 0; i<parameters.length; i++) {
                      Parameter parameter = parameters[i];
                      // 获取作用在参数上的Param注解，Param注解用于标识入参和数据表对应的列名
                      Param parameterAnnotation = parameter.getAnnotation(Param.class);
                      String paramName = parameterAnnotation.value();
                      parameterValueMap.put(paramName, args[i]);

                      // 根据参数的实际入参值获取参数值的类型
                      Type parameterizedType = parameter.getParameterizedType();
                      String typeName = parameterizedType.getTypeName();
                      parameterTypeMap.put(paramName, typeName);
                      // 记录Param注解的值的顺序
                      list.add(paramName);
                  }


                  // 2.构建PreparedStatement
                  PrepareSql prepareSql = SQLHandler.covertToPrepareStatementSql(sql);

                  List<String> paramList = prepareSql.getParamList();

                  prepareSql.setParamValueMap(parameterValueMap);
                  prepareSql.setParamTypeMap(parameterTypeMap);


                // 设置sql的参数传入
                  PreparedStatement statement = connection.prepareStatement(prepareSql.getSql());

                  for (int i=0; i< paramList.size();i++) {
                      String paramName = paramList.get(i);
                      Object value = parameterValueMap.get(paramName);
                      // 根据参数值的实际类型来选择类型处理器
                      Class<?> valueClass = value.getClass();
                      TypeHandler typeHandler = typeHandlerMap.get(valueClass);
                      typeHandler.setParameter(statement, i+1, value);

                  }

                // 3.执行sql
                statement.execute();

                // 4.获取sql执行结果
                ResultSet resultSet = statement.getResultSet();
                //获取结果集的所有列名
                  ResultSetMetaData metaData = resultSet.getMetaData();
                  ArrayList<String> columns = new ArrayList<>();
                  for (int i=0; i<metaData.getColumnCount(); i++) {
                      // +1是因为JDBC中的下标从1开始的
                      String columnName = metaData.getColumnName(i + 1);
                      columns.add(columnName);
                  }

                  // 反射获取函数返回类型
                  Class methodReturnType = null;
                  Type genericReturnType = method.getGenericReturnType();

                  if (genericReturnType instanceof Class) {
                      // 不是泛型, genericReturnType就是真实的数据类型
                      methodReturnType = (Class) genericReturnType;

                  }
                  else if (genericReturnType instanceof ParameterizedType) {
                      // 是泛型，去拿真实的数据类型
                      Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                      methodReturnType = (Class)actualTypeArguments[0];
                  }


                  // 反射获取类的所有set方法
                  HashMap<String, Method> setterMethodMap = new HashMap<>();
                  Method[] declaredMethods = methodReturnType.getDeclaredMethods();

                  for (Method declaredMethod: declaredMethods) {
                      String name = declaredMethod.getName();
                      if (declaredMethod.getName().startsWith("set")) {
                          // setName方法，截取出Name,并将第一个大写字符转为小写
                          String propertyName = declaredMethod.getName().substring(3);
                          propertyName =  propertyName.substring(0, 1).toLowerCase(Locale.ROOT) + propertyName.substring(1);
                          setterMethodMap.put(propertyName, declaredMethod);
                      }
                  }

                  // 返回结果声明为Object
                  Object result = null;
                  List<Object> resultList = new ArrayList();
                  while (resultSet.next()) {
                      // 反射获取对象实例
                      Object instance = methodReturnType.getDeclaredConstructor().newInstance();

                      for (int i = 0; i < columns.size(); i++) {
                          String columnName = columns.get(i);
                          Method setterMethod = setterMethodMap.get(columnName);

                          if (setterMethod != null) {
                              // 获取一下set方法的入参类型，正好对应类属性的类型
                              Class<?>[] parameterTypes = setterMethod.getParameterTypes();
                              Class<?> parameterType = parameterTypes[0];
                              TypeHandler typeHandler = typeHandlerMap.get(parameterType);
                              Object value = typeHandler.getResult(resultSet, columnName);
                              setterMethod.invoke(instance, value);
                          }

                      }

                      resultList.add(instance);
                }

                  // 根据函数返回值类型判断是返回List还是返回单个对象
                  Class<?> returnType = method.getReturnType();
                  if (returnType.equals(List.class)) {
                      result = resultList;
                  }
                  else if (resultList.size() > 0) {
                          result = resultList.get(0);
                  }

                  //5.关闭数据库连接
                  connection.close();

                return result;
              }
            });

        // 强制转换为泛型
        return (T) proxyInstance;

    }

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.232.128:3306/test?useSSL=false", "root", "123456");
        return connection;
    }

}
