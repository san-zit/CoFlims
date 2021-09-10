package com.example.myapplication123;

public class Chat {
    String message;
    int type;

    public String getMessage() {
        return message;
    }

    public Chat(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
