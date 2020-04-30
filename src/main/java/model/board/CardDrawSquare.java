package model.board;

import model.cards.Card;
import model.cards.CardPile;
import model.players.Player;

/**
 * The type Card draw square.
 *
 * @author Ashley McManamon
 */
public class CardDrawSquare extends Square {
    private CardPile cardPile;

    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public CardDrawSquare(String name, CardPile cardPile) {
        super(name);
        this.cardPile = cardPile;
    }

    /**
     * Draws a card for the player
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        Card card = cardPile.draw();
        LOG.debug(player.getName() + " has drawn a card: " + card.getDescription());
        if(card.getDescription().equals("Get out of jail free")){
            player.addGoof();
        }
        else{
            card.doAction();
            cardPile.addCard(card);
        }
    }
}
