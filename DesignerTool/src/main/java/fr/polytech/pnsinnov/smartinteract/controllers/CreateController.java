package fr.polytech.pnsinnov.smartinteract.controllers;

import fr.polytech.pnsinnov.smartinteract.SmartInteractAPI;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Knowledge;
import fr.polytech.pnsinnov.smartinteract.model.SpecificCategory;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@SuppressWarnings("Duplicates")
class CreateController {

    private final SmartInteractAPI api;
    private final MainController controller;

    CreateController(MainController controller, SmartInteractAPI api) {
        this.controller = controller;
        this.api = api;

        this.controller.lblFeedback.setText("");

        controller.refreshCharacters();
    }

    @FXML
    void onSave() {

        Character character;

        try {
            double difficulty = getDifficulty();
            String pseudo = getCharacterName();
            int age = getAge();
            String location = getLocation();
            List<Knowledge> knowledges = getKnowledges();
            String catchPhrase = getCatchPhrase();

            character = new Character(pseudo, catchPhrase, knowledges, difficulty, age, location);
        } catch (Exception e) {
            // If something went wrong the error has already been displayed
            // We stop here and do nothing
            return;
        }

        try {
            api.registerCharacter(character);
            controller.refreshCharacters();

            updateFeedback("Personnage créé !", Color.LIMEGREEN);

        } catch (IllegalArgumentException iae) {
            updateFeedback(iae.getMessage(), Color.DARKRED);
        } catch (Exception e) {
            updateFeedback("Erreur interne pendant la création du personnage !", Color.DARKRED);
            e.printStackTrace();
        }
    }

    private String getCharacterName() {
        String pseudo = controller.txtPseudo.getText().trim();

        if (pseudo.isEmpty()) {
            updateFeedback("Le nom ne doit pas être vide !", Color.DARKRED);
            throw new RuntimeException(); // Used to stop the process
        }

        return pseudo;
    }

    private String getLocation() {
        String location = controller.txtLocation.getText().trim();

        if (location.isEmpty()) {
            updateFeedback("Le lieu ne doit pas être vide !", Color.RED);
            throw new RuntimeException(); // Used to stop the process
        }

        return location;
    }

    private double getDifficulty() {
        String rawDiff = controller.txtDifficulty.getText().trim();

        try {
            return parseDouble(rawDiff);
        } catch (NumberFormatException nfe) {
            updateFeedback("Erreur : la difficulté doit être un nombre !", Color.RED);
            throw new RuntimeException(); // Used to stop the process
        }
    }

    private int getAge() {
        String rawAge = controller.txtAge.getText().trim();

        try {
            return parseInt(rawAge);
        } catch (NumberFormatException nfe) {
            updateFeedback("Erreur : l'âge doit être un nombre entier !", Color.RED);
            throw new RuntimeException(); // Used to stop the process
        }
    }

    private String getCatchPhrase(){
        String catchPhrase = controller.txtCatchPhrase.getText().trim();

        if (catchPhrase.isEmpty()) {
            updateFeedback("La phrase d'accroche ne doit pas être vide !", Color.RED);
            throw new RuntimeException(); // Used to stop the process
        }

        return catchPhrase;
    }

    private List<Knowledge> getKnowledges() {

        String raw = controller.txtareaKnowledge.getText();

        raw = raw.replaceAll("#.*#", "");

        if (raw.isEmpty()) throw new RuntimeException(); // Used to stop the process

        try {
            List<String> rawKnwoledges = Arrays.asList(raw.split("-----"));
            rawKnwoledges.replaceAll(String::trim);

            return rawKnwoledges
                    .stream()
                    .map(this::extractKnowledge)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            updateFeedback("Erreur de format des connaissances !", Color.RED);
            throw new RuntimeException(); // Used to stop the process
        }
    }

    private Knowledge extractKnowledge(String rawData) {
        List<String> areas = Arrays.asList(rawData.split("\\|\\|"));

        List<String> keywords = Arrays.asList(areas.get(0).split("::"));
        List<String> contents = Arrays.asList(areas.get(1).split("::"));
        List<String> specifics = Arrays.asList(areas.get(2).split("::"));

        keywords.replaceAll(String::trim);
        contents.replaceAll(String::trim);

        keywords.removeIf(String::isEmpty);
        contents.removeIf(String::isEmpty);

        HashMap<SpecificCategory, String> map = new HashMap<>();

        specifics.forEach(entry -> {
            String keyName = entry.trim().split("=")[0];
            String value = entry.trim().split("=")[1];

            map.put(SpecificCategory.fromName(keyName), value);
        });

        return new Knowledge(keywords, contents, map);
    }

    private void updateFeedback(String message, Paint color) {
        controller.lblFeedback.setText(message);
        controller.lblFeedback.setTextFill(color);
    }
}
