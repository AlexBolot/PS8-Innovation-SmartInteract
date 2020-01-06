package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;

import java.util.List;

public interface Monitor {
    /**
     * Fetches the list of all characters from Archiver
     *
     * @return The list of all characters from Archiver
     */
    List<Character> getAllCharacter();

    /**
     * Fetches a single Character that matches the given [pseudo]
     *
     * @param pseudo String name of the character to find
     * @return Character of pseudo equals to [pseudo]
     */
    Character getByName(String pseudo);
}
