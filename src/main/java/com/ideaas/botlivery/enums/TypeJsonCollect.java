package com.ideaas.botlivery.enums;

public enum TypeJsonCollect {

    FOOD("type_of_food"),
    DRINK("type_drink"),
    COLLECT_FOOD("collect_food"),
    COLLECT_DRINK("collect_drink"),
    COLLECT_USER_DATA("collect_user_data"),
    COLLECT_ADDRESS("collect_address"),
    COLLECT_CONFIRM_ORDER("collect_confirm_order");

    private String description;

    TypeJsonCollect(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
