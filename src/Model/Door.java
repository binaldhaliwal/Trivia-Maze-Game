package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a door in a maze that can be locked, closed, and associated with questions.
 *
 * @author Binal Dhaliwal, Anagha Krishna
 * @version 1.0
 */
public class Door implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The lock of the door.
     */
    private boolean myLock;
    /**
     * The closed state of the door.
     */
    private boolean myClosed;
    /**
     * The permanently locked state of the door.
     */
    private boolean myPermanentlyLocked;
    /**
     * The questions associated with the door.
     */
    private final List<Question> myQuestions;
    /**
     * The direction of the door.
     */
    private final Direction myDirection;

    /**
     * Constructs a door with the specified lock, closed state, and direction.
     *
     * @param theLock true if the door is locked, false otherwise
     * @param theClosed true if the door is closed, false otherwise
     * @param theDirection the direction in which the door leads
     */
    public Door (final Boolean theLock, final Boolean theClosed, final Direction theDirection) {
        myLock = theLock;
        myClosed = theClosed;
        myPermanentlyLocked = false;
        myQuestions = new ArrayList<>();
        myDirection = theDirection;
    }

    /**
     * Adds a question related to the door.
     *
     * @param theQuestion the question to add
     */
    public final void addQuestion(final Question theQuestion) {
        myQuestions.add(theQuestion);
    }

    /**
     * Gets the list of questions related to the door.
     *
     * @return the list of questions
     */
    public final List<Question> getQuestions() {
        return myQuestions;
    }

    /**
     * Checks if the door is locked.
     *
     * @return true if the door is locked, false otherwise
     */
    public final boolean isLock() {
        return myLock;
    }

    /**
     * Sets the lock state of the door.
     *
     * @param theLock true to lock the door, false to unlock
     */
    public final void setLock(final boolean theLock) {
        myLock = theLock;
    }

    /**
     * Checks if the door is closed.
     *
     * @return true if the door is closed, false otherwise
     */
    public final boolean isClosed() {
        return myClosed;
    }

    /**
     * Sets the closed state of the door.
     *
     * @param theClosed true to close the door, false to open
     */
    public final void setClosed(final boolean theClosed){
        myClosed = theClosed;
    }

    /**
     * Locks the door.
     */
    public final void doorLock(){
        myLock = true;
    }

    /**
     * Opens the door.
     */
    public final void doorOpen(){
        myLock = false;
        myClosed = false;
    }

    /**
     * Gets the direction in which the door leads.
     *
     * @return the direction of the door.
     */
    public final Direction getDirection() {
        return myDirection;
    }

    /**
     * Checks if the door is permanently locked.
     *
     * @return true if the door is permanently locked, false otherwise.
     */
    public final boolean permanentlyLocked() {
        return myPermanentlyLocked;
    }

    /**
     * Sets the permanently locked state of the door.
     *
     * @param thePermanentlyLocked true to permanently lock the door, false to unlock.
     */
    public final void setPermanentlyLocked(final boolean thePermanentlyLocked) {
        myPermanentlyLocked = thePermanentlyLocked;
    }

    /**
     * Resets the door to its default state (unlocked and closed).
     */
    public final void reset() {
        myLock = false;
        myClosed = true;
    }
}

