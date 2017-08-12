package com.example.prashant.lifecoach;

/**
 * Created by Prashant on 10-08-2017.
 */

public class Message {
    public String messageBody;
    public String messageId;
    public int messagePosition;
    public String messageTime;

    public Message(String messageId, String messageBody, int messagePosition, String messageTime) {
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.messagePosition = messagePosition;
        this.messageTime = messageTime;
    }
}
