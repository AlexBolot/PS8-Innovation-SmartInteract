package fr.polytech.pnsinnov.smartinteract.model;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

    private String content;
    private List<String> tokens;
    private Character sender;

    private boolean question;
    private SpecificCategory specificCategory;
    private int numberFault;
    private boolean personalQuestion;
    private String personal;


    public Message() {
        question = false;
        numberFault = 0;
    }

    public Message(String content){
        this();
        this.content = content;
    }

    public Message(String content, boolean question, List<String> tokens, Character sender) {
        this(content);
        this.question = question;
        this.tokens = tokens;
        this.sender = sender;
    }

    public String content() {
        return content;
    }

    public boolean question() {
        return question;
    }

    public List<String> tokens() {
        return tokens;
    }

    public Character sender() {
        return sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTokens(List<String> tokens){
        this.tokens = tokens;
    }

    public SpecificCategory specificCategory() {
        return specificCategory;

    }

    public boolean isPersonalQuestion(){
        return this.personalQuestion;
    }

    public void itsPersonal(boolean personalQuestion){
        this.personalQuestion = personalQuestion;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getPersonal() {
        return personal;
    }

    public void setSpecificCategory(SpecificCategory specificCategory) {
        this.specificCategory = specificCategory;
    }

    public int numberFault() {
        return numberFault;
    }

    /**
     * Ici on convertit le nombre de mot incorrect en pourcentage
     * @param numberFault nombre d'erreur détecté
     */
    public void setInPercentatageNumberFault(int numberFault) {
        if(numberFault > 0)
            this.numberFault = numberFault*100/this.tokens.size();
    }
}
