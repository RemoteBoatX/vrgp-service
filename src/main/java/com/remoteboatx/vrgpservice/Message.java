package com.remoteboatx.vrgpservice;

public class Message {
    private final long id;
    private final String sender;
    private final String content;

    public Message(long id,String sender ,String content) {
        this.id = id;
        this.content = content;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
}
