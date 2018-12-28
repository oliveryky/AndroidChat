package com.example.oliveryu.androidchat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//https://www.scaledrone.com/blog/android-chat-tutorial/
public class MessageAdapter extends BaseAdapter {
    private ArrayList<Message> msgAdapter;
    Context context;

    public MessageAdapter(Context context) {
        this.context = context;
        msgAdapter = new ArrayList<>();
    }

    public void add(Message message) {
        msgAdapter.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return msgAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return msgAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = msgAdapter.get(position);

        if(message.isUser()) {
            convertView = messageInflater.inflate(R.layout.my_msg, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getMessage());
        }else {
            convertView = messageInflater.inflate(R.layout.other_msg, null);
            holder.avatar = (View) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.name.setText(message.getUser());
            holder.messageBody.setText(message.getMessage());
            GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
            drawable.setColor(Color.parseColor(message.getColor()));
        }

        return convertView;
    }
}

class MessageViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
}
