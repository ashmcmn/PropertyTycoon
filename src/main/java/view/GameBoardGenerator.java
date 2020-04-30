package view;

import model.board.Group;
import model.board.PropertySquare;
import model.board.Square;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import utilities.Utilities;

/**
 * The type Game board generator.
 */
public class GameBoardGenerator {
    private final GameView gameView;
    private double width;
    private double height;

    /**
     * Instantiates a new Game board generator.
     *
     * @param gameView the game view
     * @param width    the width
     * @param height   the height
     */
    public GameBoardGenerator(GameView gameView, double width, double height) {
        this.gameView = gameView;
        this.width = width;
        this.height = height;
    }



    /**
     * Generate a cell for a square.
     *
     * @param gameBoard the game board
     * @param square    the square
     * @param pane      the pane to contain its elements
     * @param o         the orientation
     */
    public void generateSquare(GridPane gameBoard, Square square, StackPane pane, int o) {
        Rectangle tileInfo = null;
        Rectangle tileColor = null;

        // create the text element displaying the square's name
        Text name = new Text();
        name.setText(square.getName());
        name.setFont(Font.font("Arial", FontWeight.BOLD, 8));
        name.setTextAlignment(TextAlignment.CENTER);
        name.setWrappingWidth(height / 13f * 0.9);

        // create the text element may display the square's cost(if it has one)
        Text cost = new Text();
        cost.setFont(Font.font("Arial", FontWeight.NORMAL, 8));
        cost.setTextAlignment(TextAlignment.CENTER);

        // size and orientate the pane and its elements
        if (o == 0 || o == 180) {
            pane.setPrefWidth(height / 13f);
            pane.setPrefHeight(height / 13f * 2);

            tileInfo = new Rectangle(height / 13f, height / 13f * 2);

            tileColor = new Rectangle(height / 13f, height / 13f * 2 * 0.2);

        }
        if (o == 0) {
            StackPane.setAlignment(tileInfo, Pos.BOTTOM_CENTER);

            StackPane.setAlignment(tileColor, Pos.TOP_CENTER);

            StackPane.setAlignment(name, Pos.TOP_CENTER);
            if (square instanceof PropertySquare && ((PropertySquare) square).getGroup() != Group.STATION && ((PropertySquare) square).getGroup() != Group.UTILITIES)
                name.setTranslateY(height / 13f * 2 * 0.2 + 5);
            else
                name.setTranslateY(10);

            StackPane.setAlignment(cost, Pos.BOTTOM_CENTER);
            cost.setTranslateY(-10);
        }
        if (o == 180) {
            StackPane.setAlignment(tileInfo, Pos.TOP_CENTER);

            StackPane.setAlignment(tileColor, Pos.BOTTOM_CENTER);

            name.setRotate(180);
            StackPane.setAlignment(name, Pos.BOTTOM_CENTER);
            if (square instanceof PropertySquare && ((PropertySquare) square).getGroup() != Group.STATION && ((PropertySquare) square).getGroup() != Group.UTILITIES)
                name.setTranslateY(-height / 13f * 2 * 0.2 - 5);
            else
                name.setTranslateY(-10);

            cost.setRotate(180);
            StackPane.setAlignment(cost, Pos.TOP_CENTER);
            cost.setTranslateY(10);
        }

        if (o == 90 || o == 270) {
            pane.setPrefWidth(height / 13f * 2);
            pane.setPrefHeight(height / 13f);

            tileInfo = new Rectangle(height / 13f * 2, height / 13f);

            tileColor = new Rectangle(height / 13f * 2 * 0.2, height / 13f);
        }
        if (o == 90) {
            StackPane.setAlignment(tileInfo, Pos.CENTER_LEFT);

            StackPane.setAlignment(tileColor, Pos.CENTER_RIGHT);

            name.setRotate(90);
            StackPane.setAlignment(name, Pos.CENTER_RIGHT);
            if (square instanceof PropertySquare && ((PropertySquare) square).getGroup() != Group.STATION && ((PropertySquare) square).getGroup() != Group.UTILITIES)
                name.setTranslateX(-height / 13f * 0.02 - 10);
            else
                name.setTranslateX(10);

            cost.setRotate(90);
            StackPane.setAlignment(cost, Pos.CENTER_LEFT);
            cost.setTranslateX(5);
        }
        if (o == 270) {
            StackPane.setAlignment(tileInfo, Pos.CENTER_RIGHT);

            StackPane.setAlignment(tileColor, Pos.CENTER_LEFT);

            name.setRotate(270);
            StackPane.setAlignment(name, Pos.CENTER_LEFT);
            if (square instanceof PropertySquare && ((PropertySquare) square).getGroup() != Group.STATION && ((PropertySquare) square).getGroup() != Group.UTILITIES)
                name.setTranslateX(height / 13f * 0.02 + 10);
            else
                name.setTranslateX(-10);

            cost.setRotate(270);
            StackPane.setAlignment(cost, Pos.CENTER_RIGHT);
            cost.setTranslateX(-5);
        }

        tileInfo.setFill(Color.WHITE);
        tileInfo.setStroke(Color.BLACK);
        tileColor.setFill(Color.RED);
        tileColor.setStroke(Color.BLACK);

        pane.getChildren().addAll(tileInfo, name);

        // add optional elements such as cost and color identification
        if (square instanceof PropertySquare && ((PropertySquare) square).getGroup() != Group.STATION && ((PropertySquare) square).getGroup() != Group.UTILITIES) {
            if (o == 0 || o == 180)
                tileInfo.setHeight(height / 13f * 2 * 0.8);
            else
                tileInfo.setWidth(height / 13f * 2 * 0.8);

            cost.setText("£" + ((PropertySquare) square).getCost());

            switch (((PropertySquare) square).getGroup()) {
                case BLUE:
                    tileColor.setFill(Color.rgb(147, 161, 206));
                    break;
                case GREEN:
                    tileColor.setFill(Color.rgb(96, 122, 34));
                    break;
                case RED:
                    tileColor.setFill(Color.rgb(196, 50, 39));
                    break;
                case BROWN:
                    tileColor.setFill(Color.rgb(130, 102, 89));
                    break;
                case ORANGE:
                    tileColor.setFill(Color.rgb(215, 106, 44));
                    break;
                case PURPLE:
                    tileColor.setFill(Color.rgb(195, 143, 182));
                    break;
                case YELLOW:
                    tileColor.setFill(Color.rgb(227, 185, 95));
                    break;
                case DEEPBLUE:
                    tileColor.setFill(Color.rgb(33, 86, 124));
                    break;
            }

            pane.getChildren().addAll(tileColor, cost);
        }
    }

    /**
     * Create board grid pane.
     *
     * @return the grid pane
     */
    public GridPane createBoard() {
        GridPane gameBoard = new GridPane();

        gameBoard.setPrefHeight(height);

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                StackPane pane = new StackPane();
                pane.setStyle("-fx-background-color: rgb(221, 233, 237)");
                pane.setAlignment(Pos.TOP_CENTER);

                Rectangle tileInfo;
                Rectangle tileColor;

                if ((i == 0 && j == 0) || (i == 0 && j == 10) || (i == 10 && j == 0) || (i == 10 && j == 10)) {
                    Square square = gameView.getController().getGameManager().getBoard().getSquares()[Utilities.coordToIndex(j, i)];

                    pane.setPrefWidth(height / 13f * 2);
                    pane.setPrefHeight(height / 13f * 2);

                    tileInfo = new Rectangle();
                    tileInfo.setWidth(height / 13f * 2);
                    tileInfo.setHeight(height / 13f * 2);

                    tileInfo.setFill(Color.rgb(221, 233, 237));
                    tileInfo.setStroke(Color.BLACK);

                    Text text = new Text();
                    text.setText(square.getName());
                    text.setFont(Font.font(null, FontWeight.NORMAL, 8));
                    StackPane.setAlignment(text, Pos.CENTER);

                    pane.getChildren().addAll(tileInfo, text);
                } else if (i == 0) {
                    Square square = gameView.getController().getGameManager().getBoard().getSquares()[Utilities.coordToIndex(j, i)];

                    generateSquare(gameBoard, square, pane, 180);
                } else if (i == 10) {
                    Square square = gameView.getController().getGameManager().getBoard().getSquares()[Utilities.coordToIndex(j, i)];

                    generateSquare(gameBoard, square, pane, 0);
                } else if (j == 0) {
                    Square square = gameView.getController().getGameManager().getBoard().getSquares()[Utilities.coordToIndex(j, i)];

                    generateSquare(gameBoard, square, pane, 90);
                } else if (j == 10) {
                    Square square = gameView.getController().getGameManager().getBoard().getSquares()[Utilities.coordToIndex(j, i)];

                    generateSquare(gameBoard, square, pane, 270);
                } else {
                    pane.setPrefWidth(height / 13f);
                    pane.setPrefHeight(height / 13f);
                }

                GridPane.setRowIndex(pane, i);
                GridPane.setColumnIndex(pane, j);

                gameBoard.getChildren().addAll(pane);
            }
        }
        return gameBoard;
    }
}