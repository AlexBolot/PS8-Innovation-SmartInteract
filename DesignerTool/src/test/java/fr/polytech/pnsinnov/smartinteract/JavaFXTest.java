package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.controllers.MainController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public abstract class JavaFXTest {

    protected MainController ctrl;

    public void initJavaFX(SmartInteractAPI api) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        latch.await();

        // ---------------------------------------- //

        ctrl = new MainController();

        // Setting up MainController's fields to avoid NullPointerExceptions
        ctrl.txtPseudo = new TextField();
        ctrl.txtDifficulty = new TextField();
        ctrl.btnSave = new Button();
        ctrl.txtareaKnowledge = new TextArea();
        ctrl.lblFeedback = new Label();
        // ----------------------------------------
        ctrl.btnSend = new Button();
        ctrl.txtInput = new TextField();
        ctrl.consoleListView = new ListView<>();
        // ----------------------------------------
        ctrl.ddlCharacters = new ChoiceBox<>();
        ctrl.txtEditName = new TextField();
        ctrl.lblName = new Label();
        ctrl.txtEditDifficulty = new TextField();
        ctrl.lblDifficulty = new Label();
        ctrl.iconName = new ImageView();
        ctrl.iconDifficulty = new ImageView();
        ctrl.treeViewKnwoledge = new TreeView<>();
        // ----------------------------------------

        ctrl.initialize(api);
    }
}
