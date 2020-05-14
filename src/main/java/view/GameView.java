package view;

import com.sun.webkit.network.Util;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.board.Group;
import model.board.PropertySquare;
import model.board.Square;
import model.players.Player;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utilities.TextAreaAppender;
import utilities.Utilities;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The type Game view.
 */
public class GameView extends StackPane {
    private GameBoardGenerator gameBoardGenerator;
    private GridPane gameBoard;
    private MainController controller;
    private Map<String,Button> userControls;
    private GridPane userPane;

    /**
     * Instantiates a new Game view.
     *
     * @param controller the controller
     * @param width      the width to take
     * @param height     the height to take
     */
    public GameView(MainController controller, double width, double height) {
        this.controller = controller;

        GridPane verticalSplitter = new GridPane();

        // ensure the board view and user controls fit and display correctly
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(height+15);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(width-height-15);

        verticalSplitter.getColumnConstraints().addAll(c1, c2);
        StackPane.setAlignment(verticalSplitter, Pos.CENTER);
        verticalSplitter.setAlignment(Pos.CENTER);

        this.getChildren().add(verticalSplitter);

        StackPane gameBoardPane = new StackPane();
        GridPane otherPane = generateUserPane(width-height-15, height);

        GridPane.setRowIndex(gameBoardPane, 0);
        GridPane.setColumnIndex(gameBoardPane, 0);

        GridPane.setRowIndex(otherPane, 0);
        GridPane.setColumnIndex(otherPane, 1);

        verticalSplitter.getChildren().addAll(gameBoardPane, otherPane);

        // style the pane where the game board will be
        gameBoardPane.setStyle("-fx-background-color: rgb(221, 233, 237);");
        gameBoardPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        StackPane.setAlignment(gameBoardPane, Pos.CENTER);
        gameBoardPane.setAlignment(Pos.CENTER);

        gameBoardGenerator = new GameBoardGenerator(this, width, height);
        gameBoard = gameBoardGenerator.createBoard();

        gameBoardPane.getChildren().add(gameBoard);

        placePlayers(controller.getGameManager().getPlayers(),gameBoard);
    }

    /**
     * Generates the pane where the user can see more info about game and interact
     *
     * @param width  the width of the user pane
     * @param height the height of the user pane
     * @return the grid pane
     */
    public GridPane generateUserPane(double width, double height) {
        GridPane userPane = new GridPane();
        userPane.setVgap(10);
        userPane.setStyle("-fx-background-color: white;");
        userPane.setPadding(new Insets(20, 10, 20, 10));

        // create the pane to display assets of current player
        GridPane assets = generateUserAssets(width, height/3);
        GridPane.setRowIndex(assets, 0);
        GridPane.setColumnIndex(assets, 0);
        assets.setPrefHeight(height / 3);
        assets.setPrefWidth(width);

        // create the pane that allows the user to interact with the game
        StackPane userControls = generateGameControls(width, height/3);
        GridPane.setRowIndex(userControls, 1);
        GridPane.setColumnIndex(assets, 0);
        userControls.setPrefHeight(height / 3);
        userControls.setPrefWidth(width);

        // create the pane that display a log of the game
        StackPane gameLog = new StackPane();
        GridPane.setRowIndex(gameLog, 2);
        GridPane.setColumnIndex(assets, 0);
        gameLog.setPrefHeight(height / 3);
        gameLog.setPrefWidth(width);

        TextArea gameLogText = new TextArea();
        gameLogText.setWrapText(true);
        gameLogText.setEditable(false);
        TextAreaAppender.setTextArea(gameLogText);
        gameLog.getChildren().add(gameLogText);

        userPane.getChildren().addAll(assets, userControls, gameLog);

        this.userPane = userPane;
        return userPane;
    }

    /**
     * Generate user assets grid pane.
     *
     * @param width  the width
     * @param height the height
     * @return the grid pane
     */
    public GridPane generateUserAssets(double width, double height) {
        Player player = controller.getGameManager().getCurrentPlayer();

        GridPane pane = new GridPane();
        pane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 18 18 18 18;");

        pane.setPrefHeight(height);
        pane.setPrefWidth(width);

        StackPane namePane = new StackPane();
        namePane.setStyle(
                "-fx-background-color: rgb(221, 233, 237); " +
                        " -fx-background-radius: 18 18 0 0; -fx-border-width: 0 0 2 0; -fx-border-color: black");
        namePane.setPrefWidth(width);
        namePane.setPrefHeight(height/6);
        GridPane.setColumnIndex(namePane, 0);
        GridPane.setRowIndex(namePane, 0);

        Text text = new Text();
        text.setText(player.getName());
        namePane.getChildren().add(text);

        StackPane assetsPane = new StackPane();
        assetsPane.setPadding(new Insets(20, 20, 20, 20));
        GridPane.setColumnIndex(assetsPane, 0);
        GridPane.setRowIndex(assetsPane, 1);
        assetsPane.setPrefWidth(width);
        assetsPane.setPrefHeight((height/6)*5);

        GridPane propertyCards = new GridPane();
        assetsPane.getChildren().add(propertyCards);
        propertyCards.setHgap(5);
        propertyCards.setVgap(5);

        for(int x = 0; x <  Group.values().length; x++){
            int finalX = x;
            List<PropertySquare> properties = Stream.of(controller.getGameManager().getBoard().getSquares())
                    .filter(PropertySquare.class::isInstance)
                    .map(PropertySquare.class::cast)
                    .sorted(Comparator.comparingInt(Square::getPosition))
                    .filter(p -> p.getGroup() == Group.values()[finalX])
                    .collect(Collectors.toList());

            for(int y = 0; y < 4; y ++){
                StackPane card = new StackPane();
                
                if(y < properties.size()) {
                    PropertySquare property = properties.get(y);

                    card.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                    Rectangle cardOpaque = new Rectangle();
                    cardOpaque.setWidth(height / 14);
                    cardOpaque.setHeight(height / 8);
                    cardOpaque.setFill(Color.rgb(255, 255, 255, 0.8));

                    card.setPrefWidth(height / 14);
                    card.setPrefHeight(height / 8);

                    Rectangle cardColor = new Rectangle();
                    card.getChildren().add(cardColor);
                    cardColor.setWidth(height / 14);
                    cardColor.setHeight(height / 28);
                    StackPane.setAlignment(cardColor, Pos.TOP_CENTER);

                    switch (property.getGroup()) {
                        case BLUE:
                            cardColor.setFill(Color.rgb(147, 161, 206));
                            break;
                        case GREEN:
                            cardColor.setFill(Color.rgb(96, 122, 34));
                            break;
                        case RED:
                            cardColor.setFill(Color.rgb(196, 50, 39));
                            break;
                        case BROWN:
                            cardColor.setFill(Color.rgb(130, 102, 89));
                            break;
                        case ORANGE:
                            cardColor.setFill(Color.rgb(215, 106, 44));
                            break;
                        case PURPLE:
                            cardColor.setFill(Color.rgb(195, 143, 182));
                            break;
                        case YELLOW:
                            cardColor.setFill(Color.rgb(227, 185, 95));
                            break;
                        case DEEPBLUE:
                            cardColor.setFill(Color.rgb(33, 86, 124));
                            break;
                    }

                    if(property.getOwner() != player){
                        card.getChildren().add(cardOpaque);
                    }
                }

                GridPane.setColumnIndex(card, x);
                GridPane.setRowIndex(card, y);

                propertyCards.getChildren().add(card);
            }
        }

        Text cash = new Text("£" + player.getCash());
        cash.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        assetsPane.getChildren().add(cash);
        StackPane.setAlignment(cash, Pos.CENTER_RIGHT);

        pane.getChildren().addAll(namePane, assetsPane);

        return pane;
    }

    /**
     * Generate user assets grid pane.
     *
     * @return the grid pane
     */
    public GridPane generateUserAssets(){
        return generateUserAssets(userPane.getWidth(), userPane.getHeight()/3);
    }

    /**
     * Update assets.
     */
    public void updateAssets() {
        userPane.getChildren().remove(0);
        userPane.getChildren().add(0, generateUserAssets());
    }

    /**
     * Generate the game controls where the user can interact with the game.
     *
     * @param width  the width
     * @param height the height
     * @return the stack pane
     */
    public StackPane generateGameControls(double width, double height) {
        StackPane controls = new StackPane();

        // creating and sizing the grid so buttons display nicely
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(width/2 - 6);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(width/2 - 6);
        grid.getColumnConstraints().addAll(c1,c2);

        RowConstraints r1 = new RowConstraints();
        r1.setPrefHeight(height/3 - 4);
        RowConstraints r2 = new RowConstraints();
        r2.setPrefHeight(height/3 - 4);
        RowConstraints r3 = new RowConstraints();
        r3.setPrefHeight(height/3 - 4);
        grid.getRowConstraints().addAll(r1, r2, r3);

        controls.getChildren().add(grid);

        userControls = new HashMap<>();

        // create the button for rolling the dice
        Button diceRollButton = new Button("Roll Dice");
        diceRollButton.setOnAction(actionEvent -> controller.rollDiceHandler());
        GridPane.setRowIndex(diceRollButton, 0);
        GridPane.setColumnIndex(diceRollButton, 0);
        diceRollButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        userControls.put("RollDice", diceRollButton);

        Button auctionButton = new Button("Auction Property");
        auctionButton.setOnAction(actionEvent -> controller.auctionHandler());
        GridPane.setRowIndex(auctionButton, 0);
        GridPane.setColumnIndex(auctionButton, 1);
        auctionButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        auctionButton.setDisable(true);
        userControls.put("AuctionProperty", auctionButton);

        Button managePropertyButton = new Button("Manage Properties");
        managePropertyButton.setOnAction(actionEvent -> controller.managePropertiesHandler());
        GridPane.setRowIndex(managePropertyButton, 1);
        GridPane.setColumnIndex(managePropertyButton, 0);
        managePropertyButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        managePropertyButton.setDisable(true);
        userControls.put("ManageProperties", managePropertyButton);

        Button buyPropertyButton = new Button("Purchase Property");
        buyPropertyButton.setOnAction(actionEvent -> controller.buyPropertyHandler());
        GridPane.setRowIndex(buyPropertyButton, 1);
        GridPane.setColumnIndex(buyPropertyButton, 1);
        buyPropertyButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buyPropertyButton.setDisable(true);
        userControls.put("PurchaseProperty", buyPropertyButton);

        Button endTurnButton = new Button("End Turn");
        endTurnButton.setOnAction(actionEvent -> controller.endTurnHandler());
        GridPane.setRowIndex(endTurnButton, 2);
        GridPane.setColumnIndex(endTurnButton, 0);
        endTurnButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        endTurnButton.setDisable(true);
        userControls.put("EndTurn", endTurnButton);

        Button bankruptButton = new Button("File Bankruptcy");
        bankruptButton.setOnAction(actionEvent -> controller.fileBankruptcyHandler());
        GridPane.setRowIndex(bankruptButton, 2);
        GridPane.setColumnIndex(bankruptButton, 1);
        bankruptButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        userControls.put("FileBankruptcy", bankruptButton);

        grid.getChildren().addAll(diceRollButton, auctionButton, managePropertyButton, buyPropertyButton, endTurnButton, bankruptButton);
        return controls;
    }

    // place the players on the intial board position
    private void placePlayers(List<Player> players, GridPane gameBoard) {
        for(Player player : players){
            Image token = new Image(player.getToken().getPath());

            ImageView imageView = new ImageView();
            imageView.setImage(token);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            StackPane.setAlignment(imageView, Pos.CENTER);
            player.setBoardPiece(imageView);

            int[] coord = Utilities.indexToCoord(0);

            StackPane pane = (StackPane) Utilities.getGridCell(gameBoard, coord[0], coord[1]);

            pane.getChildren().add(imageView);
        }

    }

    /**
     * Gets controller.
     *
     * @return the controller
     */
    public MainController getController() {
        return controller;
    }

    /**
     * Gets game board.
     *
     * @return the game board
     */
    public GridPane getGameBoard() {
        return gameBoard;
    }

    /**
     * Gets user controls.
     *
     * @return the user controls
     */
    public Map<String, Button> getUserControls() {
        return userControls;
    }
}
