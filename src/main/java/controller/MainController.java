package controller;

import javafx.beans.property.Property;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.auction.Auction;
import model.board.PropertySquare;
import model.board.Square;
import model.players.Player;
import model.transactions.Transaction;
import utilities.Utilities;
import view.GameView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainController {
    private GameManager gameManager;
    private GameView view;
    private Stage stage;
    private static final Logger LOG = LogManager.getLogger(MainController.class);

    public MainController(Stage stage) {
        gameManager = new GameManager(new String[]{"P1","P2"});
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
        gameManager.getDice().roll();

        if(!gameManager.getDice().wasDouble()) {
            view.getUserControls().get("RollDice").setDisable(true);
            view.getUserControls().get("EndTurn").setDisable(false);

            if(gameManager.getDice().getDoubles() == 3){
                gameManager.getCurrentPlayer().sendToJail();
                gameManager.getDice().resetDoubles();
            }

            if(gameManager.getCurrentPlayer().isJailed()){
                gameManager.getCurrentPlayer().addJailedTurn();
                if(gameManager.getCurrentPlayer().getJailedTurns() == 3) {
                    LOG.debug(gameManager.getCurrentPlayer().getName() + " has been in jail 3 turns so they have been released");
                    gameManager.getCurrentPlayer().releaseFromJail();
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
            else{
                LOG.debug("A double was rolled so " + gameManager.getCurrentPlayer().getName() + " takes another turn");
            }
        }

        int[] result = gameManager.getDice().getResult();
        Square square = gameManager.getCurrentPlayer().move(IntStream.of(result).sum(), true);

        if(square.getClass() == PropertySquare.class){
            if(((PropertySquare) square).getOwner() == gameManager.getBoard().getBank()){

                view.getUserControls().get("RollDice").setDisable(true);
                view.getUserControls().get("EndTurn").setDisable(true);

                Transaction transaction = new Transaction(gameManager.getCurrentPlayer(), gameManager.getBoard().getBank(), new Object[]{((PropertySquare) square).getCost()}, new Object[]{square});
                if(gameManager.getCurrentPlayer().canBuy() && transaction.canSettle())
                    view.getUserControls().get("PurchaseProperty").setDisable(false);
                if(gameManager.getCurrentPlayer().canBuy())
                    view.getUserControls().get("AuctionProperty").setDisable(false);
                else view.getUserControls().get("EndTurn").setDisable(false);

            }
            else{
                square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
            }
        }
        else{
            square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
        }
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void endTurnHandler() {
        gameManager.endTurn();

        Player player = gameManager.getCurrentPlayer();

        if(player.isJailed()){
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
            if(!transaction.canSettle())
                payBail.setDisable(true);
            GridPane.setColumnIndex(payBail, 0);
            GridPane.setRowIndex(payBail, 2);
            GridPane.setHalignment(payBail, HPos.CENTER);
            GridPane.setValignment(payBail, VPos.CENTER);

            Button useCard = new Button("Use Card");
            useCard.setAlignment(Pos.CENTER);
            if(!player.hasGoof())
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
        }
        else{
            view.getUserControls().get("EndTurn").setDisable(true);
            view.getUserControls().get("RollDice").setDisable(false);
        }

        view.updateAssets();
    }

    public void buyPropertyHandler() {
        PropertySquare property = (PropertySquare) gameManager.getBoard().getSquares()[gameManager.getCurrentPlayer().getPosition()];

        Transaction transaction = new Transaction(gameManager.getCurrentPlayer(), gameManager.getBoard().getBank(), new Object[]{property.getCost()}, new Object[]{property});
        transaction.settle();

        view.getUserControls().get("PurchaseProperty").setDisable(true);
        view.getUserControls().get("AuctionProperty").setDisable(true);

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
}