package frontend.menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
            FileInputStream start = new FileInputStream("src/main/java/frontend/resources/start.png");
            Image startIcon = new Image(start, 30, 30, false, false);
            ImageView iv1 = new ImageView(startIcon);
            Button btnStart = new Button("Start", iv1);
            btnStart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("not implimented yet ");
                }
            });


            FileInputStream help = new FileInputStream("src/main/java/frontend/resources/help.png");
            Image helpIcon = new Image(help, 30, 30, false, false);
            ImageView iv2 = new ImageView(helpIcon);
            Button btnHelp = new Button("Help", iv2);
            btnHelp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //WebView browser = new WebView();
                    //WebEngine webEngine = browser.getEngine();
                    //URL url = getClass().getResource("src/main/java/frontend/resources/website/userguide.html");

                    //getHostServices().showDocument(url.toString());
                }
            });

            FileInputStream exit = new FileInputStream("src/main/java/frontend/resources/exit.png");
            Image exitIcon = new Image(exit, 30, 30, false, false);
            ImageView iv3 = new ImageView(exitIcon);
            Button btnExit = new Button("Exit", iv3);
            btnExit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

            FileInputStream settings = new FileInputStream("src/main/java/frontend/resources/settings.png");
            Image settingsIcon = new Image(settings, 30, 30, false, false);
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

            //New Game Buttons
            CheckBox cbNewGame = new CheckBox();
            CheckBox cbOldGame = new CheckBox();
            HBox createGame = new HBox();
            createGame.getChildren().addAll(cbNewGame, cbOldGame);
            createGame.setSpacing(70);
            createGame.setScaleX(1.2);
            createGame.setScaleY(1.2);
            createGame.setPadding(new Insets(0,0,0,27));

            //Game type
            CheckBox cbClassic = new CheckBox();
            CheckBox cbAbridged = new CheckBox();
            HBox gameType = new HBox();
            gameType.getChildren().addAll(cbAbridged, cbClassic);
            gameType.setSpacing(90);
            gameType.setScaleX(1.2);
            gameType.setScaleY(1.2);
            gameType.setPadding(new Insets(7,0,0,33));

            //Round time buttons
            TextField timeLimit = new TextField("");
            HBox roundTime = new HBox();
            roundTime.getChildren().add(timeLimit);
            roundTime.setPadding(new Insets(7,0,0,-15));

            //Number of players buttons
            TextField noOfPlayers = new TextField();
            HBox players = new HBox();
            players.getChildren().add(noOfPlayers);
            players.setSpacing(100);
            players.setPadding(new Insets(7,0,0,-15));

            //Add CPU players
            CheckBox cbAddCpu = new CheckBox();
            CheckBox cbNoCpu = new CheckBox();
            HBox CPU = new HBox();
            CPU.getChildren().addAll(cbAddCpu, cbNoCpu);
            CPU.setSpacing(70);
            CPU.setPadding(new Insets(7,0,0,25));
            CPU.setScaleX(1.2);
            CPU.setScaleY(1.2);

            //Creating Selection For Player Tokens
            ComboBox cmbPawn = new ComboBox();
            Label cbBoot = new Label("Boot");
            Label cbSmartphone = new Label("Smartphone");
            Label cbGoblet = new Label("Goblet");
            Label cbHatstand = new Label("Hatstand");
            Label cbCat = new Label("Cat");
            Label cbSpoon = new Label("Spoon");
            cmbPawn.getItems().addAll(cbBoot, cbSmartphone, cbGoblet, cbHatstand, cbCat, cbSpoon);
            HBox tokenSelection = new HBox();
            tokenSelection.getChildren().add(cmbPawn);
            tokenSelection.setSpacing(100);


            //Setting Button Menu
            HBox startButtons = new HBox(btnStart, btnSettings, btnHelp, btnExit);
            startButtons.setSpacing(30);
            startButtons.setAlignment(Pos.CENTER);
            startButtons.setPadding(new Insets(0, 0, 130, 30));

            // Creating the background
            FileInputStream bc = new FileInputStream("src/main/java/frontend/resources/website/empty_menu.jpg");
            Image image = new Image(bc, 1280, 720, true, true);
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);
            Background background = new Background(backgroundimage);

            //Setting up the scene
            TilePane main = new TilePane();
            main.setHgap(1000);
            main.setVgap(20);
            main.setPadding(new Insets(243,0,0,600));
            main.getChildren().add(createGame);
            main.getChildren().add(gameType);
            main.getChildren().add(roundTime);
            main.getChildren().add(players);
            main.getChildren().add(tokenSelection);
            main.getChildren().add(CPU);



            BorderPane root = new BorderPane();
            root.setBackground(background);

            root.setCenter(main);
            root.setBottom(startButtons);
            BorderPane.setMargin(startButtons, new Insets(0));

            // create a scene
            Scene scene = new Scene(root, 1280, 720);
            // set the scene
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // Main Method@
    public static void main(String args[])
    {
        // launch the application
        launch(args);
    }
}
