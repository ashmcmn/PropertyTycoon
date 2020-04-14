package backend.game;

import backend.board.Board;
import backend.board.PropertySquare;
import backend.board.Square;
import backend.players.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * The type Game manager.
 */
public class GameManager {
    private Board board;

    private Player currentPlayer;

    /**
     * Instantiates a new Game manager.
     *
     * @param board the board
     */
    public GameManager(Board board) {
        this.board = board;
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets board.
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Starts the game.
     */
    public void startGame(){
        currentPlayer = board.getPlayer(0);
    };

    /**
     * Load game config from json file
     *
     * @param configPath the config file's path
     */
    public void loadConfig(String configPath){
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(new FileReader(configPath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject =  (JSONObject) obj;

        JSONObject board = (JSONObject) jsonObject.get("board");

        JSONArray importSquares = (JSONArray) board.get("squares");

        Square[] squares = new Square[importSquares.size()];

        for(int i = 0; i < importSquares.size(); i++){
            JSONObject importSquare = (JSONObject) importSquares.get(i);

            Square newSquare = null;

            boolean ownable = (importSquare.get("ownable")).equals("Yes");

            if(ownable){
                newSquare = new PropertySquare((String) importSquare.get("name"), this.getBoard().getBank());
            } else {
                newSquare = new Square((String) importSquare.get("name"));
            }

            squares[i] = newSquare;
        }

        this.getBoard().setSquares(squares);


    }


}
