package com.project.chatting.ws;


import com.google.gson.Gson;
import com.project.chatting.model.SocketEvent;
import com.project.chatting.model.TLSMessage;
import com.project.chatting.model.UserFriend;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@ServerEndpoint("/chat")
public class ChatEndPoint {
    static List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        for (Session sess : sessions) {
            if (sess.isOpen())
                sess.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session);
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    @OnMessage
    public void binaryMessage(Session session, ByteBuffer msg) {
        System.out.println("Binary message: " + msg.toString());
    }

    @OnMessage
    public void pongMessage(Session session, PongMessage msg) {
        System.out.println("Pong message: " +
                msg.getApplicationData().toString());
    }
}
