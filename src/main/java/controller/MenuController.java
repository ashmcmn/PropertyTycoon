package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.menu.PrePlayer;
import model.players.Token;
import view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuController {

    AnchorPane view;
    List<PrePlayer> players;
    Stage primaryStage;

    public MenuController(Stage primaryStage) {
        players = new ArrayList<>();

        players.add(new PrePlayer(Token.BOOT, "P1", false));
        players.add(new PrePlayer(Token.CAT, "P2", false));

        this.primaryStage = primaryStage;
    }

    public void setView(AnchorPane view) {
        this.view = view;
    }

    public void updatePlayerList() {
        HBox playerList = (HBox) view.lookup("#playerList");

        playerList.getChildren().clear();

        int count = 0;
        for (PrePlayer player : players
             ) {
            VBox vBox = new VBox();

            vBox.setPrefWidth(200);
            vBox.setPrefHeight(120);
            vBox.setMinWidth(200);
            vBox.setMinHeight(120);

            TextField name = new TextField();
            name.textProperty().addListener((a, oldName, newName) -> {
                player.setName(newName);
            });
            name.setText(player.getName());

            ChoiceBox tokenSelect = new ChoiceBox();

            List<Token> availableTokens = Stream.of(Token.values()).filter(t -> players.stream().filter(p -> p.getToken() == t).count() == 0).collect(Collectors.toList());
            availableTokens.add(player.getToken());

            tokenSelect.getItems().setAll(availableTokens);
            tokenSelect.setValue(player.getToken());
            tokenSelect.valueProperty().addListener((a, oldToken, newToken) -> {
                player.setToken((Token) newToken);
                updatePlayerList();
            });

            CheckBox ai = new CheckBox();
            ai.setText("AI");
            ai.setSelected(player.getAi());
            ai.selectedProperty().addListener((a, oldVal, newVal) -> {
                player.setAi(newVal);
            });

            vBox.getChildren().addAll(name, tokenSelect, ai);

            if(count > 1) {
                Button remove = new Button();
                remove.setText("Remove");
                remove.setOnAction(actionEvent -> {
                    players.remove(player);
                    updatePlayerList();
                });
                vBox.getChildren().add(remove);
            }

            playerList.getChildren().add(vBox);
            count++;
        }

        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPrefWidth(200);
        pane.setPrefHeight(120);
        pane.setMinWidth(200);
        pane.setMinHeight(120);

        Button button = new Button("Add Player");
        button.setOnAction(this::handleAddPlayer);

        pane.getChildren().add(button);
        playerList.getChildren().add(pane);
        ((AnchorPane) playerList.getParent()).setPrefWidth((players.size() + 1) * 200);
    }

    public void handleAbridgedCheck(ActionEvent actionEvent) {
        ((CheckBox) view.lookup("#classicCheck")).setSelected(false);
        view.lookup("#timeLimitPane").setVisible(true);
    }

    public void handleClassicCheck(ActionEvent actionEvent) {
        ((CheckBox) view.lookup("#abridgedCheck")).setSelected(false);
        view.lookup("#timeLimitPane").setVisible(false);
    }

    public void handleAddPlayer(ActionEvent actionEvent) {
        players.add(new PrePlayer("New", false));

        updatePlayerList();
    }

    public void handleStartGame() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        MainController controller = new MainController(primaryStage, players);
        GameView view = new GameView(controller, screenBounds.getWidth() * 0.8, screenBounds.getHeight() * 0.8);
        controller.setView(view);

        Scene scene = new Scene(view, screenBounds.getWidth() * 0.8, screenBounds.getHeight() * 0.8);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);

        if(controller.getGameManager().getCurrentPlayer().isAI()){
            controller.handleAITurn();
        }

        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
