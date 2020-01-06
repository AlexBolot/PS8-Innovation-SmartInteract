package fr.polytech.pnsinnov.smartinteract.controllers;

import fr.polytech.pnsinnov.smartinteract.SmartInteractAPI;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class MainController {

    public TextField txtPseudo;
    public TextField txtLocation;
    public TextField txtAge;
    public TextField txtDifficulty;
    public TextField txtCatchPhrase;
    public TextArea txtareaKnowledge;
    public Button btnSave;
    public Label lblFeedback;

    public ChoiceBox<String> ddlCharacters;
    public ImageView iconName;
    public ImageView iconLocation;
    public ImageView iconAge;
    public ImageView iconDifficulty;
    public ImageView iconCatchPhrase;
    public TextArea txtEditKeywords;
    public TextArea txtEditContents;
    public Button btnSaveKnowledge;
    public TextField txtEditName;
    public TextField txtEditLocation;
    public TextField txtEditAge;
    public TextField txtEditDifficulty;
    public TextField txtEditCatchPhrase;
    public Label lblName;
    public Label lblLocation;
    public Label lblAge;
    public Label lblDifficulty;
    public Label lblCatchPhrase;
    public TreeView<String> treeViewKnwoledge;
    public Button btnDelete;

    public TextField txtInput;
    public Button btnSend;
    public ListView<String> consoleListView;

    private CreateController createController;
    private LookupController lookupController;
    private CLIController cliController;
    private SmartInteractAPI smartInteractAPI;

    @FXML
    public void initialize() {
        this.initialize(new SmartInteractAPI());
    }

    public void initialize(SmartInteractAPI api) {
        smartInteractAPI = api;
        createController = new CreateController(this, api);
        lookupController = new LookupController(this, api);
        cliController = new CLIController(this, api);
    }

    CreateController getCreateController() {
        return createController;
    }

    LookupController getLookupController() {
        return lookupController;
    }

    CLIController getCliController() {
        return cliController;
    }

    // --------------- Create Tab --------------- //

    @FXML
    public void onSave() {
        createController.onSave();
    }

    // ---------------- Lookup Tab ---------------- //

    @FXML
    public void onNameToggle() {
        lookupController.onNameToggle();
    }

    @FXML
    public void onLocationToggle() {
        lookupController.onLocationToggle();
    }

    @FXML
    public void onAgeToggle() {
        lookupController.onAgeToggle();
    }

    @FXML
    public void onDifficultyToggle() {
        lookupController.onDiffToggle();
    }

    @FXML
    public void onCatchPhraseToggle() {
        lookupController.onCatchPhraseToggle();
    }

    @FXML
    public void onSaveKnowledge() {
        lookupController.onSaveKnowledge();
    }

    @FXML
    public void onDelete() {
        lookupController.onDelete();
    }

    // ---------------- Console Tab ---------------- //

    @FXML
    public void onConsoleTyped(KeyEvent keyEvent) {
        cliController.onConsoleTyped(keyEvent);
    }

    @FXML
    public void onSend() {
        cliController.onSend();
    }

    // ---------------- Utils ---------------- //

    void refreshCharacters() {
        List<String> characterNames = smartInteractAPI.getCharacters().stream()
                .map(Character::pseudo)
                .sorted(String::compareTo)
                .collect(Collectors.toList());

        ObservableList<String> items = observableArrayList(characterNames);
        ddlCharacters.setItems(items);

        if (ddlCharacters.getSelectionModel().isEmpty())
            ddlCharacters.getSelectionModel().selectFirst();
    }

}
