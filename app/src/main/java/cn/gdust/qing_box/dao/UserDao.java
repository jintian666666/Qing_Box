package cn.gdust.qing_box.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.Statement;

import cn.gdust.qing_box.bean.User;
import cn.gdust.qing_box.dbutils.DBUtils;

/**
 * 用户表操作
 */
public class UserDao {

    //新增
    public static boolean add(User user) {
        String sql = "insert into user(user_name,phone,create_date,password)values('" + user.getUsername() + "','" + user.getPassword() + "','" + user.getPhone() + "','" + user.getEmail() + "')";
        Connection conn = DBUtils.getConnection();
        Statement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("add->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtils.close(state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }

    //删除
    public static boolean delete(User user) {
        String sql = "delete from user where id=" + user.getId();
        Connection conn = DBUtils.getConnection();
        Statement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("delete->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtils.close(state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }


    //修改
    public static boolean update(User user) {

        String sql = "update user set " + "user_name='" + user.getUsername() + "', phone='" + user.getPhone() + "', email='" + user.getEmail() + "', password='" + user.getPassword() + "' where id='" + user.getId() + "'";
        Connection conn = DBUtils.getConnection();
        Statement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("update->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtils.close(state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }


}
