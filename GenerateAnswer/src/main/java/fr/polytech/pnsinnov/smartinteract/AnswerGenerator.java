package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.exceptions.CannotMatchKeywordsException;

import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;
import fr.polytech.pnsinnov.smartinteract.model.Message;

public interface AnswerGenerator {

    /**
     * Process a Message answer corresponding to the given [message] in
     * a given [dialogue]
     *
     * @param dialogue Dialogue in which the message is sent
     * @param message Message to respond to
     * @return The generated reply
     */
    Message generateAnswer(Dialogue dialogue, Message message) throws CannotMatchKeywordsException;

}
