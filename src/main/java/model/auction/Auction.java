package model.auction;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.board.PropertySquare;
import model.party.Bank;
import model.players.Player;
import model.transactions.Transaction;
import view.GameView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Auction.
 */
public class Auction {
    private PropertySquare property;
    private List<Player> players;
    private Bank bank;
    Map<Player, Integer> bids;

    /**
     * Instantiates a new Auction.
     *
     * @param property the property
     * @param players  the players
     */
    public Auction(PropertySquare property, List<Player> players, Bank bank) {
        this.property = property;
        this.players = players;
        this.bank = bank;
        this.bids = new HashMap<>();

        for(Player player : players){
            this.bids.put(player, null);
        }
    }

    /**
     * Settles the auction returning the winning player
     *
     * @return the winner
     */
    public void settle(MainController controller) {
        Map.Entry<Player, Integer> winner = bids.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get();
        Transaction transaction = new Transaction(winner.getKey(), bank, new Object[]{winner.getValue()}, new Object[]{property});
        transaction.settle();

        controller.getView().getUserControls().get("PurchaseProperty").setDisable(true);
        controller.getView().getUserControls().get("AuctionProperty").setDisable(true);

        if(controller.getGameManager().getDice().wasDouble()) {
            controller.getView().getUserControls().get("RollDice").setDisable(false);
            controller.getView().getUserControls().get("EndTurn").setDisable(true);
        }
        else{
            controller.getView().getUserControls().get("RollDice").setDisable(true);
            controller.getView().getUserControls().get("EndTurn").setDisable(false);
        }
    }

    public void getBids(MainController controller) {
        Player player = null;
        try {
            player = bids.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), null))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                    .get(0);
        }
        catch(Exception ignored){
            settle(controller);
        };

        if(player != null){
            Stage dialog = new Stage();

            StackPane pane = new StackPane();

            Text text = new Text();
            text.setText(player.getName() + ", enter your bid:");
            StackPane.setAlignment(text, Pos.TOP_CENTER);
            StackPane.setMargin(text, new Insets(8,8,8,8));
            text.setTextAlignment(TextAlignment.CENTER);

            TextField input = new TextField();
            input.setAlignment(Pos.CENTER);
            StackPane.setMargin(input, new Insets(8,8,8,8));

            Button submit = new Button();
            submit.setText("Submit");
            submit.setDisable(true);
            Player finalPlayer = player;
            submit.setOnAction(actionEvent -> {
                bids.put(finalPlayer, Integer.valueOf(input.getText()));
                dialog.close();
                getBids(controller);
            });
            input.textProperty().addListener((a,b,c) -> {
                if(Integer.parseInt(c) < finalPlayer.getCash()){
                    submit.setDisable(false);
                }
                else{
                    submit.setDisable(true);
                }
            });
            StackPane.setMargin(submit, new Insets(8,8,8,8));
            submit.setAlignment(Pos.BOTTOM_CENTER);
            StackPane.setAlignment(submit, Pos.BOTTOM_CENTER);

            pane.getChildren().addAll(text, input, submit);

            Scene scene = new Scene(pane, 200, 140);
            dialog.setTitle(player.getName() + " enter bid");
            dialog.setScene(scene);
            dialog.initOwner(controller.getStage());
            dialog.show();
        }
    }
}
