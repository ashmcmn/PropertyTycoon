package backend.transactions;

import backend.board.PropertySquare;
import backend.party.Party;

/**
 * The type Transaction.
 *
 * @author Ashley McManamon
 */
public class Transaction {
    private Party partyOne;
    private Party partyTwo;
    private Object[] oneToTwo;
    private Object[] twoToOne;

    /**
     * Instantiates a new Transaction.
     *
     * @param partyOne the first party of the transaction
     * @param partyTwo the second party of the transaction
     * @param oneToTwo the game assets for the first party to transfer to the second party
     * @param twoToOne the game assets for the second party to transfer to the first party
     */
    public Transaction(Party partyOne, Party partyTwo, Object[] oneToTwo, Object[] twoToOne) {
        this.partyOne = partyOne;
        this.partyTwo = partyTwo;
        this.oneToTwo = oneToTwo;
        this.twoToOne = twoToOne;
    }

    /**
     * Checks whether player's have the game assets to allow the transaction to settle
     *
     * @return the outcome of the check
     */
    public boolean canSettle() {
        for (Object item : oneToTwo) {
            if(item instanceof Integer && partyOne.getCash() < (Integer) item){
                return false;
            }
            else if(item instanceof PropertySquare && !partyOne.getProperties().contains(item)){
                return false;
            }
        }
        for (Object item : twoToOne) {
            if(item instanceof Integer && partyTwo.getCash() < (Integer) item){
                return false;
            }
            else if(item instanceof PropertySquare && !partyTwo.getProperties().contains(item)){
                return false;
            }
        }
        return true;
    }

    /**
     * Settles the transaction by making the necessary game asset transfers
     */
    public void settle() {
        for (Object item : oneToTwo) {
            if(item instanceof Integer){
                partyOne.setCash(partyOne.getCash() - ((Integer) item));
                partyTwo.setCash(partyTwo.getCash() + ((Integer) item));
            }
            else if(item instanceof PropertySquare){
                partyOne.removeProperty((PropertySquare) item);
                partyTwo.addProperty((PropertySquare) item);
                ((PropertySquare) item).setOwner(partyTwo);
            }
        }

        for (Object item : twoToOne) {
            if(item instanceof Integer){
                partyTwo.setCash(partyTwo.getCash() - ((Integer) item));
                partyOne.setCash(partyOne.getCash() + ((Integer) item));
            }
            else if(item instanceof PropertySquare){
                partyTwo.removeProperty((PropertySquare) item);
                partyOne.addProperty((PropertySquare) item);
                ((PropertySquare) item).setOwner(partyOne);
            }
        }
    }
}
