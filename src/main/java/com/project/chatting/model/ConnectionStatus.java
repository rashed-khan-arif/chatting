package com.project.chatting.model;

public enum ConnectionStatus {
    Connected(1), Disconnected(2);
    public int value;

    ConnectionStatus(int i) {
        this.value = i;
    }
}
