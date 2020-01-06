package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.exceptions.CannotMatchKeywordsException;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;
import fr.polytech.pnsinnov.smartinteract.model.Message;
import fr.polytech.pnsinnov.smartinteract.stubs.InteractionProcessorPOA;
import fr.polytech.pnsinnov.smartinteract.stubs.Replique;
import org.omg.CORBA.ORB;

import java.io.IOException;
import java.util.Optional;

public class InteractionObj extends InteractionProcessorPOA {
    private final Archiver archiver;
    private SyntaxAnalyser analyser;
    private final AnswerGenerator generator;
    private final ORB broker;

    public InteractionObj(ORB broker) {
        this(broker, new ArchiverImpl());
    }

    public InteractionObj(ORB broker, Archiver archiver)  {
        this.broker = broker;
        this.archiver = archiver;
        try {
            analyser = new SyntaxAnalyserImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generator = new AnswerGeneratorImpl(archiver);
    }

    @Override
    public void shutdown() {
        broker.shutdown(false);
    }

    @Override
    public Replique sendMessage(Replique replique) {
        Optional<Dialogue> optionalDialogue = archiver.findDialogue(replique.idDia);
        if (!optionalDialogue.isPresent())
            throw new IllegalArgumentException("No Dialogue matches the ID " + replique.idDia);

        Message messToSave = null;
        try {
            messToSave = analyser.analyseMessage(new Message(replique.content));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dialogue dialogue = optionalDialogue.get();
        dialogue.addMessage(messToSave);
        archiver.save(dialogue);

        try {
            return new Replique(generator.generateAnswer(dialogue, messToSave).content(),replique.idDia);
        } catch (CannotMatchKeywordsException e) {
            System.out.println(e.fillInStackTrace());
        }

        return new Replique("Error ! check your console on server", replique.idDia);
    }

    @Override
    public String beginDialogue(String character) {
        Dialogue dialogue = new Dialogue();
        dialogue.setSpeaker(character);
        archiver.save(dialogue);
        return dialogue.ID();
    }

    public String getCharacterID(String pseudo) {
        Character character = archiver.findCharacter(pseudo)
                .orElseThrow(() -> new IllegalArgumentException("No character has pseudo : " + pseudo));

        return character.ID();
    }

}