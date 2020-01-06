package fr.polytech.pnsinnov.smartinteract.model;

import java.time.LocalDateTime;
import java.util.UUID;

class IDGenerator {

    static String generateID() {
        String UID = UUID.randomUUID().toString();
        String timeStamp = LocalDateTime.now().toString();
        return (UID + timeStamp).replaceAll("-", "");
    }

}
