package frontend.view;

import backend.board.Square;
import backend.players.Player;
import frontend.controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Game view.
 */
public class GameView extends StackPane {
    private GameBoardGenerator gameBoardGenerator;
    private GridPane gameBoard;
    private MainController controller;
    private Map<Player,Node> playerIdentifiers;

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

        playerIdentifiers = placePlayers(controller.getGameManager().getPlayers(),gameBoard);
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
        StackPane assets = new StackPane();
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

        userPane.getChildren().addAll(assets, userControls, gameLog);

        return userPane;
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

        // create the button for rolling the dice
        Button diceRollButton = new Button("Roll Dice");
        diceRollButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Player player = controller.getGameManager().getCurrentPlayer();
                Node identifier = playerIdentifiers.get(player);

                int[] coord = gameBoardGenerator.indexToCoord(player.getPosition());
                StackPane oldPane = (StackPane) getGridCell(gameBoard, coord[0], coord[1]);
                oldPane.getChildren().remove(identifier);

                controller.getGameManager().getDice().roll();
                int[] result = controller.getGameManager().getDice().getResult();
                Square square = controller.getGameManager().getCurrentPlayer().move(IntStream.of(result).sum(), true);

                coord = gameBoardGenerator.indexToCoord(player.getPosition());
                StackPane newPane = (StackPane) getGridCell(gameBoard, coord[0], coord[1]);
                newPane.getChildren().add(identifier);

            }
        });
        GridPane.setRowIndex(diceRollButton, 0);
        GridPane.setColumnIndex(diceRollButton, 0);
        diceRollButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button auctionButton = new Button("Goto Auction");
        GridPane.setRowIndex(auctionButton, 0);
        GridPane.setColumnIndex(auctionButton, 1);
        auctionButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button managePropertyButton = new Button("Manage Properties");
        GridPane.setRowIndex(managePropertyButton, 1);
        GridPane.setColumnIndex(managePropertyButton, 0);
        managePropertyButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button buyPropertyButton = new Button("Purchase Property");
        GridPane.setRowIndex(buyPropertyButton, 1);
        GridPane.setColumnIndex(buyPropertyButton, 1);
        buyPropertyButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button endTurnButton = new Button("End Turn");
        GridPane.setRowIndex(endTurnButton, 2);
        GridPane.setColumnIndex(endTurnButton, 0);
        endTurnButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button menuButton = new Button("Menu");
        GridPane.setRowIndex(menuButton, 2);
        GridPane.setColumnIndex(menuButton, 1);
        menuButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        grid.getChildren().addAll(diceRollButton, auctionButton, managePropertyButton, buyPropertyButton, endTurnButton, menuButton);
        return controls;
    }

    // given an x(column) and y(row) fetch the contents of that cell
    private Node getGridCell (GridPane grid, int x, int y){
        Node node = (StackPane) grid.getChildren().stream()
                .filter(c -> GridPane.getColumnIndex(c) == x && GridPane.getRowIndex(c) == y)
                .collect(Collectors.toList())
                .get(0);
        return node;
    }

    // place the players on the intial board position
    private Map<Player, Node> placePlayers(List<Player> players, GridPane gameBoard) {
        Map<Player, Node> identifiers = new HashMap<>();

        for(Player player : players){
            Text text = new Text();
            text.setText(player.getName());
            identifiers.put(player, text);

            int[] coord = gameBoardGenerator.indexToCoord(0);

            StackPane pane = (StackPane) getGridCell(gameBoard, coord[0], coord[1]);

            pane.getChildren().add(text);
        }

        return identifiers;
    }

    /**
     * Gets controller.
     *
     * @return the controller
     */
    public MainController getController() {
        return controller;
    }
}
