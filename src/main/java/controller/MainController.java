package controller;

import com.sun.tools.javac.Main;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.board.Square;
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
}