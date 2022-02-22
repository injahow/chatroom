package com.chatroom.dao;

import com.chatroom.model.User;
import com.chatroom.utlis.DBUtil;

import java.sql.*;
import java.util.ArrayList;

public class DaoUser extends DBUtil {

    /**
     * 检查账号是否重复
     * @param account
     * @return
     * @throws Exception
     */
    public boolean hasAccount(String account) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from user where account=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1, account);

        ResultSet rs = stmt.executeQuery();
        boolean result=false;
        while(rs.next()) {
            result = true;
            break;
        }
        DBUtil.closeAll(rs,stmt,conn);
        return result;
    }

    /**
     * 添加 user 账号
     * @param acc
     * @param pw
     * @return
     * @throws Exception
     */
    public int addUser(String acc, String pw) throws Exception {
        Connection conn= DBUtil.getConnection();
        String sql = "insert into user (account,pwd,voteNum) values(?,?,5)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1, acc);
        stmt.setString(2, pw);
        int resultSet = stmt.executeUpdate();
        DBUtil.closeAll(stmt, conn);
        return resultSet;
    }

    /**
     * 检查登陆
     * @param account
     * @param pwd
     * @return
     * @throws Exception
     */
    public User check(String account, String pwd) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from user where account=? and pwd=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1, account);
        stmt.setString(2, pwd);

        ResultSet rs = stmt.executeQuery();
        User user = new User();

        while(rs.next()) {
            user.setId(rs.getInt("id"));
            user.setAccount(rs.getString("account"));
            user.setName(rs.getString("name"));
            user.setSex(rs.getString("sex"));
            break;
        }
        DBUtil.closeAll(rs,stmt,conn);
        return user;
    }

    public User getUserInfo(String id) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select id,account,name,sex from user where id=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1, id);

        ResultSet rs = stmt.executeQuery();
        User user = new User();

        while(rs.next()) {
            user.setId(rs.getInt("id"));
            user.setAccount(rs.getString("account"));
            user.setName(rs.getString("name"));
            user.setSex(rs.getString("sex"));
            break;
        }
        DBUtil.closeAll(rs,stmt,conn);
        return user;
    }

    public ArrayList<User> getOnlineList() throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select id,account,name from user where online=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1, 1);
        ResultSet rs = stmt.executeQuery();
        ArrayList<User> userList = new ArrayList();
        while(rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setAccount(rs.getString("account"));
            user.setName(rs.getString("name"));
            userList.add(user);
        }
        DBUtil.closeAll(rs,stmt,conn);
        return userList;
    }

    public int userStart(int id) throws Exception  {//;/
        Connection conn= DBUtil.getConnection();
        String sql="update user set online=? where id=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1, 1);
        stmt.setInt(2, id);
        int resultSet = stmt.executeUpdate();
        DBUtil.closeAll(stmt, conn);
        return resultSet;
    }

    public int userQuit(int id) throws Exception {
        Connection conn= DBUtil.getConnection();
        String sql="update user set online=? where id=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1, 0);
        stmt.setInt(2, id);
        int resultSet = stmt.executeUpdate();
        DBUtil.closeAll(stmt, conn);
        return resultSet;
    }
}
