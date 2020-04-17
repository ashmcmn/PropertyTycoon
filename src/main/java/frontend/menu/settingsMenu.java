package frontend.menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class settingsMenu extends Application {
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();

        //Settings buttons
        Slider volumeControl = new Slider(0, 100, 100);
        Label volume = new Label("Volume");

        Button returnMenu = new Button("Return");
        returnMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage primaryStage = new Stage();
                mainMenu main = new mainMenu();
                main.start(primaryStage);
                stage.hide();
                primaryStage.show();
            }
        });

        root.getChildren().addAll(volume, volumeControl, returnMenu);

        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.show();
    }
}
