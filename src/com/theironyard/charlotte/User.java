package com.theironyard.charlotte;

import java.util.ArrayList;

/**
 * Created by jaradtouchberry on 4/14/17.
 */
public class User {
    String name;
    ArrayList<Message> messageList;

    public User(String name) {
        this.name = name;
        this.messageList = new ArrayList<>();
    }
}