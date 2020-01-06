package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;

public interface GamePreparer {

    /**
     * Tries to add the given [character] to the JSON storage
     *
     * @param character Character to register
     */
    void registerCharacter(Character character);

    /**
     * Saves changes to the given [character], by replacing its data.
     * The previous instance is found using [chararcter.ID]
     * @param character Character to update
     */
    void updateCharacter(Character character);

    /**
     * Deletes the given [character] from the JSON storage
     *
     * @param character Character to delete
     */
    void deleteCharacter(Character character);
}
