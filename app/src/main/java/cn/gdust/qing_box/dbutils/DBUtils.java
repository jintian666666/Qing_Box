package cn.gdust.qing_box.dbutils;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    private static final String TAG = "DBUtils";
    private static String driver = "com.mysql.jdbc.Driver";//MySQL 驱动
    private static String url = "jdbc:mysql://43.138.202.34:3306/Qing_Box?  " +
            "useUnicode=true&characterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true";//MYSQL数据库连接Url
    private static String user = "root";//用户名
    private static String password = "Aa2550481";//密码


    /**
     * 连接数据库
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
            Log.d(TAG, "getConnection: 连接成功");
        } catch (SQLException ex) {
            Log.d(TAG, "getConnection: 连接失败1");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Log.d(TAG, "getConnection: 连接失败2");
            ex.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放连接资源
     */
    public static void close(Statement state, Connection conn) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs, Statement state, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
