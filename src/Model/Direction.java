package Model;
import java.io.Serializable;

/**
 * Direction represents a direction in the maze.
 *
 * @author Binal Dhaliwal, Bhavneet Bhargava
 * @version 1.0
 */
public enum Direction implements Serializable {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1);

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 10L;
    /**
     * The row of the direction.
     */
    private final int myRow;
    /**
     * The column of the direction.
     */
    private final int myCol;

    /**
     * Constructs a direction with the given row and column.
     *
     * @param theRow theRow the row change associated with the direction
     * @param theCol theCol the column change associated with the direction
     */
    Direction(final int theRow, final int theCol) {
        myRow = theRow;
        myCol = theCol;
    }

    /**
     * Gets the row of the direction.
     *
     * @return the row change
     */
    public final int getRow() {
        return myRow;
    }

    /**
     * Gets the column of the direction.
     *
     * @return the column change
     */
    public final int getCol() {
        return myCol;
    }
}


