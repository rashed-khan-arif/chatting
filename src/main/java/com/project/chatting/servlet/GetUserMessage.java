package com.project.chatting.servlet;

import com.google.gson.Gson;
import com.project.chatting.dao.Dao;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.Message;
import com.project.chatting.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/getMessages")
public class GetUserMessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao dao = new DAOImpl();
        try {
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            boolean isP2P = Boolean.parseBoolean(req.getParameter("pp"));
            if (isP2P) {
                int member1 = Integer.parseInt(req.getParameter("member1"));
                int member2 = Integer.parseInt(req.getParameter("member2"));
                Room room = dao.getMessageDao().isItP2P(member1, member2);
                if (room != null) {
                    List<Message> messages = dao.getMessageDao().getP2PMsgList(room.getRoomId(), member1, member2);
                    resp.getOutputStream().print(new Gson().toJson(messages));
                }
            }else{
                List<Message> messages = dao.getMessageDao().getMessageByRoom(roomId);
                resp.getOutputStream().print(new Gson().toJson(messages));
            }

//            if (roomId == 0) {
//                int member1 = Integer.parseInt(req.getParameter("member1"));
//                int member2 = Integer.parseInt(req.getParameter("member2"));
//                Room room = dao.getMessageDao().isItP2P(member1, member2);
//                if (room != null) {
//                    List<Message> messages = dao.getMessageDao().getP2PMsgList(room.getRoomId(), member1, member1);
//                    resp.getOutputStream().print(new Gson().toJson(messages));
//                }
//            } else {
//
//                List<Message> messages = dao.getMessageDao().getMessageByRoom(roomId);
//                resp.getOutputStream().print(new Gson().toJson(messages));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
