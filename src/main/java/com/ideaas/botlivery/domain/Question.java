package com.ideaas.botlivery.domain;

import com.ideaas.botlivery.enums.TypeResponse;
import org.json.JSONObject;

public class Question {

    private JSONObject json = new JSONObject();

    public Question(String question, String name, TypeResponse type){
        json.put("question", question).put("name", name).put("type", type.getDescription());
    }

    public Question(String question, String name){
        json.put("question", question).put("name", name);
    }

    public Question withValidate(Validate validate){
        json.put("validate", validate.toJson());
        return this;
    }

    public JSONObject toJson(){
        return json;
    }

}
