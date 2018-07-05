package com.project.chatting.servlet;

import com.google.gson.Gson;
import com.project.chatting.dao.Dao;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.FriendRequestStatus;
import com.project.chatting.model.UserFriend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/getFriendRequest")
public class GetFriendRequestServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        if (userId == 0) {
            resp.getOutputStream().print("{'msg':'No friend request found '}");
            return;
        }
        Dao dao = new DAOImpl();
        List<UserFriend> userFriends = dao.getFriendDao().getFriendsRequests(userId, FriendRequestStatus.Requested);
        resp.getWriter().write(new Gson().toJson(userFriends));
    }
}
