package com.chatroom.controller;

import com.chatroom.dao.DaoUser;
import com.chatroom.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet(name="doLogin", urlPatterns ="/login" )
public class doLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // out
        response.setHeader("Content-Type","text/plain; charset=utf-8");

        String account = request.getParameter("account");
        String pwd = request.getParameter("pwd");
        account = account==null?"":account;
        pwd = pwd==null?"":pwd;

        DaoUser daoUser = new DaoUser();
        try {
            User user = daoUser.check(account, pwd);
            int id = user.getId();
            if(id!=0){
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(600);
                request.getSession().setAttribute("account", user);
                request.getSession().setAttribute("login_time", new Date().getTime());
                response.getWriter().print("success");
            }else{
                response.getWriter().print("用户名或密码错误！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


}
