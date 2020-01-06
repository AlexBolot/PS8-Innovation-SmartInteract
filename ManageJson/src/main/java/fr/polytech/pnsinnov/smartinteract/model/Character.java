package fr.polytech.pnsinnov.smartinteract.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Character {

    private final String ID = IDGenerator.generateID();
    private final List<Knowledge> knowledges;

    private String pseudo;
    private String catchPhrase;
    private double difficulty;
    private String location;
    private int age;

    public Character(String pseudo, String catchPhrase) {
        this(pseudo, catchPhrase, new ArrayList<>());
    }

    public Character(String pseudo, String catchPhrase, List<Knowledge> knowledges) {
        this(pseudo, catchPhrase, knowledges, -1);
    }

    public Character(String pseudo, String catchPhrase, List<Knowledge> knowledges, double difficulty) {
        this(pseudo, catchPhrase, knowledges, difficulty, -1, "");
    }

    public Character(String pseudo, String catchPhrase, List<Knowledge> knowledges, double difficulty, int age, String location) {
        this.pseudo = pseudo;
        this.catchPhrase = catchPhrase;
        this.knowledges = knowledges;
        this.difficulty = difficulty;
        this.age = age;
        this.location = location;
    }

    // ----- Accessors ----- //

    public String ID() {
        return ID;
    }

    public String pseudo() {
        return pseudo;
    }

    public List<Knowledge> knowledges() {
        return knowledges;
    }

    public double difficulty() {
        return difficulty;
    }

    public int age() {
        return age;
    }

    public String location() {
        return location;
    }

    public String catchPhrase() {
        return catchPhrase;
    }

    public boolean hasPseudo(String pseudo) {
        return this.pseudo.equalsIgnoreCase(pseudo);
    }

    // ----- Setters ----- //

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // ----- Updaters ----- //

    public void addKnowledge(Knowledge knowledge) {
        knowledges.add(knowledge);
    }

    public void updateKnowledge(Knowledge newVal) {

        boolean foundMatch = false;

        for (Knowledge current : knowledges) {
            if (current.ID().equals(newVal.ID())) {
                foundMatch = true;
                current.setKeywords(newVal.keywords());
                current.setContents(newVal.contents());
            }
        }

        if (!foundMatch)
            addKnowledge(newVal);
    }

    public void giveContext(String location, int age) {
        this.location = location;
        this.age = age;
    }

    // ----- Override ----- //

    @Override
    public String toString() {
        String show = "Pseudo : " + this.pseudo + "\n";
        show += "Catch phrase : " + this.catchPhrase + "\n";
        return show;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return Objects.equals(ID, character.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
