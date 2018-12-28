package com.example.oliveryu.androidchat;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }


    public void login(View view) {
        Intent intent = new Intent(this, ChatRoom.class);
        EditText userField = (EditText) findViewById(R.id.userName);
        EditText roomField = (EditText) findViewById(R.id.roomName);

        String userName = userField.getText().toString();
        String roomName = roomField.getText().toString();

        intent.putExtra("user", userName);
        intent.putExtra("room", roomName);

        //start establish
        //send ws connection
        //after server back -> add listener
        //pass listener to chatroom
        startActivity(intent);
    }
}
