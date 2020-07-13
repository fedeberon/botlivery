package com.ideaas.botlivery.domain;

import org.json.JSONObject;

public class Message {

    private JSONObject json = new JSONObject();

    public Message(String message){
        json.put("say", message);
    }

    public JSONObject toJson(){
        return json;
    }
}