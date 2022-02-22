package com.chatroom.utlis;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtil {

    private static final Properties config = new Properties();
    static{
        try {
            // 读取配置文件
            config.load(DBUtil.class.getClassLoader().getResourceAsStream("com/chatroom/config/db.properties"));
            // 加载驱动
            Class.forName(config.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password")
        );
    }

    /**
     * 关闭数据库
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) throws SQLException{
        if(rs!=null && !rs.isClosed())
            rs.close();
        if(stmt!=null && !stmt.isClosed())
            stmt.close();
        if(conn!=null && !conn.isClosed())
            conn.close();
    }
    // 重载优化
    public static void closeAll(Statement stmt, Connection conn) throws SQLException{
        closeAll(null, stmt, conn);
    }

}
