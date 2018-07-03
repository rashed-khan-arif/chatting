package com.project.chatting.servlet;

import com.google.gson.Gson;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.UserFriend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/getContactList")
public class GetContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        //new Thread(() -> {
        List<UserFriend> users = new DAOImpl().getUserDao().getFriendsByUserId(userId);
        resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        resp.getWriter().write(new Gson().toJson(users));
        // }).start();
    }
}
