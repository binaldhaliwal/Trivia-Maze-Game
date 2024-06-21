package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * GameState class that represents the state of the game, including player location
 * and answered questions.
 *
 * @author Anagha Krishna, Binal Dhaliwal, Bhavneet Bhargava
 * @version 1.0
 */
public class GameState implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 3L;
    /**
     * The current location of the player in the game.
     */
    private Room myPlayerLocation;
    /**
     * The list of questions that have been answered by the player.
     */
    private final List<Question> myAnsweredQuestions;
    /**
     * Constructs a new GameState with an empty list of answered questions.
     */
    public GameState() {
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
     * @param theRoom The new player location.
     * @throws IllegalArgumentException if the player location is null;
     */
    public final void setPlayerLocation(final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("Player location cannot be null");
        }
        myPlayerLocation = theRoom;
    }
    /**
     * Adds a question to the list of answered questions.
     *
     * @param theQuestion The question to add.
     */
    public final void addAnsweredQuestions(final Question theQuestion) {
        myAnsweredQuestions.add(theQuestion);
    }
    /**
     * Gets and returns the list of answered questions.
     *
     * @return The list of answered questions.
     */
    public final List<Question> getAnsweredQuestions() {
        return myAnsweredQuestions;
    }
    /**
     * Updates the current state with another GameState instance.
     *
     * @param theGameState The GameState instance to copy the state from.
     */
    public final void updateState(final GameState theGameState) {
        myPlayerLocation = theGameState.myPlayerLocation;
        myAnsweredQuestions.clear();
        myAnsweredQuestions.addAll(theGameState.myAnsweredQuestions);
    }
}

