package com.example.prashant.lifecoach;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MessageList extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    EditText userInput;
    ArrayList<Message> messageList;
    ConversationService service;
    MessageRequest newMessage;
    Map<String,Object> yo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        userInput = (EditText) findViewById(R.id.edittext_chatbox);

        messageList = new ArrayList<Message>();
        yo=null;
        Button button = (Button) findViewById(R.id.button_chatbox_send);

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setHasFixedSize(true);
        mMessageAdapter = new MessageAdapter(this, messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);

        service = new ConversationService("2017-05-26");
        service.setUsernameAndPassword("49b7fe1e-5037-4ba9-b2dc-2e37d8f9f9d4", "WTt8P8NWOL51");
    }

    public String curTime(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        return date.format(currentLocalTime);
    }

    public void sendMessage(View view) {
        Message newMessage = new Message("sent",userInput.getText().toString(),messageList.size(),curTime());
        messageList.add(newMessage);
        userInput.setText("");
//        i++;
        Log.i("messageid", "in sendmessage mposition" + userInput.getText().toString() + " " + newMessage.messagePosition);
        mMessageAdapter.notifyDataSetChanged();
        mMessageRecycler.scrollToPosition(mMessageAdapter.getItemCount()-1);
        new Task().execute(newMessage.messageBody);
    }

    private class Task extends AsyncTask<String,Void,String>{
        String data;
        public Task() {
            data = "";
        }

        @Override
        protected String doInBackground(String... params) {
         newMessage = new MessageRequest.Builder()
                        .inputText(params[0])
                        .context(yo)
                        .build();

            String workspaceId = "19bde015-bd33-4552-bc32-f9be5257d4d6";
            MessageResponse response = service.message(workspaceId, newMessage).execute();
            data = response.toString();
            Log.e("joke",data);
            String result = "";
            Map<String,Object> output = response.getOutput();
            List<String> values = (List<String>) output.get("text");
            if(values.size()!=0)
            result = values.get(0);
            yo = response.getContext();
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.length()==0)
                s="I din't get you. Please try again.";
            Message newMessage = new Message("recieved",s,messageList.size(),curTime());
            messageList.add(newMessage);
            mMessageAdapter.notifyDataSetChanged();
        Log.i("messageid", "in onpost mposition" + s + " " + newMessage.messagePosition);
           mMessageRecycler.scrollToPosition(mMessageAdapter.getItemCount()-1);
        }
    }
}