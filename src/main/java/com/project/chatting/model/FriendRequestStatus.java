package com.project.chatting.model;

import com.google.gson.annotations.SerializedName;

public enum FriendRequestStatus {
    @SerializedName("1")
    Requested(1),
    @SerializedName("2")
    Accepted(2),
    @SerializedName("3")
    Cancelled(3);
    public int val;

    FriendRequestStatus(int i) {
        this.val = i;
    }

    public static FriendRequestStatus intToEnum(int value) {
        switch (value) {
            case 1:
                return Requested;
            case 2:
                return Accepted;
            case 3:
                return Cancelled;
            default:
                return null;
        }
    }
}
