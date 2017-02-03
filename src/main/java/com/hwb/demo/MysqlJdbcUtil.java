package com.hwb.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by hwb
 * Mysql工具
 * On 2017/2/3 14:04
 */
public class MysqlJdbcUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MysqlJdbcUtil.class);
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "ROOT";
    private static final String CONNECTION_URL =
            "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    /**
     * 获得链接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME); // 指定连接类型
            return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);//获取连接
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 释放资源
     *
     * @param preparedStatement
     */
    public static void release(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null)
            preparedStatement.close();
    }

    public static void close(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }

}
