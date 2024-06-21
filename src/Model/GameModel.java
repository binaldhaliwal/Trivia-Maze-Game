package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * GameModel class that represents the game state for the Trivia Maze Game.
 *
 * @author Anagha Krishna, Binal Dhaliwal, Bhavneet Bhargava
 * @version 1.0
 */
public class GameModel implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 2L;
    /**
     * The maze for the game.
     */
    private final Maze myMaze;
    /**
     * The current location of the player in the maze.
     */
    private Room myPlayerLocation;
    /**
     * The list of questions that have been answered.
     */
    private final List<Question> myAnsweredQuestions;
    /**
     * Constructs a new GameModel with a new maze, initializes player location,
     * answered questions list, and question database.
     */
    public GameModel() {
        myMaze = new Maze();
        myPlayerLocation = myMaze.getCurrentRoom();
        myAnsweredQuestions = new ArrayList<>();
    }
    /**
     * Gets and returns the current player location.
     *
     * @return myPlayerLocation the current player location.
     */
    public final Room getPlayerLocation() {
        return myPlayerLocation;
    }

    /**
     * Sets the player location.
     *
     * @param thePlayerLocation the new player location.
     * @throws IllegalArgumentException if the player location is null.
     */
    public final void setPlayerLocation(final Room thePlayerLocation) {
        if (thePlayerLocation == null) {
            throw new IllegalArgumentException("Player location cannot be null");
        }
        myPlayerLocation = thePlayerLocation;
    }
    /**
     * Gets and returns the list of answered questions.
     *
     * @return myAnsweredQuestions the list of answered questions.
     */
    public final List<Question> getAnsweredQuestions() {
        return myAnsweredQuestions;
    }
    /**
     * Adds a question to the list of answered questions.
     *
     * @param theQuestion is the question to add.
     */
    public final void addAnsweredQuestions(final Question theQuestion) {
        myAnsweredQuestions.add(theQuestion);
    }
    /**
     * Fetches a new question from the database.
     *
     * @return A new question.
     */
    public final Question getQuestion() {
        return null;
    }
    /**
     * Starts a new game by resetting the maze, player location,
     * and clearing the answered questions list.
     */
    public final void startGame() {
        myMaze.reset();
        myPlayerLocation = myMaze.getCurrentRoom();
        myAnsweredQuestions.clear();
    }
    /**
     * Ends the current game by resetting the maze, player location,
     * and clearing the answered questions list.
     */
    public final void endGame() {
        myMaze.reset();
        myPlayerLocation = myMaze.getCurrentRoom();
        myAnsweredQuestions.clear();
    }
    /**
     * Updates the door state (locked or unlocked) in a specified room and direction.
     *
     * @param theRoom The room containing the door.
     * @param theDirection The direction of the door.
     * @param theIsLocked Boolean representing new state of door.
     * True if locked, false if unlocked.
     */
    public final void updateDoorState(final Room theRoom, final Direction theDirection, final boolean theIsLocked) {
        final Door door = theRoom.getDoor(theDirection);
        if (door != null) {
            if (theIsLocked) {
                door.doorLock();
            } else {
                door.doorOpen();
            }
        }
    }
    /**
     * Gets and returns the maze.
     *
     * @return myMaze the maze.
     */
    public final Maze getMaze() {
        return myMaze;
    }
}

