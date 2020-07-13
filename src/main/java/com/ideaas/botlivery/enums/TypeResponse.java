package com.ideaas.botlivery.enums;

public enum TypeResponse {

    YES_NO("YES_NO"),
    NUMBER("Twilio.NUMBER"),
    FIRST_NAME("Twilio.FIRST_NAME"),
    LAST_NAME("Twilio.LAST_NAME");

    private String description;

    TypeResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
