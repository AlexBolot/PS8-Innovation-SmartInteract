package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.exceptions.CannotMatchKeywordsException;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;
import fr.polytech.pnsinnov.smartinteract.model.Knowledge;
import fr.polytech.pnsinnov.smartinteract.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswerGeneratorImpl implements AnswerGenerator {

    private final Archiver archiver;

    public AnswerGeneratorImpl() {
        this(new ArchiverImpl());
    }

    public AnswerGeneratorImpl(Archiver archiver) {
        this.archiver = archiver;
    }

    private boolean isAnswerAQuestion(String content) {
        return content.substring(content.length() - 1).equals("?");
    }

    @Override
    public Message generateAnswer(Dialogue dialogue, Message message) throws CannotMatchKeywordsException {
        Optional<Character> optCharacter = archiver.findCharacter(dialogue.speaker());

        if (!optCharacter.isPresent()) {
            throw new CannotMatchKeywordsException("character not find. "+ dialogue.speaker());
        }

        if(message.isPersonalQuestion()){
            return this.generateAutomaticAnswer(message, optCharacter.get());
        }

        if(message.numberFault()>20)
            return new Message("Try to speak correctly please !");

        return generateWithKnowledge(optCharacter.get(),message);
    }


    private Message generateWithKnowledge(Character character, Message message){
        List<Knowledge> knowledges = character.knowledges();
        List<String> tokens = message.tokens();
        for (Knowledge k : knowledges) {
            for (String token : tokens) {
                if (k.keywords().contains(token)) {
                    String content = k.getAnyContent();
                    if(message.specificCategory() != null)
                        content = k.getSpecificKnowledge(message.specificCategory());
                    return new Message(content, isAnswerAQuestion(content), new ArrayList<>(), character);
                }
            }
        }
        return new Message("Sorry I'have to improve myself !");
    }

    private Message generateAutomaticAnswer(Message message, Character character){
        String content = "I don't have the answer";
        switch(message.getPersonal()){
            case "age":
                content = "My age is " + character.age();
                break;
            case "place":
                content = "I live in " + character.location();
                break;

        }
        return new Message(content, isAnswerAQuestion(content), new ArrayList<>(), character);
    }
}
