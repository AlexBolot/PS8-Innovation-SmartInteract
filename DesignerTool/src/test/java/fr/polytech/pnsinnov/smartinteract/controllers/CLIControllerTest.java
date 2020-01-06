package fr.polytech.pnsinnov.smartinteract.controllers;

import fr.polytech.pnsinnov.smartinteract.JavaFXTest;
import fr.polytech.pnsinnov.smartinteract.SmartInteractAPI;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CLIControllerTest extends JavaFXTest {

    private CLIController cli;
    private List<String> consoleLines;

    @Before
    public void setUp() throws Exception {
        SmartInteractAPI api = mock(SmartInteractAPI.class);
        initJavaFX(api);

        String dialogueID = "5UP3R-D14L0GU3-1D3NT1F13R";

        when(api.getCharacter(anyString())).thenReturn(new Character("Alice", "Hello there !"));
        when(api.beginDialogue(anyString())).thenReturn(dialogueID);
        when(api.sendMessage(eq("Bla"), eq(dialogueID))).thenReturn("This is a mock reply");
        when(api.sendMessage(eq("Blabla"), eq(dialogueID))).thenReturn("This is a mock reply");
        when(api.sendMessage(anyString(), eq(dialogueID))).thenReturn("GoodBye");

        cli = ctrl.getCliController();
        consoleLines = cli.getConsoleLines();
    }

    @Test
    public void onConsoleTyped_Enter() {
        KeyEvent keyEvent = buildEvent(KeyCode.ENTER);

        // starter message + commands
        assertEquals(2, consoleLines.size());

        ctrl.txtInput.setText("Alice");
        cli.onConsoleTyped(keyEvent);

        // + player message + feedback + blank + catchphrase
        assertEquals(6, consoleLines.size());
    }

    @Test
    public void onConsoleTyped_Other() {
        KeyEvent keyEvent = buildEvent(KeyCode.B);

        // starter message + commands
        assertEquals(2, consoleLines.size());

        ctrl.txtInput.setText("Alice");
        cli.onConsoleTyped(keyEvent);

        // Nothing added
        assertEquals(2, consoleLines.size());
    }

    @Test
    public void consoleStarter() {
        // starter message + commands
        assertEquals(2, consoleLines.size());
    }

    @Test
    public void consoleSendMessage() {
        // starter message + commands
        assertEquals(2, consoleLines.size());

        this.send("Bob");

        // + player message + feedback + blank + catchphrase
        assertEquals(6, consoleLines.size());

        assertTrue(deepContains(consoleLines, "Bob"));
    }

    @Test
    public void consoleSendEmpty() {
        // starter message + commands
        assertEquals(2, consoleLines.size());

        this.send("");

        // Nothing added
        assertEquals(2, consoleLines.size());
    }

    @Test
    public void consoleSendBye() {
        // starter message + commands
        assertEquals(2, consoleLines.size());

        this.send("Bob");

        // + player message + feedback + blank + catchphrase
        assertEquals(6, consoleLines.size());

        this.send("Blabla");
        this.send("Bla");

        // + 2x (user message + reply)
        assertEquals(10, consoleLines.size());

        this.send("Bye");

        // user message + reply + blank + "who ?"
        assertEquals(14, consoleLines.size());

        assertTrue(deepContains(consoleLines, "Bye"));
        assertTrue(deepContains(consoleLines, "GoodBye"));
    }

    private void send(String message) {
        ctrl.txtInput.setText(message);
        cli.onSend();
    }

    private boolean deepContains(List<String> list, String message) {
        return list.stream().anyMatch(item -> item.contains(message));
    }

    private KeyEvent buildEvent(KeyCode keyCode) {
        return new KeyEvent(null, null, null, keyCode, false, false, false, false);
    }
}