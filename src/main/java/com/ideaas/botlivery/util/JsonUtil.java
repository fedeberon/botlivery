package com.ideaas.botlivery.util;

import com.ideaas.botlivery.enums.TypeJsonCollect;
import org.json.JSONObject;

public class JsonUtil {

    public static JSONObject getAnswers(JSONObject jsonRegister, TypeJsonCollect nameCollect){
        JSONObject jsonChat = jsonRegister.getJSONObject("twilio").getJSONObject("collected_data");
        JSONObject jsonFoodItems = jsonChat.getJSONObject(nameCollect.getDescription());
        return  jsonFoodItems.getJSONObject("answers");
    }

    public static JSONObject getAnswer(JSONObject jsonRegister, String nameOfQuestion){
        JSONObject jsonChat = jsonRegister.getJSONObject("twilio").getJSONObject("collected_data");
        JSONObject jsonFoodItems = jsonChat.getJSONObject(nameOfQuestion);
        return  jsonFoodItems.getJSONObject("answers");
    }

    public static boolean checkResponse(JSONObject jsonRegister, TypeJsonCollect typeCollect){
        JSONObject jsonChat = jsonRegister.getJSONObject("twilio").getJSONObject("collected_data");
        return jsonChat.has(typeCollect.getDescription());
    }

    public static String getAnswer(JSONObject json, TypeJsonCollect collect, String nameOfQuestion){
        JSONObject jsonCollect = json.getJSONObject("twilio").getJSONObject("collected_data").getJSONObject(collect.getDescription());
        JSONObject jsonAnwers = jsonCollect.getJSONObject("answers");
        JSONObject jsonAnswer = jsonAnwers.getJSONObject(nameOfQuestion);
        return jsonAnswer.getString("answer");
    }

    public static String getChannelSid(JSONObject json){
        JSONObject jsonCollect = json.getJSONObject("twilio").getJSONObject("chat");
        return jsonCollect.getString("ChannelSid");
    }

}

