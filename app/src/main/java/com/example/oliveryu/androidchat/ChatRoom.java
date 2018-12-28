package com.example.oliveryu.androidchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neovisionaries.ws.client.HostnameUnverifiedException;
import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatRoom extends AppCompatActivity {
    private ArrayList<Message> chatList;
    private MessageAdapter adapter;
    private ListView chatDisplay;
    private WebSocket ws;
    private String user, room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        chatDisplay = findViewById(R.id.chatDisplay);
        chatDisplay.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);

        chatList = new ArrayList<>();
        adapter = new MessageAdapter(this);
        chatDisplay.setAdapter(adapter);

        //gets user and room name
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        room = intent.getStringExtra("room");

        try {
            ws = new WebSocketFactory().createSocket("ws://10.0.2.2:8080");
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
                    ws.sendText("join " + room);
                }
                @Override
                public void onFrame(WebSocket websocket, WebSocketFrame frame) {
                    addToList(frame.getPayloadText());
                }
            });
            System.out.println("Connecting to server");
            ws.connectAsynchronously();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ws.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ws.disconnect();
    }

    public void sendMessage(View view) {
        EditText message = (EditText) findViewById(R.id.msgContent);

        String msg = message.getText().toString();
        ws.sendText(user + " " + msg);

        message.getText().clear();
    }

    private void addToList(String message) {
        JsonParser jparse = new JsonParser();
        JsonElement jsonTree = jparse.parse(message);
        JsonObject jsonObj = jsonTree.getAsJsonObject();

        String userCheck = jsonObj.get("user").getAsString();
        String parsedMsg =
                userCheck.toLowerCase().equals(this.user.toLowerCase()) ? jsonObj.get("message").getAsString() : userCheck  + ": " + jsonObj.get("message").getAsString();

        final Message msg = new Message(userCheck, jsonObj.get("message").getAsString(), userCheck.toLowerCase().equals(this.user.toLowerCase()), getRandomColor());

        chatList.add(msg);
        chatDisplay.post(
            new Runnable() {
                @Override
                public void run() {
                    adapter.add(msg);
                    chatDisplay.setSelection(chatList.size() - 1);
                    chatDisplay.smoothScrollToPosition(chatList.size());
                    adapter.notifyDataSetChanged();
                }
            }
        );
    }

    //https://www.scaledrone.com/blog/android-chat-tutorial/
    //pick random colors and stuff
    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}
