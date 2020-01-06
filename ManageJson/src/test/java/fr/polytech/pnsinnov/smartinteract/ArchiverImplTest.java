package fr.polytech.pnsinnov.smartinteract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
public class ArchiverImplTest {

    private static final ArchiverImpl archiver = new ArchiverImpl();
    private final Gson parser = new Gson();

    @BeforeClass
    public static void setUp() throws Exception {
        archiver.setRootPath("src/test/java/resources/");

        new File(archiver.characterPath()).createNewFile();
        new File(archiver.dialoguePath()).createNewFile();
    }

    @Before
    public void before() throws Exception {
        new PrintWriter(archiver.characterPath()).append("{}").close();
        new PrintWriter(archiver.dialoguePath()).append("{}").close();
    }

    @AfterClass
    public static void tearDown() {
        new File(archiver.characterPath()).delete();
        new File(archiver.dialoguePath()).delete();
    }

    // -------------------------------------------------------------------------------- //
    // -------------------------------------------------------------------------------- //
    // -------------------------------------------------------------------------------- //

    @Test
    public void saveDialogue() {
        String path = archiver.dialoguePath();
        Dialogue toSave = new Dialogue();

        archiver.save(toSave);

        Type empMapType = new TypeToken<Map<String, Dialogue>>() {
        }.getType();

        Map<String, Dialogue> dialogues = readMap(empMapType, path);

        assertNotNull(dialogues);
        assertTrue(dialogues.containsKey(toSave.ID()));
        assertEquals(toSave, dialogues.get(toSave.ID()));
    }

    @Test
    public void saveCharacter() {
        String path = archiver.characterPath();
        Character toSave = new Character("Bob", "My name is Bob :)");

        archiver.save(toSave);

        Type empMapType = new TypeToken<Map<String, Character>>() {
        }.getType();

        Map<String, Character> characters = readMap(empMapType, path);

        assertNotNull(characters);
        assertTrue(characters.containsKey(toSave.ID()));
        assertEquals(toSave, characters.get(toSave.ID()));
    }

    @Test
    public void findDialogue() {
        String path = archiver.dialoguePath();
        Dialogue toSave = new Dialogue();
        Map<String, Dialogue> map = new HashMap<>();
        map.put(toSave.ID(), toSave);

        writeToFile(map, path);

        Optional<Dialogue> dialogueFound = archiver.findDialogue(toSave.ID());

        assertTrue(dialogueFound.isPresent());
        assertEquals(toSave, dialogueFound.get());
    }

    @Test
    public void findCharacter() {
        String path = archiver.characterPath();
        Character toSave = new Character("Bob", "My name is Bob :)");
        Map<String, Character> map = new HashMap<>();
        map.put(toSave.pseudo(), toSave);

        writeToFile(map, path);

        Optional<Character> characterFound = archiver.findCharacter(toSave.pseudo());

        assertTrue(characterFound.isPresent());
        assertEquals(toSave, characterFound.get());
    }

    private Map readMap(Type itemType, String path) {

        try (BufferedReader input = new BufferedReader(new FileReader(path))) {

            StringBuilder builder = new StringBuilder();
            input.lines().forEach(builder::append);

            return parser.fromJson(builder.toString(), itemType);

        } catch (IOException e) {
            System.err.println("/!\\ Error something went wrong while reading from " + path);

            fail();
        }

        return null;
    }

    private void writeToFile(Map map, String path) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path))) {
            output.write(parser.toJson(map));
        } catch (IOException e) {
            System.err.println("/!\\ Error something went wrong while saving to " + path);
        }
    }
}