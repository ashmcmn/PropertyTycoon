package model.auction;

import model.board.PropertySquare;
import controller.GameManager;
import model.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuctionTest {
    Auction auction;
    GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(new String[]{"p1","p2","p2"});
        PropertySquare prop = (PropertySquare) gameManager.getBoard().getSquares()[1];
        auction = new Auction(prop, gameManager.getPlayers(), gameManager.getBoard().getBank());
    }
}