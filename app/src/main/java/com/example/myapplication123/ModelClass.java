package com.example.myapplication123;

import com.parse.ParseFile;

public class ModelClass {

    private ParseFile photo;
    private String uname;
    private String message;
    private String time;
    private String divider;

    public ModelClass(ParseFile photo, String uname, String message, String time, String divider) {
        this.photo = photo;
        this.uname = uname;
        this.message = message;
        this.time = time;
        this.divider = divider;
    }

    public ParseFile getPhoto() {
        return photo;
    }

    public void setPhoto(ParseFile photo) {
        this.photo = photo;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }
}