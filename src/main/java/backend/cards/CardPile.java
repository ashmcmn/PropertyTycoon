package backend.cards;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Card pile.
 */
public class CardPile {
    private List<Card> cards;

    /**
     * Instantiates a new Card pile.
     *
     * @param cards the cards
     */
    public CardPile(List<Card> cards){
        this.cards = cards;
    }

    /**
     * Draw card.
     *
     * @return the card
     */
    public Card draw(){
        return cards.remove(0);
    }

    /**
     * Add card.
     *
     * @param card the card
     */
    public void addCard(Card card) {
        cards.add(card);
    }
}
