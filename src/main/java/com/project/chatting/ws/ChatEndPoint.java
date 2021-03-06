package com.project.chatting.ws;


import com.google.gson.Gson;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.data.IMessageProcessor;
import com.project.chatting.data.MessageProcessor;
import com.project.chatting.model.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@ServerEndpoint("/chat/{userId}")
public class ChatEndPoint {
    static List<ChatEndPoint> clients = new ArrayList<>();
    private volatile String userId;
    private Session currentSession;

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        clients.add(this);
        this.userId = userId;
        currentSession = session;
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        new Thread(() -> {
            TLSMessage smsg = new Gson().fromJson(message, TLSMessage.class);
            if (smsg.getEventId() == SocketEvent.SendMsg.value) {
                try {
                    MessageItem item = new Gson().fromJson(smsg.getData().toString(), MessageItem.class);
                    Message sentMsg = MessageProcessor.getInstance().processMessage(item);
                    if (sentMsg != null) {
                        for (ChatEndPoint client : clients) {
                            boolean isUserExits = new DAOImpl().getMessageDao().isUserExitsInThisRoom(sentMsg.getRoomId(), Integer.parseInt(client.userId));
                            if (isUserExits && client.currentSession.isOpen()) {
                                smsg.setEventId(SocketEvent.TextMessage.value);
                                smsg.setData(sentMsg);
                                client.currentSession.getBasicRemote().sendText(new Gson().toJson(smsg));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        clients.remove(this);
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

    public void sendMessage(String msg) {
        try {
            currentSession.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void pongMessage(Session session, PongMessage msg) {
        System.out.println("Pong message: " +
                msg.getApplicationData().toString());
    }

    public static ChatEndPoint getClient(String id) {
        for (ChatEndPoint client : clients) {
            if (client.userId.equals(id))
                return client;
        }
        return null;
    }

}
