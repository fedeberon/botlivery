package com.ideaas.botlivery.util;

import java.time.LocalTime;

public class Util {

    private static final LocalTime MORNING = LocalTime.of(0, 0, 0);
    private static final LocalTime AFTER_NOON = LocalTime.of(12, 0, 0);
    private static final LocalTime EVENING = LocalTime.of(16, 0, 0);
    private static final LocalTime NIGHT = LocalTime.of(21, 0, 0);

    public static String getGreeting() {
        if (between(MORNING, AFTER_NOON)) {
            return "Buen dia ";
        } else if (between(AFTER_NOON, EVENING)) {
            return "Buenas Tardes ";
        } else if (between(EVENING, NIGHT)) {
            return "Buenas Tardes ";
        } else {
            return "Buenas Noches ";
        }
    }

    private static boolean between(LocalTime start, LocalTime end) {
        return (!LocalTime.now().isBefore(start)) && LocalTime.now().isBefore(end);
    }
}
