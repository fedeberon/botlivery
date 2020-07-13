package com.ideaas.botlivery.domain;

import org.json.JSONArray;
import org.json.JSONObject;

public class AllowValue {

    JSONArray values = new JSONArray();

    public AllowValue addValue(String value){
        values.put(value);
        return this;
    }

    public AllowValue addValue(String... value){
        for (String oneValue : value) {
            values.put(oneValue);
        }
        return this;
    }

    public JSONObject toJson(){
        JSONObject allowValues = new JSONObject();
        return allowValues.put("list", values);
    }
}
