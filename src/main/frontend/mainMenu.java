package main.frontend;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.web.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import java.io.*;
import javafx.geometry.*;
import javafx.scene.Group;

public class mainMenu extends Application {

    // launch the application
    public void start(Stage stage)
    {
        try {
            //Stage management
            stage.setTitle("Property Tycoon - Menu");

            //Creating menu buttons
            Button btnStart = new Button("Start");
            Button btnHelp = new Button("Help");
            Button btnExit = new Button("Exit");
            Button btnSettings = new Button("Settings");

            //Setting Button Menu
            HBox hbox = new HBox(btnStart, btnSettings, btnHelp, btnExit);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);

            // create a scene
            Scene scene = new Scene(hbox, 650, 800);

            // Creating the background
            FileInputStream input = new FileInputStream("C:\\Users\\Harry\\OneDrive - University of Sussex" +
                    "\\Computer Science\\Year 2\\PropertyTycoon\\src\\main\\frontend\\bc.jpg");
            Image image = new Image(input);
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background background = new Background(backgroundimage);
            hbox.setBackground(background);

            // set the scene
            stage.setScene(scene);
            stage.show();
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
