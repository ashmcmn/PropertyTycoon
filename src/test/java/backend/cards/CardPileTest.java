package backend.cards;

import backend.board.Board;
import backend.party.Bank;
import backend.players.Player;
import backend.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardPileTest {
    CardPile cardPile;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank =  new Bank(50000);

        cardPile = new CardPile(new ArrayList<>());

        cardPile.addCard(new Card("You inherit £100", new Action() {
            public void action(Player player, Board board){
                Transaction transaction = new Transaction(player, bank, new Object[]{}, new Object[]{100});
                if(transaction.canSettle())
                    transaction.settle();
            }}));

        cardPile.addCard(new Card("You have won 2nd prize in a beauty contest, collect £20", new Action() {
            public void action(Player player, Board board){
                Transaction transaction = new Transaction(player, bank, new Object[]{}, new Object[]{20});
                if(transaction.canSettle())
                    transaction.settle();
            }}));
    }

    @Test
    void draw() {
        Card card = cardPile.draw();
        assertEquals("You inherit £100", card.getDescription());
    }

    @Test
    void addCard() {
        cardPile.addCard(
            new Card("Go back to Crapper Street", new Action() {
                public void action(Player player, Board board){
                    player.setPosition(1);
                }})
        );
        Card card = cardPile.draw();
        card = cardPile.draw();
        card = cardPile.draw();
        assertEquals("Go back to Crapper Street", card.getDescription());
    }
}