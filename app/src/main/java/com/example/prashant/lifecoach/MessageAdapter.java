package com.example.prashant.lifecoach;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.prashant.lifecoach.R.id.view;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    Context context;
    ArrayList<Message> message;
    int i;
    public MessageAdapter(Context context, ArrayList<Message> message) {
        this.context = context;
        this.message = message;
        i=0;
        Log.d("messageid", "in constructor");
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(i%2==0)
        view = layoutInflater.inflate(R.layout.item_message_sent,parent,false);
        else
            view = layoutInflater.inflate(R.layout.item_message_received,parent,false);
        i++;
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        Log.e("messageid", "in onCreateViewHolder posOfMsg" + message.get(message.size()-1).messagePosition + message.get(getItemCount()-1).messageBody);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Log.e("messageid", "in onBindViewHolder position itemciunt" + position +" " + getItemCount());
        holder.bind(message.get(position));
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView mess,time;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mess = (TextView)itemView.findViewById(R.id.text_message_body);
            time = (TextView)itemView.findViewById(R.id.text_message_time);
            Log.d("messageid", "in MessageViewHolder constructor");
        }

        void bind(Message message){
            Log.d("messageid", "in bind");
            mess.setText(message.messageBody);
            time.setText(message.messageTime);
        }
    }
}
