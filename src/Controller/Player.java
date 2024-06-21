package Controller;

import Model.Direction;
import Model.Maze;

import java.io.Serial;
import java.io.Serializable;

/**
 *  The Player class represents a player in a game, including their name, score, and current location.
 *  This class implements Serializable to allow player objects to be serialized.
 *
 *  @author Binal Dhaliwal, Bhavneet Bhargava
 *  @version 1.0
 */
public class Player implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 9L;
    /**
     * The name of the player.
     */
    private final String myName;
    /**
     * The score of the player.
     */
    private final int myScore;
    /**
     * The current row of the player.
     */
    private int myCurrentRow;
    /**
     * The current column of the player.
     */
    private int myCurrentCol;

    /**
     * Constructs a player with the given name, score, and maze.
     *
     * @param theName the name of the player.
     * @param theScore the score of the player.
     * @param theMaze the maze of the player.
     */
    public Player(final String theName, final int theScore, final Maze theMaze) {
        myName = theName;
        myScore = theScore;
        myCurrentRow = 0;
        myCurrentCol = 0;
    }

    /**
     * Moves the player one step north (decreases the row by 1).
     */
    public final void moveNorth() {
        move(Direction.NORTH);
    }

    /**
     * Moves the player one step south (increases the row by 1).
     */
    public final void moveSouth() {
        move(Direction.SOUTH);
    }

    /**
     * Moves the player one step east (increases the column by 1).
     */
    public final void moveEast() {
        move(Direction.EAST);
    }

    /**
     * Moves the player one step west (decreases the column by 1).
     */
    public final void moveWest() {
        move(Direction.WEST);
    }

    /**
     * Moves the player in the given direction.
     *
     * @param theDirection the direction to move the player in.
     */
    private void move(final Direction theDirection) {
        switch (theDirection) {
            case NORTH -> myCurrentRow--;
            case SOUTH -> myCurrentRow++;
            case EAST -> myCurrentCol++;
            case WEST -> myCurrentCol--;
        }
    }

    /**
     * Gets the current row of the player.
     *
     * @return the current row of the player.
     */
    public final int getCurrentRow() {
        return myCurrentRow;
    }

    /**
     * Gets the current column of the player.
     *
     * @return the current column of the player.
     */
    public final int getCurrentCol() {
        return myCurrentCol;
    }

    /**
     * Sets the current location of the player.
     *
     * @param theRow the new row of the player
     * @param theCol the new column of the player
     * @throws IllegalArgumentException if theRow or theCol is negative
     */
    public final void setCurrentLocation(final int theRow, final int theCol) {
        if (theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Row and column must be non-negative.");
        }
        myCurrentRow = theRow;
        myCurrentCol = theCol;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return a string representation of the player
     */
    @Override
    public final String toString() {
        return "Player: " + myName + ", Score: " + myScore;
    }
}


