package com.chatroom.controller;

import com.chatroom.dao.DaoUser;
import com.chatroom.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "onlineList",urlPatterns = "/online_list")
public class onlineList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoUser daoUser = new DaoUser();
        try {
            ArrayList<User> onlineList = daoUser.getOnlineList();
            request.setAttribute("onlineList",onlineList);
            request.getRequestDispatcher("online_list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
