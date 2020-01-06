package fr.polytech.pnsinnov.smartinteract;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.omg.CORBA.ORB;

public class Main extends Application {

     public static ORB broker;

    /**
     * First you need to start ORBD using :
     * Windows : start orbd -ORBInitialPort 1050 -ORBInitialHost localhost
     * MacOs   : orbd -ORBInitialPort 1050 -ORBInitialHost localhost
     *
     * @param args -ORBInitialPort 1050 -ORBInitialHost localhost
     */
    public static void main(String[] args) {

        broker = ORB.init(args, null);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //noinspection ConstantConditions
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("DesignerToolGUI.fxml"));
            primaryStage.setTitle("Designer Tool");
            primaryStage.setScene(new Scene(root));
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
}
