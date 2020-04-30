package controller;

import com.sun.tools.javac.Main;
import javafx.beans.property.Property;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.board.PropertySquare;
import model.board.Square;
import model.transactions.Transaction;
import utilities.Utilities;
import view.GameView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.IntStream;

public class MainController {
    private GameManager gameManager;
    private GameView view;
    private static final Logger LOG = LogManager.getLogger(MainController.class);

    public MainController(Stage stage) {
        gameManager = new GameManager(new String[]{"P1","P2"});
    }

    public GameManager getGameManager() {
        return gameManager;
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
        }
        else{
            LOG.debug("A double was rolled so " + gameManager.getCurrentPlayer().getName() + " takes another turn");
        }

        int[] coord = Utilities.indexToCoord(gameManager.getCurrentPlayer().getPosition());
        StackPane oldPane = (StackPane) Utilities.getGridCell(view.getGameBoard(), coord[0], coord[1]);
        oldPane.getChildren().remove(gameManager.getCurrentPlayer().getBoardPiece());

        int[] result = gameManager.getDice().getResult();
        Square square = gameManager.getCurrentPlayer().move(IntStream.of(result).sum(), true);

        if(square.getClass() == PropertySquare.class){
            if(((PropertySquare) square).getOwner() == gameManager.getBoard().getBank()){

                view.getUserControls().get("EndTurn").setDisable(true);
                view.getUserControls().get("RollDice").setDisable(true);

                Transaction transaction = new Transaction(gameManager.getCurrentPlayer(), gameManager.getBoard().getBank(), new Object[]{((PropertySquare) square).getCost()}, new Object[]{square});
                if(transaction.canSettle())
                    view.getUserControls().get("PurchaseProperty").setDisable(false);
                view.getUserControls().get("AuctionProperty").setDisable(false);
            }
            else{
                square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
            }
        }
        else{
            square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
        }

        coord = Utilities.indexToCoord(gameManager.getCurrentPlayer().getPosition());
        StackPane newPane = (StackPane) Utilities.getGridCell(view.getGameBoard(), coord[0], coord[1]);
        newPane.getChildren().add(gameManager.getCurrentPlayer().getBoardPiece());
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void endTurnHandler() {
        view.getUserControls().get("EndTurn").setDisable(true);
        view.getUserControls().get("RollDice").setDisable(false);
        gameManager.endTurn();
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

        LOG.debug(gameManager.getCurrentPlayer().getName() + " is buying " + property.getName());
    }
}