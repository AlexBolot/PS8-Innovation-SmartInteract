package fr.polytech.pnsinnov.smartinteract.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dialogue {

    private final String ID = IDGenerator.generateID();
    private List<Message> messages = new ArrayList<>();

    private String speaker;
    private String player;

    public Dialogue() {
    }

    public Dialogue(List<Message> messages, String speaker, String player) {
        this.messages = messages;
        this.speaker = speaker;
        this.player = player;
    }

    public String ID() {
        return ID;
    }

    public List<Message> messages() {
        return messages;
    }

    public String speaker() {
        return speaker;
    }

    public String player() {
        return player;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dialogue)) return false;
        Dialogue dialogue = (Dialogue) o;
        return Objects.equals(ID, dialogue.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
