package model.board;

import model.party.Bank;
import model.party.Party;
import model.players.Player;
import model.transactions.Transaction;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Property square.
 *
 * @author Ashley McManamon
 */
public class PropertySquare extends Square {
    private Party owner;
    private int[] rents;
    private int level;
    private Group group;
    private int cost;
    private boolean mortgaged = false;

    /**
     * Instantiates a new Square.
     *
     * @param name  the name of the square
     * @param owner the owner
     * @param rents the different levels of rent payment
     * @param group the group
     * @param board the board
     * @param cost  the cost of the property
     */
    public PropertySquare(String name, Party owner, int[] rents, Group group, Board board, int cost) {
        super(name);
        this.owner = owner;
        this.rents = rents;
        this.group = group;
        this.board = board;
        this.cost = cost;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public Party getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(Party owner) {
        this.owner = owner;
        if(owner == board.getBank()){
            level = 0;
        }
    }

    /**
     * Gets the current rent for this property.
     *
     * @return the rent
     */
    public int getRent() {
        int rent = rents[0];

        if(owner.ownsGroup(group)){
            rent = rent * 2;

            if(level>0){
                rent = rents[level];
            }
        }
        return rent;
    }

    /**
     * Function for buying, paying rent or doing nothing
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        LOG.debug(player.getName() + " pays " + getOwner().getName() + " £" + getRent() + " in rent");

        if(((Player) owner).isJailed()){
            LOG.debug(getOwner().getName() + " is in jail so can't collect rent");
        }
        else if(mortgaged){
            LOG.debug(getName() + " is mortgaged so rent can't be collected");
        }
        else{
            Transaction transaction = new Transaction(player, owner, new Object[]{getRent()}, new Object[]{});
            if(transaction.canSettle())
                transaction.settle();
            else {
                if(player.isAI() && player.getProperties().stream().filter(p -> !p.isMortgaged()).count() > 0){
                    Random random = new Random();
                    int index = random.nextInt((int) player.getProperties().stream().filter(p -> !p.isMortgaged()).count());
                    PropertySquare prop = player.getProperties().stream().filter(p -> !p.isMortgaged()).collect(Collectors.toList()).get(index);
                    prop.mortgage();
                    doAction(player, board);
                }
                else if(player.isAI()){
                    transaction.settle();
                }
            }
        }
    }

    /**
     * Gets the group the property belongs to.
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Add house/hotel to property.
     *
     * @return whether it was possible to add level
     */
    public boolean addLevel() {
        if(level == 5)
            return false;

        for (PropertySquare prop : Stream.of(board.getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == group)
                .collect(Collectors.toList())
             ) {
            if(prop.level - level < 0){
                return false;
            }
        }

        level++;
        return true;
    }

    /**
     * Remove house/hotel from property.
     *
     * @return whether it was possible to remove level
     */
    public boolean removeLevel() {
        if(level == 0)
            return false;

        for (PropertySquare prop : Stream.of(board.getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == group)
                .collect(Collectors.toList())
        ) {
            if(prop.level - level > 0){
                return false;
            }
        }

        level--;
        return true;
    }

    public boolean canAddLevel() {
        if(level == 5)
            return false;

        for (PropertySquare prop : Stream.of(board.getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == group)
                .collect(Collectors.toList())
        ) {
            if(prop.level - level < 0){
                return false;
            }
        }

        return true;
    }

    public boolean canRemoveLevel() {
        if(level == 0)
            return false;

        for (PropertySquare prop : Stream.of(board.getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == group)
                .collect(Collectors.toList())
        ) {
            if(prop.level - level > 0){
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the level of development.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() { return cost; }

    /**
     * Mortgage.
     */
    public void mortgage() {
        Transaction transaction = new Transaction(board.getBank(), owner, new Object[]{cost/2}, new Object[]{});
        transaction.settle();
        mortgaged = true;
    }

    /**
     * Payoff mortgage.
     */
    public void payoff() {
        Transaction transaction = new Transaction(owner, board.getBank(), new Object[]{cost/2}, new Object[]{});
        if(transaction.canSettle()){
            mortgaged = false;
            transaction.settle();
        }
    }

    public boolean isMortgaged() {
        return mortgaged;
    }
}
