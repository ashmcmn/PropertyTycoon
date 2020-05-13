package controller;

import javafx.beans.property.Property;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.auction.Auction;
import model.board.Board;
import model.board.Group;
import model.board.PropertySquare;
import model.board.Square;
import model.menu.PrePlayer;
import model.players.Choice;
import model.players.Player;
import model.transactions.Transaction;
import utilities.Utilities;
import view.GameView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainController {
    private GameManager gameManager;
    private GameView view;
    private Stage stage;
    private static final Logger LOG = LogManager.getLogger(MainController.class);

    public MainController(Stage stage, List<PrePlayer> prePlayers) {
        gameManager = new GameManager(prePlayers);
        gameManager.setController(this);
    }

    public MainController(Stage stage, List<PrePlayer> prePlayers, long timeLimit) {
        gameManager = new GameManager(prePlayers, timeLimit);
        gameManager.setController(this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public GameView getView() {
        return view;
    }

    public Stage getStage() {
        return stage;
    }

    public void rollDiceHandler() {
        LOG.debug(gameManager.getCurrentPlayer().getName() + " rolls the dice");
        gameManager.getDice().roll();

        if(!gameManager.getDice().wasDouble()) {
            view.getUserControls().get("RollDice").setDisable(true);
            view.getUserControls().get("EndTurn").setDisable(false);

            if(gameManager.getCurrentPlayer().isJailed()){
                gameManager.getCurrentPlayer().addJailedTurn();
                if(gameManager.getCurrentPlayer().getJailedTurns() == 3) {
                    LOG.debug(gameManager.getCurrentPlayer().getName() + " has been in jail 3 turns so they have been released");
                    gameManager.getCurrentPlayer().releaseFromJail();
                    view.getUserControls().get("EndTurn").setDisable(false);
                }
                else LOG.debug("A double was not rolled so " + gameManager.getCurrentPlayer().getName() + " stays in jail");
                return;
            }
        }
        else{
            if(gameManager.getCurrentPlayer().isJailed()){
                LOG.debug("A double was rolled so " + gameManager.getCurrentPlayer().getName() + " is released from jail");
                gameManager.getCurrentPlayer().releaseFromJail();
                gameManager.getDice().resetDoubles();
            }
            else if(gameManager.getDice().getDoubles() == 3){
                gameManager.getCurrentPlayer().sendToJail();
                gameManager.getDice().resetDoubles();
                view.getUserControls().get("RollDice").setDisable(true);
                view.getUserControls().get("EndTurn").setDisable(false);
            }
            else{
                LOG.debug("A double was rolled so " + gameManager.getCurrentPlayer().getName() + " will take another turn");
            }
        }

        int[] result = gameManager.getDice().getResult();
        Square square = gameManager.getCurrentPlayer().move(IntStream.of(result).sum(), true);
        view.updateAssets();

        if(square.getClass() == PropertySquare.class){
            if(((PropertySquare) square).getOwner() == gameManager.getBoard().getBank()){

                view.getUserControls().get("RollDice").setDisable(true);
                view.getUserControls().get("EndTurn").setDisable(true);

                Transaction transaction = new Transaction(gameManager.getCurrentPlayer(), gameManager.getBoard().getBank(), new Object[]{((PropertySquare) square).getCost()}, new Object[]{square});
                if(gameManager.getCurrentPlayer().canBuy() && transaction.canSettle())
                    view.getUserControls().get("PurchaseProperty").setDisable(false);
                if(gameManager.getCurrentPlayer().canBuy())
                    view.getUserControls().get("AuctionProperty").setDisable(false);
                else {
                    if(gameManager.getDice().wasDouble())
                        view.getUserControls().get("RollDice").setDisable(false);
                    else
                        view.getUserControls().get("EndTurn").setDisable(false);
                }

            }
            else if(((PropertySquare) square).getOwner() != gameManager.getCurrentPlayer()){
                square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
                view.updateAssets();
            }
        }
        else{
            square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
            view.updateAssets();
        }
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void endTurnHandler() {
        LOG.debug(gameManager.getCurrentPlayer().getName() + " has ended their turn");

        gameManager.endTurn();

        Player player = gameManager.getCurrentPlayer();

        if(player.isAI()){
            handleAITurn();
        }
        else {
            if (player.isJailed()) {
                view.getUserControls().get("EndTurn").setDisable(true);
                view.getUserControls().get("RollDice").setDisable(true);

                Stage dialog = new Stage();

                GridPane pane = new GridPane();
                pane.setStyle("-fx-background-color: red");
                pane.setPrefHeight(300);
                pane.setPrefWidth(200);

                ColumnConstraints c1 = new ColumnConstraints();
                c1.setPrefWidth(200);
                pane.getColumnConstraints().add(c1);

                RowConstraints r1 = new RowConstraints();
                r1.setPrefHeight(75);
                RowConstraints r2 = new RowConstraints();
                r2.setPrefHeight(75);
                RowConstraints r3 = new RowConstraints();
                r3.setPrefHeight(75);
                RowConstraints r4 = new RowConstraints();
                r4.setPrefHeight(75);
                pane.getRowConstraints().addAll(r1, r2, r3, r4);

                Text text = new Text();
                text.setText(player.getName() + " is in jail, what to do?");
                text.setTextAlignment(TextAlignment.CENTER);
                GridPane.setColumnIndex(text, 0);
                GridPane.setRowIndex(text, 0);
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.CENTER);

                Button rollDice = new Button("Roll Dice");
                rollDice.setAlignment(Pos.CENTER);
                GridPane.setColumnIndex(rollDice, 0);
                GridPane.setRowIndex(rollDice, 1);
                GridPane.setHalignment(rollDice, HPos.CENTER);
                GridPane.setValignment(rollDice, VPos.CENTER);

                Button payBail = new Button("Pay Bail");
                payBail.setAlignment(Pos.CENTER);
                Transaction transaction = new Transaction(player, gameManager.getBoard().getBank(), new Object[]{50}, new Object[]{});
                if (!transaction.canSettle())
                    payBail.setDisable(true);
                GridPane.setColumnIndex(payBail, 0);
                GridPane.setRowIndex(payBail, 2);
                GridPane.setHalignment(payBail, HPos.CENTER);
                GridPane.setValignment(payBail, VPos.CENTER);

                Button useCard = new Button("Use Card");
                useCard.setAlignment(Pos.CENTER);
                if (!player.hasGoof())
                    useCard.setDisable(true);
                GridPane.setColumnIndex(useCard, 0);
                GridPane.setRowIndex(useCard, 3);
                GridPane.setHalignment(useCard, HPos.CENTER);
                GridPane.setValignment(useCard, VPos.CENTER);

                rollDice.setOnAction(actionEvent -> {
                    dialog.close();
                    rollDiceHandler();
                });

                payBail.setOnAction(actionEvent -> {
                    transaction.settle();
                    player.releaseFromJail();
                    dialog.close();
                    view.updateAssets();
                    view.getUserControls().get("EndTurn").setDisable(true);
                    view.getUserControls().get("RollDice").setDisable(false);
                });

                useCard.setOnAction(actionEvent -> {
                    player.useGoof();
                    dialog.close();
                    view.getUserControls().get("EndTurn").setDisable(true);
                    view.getUserControls().get("RollDice").setDisable(false);
                });

                pane.getChildren().addAll(text, rollDice, payBail, useCard);

                Scene scene = new Scene(pane, 200, 300);
                dialog.setTitle(player.getName() + " is jailed");
                dialog.setScene(scene);
                dialog.initOwner(getStage());
                dialog.show();
            } else {
                view.getUserControls().get("EndTurn").setDisable(true);
                view.getUserControls().get("RollDice").setDisable(false);

                if (player.getProperties().size() > 0) {
                    view.getUserControls().get("ManageProperties").setDisable(false);
                }
            }
        }

        view.updateAssets();
    }

    public void handleAITurn() {
        Player currentPlayer = gameManager.getCurrentPlayer();

        Choice choice = new Choice();

        if(currentPlayer.isJailed()){
            Transaction transaction = new Transaction(currentPlayer, gameManager.getBoard().getBank(), new Object[]{50}, new Object[]{});

            if(transaction.canSettle()){
                choice.add(transaction::settle);
                currentPlayer.releaseFromJail();
                LOG.debug(currentPlayer.getName() + " rolls the dice");
                gameManager.getDice().roll();
            }

            choice.add(() -> {
                LOG.debug(currentPlayer.getName() + " rolls the dice");
                gameManager.getDice().roll();
            });

            choice.decide();

            if(!gameManager.getDice().wasDouble() && currentPlayer.isJailed())
                return;
            else {
                currentPlayer.releaseFromJail();
                LOG.debug(currentPlayer.getName() + " rolls the dice");
                gameManager.getDice().roll();
            }
        }
        else{
            LOG.debug(currentPlayer.getName() + " rolls the dice");
            gameManager.getDice().roll();
        }

        int[] result = gameManager.getDice().getResult();
        Square square = gameManager.getCurrentPlayer().move(IntStream.of(result).sum(), true);

        if(square.getClass() == PropertySquare.class){
            PropertySquare property = (PropertySquare) gameManager.getBoard().getSquares()[gameManager.getCurrentPlayer().getPosition()];
            if(property.getOwner() == gameManager.getBoard().getBank()){
                if(gameManager.getCurrentPlayer().canBuy()){
                    Transaction transaction = new Transaction(gameManager.getCurrentPlayer(), gameManager.getBoard().getBank(), new Object[]{property.getCost()}, new Object[]{property});

                    choice = new Choice();

                    if(transaction.canSettle()) {
                        //make AI more likely to buy outright than go for auction
                        for(int i = 0; i < 4; i ++)
                            choice.add(transaction::settle);
                    }

                    choice.add(this::auctionHandler);

                    choice.decide();
                }
            }
            else if(((PropertySquare) square).getOwner() != gameManager.getCurrentPlayer()){
                square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
            }
        }
        else{
            square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
        }

        if(currentPlayer.getCash() < 0){
            fileBankruptcyHandler();
        }

        endTurnHandler();
    }

    public void buyPropertyHandler() {
        PropertySquare property = (PropertySquare) gameManager.getBoard().getSquares()[gameManager.getCurrentPlayer().getPosition()];

        Transaction transaction = new Transaction(gameManager.getCurrentPlayer(), gameManager.getBoard().getBank(), new Object[]{property.getCost()}, new Object[]{property});
        transaction.settle();

        view.getUserControls().get("PurchaseProperty").setDisable(true);
        view.getUserControls().get("AuctionProperty").setDisable(true);
        view.getUserControls().get("ManageProperties").setDisable(false);

        if(gameManager.getDice().wasDouble()){
            view.getUserControls().get("EndTurn").setDisable(true);
            view.getUserControls().get("RollDice").setDisable(false);
        }
        else{
            view.getUserControls().get("EndTurn").setDisable(false);
            view.getUserControls().get("RollDice").setDisable(true);
        }

        view.updateAssets();
        LOG.debug(gameManager.getCurrentPlayer().getName() + " is buying " + property.getName());
    }

    public void auctionHandler() {
        PropertySquare property = (PropertySquare) gameManager.getBoard().getSquares()[gameManager.getCurrentPlayer().getPosition()];
        List<Player> potentialBuyers = gameManager.getPlayers().stream().filter(Player::canBuy).collect(Collectors.toList());
        Auction auction = new Auction(property, potentialBuyers, gameManager.getBoard().getBank());
        auction.getBids(this);
        view.updateAssets();
    }

    public void managePropertiesHandler() {
        Stage dialog = new Stage();
        GridPane pane = new GridPane();

        Player player = gameManager.getCurrentPlayer();

        pane.setPrefHeight(600);
        pane.setPrefWidth(500);

        StackPane assetsPane = new StackPane();
        assetsPane.setPadding(new Insets(20, 20, 20, 20));
        GridPane.setColumnIndex(assetsPane, 0);
        GridPane.setRowIndex(assetsPane, 0);
        assetsPane.setPrefWidth(400);
        assetsPane.setPrefHeight(500);

        GridPane propertyCards = new GridPane();
        assetsPane.getChildren().add(propertyCards);
        propertyCards.setHgap(5);
        propertyCards.setVgap(5);

        for(int x = 0; x <  Group.values().length; x++){
            int finalX = x;
            List<PropertySquare> properties = Stream.of(gameManager.getBoard().getSquares())
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
                    cardOpaque.setWidth(500 / 13);
                    cardOpaque.setHeight(500 / 6);
                    cardOpaque.setFill(Color.rgb(255, 255, 255, 0.8));

                    card.setPrefWidth(500 / 13);
                    card.setPrefHeight(500 / 6);

                    Rectangle cardColor = new Rectangle();
                    card.getChildren().add(cardColor);
                    cardColor.setWidth(500 / 13);
                    cardColor.setHeight(500 / 24);
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
                    else {
                        card.setOnMouseClicked(actionEvent -> {
                            if(pane.getChildren().size() == 2){
                                pane.getChildren().remove(1);
                            }
                            pane.getChildren().add(generatePropertyManager(property));
                        });
                    }
                }

                GridPane.setColumnIndex(card, x);
                GridPane.setRowIndex(card, y);

                propertyCards.getChildren().add(card);
            }
        }

        pane.getChildren().addAll(assetsPane);

        Scene scene = new Scene(pane, 500, 600);
        dialog.setTitle("Manage Properties");
        dialog.setScene(scene);
        dialog.initOwner(getStage());
        dialog.show();
    }

    public GridPane generatePropertyManager(PropertySquare property){
        GridPane pane = new GridPane();
        pane.setPrefWidth(500);
        pane.setPrefHeight(200);
        GridPane.setColumnIndex(pane, 0);
        GridPane.setRowIndex(pane, 1);

        if(property.getGroup() == Group.UTILITIES || property.getGroup() == Group.STATION){
            ColumnConstraints c1 = new ColumnConstraints();
            c1.setPrefWidth(500/3);
            ColumnConstraints c2 = new ColumnConstraints();
            c2.setPrefWidth(500/3);
            pane.getColumnConstraints().addAll(c1,c2);

            RowConstraints r1 = new RowConstraints();
            r1.setPrefHeight(20);
            RowConstraints r2 = new RowConstraints();
            r2.setPrefHeight(100);
            RowConstraints r3 = new RowConstraints();
            r3.setPrefHeight(100);
            pane.getRowConstraints().addAll(r1,r2,r3);

            Text name = new Text(property.getName());
            name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            GridPane.setColumnSpan(name, 3);

            pane.addRow(0, name);

            Text mortgaged = new Text();
            mortgaged.setText("Mortgaged: " + property.isMortgaged());

            pane.addRow(1, mortgaged);

            Button mortgage = new Button((property.isMortgaged() ? "Pay off mortgage" : "Mortgage") + " for £" + property.getCost()/2);
            if(property.isMortgaged())
                mortgage.setOnAction(actionEvent -> {
                    property.payoff();
                    GridPane parent = (GridPane) pane.getParent();
                    parent.getChildren().remove(1);
                    parent.getChildren().add(generatePropertyManager(property));
                    view.updateAssets();
                });
            else mortgage.setOnAction(actionEvent -> {
                property.mortgage();
                GridPane parent = (GridPane) pane.getParent();
                parent.getChildren().remove(1);
                parent.getChildren().add(generatePropertyManager(property));
                view.updateAssets();
            });

            pane.addRow(2, mortgage);

            for(Node node : pane.getChildren()){
                GridPane.setHalignment(node, HPos.CENTER);
                GridPane.setValignment(node, VPos.CENTER);
            }
        }
        else{
            ColumnConstraints c1 = new ColumnConstraints();
            c1.setPrefWidth(500/3);
            ColumnConstraints c2 = new ColumnConstraints();
            c2.setPrefWidth(500/3);
            ColumnConstraints c3 = new ColumnConstraints();
            c3.setPrefWidth(500/3);
            pane.getColumnConstraints().addAll(c1,c2,c3);

            RowConstraints r1 = new RowConstraints();
            r1.setPrefHeight(20);
            RowConstraints r2 = new RowConstraints();
            r2.setPrefHeight(100);
            RowConstraints r3 = new RowConstraints();
            r3.setPrefHeight(100);
            pane.getRowConstraints().addAll(r1,r2,r3);

            Board board = gameManager.getBoard();
            Player player = gameManager.getCurrentPlayer();

            Text name = new Text(property.getName());
            name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            GridPane.setColumnSpan(name, 3);

            pane.addRow(0, name);

            Text currentHouses = new Text();
            currentHouses.setText("Houses: " + (property.getLevel() > 0 && property.getLevel() < 5 ? property.getLevel() : 0));

            Text currentHotels = new Text();
            currentHotels.setText("Hotels: " + (property.getLevel() == 5 ? 1 : 0));

            Text mortgaged = new Text();
            mortgaged.setText("Mortgaged: " + property.isMortgaged());

            pane.addRow(1, currentHouses, currentHotels, mortgaged);

            Button improve = new Button("Improve property for £" + board.getImprovementCost(property.getGroup(), 0));
            if(!player.canImprove(property))
                improve.setDisable(true);
            improve.setOnAction(actionEvent -> {
                player.improve(property);
                GridPane parent = (GridPane) pane.getParent();
                parent.getChildren().remove(1);
                parent.getChildren().add(generatePropertyManager(property));
                view.updateAssets();
            });

            Button devalue = new Button("Devalue property for £" + board.getImprovementCost(property.getGroup(), property.getLevel() == 5 ? 1 : 0));
            if(!player.canDevalue(property))
                devalue.setDisable(true);
            devalue.setOnAction(actionEvent -> {
                player.devalue(property);
                GridPane parent = (GridPane) pane.getParent();
                parent.getChildren().remove(1);
                parent.getChildren().add(generatePropertyManager(property));
                view.updateAssets();
            });

            Button mortgage = new Button((property.isMortgaged() ? "Pay off mortgage" : "Mortgage") + " for £" + property.getCost()/2);
            if(property.getLevel() != 0)
                mortgage.setDisable(true);
            if(property.isMortgaged())
                mortgage.setOnAction(actionEvent -> {
                    property.payoff();
                    GridPane parent = (GridPane) pane.getParent();
                    parent.getChildren().remove(1);
                    parent.getChildren().add(generatePropertyManager(property));
                    view.updateAssets();
                });
            else mortgage.setOnAction(actionEvent -> {
                property.mortgage();
                GridPane parent = (GridPane) pane.getParent();
                parent.getChildren().remove(1);
                parent.getChildren().add(generatePropertyManager(property));
                view.updateAssets();
            });

            pane.addRow(2, improve, devalue, mortgage);

            for(Node node : pane.getChildren()){
                GridPane.setHalignment(node, HPos.CENTER);
                GridPane.setValignment(node, VPos.CENTER);
            }
        }

        return pane;
    }

    public void fileBankruptcyHandler() {
        Player player = gameManager.getCurrentPlayer();

        for(PropertySquare propertySquare : player.getProperties()){
            propertySquare.setOwner(gameManager.getBoard().getBank());
        }

        Transaction transaction = new Transaction(player, gameManager.getBoard().getBank(), new Object[]{player.getCash()}, new Object[]{});
        transaction.settle();

        ((StackPane) player.getBoardPiece().getParent()).getChildren().remove(player.getBoardPiece());
        gameManager.getPlayers().remove(player);

        if(gameManager.getPlayers().size() == 1)
            endGameHandler();
        else endTurnHandler();
    }

    private void endGameHandler() {
        LOG.debug(gameManager.getPlayers().get(0).getName() + " wins the game!");

        for(Button button : view.getUserControls().values()){
            button.setDisable(true);
        }
    }

    public void handleEndAbridged() {
        LOG.debug("Time has run out!");

        view.setDisable(true);

        Player winner = gameManager.getPlayers().get(0);

        for(Player player : gameManager.getPlayers()){
            if(evaluate(player) > evaluate(winner)){
                winner = player;
            }
        }

        LOG.debug(winner.getName() + " wins the game!");
    }

    private int evaluate(Player player) {
        int total = player.getCash();

        for(PropertySquare prop : player.getProperties()){
            if(prop.isMortgaged()){
                total += prop.getCost()/2;
            }
            else{
                total += prop.getCost();
            }

            if(prop.getGroup() != Group.STATION && prop.getGroup() != Group.UTILITIES){
                if(prop.getLevel() < 5){
                    total += prop.getLevel() * gameManager.getBoard().getImprovementCost(prop.getGroup(),0);
                }
                else if(prop.getLevel() == 5){
                    total += prop.getLevel() * gameManager.getBoard().getImprovementCost(prop.getGroup(),0);
                    total += gameManager.getBoard().getImprovementCost(prop.getGroup(),1);
                }
            }
        }

        return total;
    }
}