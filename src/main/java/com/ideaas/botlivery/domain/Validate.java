package com.ideaas.botlivery.domain;

import org.json.JSONArray;
import org.json.JSONObject;

public class Validate {

    private JSONObject json = new JSONObject();
    private JSONArray messageJson = new JSONArray();

    public Validate() {
        messageJson.put("messages");
    }

    public Validate(AllowValue allowValue){
        json.put("allowed_values", allowValue.toJson());
    }

    public Validate withMessage(String message){
        messageJson.put(new Message(message).toJson());
        return this;
    }

    public Validate withMessage(Message message){
        messageJson.put(message.toJson());
        return this;
    }

    public JSONObject toJson(){
        json.put("on_failure", new JSONObject().put("messages", messageJson).put("repeat_question", true));
        json.put("on_success", new JSONObject().put("say", "ok, agregado a tu orden ..!"));
        return json;
    }
}
