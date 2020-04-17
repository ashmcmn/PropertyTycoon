package frontend.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.*;

import java.io.*;
import javafx.geometry.*;
import javafx.scene.Group;

public class mainMenu extends Application {

    // launch the application
    public void start(Stage primaryStage)
    {
        try {
            //Stage management
            primaryStage.setTitle("Property Tycoon - Start Menu");

            //Creating menu buttons and assigning icons
            FileInputStream start = new FileInputStream("src/main/frontend/resources/start.png");
            Image startIcon = new Image(start, 50, 50, false, false);
            ImageView iv1 = new ImageView(startIcon);
            Button btnStart = new Button("Start", iv1);


            FileInputStream help = new FileInputStream("src/main/frontend/resources/help.png");
            Image helpIcon = new Image(help, 50, 50, false, false);
            ImageView iv2 = new ImageView(helpIcon);
            Button btnHelp = new Button("Help", iv2);
            btnHelp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            FileInputStream exit = new FileInputStream("src/main/frontend/resources/exit.png");
            Image exitIcon = new Image(exit, 50, 50, false, false);
            ImageView iv3 = new ImageView(exitIcon);
            Button btnExit = new Button("Exit", iv3);
            btnExit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

            FileInputStream settings = new FileInputStream("src/main/frontend/resources/settings.png");
            Image settingsIcon = new Image(settings, 50, 50, false, false);
            ImageView iv4 = new ImageView(settingsIcon);
            Button btnSettings = new Button("Settings", iv4);
            btnSettings.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage settingsStage = new Stage();
                    settingsMenu settings = new settingsMenu();
                    try {
                        primaryStage.hide();
                        settings.start(settingsStage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //Setting Button Menu
            HBox startButtons = new HBox(btnStart, btnSettings, btnHelp, btnExit);
            startButtons.setSpacing(30);
            startButtons.setAlignment(Pos.CENTER);

            //Creating player selction menu
            Label labBoot = new Label("BOOT");
            Label labSmartphone = new Label("SMARTPHONE");
            Label labGoblet = new Label("GOBLET");
            Label labHatstand = new Label("HATSTAND");
            Label labCat = new Label("CAT");
            Label labSpoon = new Label("SPOON");

            CheckBox cbBoot = new CheckBox();
            CheckBox cbSmartphone = new CheckBox();
            CheckBox cbGoblet = new CheckBox();
            CheckBox cbHatstand = new CheckBox();
            CheckBox cbCat = new CheckBox();
            CheckBox cbSpoon = new CheckBox();

            //Setting the player selection menu
            HBox tokLabel = new HBox();
            tokLabel.getChildren().addAll(labBoot, labSmartphone, labGoblet, labHatstand, labCat, labSpoon);
            HBox tokCheckbox = new HBox();
            tokCheckbox.getChildren().addAll(cbBoot, cbSmartphone, cbGoblet, cbHatstand, cbCat, cbSpoon);

            



            // Creating the background
            FileInputStream bc = new FileInputStream("src/main/frontend/resources/bc.jpg");
            Image image = new Image(bc);
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background background = new Background(backgroundimage);
            startButtons.setBackground(background);

            BorderPane root = new BorderPane();
            root.setBottom(startButtons);
            Group tokenLabel = new Group(tokLabel);
            Group tokenCb = new Group(tokCheckbox);
            root.setCenter(tokenLabel);

            // create a scene
            Scene scene = new Scene(root, 652, 800);
            // set the scene
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // Main Method
    public static void main(String args[])
    {
        // launch the application
        launch(args);
    }
}
