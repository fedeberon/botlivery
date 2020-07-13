package com.ideaas.botlivery.domain;

import org.json.JSONArray;
import org.json.JSONObject;

public class Action {

    private JSONArray data;

    public Action() {
        this.data = new JSONArray();
    }

    public Action withSay(String say){
        JSONObject json = new JSONObject();
        json.put("say", say);
        data.put(json);

        return this;
    }

    public Action withListen(boolean listen){
        JSONObject json = new JSONObject();
        json.put("listen", listen);
        data.put(json);

        return this;
    }

    public JSONObject toJson(){
        JSONObject result = new JSONObject();
        result.put("actions", data);

        return result;
    }


    public Action withCollect(Collect collect){
        JSONObject json = new JSONObject();
        json.put("collect", collect.toJson());
        data.put(json);

        return this;
    }

    public Action withRedirect(String redirect){
        JSONObject json = new JSONObject();
        JSONObject jsonRedirect = new JSONObject().put("uri", redirect).put("method", "POST");
        json.put("redirect", jsonRedirect);
        data.put(json);

        return this;
    }
}
