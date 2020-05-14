package model.transactions;

import model.board.PropertySquare;
import model.party.Bank;
import model.party.Party;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    protected static final Logger LOG = LogManager.getLogger(Transaction.class);

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
                LOG.debug(partyOne.getName() + " can't afford to give " + partyTwo.getName() + " £" + item);
                return false;
            }
            else if(item instanceof PropertySquare && !partyOne.getProperties().contains(item)){
                return false;
            }
        }
        for (Object item : twoToOne) {
            if(item instanceof Integer && partyTwo.getCash() < (Integer) item){
                LOG.debug(partyTwo.getName() + " can't afford to give " + partyOne.getName() + " £" + item);
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
                LOG.debug(partyOne.getName() + " has paid " + partyTwo.getName() + " £" + item);
                partyOne.setCash(partyOne.getCash() - ((Integer) item));
                partyTwo.setCash(partyTwo.getCash() + ((Integer) item));
                if(!(partyOne instanceof Bank))
                    LOG.debug(partyOne.getName() + " now has £" + partyOne.getCash());
                if(!(partyTwo instanceof Bank))
                    LOG.debug(partyTwo.getName() + " now has £" + partyTwo.getCash());
            }
            else if(item instanceof PropertySquare){
                LOG.debug(partyOne.getName() + " has given " + partyTwo.getName() + " " + ((PropertySquare) item).getName());
                partyOne.removeProperty((PropertySquare) item);
                partyTwo.addProperty((PropertySquare) item);
                ((PropertySquare) item).setOwner(partyTwo);
            }
        }

        for (Object item : twoToOne) {
            if(item instanceof Integer){
                LOG.debug(partyTwo.getName() + " has paid " + partyOne.getName() + " £" + item);
                partyTwo.setCash(partyTwo.getCash() - ((Integer) item));
                partyOne.setCash(partyOne.getCash() + ((Integer) item));
                if(!(partyOne instanceof Bank))
                    LOG.debug(partyOne.getName() + " now has £" + partyOne.getCash());
                if(!(partyTwo instanceof Bank))
                    LOG.debug(partyTwo.getName() + " now has £" + partyTwo.getCash());
            }
            else if(item instanceof PropertySquare){
                LOG.debug(partyTwo.getName() + " has given " + partyOne.getName()+ " " + ((PropertySquare) item).getName());
                partyTwo.removeProperty((PropertySquare) item);
                partyOne.addProperty((PropertySquare) item);
                ((PropertySquare) item).setOwner(partyOne);
            }
        }
    }
}
