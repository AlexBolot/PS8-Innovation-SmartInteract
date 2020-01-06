package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Message;

import java.io.IOException;

public interface SyntaxAnalyser {

    /**
     * Uses a SpaCy script to analyse the given [message]
     *
     * @param message Message to analyse, to find key words and meaning
     * @return A message instance, copy of the given one, with the key words added
     */
    Message analyseMessage(Message message) throws IOException;

    void endDialogue();
}
