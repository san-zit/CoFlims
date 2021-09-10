package com.example.myapplication123;

import com.parse.ParseFile;

public class userListModelClass {

    private String name;
    private ParseFile image;

    public userListModelClass(String name, ParseFile image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }
}
