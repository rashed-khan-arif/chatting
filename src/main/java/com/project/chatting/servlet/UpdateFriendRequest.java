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

@WebServlet(urlPatterns = "/updateFriendRequest")
public class UpdateFriendRequest extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        int userFriendId = Integer.parseInt(req.getParameter("userFriendId"));
        FriendRequestStatus status = FriendRequestStatus.intToEnum(Integer.parseInt(req.getParameter("status")));
        Dao dao = new DAOImpl();
        UserFriend userFriend = dao.getFriendDao().updateRequestStatus(userFriendId, status);
        if (userFriend != null) {
            Notification notification = new Notification();
            notification.setContent(userFriend.getFriend().getFullName().concat(" " + status.name()).concat(" your friend request!"));
            notification.setToUser(userFriend.getUser().getUserId());
            notification.setNotifyDate(new Date());
            notification.setTitle("Friend Request " + status.name());
            dao.getNotificationDao().sendNotification(notification);
            TLSMessage msg = new TLSMessage();
            msg.setEventId(SocketEvent.Notification.value);
            msg.setData(notification);
            ChatEndPoint client = ChatEndPoint.getClient(String.valueOf(userFriend.getUserId()));
            if (client != null) {
                client.sendMessage(new Gson().toJson(msg));
            }
        }
    }
}
