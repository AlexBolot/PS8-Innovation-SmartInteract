package fr.polytech.pnsinnov.smartinteract.controllers;

import fr.polytech.pnsinnov.smartinteract.SmartInteractAPI;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static javafx.collections.FXCollections.observableArrayList;

class CLIController {

    private final SmartInteractAPI api;
    private final MainController controller;

    private List<String> consoleLines;
    private Character character;
    private String dialogueID;

    CLIController(MainController controller, SmartInteractAPI api) {
        this.controller = controller;
        this.api = api;

        initConsole();
    }

    public List<String> getConsoleLines() {
        return consoleLines;
    }

    private void initConsole() {
        dialogueID = null;
        character = null;
        consoleLines = new ArrayList<>();
        printStarter();
        println("Who do do you want to talk to ?");
    }

    @FXML
    void onConsoleTyped(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            this.onSend();
    }

    @FXML
    void onSend() {
        String message = controller.txtInput.getText().trim();
        controller.txtInput.setText("");

        if (message.isEmpty()) return;

        println("you : " + message);

        String lowerMessage = message.toLowerCase();

        if (Stream.of("help", "h", "aide").anyMatch(lowerMessage::contains)) {
            printHelp();
            return;
        }

        if (character != null && Stream.of("bye", "goodbye", "ciao").anyMatch(lowerMessage::contains)) {
            dialogueID = null;
            println(character.pseudo() + " : Goodbye !");
            println("");
            println("Who do do you want to talk to ?");
            character = null;
            return;
        }

        if (message.equalsIgnoreCase("list")) {
            printCharactersList();
            return;
        }

        if (message.equalsIgnoreCase("clear")) {
            initConsole();
            return;
        }

        if (dialogueID == null) startDialogue(message);
        else handleDialogue(message);
    }

    private void handleDialogue(String message) {
        println(character.pseudo() + " : " + api.sendMessage(message, dialogueID));
    }

    private void startDialogue(String characterName) {
        try{
            character = api.getCharacter(characterName);
            dialogueID = api.beginDialogue(character.ID());

            println("");
            println("You started talking with " + character.pseudo() + " !");
            println(character.catchPhrase());
        }catch (Exception e){
            println("doesn't exist ! ");
        }

    }

    private void printStarter() {

        String starterMessage = "" +
                "------------------------------------------------------------------------\n" +
                "\t\t\t Welcome to SmartInteract testing console !\n" +
                "------------------------------------------------------------------------\n" +
                "\n" +
                "-> Here you can talk with your characters to test their reactions !\n" +
                "\t-> When chosing a character type 'help' to obtain list of commands\n" +
                "\t-> When in a dialogue type 'goodbye' to end the dialogue\n";

        println(starterMessage);
    }

    private void printHelp() {
        String helpMessage = "-> Help : \n" +
                "\t- help\t\t : display this list of commands\n" +
                "\t- clear\t\t : restarts this console\n" +
                "\t- list\t\t : display list of characters";

        println(helpMessage);
    }

    private void printCharactersList() {
        StringBuilder builder = new StringBuilder();

        for (Character character : api.getCharacters()) {
            builder.append("\t-> ").append(character.pseudo()).append("\n");
        }

        println(builder.toString());
    }

    private void println(String content) {
        consoleLines.add(content + "\n");

        controller.consoleListView.setItems(observableArrayList(consoleLines));

        // Auto scroll to bottom
        Platform.runLater(() -> controller.consoleListView.scrollTo(consoleLines.size() - 1));
    }
}