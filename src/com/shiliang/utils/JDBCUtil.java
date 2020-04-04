package com.shiliang.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 1. 加载配置文件
 * 2. 获取DataSource对象
 * 3. 编写方法
 * @create 2020-02-28 22:51
 * @description JDBC工具类
 */
public class JDBCUtil {
    static DataSource dataSource;
    static ThreadLocal<Connection> cons=new ThreadLocal<>();
    static {
        try {
            //加载配置文件
            Properties properties = new Properties();
            properties.load(JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取数据源对象
     */
      public static DataSource getDataSource(){
          return dataSource;
      }


    /**
     * 获取数据库连接对象
     */
    public static Connection getConnection() {

        try {
            Connection connection = dataSource.getConnection();
//            if (connection != null) {
//                cons.set(connection);
                return connection;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

      return null;
    }

    /**
     * 关闭资源
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {

            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if (st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }











    }

}
