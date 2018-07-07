package com.project.chatting.servlet;

import com.project.chatting.dao.Dao;
import com.project.chatting.dao.UserDao;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.FriendRequestStatus;
import com.project.chatting.model.Notification;
import com.project.chatting.model.User;
import com.project.chatting.model.UserFriend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/addContact")
public class AddContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Add Contact Page");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        int userId = Integer.parseInt(req.getParameter("userId"));
        if (!validateEmail(email)) {
            resp.getWriter().print("Invalid Email Address !");
            return;
        }
        if (userId == 0) {
            resp.getWriter().print("Invalid user current user info!");
            return;
        }
        Dao dao = new DAOImpl();
        User user = dao.getUserDao().getUserByEmail(email);
        if (user == null) {
            resp.getOutputStream().println("No user found for this email address !");
            return;
        }
        UserFriend friendReq = new UserFriend();
        friendReq.setFriendId(user.getUserId());
        friendReq.setUserId(userId);
        friendReq.setRequestStatus(FriendRequestStatus.Requested.val);
        boolean isAlreadySent = dao.getFriendDao().isRequestAlreadySent(userId, user.getUserId());
        if (isAlreadySent) {
            resp.getOutputStream().println("You already send a friend a friend request to this user  !");
            return;
        }
        boolean isSent = dao.getFriendDao().sendRequest(friendReq);

        if (!isSent) {
            resp.getOutputStream().println("Failed to send friend request !");
        } else { 
            resp.getOutputStream().print("request send !");
        }

    }


    private boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


}
