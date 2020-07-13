package com.ideaas.botlivery.domain;

import com.ideaas.botlivery.enums.TypeJsonCollect;
import org.json.JSONArray;
import org.json.JSONObject;

public class Collect {

    private JSONObject data;
    private JSONArray questions;

    public Collect(TypeJsonCollect name) {
        data = new JSONObject();
        data.put("name", name);
        questions = new JSONArray();
    }

    public Collect(String name) {
        data = new JSONObject();
        data.put("name", name);
        questions = new JSONArray();
    }

    public Collect withQuestion(Question question){
        questions.put(question.toJson());
        return this;
    }

    public Collect onComplete(String url){
        JSONObject redirect = new JSONObject();
        redirect.put("redirect", url);
        data.put("on_complete", redirect);
        return this;
    }

    public JSONObject toJson(){
        return data.put("questions", questions);
    }


}
