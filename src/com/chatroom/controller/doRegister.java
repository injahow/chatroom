package com.chatroom.controller;

import com.chatroom.dao.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "doRegister", urlPatterns ="/register" )
public class doRegister extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-Type","text/plain; charset=utf-8");

        String account = request.getParameter("account");
        String pwd = request.getParameter("pwd");
        account = account==null?"":account;
        pwd = pwd==null?"":pwd;

        DaoUser daoUser = new DaoUser();
        try {
            boolean hasVote = daoUser.hasAccount(account);
            if(!hasVote){
                daoUser.addUser(account, pwd);
                response.getWriter().print("success");
            }else{
                response.getWriter().print("账号重复！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
