package model.cards;

/**
 * The type Card.
 */
public class Card {
    private String description;
    private Action action;

    /**
     * Instantiates a new Card.
     *
     * @param description the description
     * @param action      the action
     */
    public Card(String description, Action action){
        this.description = description;
        this.action = action;
    }

    /**
     * Gets card's description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public void doAction() {
        action.action();
    }

}
