package com.project.chatting.ws;


import javax.websocket.*;
import java.io.IOException;

public class Chat extends Endpoint {
    @Override
    public void onOpen(final Session session, EndpointConfig endpointConfig) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                try {
                    session.getBasicRemote().sendText(s);
                } catch (IOException e) {
                    System.out.println("SocketOpen: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);
    }
}
