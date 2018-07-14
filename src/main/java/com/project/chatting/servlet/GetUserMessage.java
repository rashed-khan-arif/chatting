package com.project.chatting.servlet;

import com.project.chatting.dao.Dao;
import com.project.chatting.dao.impl.DAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/getMessages")
public class GetUserMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        if (roomId == 0) {
            int member1 = Integer.parseInt(req.getParameter("member1"));
            int member2 = Integer.parseInt(req.getParameter("member2"));
        } else {
            Dao dao = new DAOImpl();
            dao.getMessageDao().getMessageByRoom(roomId);
        }
    }
}
