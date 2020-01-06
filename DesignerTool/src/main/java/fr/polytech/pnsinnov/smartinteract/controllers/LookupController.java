package fr.polytech.pnsinnov.smartinteract.controllers;

import fr.polytech.pnsinnov.smartinteract.SmartInteractAPI;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Knowledge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

class LookupController {

    private boolean isEditingName = false;
    private boolean isEditingLocation = false;
    private boolean isEditingAge = false;
    private boolean isEditingDifficulty = false;
    private boolean isEditingCatchPhrase = false;

    private Character current;
    private Knowledge editedKnowledge;

    private final SmartInteractAPI api;
    private final MainController ctrl;

    LookupController(MainController controller, SmartInteractAPI api) {
        this.ctrl = controller;
        this.api = api;

        ctrl.ddlCharacters.valueProperty().addListener((__, ___, newValue) -> onSelectedValue(newValue));

        Callback<TreeView<String>, TreeCell<String>> defaultCellFactory = TextFieldTreeCell.forTreeView();

        ctrl.treeViewKnwoledge.setEditable(false);
        ctrl.treeViewKnwoledge.setCellFactory(param -> {
            TreeCell<String> cell = defaultCellFactory.call(param);

            cell.treeItemProperty().addListener((__, ___, ____) -> {
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                    TreeItem<String> item = cell.getTreeItem();
                    boolean doubleClick = event.getClickCount() == 2;

                    if (item == null) return;

                    String rawData = item.getValue();
                    List<String> data = Arrays.asList(item.isLeaf() ? rawData.split("\n") : rawData.split(", "));

                    if (doubleClick) {
                        editedKnowledge = item.isLeaf() ? fromContent(data) : fromKeywords(data);
                        ctrl.txtEditContents.setText(String.join("\n", editedKnowledge.contents()));
                        ctrl.txtEditKeywords.setText(String.join("\n", editedKnowledge.keywords()));

                        ctrl.btnSaveKnowledge.setText("Enregistrer");
                    }
                });
            });

            return cell;
        });


        init();
    }

    private void init() {
        ctrl.refreshCharacters();
        refreshKnowledges();
        ctrl.ddlCharacters.getSelectionModel().selectFirst();
    }

    @FXML
    void onNameToggle() {

        isEditingName = !isEditingName;

        // Display textFields
        if (isEditingName) {
            ctrl.txtEditName.setVisible(true);
            ctrl.lblName.setVisible(false);
            ctrl.iconName.setImage(new Image("save_icon.png"));
        }
        // Save changes and display read-only
        else {
            ctrl.txtEditName.setVisible(false);
            ctrl.lblName.setVisible(true);
            ctrl.iconName.setImage(new Image("edit_icon.png"));

            String name = ctrl.txtEditName.getText().trim();

            current.setPseudo(name);
            api.saveChanges(current);

            ctrl.txtEditName.setText(ctrl.lblName.getText());
            ctrl.ddlCharacters.getSelectionModel().clearSelection();
            ctrl.ddlCharacters.getSelectionModel().select(current.pseudo());
            ctrl.refreshCharacters();
        }
    }

    @FXML
    void onLocationToggle() {
        isEditingLocation = !isEditingLocation;

        // Display textFields
        if (isEditingLocation) {
            ctrl.txtEditLocation.setVisible(true);
            ctrl.lblLocation.setVisible(false);
            ctrl.iconLocation.setImage(new Image("save_icon.png"));
        }
        // Save changes and display read-only
        else {
            ctrl.txtEditLocation.setVisible(false);
            ctrl.lblLocation.setVisible(true);
            ctrl.iconLocation.setImage(new Image("edit_icon.png"));

            String location = ctrl.txtEditLocation.getText().trim();

            ctrl.lblLocation.setText(location);
            current.setLocation(location);
            api.saveChanges(current);
        }
    }

    @FXML
    void onAgeToggle() {

        isEditingAge = !isEditingAge;

        // Display textFields
        if (isEditingAge) {
            ctrl.txtEditAge.setVisible(true);
            ctrl.lblAge.setVisible(false);
            ctrl.iconAge.setImage(new Image("save_icon.png"));
        }
        // Save changes and display read-only
        else {
            ctrl.txtEditAge.setVisible(false);
            ctrl.lblAge.setVisible(true);
            ctrl.iconAge.setImage(new Image("edit_icon.png"));

            int age = parseInt(ctrl.txtEditAge.getText().trim());

            ctrl.lblAge.setText(String.valueOf(age));
            current.setAge(age);
            api.saveChanges(current);
        }
    }

    @FXML
    void onDiffToggle() {

        isEditingDifficulty = !isEditingDifficulty;

        // Display textFields
        if (isEditingDifficulty) {
            ctrl.txtEditDifficulty.setVisible(true);
            ctrl.lblDifficulty.setVisible(false);
            ctrl.iconDifficulty.setImage(new Image("save_icon.png"));
        }
        // Save changes and display read-only
        else {
            ctrl.txtEditDifficulty.setVisible(false);
            ctrl.lblDifficulty.setVisible(true);
            ctrl.iconDifficulty.setImage(new Image("edit_icon.png"));

            double newDiff = parseDouble(ctrl.txtEditDifficulty.getText().trim());

            ctrl.lblDifficulty.setText(String.valueOf(newDiff));
            current.setDifficulty(newDiff);
            api.saveChanges(current);
        }
    }

    @FXML
    void onCatchPhraseToggle() {

        isEditingCatchPhrase = !isEditingCatchPhrase;

        // Display textFields
        if (isEditingCatchPhrase) {
            ctrl.txtEditCatchPhrase.setVisible(true);
            ctrl.lblCatchPhrase.setVisible(false);
            ctrl.iconCatchPhrase.setImage(new Image("save_icon.png"));
        }
        // Save changes and display read-only
        else {
            ctrl.txtEditCatchPhrase.setVisible(false);
            ctrl.lblCatchPhrase.setVisible(true);
            ctrl.iconCatchPhrase.setImage(new Image("edit_icon.png"));

            String catchPhrase = ctrl.txtEditCatchPhrase.getText().trim();

            ctrl.lblCatchPhrase.setText(catchPhrase);
            current.setCatchPhrase(catchPhrase);
            api.saveChanges(current);
        }
    }

    @FXML
    void onSaveKnowledge() {

        // --- Collect data ---
        List<String> keywords = Arrays.asList(ctrl.txtEditKeywords.getText().trim().split("\n"));
        List<String> contents = Arrays.asList(ctrl.txtEditContents.getText().trim().split("\n"));

        // --- Process data ---
        if (editedKnowledge == null)
            editedKnowledge = new Knowledge(keywords, contents);

        editedKnowledge.setContents(keywords);
        editedKnowledge.setKeywords(contents);

        current.updateKnowledge(editedKnowledge);

        api.saveChanges(current);

        // --- Preparing for next round ---

        ctrl.txtEditContents.setText("");
        ctrl.txtEditKeywords.setText("");
        ctrl.btnSaveKnowledge.setText("Ajouter");
        editedKnowledge = null;

        refreshKnowledges();
    }

    @FXML
    void onDelete() {
        if (current == null) return;

        api.deleteCharacter(current);
        init();
    }

    private void onSelectedValue(String newValue) {

        if (newValue == null) {
            ctrl.btnDelete.setVisible(false);

            current = null;

            ctrl.lblName.setText("");
            ctrl.lblAge.setText("");
            ctrl.lblDifficulty.setText("");
            ctrl.lblCatchPhrase.setText("");
            ctrl.lblLocation.setText("");

            ctrl.txtEditName.setText("");
            ctrl.txtEditAge.setText("");
            ctrl.txtEditDifficulty.setText("");
            ctrl.txtEditCatchPhrase.setText("");
            ctrl.txtEditLocation.setText("");

            ctrl.txtEditContents.setText("");
            ctrl.txtEditKeywords.setText("");

            refreshKnowledges();
        } else {
            ctrl.btnDelete.setVisible(true);
            current = api.getCharacter(newValue);

            ctrl.lblName.setText(current.pseudo());
            ctrl.lblAge.setText(String.valueOf(current.age()));
            ctrl.lblDifficulty.setText(String.valueOf(current.difficulty()));
            ctrl.lblCatchPhrase.setText(current.catchPhrase());
            ctrl.lblLocation.setText(current.location());

            ctrl.txtEditName.setText(current.pseudo());
            ctrl.txtEditAge.setText(String.valueOf(current.age()));
            ctrl.txtEditDifficulty.setText(String.valueOf(current.difficulty()));
            ctrl.txtEditCatchPhrase.setText(current.catchPhrase());
            ctrl.txtEditLocation.setText(current.location());

            ctrl.txtEditContents.setText("");
            ctrl.txtEditKeywords.setText("");

            refreshKnowledges();
        }
    }

    private void refreshKnowledges() {
        Platform.runLater(() -> {
            TreeItem<String> root = new TreeItem<>("Knowledges");
            root.setExpanded(true);

            if (current != null) {
                for (Knowledge knowledge : current.knowledges()) {

                    List<String> keywords = knowledge.keywords();
                    List<String> contents = knowledge.contents();

                    TreeItem<String> key = new TreeItem<>(String.join(", ", keywords));

                    List<TreeItem<String>> children = new ArrayList<>();

                    children.add(new TreeItem<>(String.join("\n", contents)));

                    knowledge.specificKnowledges().forEach((category, value) -> {
                        if (!value.isEmpty())
                            children.add(new TreeItem<>(category.name() + " - " + value));
                    });

                    root.getChildren().add(key);
                    key.getChildren().addAll(children);
                    key.setExpanded(true);
                }
            }

            ctrl.treeViewKnwoledge.setRoot(root);
            ctrl.treeViewKnwoledge.setShowRoot(false);
            ctrl.treeViewKnwoledge.refresh();
        });
    }

    private Knowledge fromContent(List<String> contents) {
        return current.knowledges().stream()
                .filter(knowledge -> knowledge.hasContent(contents))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    private Knowledge fromKeywords(List<String> keywords) {
        return current.knowledges().stream()
                .filter(knowledge -> knowledge.hasKeywords(keywords))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
