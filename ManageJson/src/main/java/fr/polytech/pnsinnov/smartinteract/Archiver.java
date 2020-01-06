package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;

import java.util.List;
import java.util.Optional;

public interface Archiver {

    /**
     * Saves the given [dialogue] in a file using a JSON format
     *
     * @param dialogue Dialogue instance to be saved
     */
    void save(Dialogue dialogue);

    /**
     * Saves the given [character] in a file using a JSON format
     *
     * @param character Character instance to be saved
     */
    void save(Character character);

    /**
     * Finds a Dialogue instance from the JSON storage, using one of the speakers
     *
     * @param ID String identifier of the Dialogue
     * @return A dialogue instance from the JSON storage
     */
    Optional<Dialogue> findDialogue(String ID);

    /**
     * Finds a Character instance from the JSON storage, using its pseudonym
     *
     * @param pseudo The name of the character
     * @return A character instance from the JSON storage
     */
    Optional<Character> findCharacter(String pseudo);

    /**
     * Get all the character whom are in the json
     *
     * @return a list of characters
     */
    List<Character> getAllCharacter();

    void remove(Character character);
}
