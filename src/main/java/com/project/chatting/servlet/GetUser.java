package com.project.chatting.servlet;

import com.google.gson.Gson;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user")
public class GetUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        User user = new DAOImpl().getUserDao().getUser(userId);
        resp.getOutputStream().print(new Gson().toJson(user));
    }
}
