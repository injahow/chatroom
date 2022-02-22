package com.chatroom.filter;

import com.chatroom.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="loginFilter", value={"/index.jsp","/online_list.jsp"})
public class loginFilter implements javax.servlet.Filter {
    public void destroy() {
        //System.out.println("destroy");
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        HttpSession session=((HttpServletRequest)req).getSession();
        User user = (User)session.getAttribute("account");
        if(user!=null){
            chain.doFilter(req, resp);
        }else{
            ((HttpServletResponse)resp).sendRedirect("login.jsp");
        }
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {
        //System.out.println("init");
    }

}
