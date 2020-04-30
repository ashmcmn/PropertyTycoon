package model.auction;

import model.board.PropertySquare;
import model.players.Player;

import java.util.*;

/**
 * The type Auction.
 */
public class Auction {
    private PropertySquare property;
    private Player[] players;

    /**
     * Instantiates a new Auction.
     *
     * @param property the property
     * @param players  the players
     */
    public Auction(PropertySquare property, Player[] players) {
        this.property = property;
        this.players = players;
    }

    /**
     * Settles the auction returning the winning player
     *
     * @return the winner
     */
    public Player settle() {
        List<Integer> bids = new ArrayList<>();
        for(Player player : players){
            //get bid
            Random random = new Random();
            bids.add(random.nextInt(100));
        }

        return players[bids.indexOf(bids.stream().max(Integer::compareTo).orElse(-1))];
    }
}
