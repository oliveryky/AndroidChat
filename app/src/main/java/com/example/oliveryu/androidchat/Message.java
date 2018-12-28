package com.example.oliveryu.androidchat;

import android.graphics.Color;

//https://www.scaledrone.com/blog/android-chat-tutorial/
public class Message {
    private String user, message;
    private boolean isUser;
    private String color;

    public Message(String user, String message, boolean isUser, String color) {
        this.user = user;
        this.message = message;
        this.isUser = isUser;
        this.color = color;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUser() {
        return isUser;
    }

    public String getColor() {
        return color;
    }
}
