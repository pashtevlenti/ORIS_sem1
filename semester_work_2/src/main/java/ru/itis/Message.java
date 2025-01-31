package ru.itis;

import com.google.gson.Gson;

public class Message {
    private final String type;
    private String content;

    public Message(String type){
        this.type = type;
    }

    public Message(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

}