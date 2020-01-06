package fr.polytech.pnsinnov.smartinteract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@SuppressWarnings("unchecked")
public class ArchiverImpl implements Archiver {

    private String rootPath = "ManageJson-c/src/main/java/resources/";
    private final Gson parser = new Gson();

    private final Type dialogueType = new TypeToken<Map<String, Dialogue>>() {
    }.getType();
    private final Type characterType = new TypeToken<Map<String, Character>>() {
    }.getType();

    @Override
    public void save(Dialogue dialogue) {
        Map<String, Dialogue> dialogues = readMapFromFile(dialogueType, dialoguePath());

        if (dialogues.containsKey(dialogue.ID()))
            dialogues.replace(dialogue.ID(), dialogue);
        else
            dialogues.put(dialogue.ID(), dialogue);

        save(parser.toJson(dialogues), dialoguePath());
    }

    @Override
    public void save(Character character) {
        Map<String, Character> characters = readMapFromFile(characterType, characterPath());

        if (characters.containsKey(character.ID()))
            characters.replace(character.ID(), character);
        else
            characters.put(character.ID(), character);

        save(parser.toJson(characters), characterPath());
    }

    @Override
    public Optional<Dialogue> findDialogue(String dialogueID) {
        Map<String, Dialogue> dialogues = readMapFromFile(dialogueType, dialoguePath());
        return Optional.ofNullable(dialogues.get(dialogueID));
    }

    @Override
    public Optional<Character> findCharacter(String pseudo) {
        Map<String, Character> characters = readMapFromFile(characterType, characterPath());
        return characters.values()
                .stream()
                .filter(c -> c.pseudo().equalsIgnoreCase(pseudo))
                .findFirst();
    }

    @Override
    public List<Character> getAllCharacter() {
        Map<String, Character> characters = readMapFromFile(characterType, characterPath());
        return new ArrayList<>(characters.values());
    }

    @Override
    public void remove(Character character) {
        Map<String, Character> characters = readMapFromFile(characterType, characterPath());
        characters.remove(character.ID());
        save(parser.toJson(characters), characterPath());
    }

    // ------------------------------------------------------------ //
    // -------------------- Read & Write Utils -------------------- //
    // ------------------------------------------------------------ //

    private void save(String content, String filePath) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(filePath))) {

            output.write(content);

        } catch (IOException e) {
            System.err.println("/!\\ Error something went wrong while saving to " + filePath);
        }
    }

    private Map readMapFromFile(Type type, String path) {
        try (BufferedReader input = new BufferedReader(new FileReader(path))) {

            StringBuilder builder = new StringBuilder();
            input.lines().forEach(builder::append);
            return parser.fromJson(builder.toString(), type);

        } catch (IOException e) {
            System.err.println("/!\\ Error something went wrong while reading from " + path);
        }

        return new HashMap<>();
    }

    // ------------------------------------------------------------ //
    // -------------------- Paths getter-setter ------------------- //
    // ------------------------------------------------------------ //

    public String rootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String dialoguePath() {
        return rootPath() + "dialogues.json";
    }

    public String characterPath() {
        return rootPath() + "characters.json";
    }

}
