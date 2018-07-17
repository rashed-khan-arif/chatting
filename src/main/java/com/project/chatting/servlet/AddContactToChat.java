package com.project.chatting.servlet;

import com.google.gson.Gson;
import com.project.chatting.dao.Dao;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.*;
import com.project.chatting.ws.ChatEndPoint;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/addContactToChat")
public class AddContactToChat extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        int userId = Integer.parseInt(req.getParameter("userId"));
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        if (!validateEmail(email)) {
            resp.getWriter().print("Invalid Email Address !");
            return;
        }
        if (userId == 0) {
            resp.getWriter().print("Invalid user current user info!");
            return;
        }
        if (roomId == 0) {
            resp.getWriter().print("Please send at least one message..then add another contact !");
            return;
        }


        Dao dao = new DAOImpl();
        User friend = dao.getUserDao().getUserByEmail(email);
        if (friend == null) {
            resp.getOutputStream().println("No user found for this email address !");
            return;
        }

        //
        Member member = new Member();
        member.setUserId(friend.getUserId());
        member.setRoomId(roomId);
        member.setConnectionStatus(ConnectionStatus.Connected.value);
        Member newMember = dao.getMessageDao().addMemberToRoom(member);
        if (newMember == null) {
            resp.getOutputStream().println("Failed to add your contact !");
        } else {
            resp.getOutputStream().print("Added !");
            ChatEndPoint chatEndPoint = ChatEndPoint.getClient(String.valueOf(friend.getUserId()));
            if (chatEndPoint != null) {
                User user = (User) req.getSession().getAttribute("user");
                Notification notification = new Notification();
                notification.setTitle(user.getFullName().concat("Group "));
                notification.setContent(user.getFullName().concat(" added you to a group !"));
                notification.setToUser(friend.getUserId());
                notification.setNotifyDate(new Date());
                dao.getNotificationDao().sendNotification(notification);
                TLSMessage msg = new TLSMessage();
                msg.setEventId(SocketEvent.Notification.value);
                msg.setData(notification);

                chatEndPoint.sendMessage(new Gson().toJson(msg));
            }
        }

    }


    private boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
