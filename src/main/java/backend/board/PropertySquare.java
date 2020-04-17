package backend.board;

import backend.party.Party;
import backend.players.Player;
import backend.transactions.Transaction;

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

    /**
     * Instantiates a new Square.
     *
     * @param name  the name of the square
     * @param owner the owner
     */
    public PropertySquare(String name, Party owner, int[] rents, Group group, Board board) {
        super(name);
        this.owner = owner;
        this.rents = rents;
        this.group = group;
        this.board = board;
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
        if(owner == board.getBank()){
            Transaction transaction = new Transaction(player, board.getBank(), new Object[]{}, new Object[]{this});
            if(transaction.canSettle()){
                transaction.settle();
            }
        }
        else if(owner != player){
            Transaction transaction = new Transaction(player, owner, new Object[]{getRent()}, new Object[]{});
            if(transaction.canSettle())
                transaction.settle();
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

    /**
     * Gets the level of development.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }
}
