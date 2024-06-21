package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a room in a maze that has doors in each direction associated with questions.
 *
 * @author Binal Dhaliwal, Anagha Krishna
 * @version 1.0
 */
public class Room implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 6L;
    /**
     * Constant int field representing the index of the north door.
     */
    private static final int NORTH_DOOR = 0;
    /**
     * Constant int field representing the index of the south door.
     */
    private static final int SOUTH_DOOR = 1;
    /**
     * Constant int field representing the index of the east door.
     */
    private static final int EAST_DOOR = 2;
    /**
     * Constant int field representing the index of the west door.
     */
    private static final int WEST_DOOR = 3;
    /**
     * Boolean field stating whether there is a north door in that direction.
     */
    private boolean myNorthDoor;
    /**
     * Boolean field stating whether there is a south door in that direction.
     */
    private boolean mySouthDoor;
    /**
     * Boolean field stating whether there is an east door in that direction.
     */
    private boolean myEastDoor;
    /**
     * Boolean field stating whether there is a west door in that direction.
     */
    private boolean myWestDoor;
    /**
     * Row position of the room in the maze.
     */
    private int myRow;
    /**
     * Column position of the room in the maze.
     */
    private int myCol;
    /**
     * Boolean field indicating whether the room has been visited
     */
    private boolean myVisited;
    /**
     * Boolean field indicating whether the room is an exit to maze.
     */
    private boolean myExit;
    /**
     * Boolean field indicating whether the room is a start room.
     */
    private boolean myStart;
    /**
     * List of doors in the room.
     */
    private final List<Door> myDoors;
    /**
     * Constructs a Room with the specified doors.
     *
     * @param theNorthDoor The north door, added to list of available doors if not null.
     * @param theSouthDoor The south door, added to list of available doors if not null.
     * @param theEastDoor The east door, added to list of available doors if not null.
     * @param theWestDoor The west door, added to list of available doors if not null.
     */
    public Room(final Door theNorthDoor,final Door theSouthDoor, final Door theEastDoor, final Door theWestDoor) {
        myDoors = new ArrayList<>();
        myVisited = false;
        if (theNorthDoor != null) {
            myDoors.add(theNorthDoor);
        }
        if (theSouthDoor != null) {
            myDoors.add(theSouthDoor);
        }
        if (theEastDoor != null) {
            myDoors.add(theEastDoor);
        }
        if (theWestDoor != null) {
            myDoors.add(theWestDoor);
        }
    }
    /**
     * Sets the row position of the room.
     *
     * @param theRow The row position.
     */
    public final void setRow(final int theRow) {
        if (theRow < 0) {
            throw new IllegalArgumentException("Row value cannot be negative.");
        }
        myRow = theRow;
    }
    /**
     * Gets the row position of the room.
     *
     * @return myRow returns the row position of the room.
     */
    public final int getRow() {
        return myRow;
    }
    /**
     * Sets the column position of the room.
     *
     * @param theCol The column position of the room.
     * @throws IllegalArgumentException if the column is a negative value.
     */
    public final void setCol(final int theCol) {
        if (theCol < 0) {
            throw new IllegalArgumentException("Column value cannot be negative.");
        }
        myCol = theCol;
    }
    /**
     * Gets the column position of the room.
     *
     * @return myCol returns the column position of the room.
     */
    public final int getCol() {
        return myCol;
    }
    /**
     * Sets the visited status of the room indicating whether
     * it has already been visited or not.
     *
     * @param theVisited The visited status, true if visited and false if not visited.
     * @throws IllegalArgumentException if the room is already marked as visited.
     */
    public final void setVisited(final boolean theVisited) {
        if (theVisited && myVisited) {
            throw new IllegalStateException("Room is already marked as visited");
        }
        myVisited = theVisited;
    }
    /**
     * Gets the visited status of the room.
     *
     * @return myVisited returns the visited status.
     */
    public final boolean getVisited() {
        return myVisited;
    }
    /**
     * Sets whether the room is an exit.
     *
     * @param theExit The exit status.
     * @throws IllegalArgumentException if the room is already marked as an exit.
     */
    public final void setExit(final boolean theExit) {
        if (myExit && theExit) {
            throw new IllegalStateException("Room is already marked as an exit.");
        }
        myExit = theExit;
    }
    /**
     * Gets and returns whether the room is an exit.
     *
     * @return myExit true if the room is an exit, false otherwise.
     */
    public final boolean getExit() {
        return myExit;
    }
    /**
     * Sets whether there is a north door.
     *
     * @param theNorthDoor The north door status.
     * @throws IllegalArgumentException if north door is already set to true.
     */
    public final void setNorthDoor(final boolean theNorthDoor) {
        if (myNorthDoor && theNorthDoor) {
            throw new IllegalStateException("North door is already set.");
        }
        myNorthDoor = theNorthDoor;
    }
    /**
     * Sets whether there is a south door.
     *
     * @param theSouthDoor The south door status.
     * @throws IllegalArgumentException if south door is already set to true.
     */
    public final void setSouthDoor(final boolean theSouthDoor) {
        if (mySouthDoor && theSouthDoor) {
            throw new IllegalStateException("South door is already set.");
        }
        mySouthDoor = theSouthDoor;
    }
    /**
     * Sets whether there is an east door.
     *
     * @param theEastDoor The east door status.
     * @throws IllegalArgumentException if east door is already set to true.
     */
    public void setEastDoor(final boolean theEastDoor) {
        if (myEastDoor && theEastDoor) {
            throw new IllegalStateException("East door is already set.");
        }
        myEastDoor = theEastDoor;
    }
    /**
     * Sets whether there is a west door.
     *
     * @param theWestDoor The west door status.
     * @throws IllegalArgumentException if west door is already set to true.
     */
    public final void setWestDoor(final boolean theWestDoor) {
        if (myWestDoor && theWestDoor) {
            throw new IllegalStateException("West door is already set.");
        }
        myWestDoor = theWestDoor;
    }
    /**
     * Gets the door at the specific index.
     *
     * @param theDoorIdx The door index.
     * @return The door at the specified index.
     */
    public final Door getDoor(final int theDoorIdx) {
        return myDoors.get(theDoorIdx);
    }
    /**
     * Gets the door in the specific direction.
     *
     * @param theDir The direction of the room.
     * @return The door in the specific direction.
     */
    public final Door getDoor(final Direction theDir) {
        return switch (theDir) {
            case NORTH -> getDoor(NORTH_DOOR);
            case SOUTH -> getDoor(SOUTH_DOOR);
            case EAST -> getDoor(EAST_DOOR);
            case WEST -> getDoor(WEST_DOOR);
        };
    }
    /**
     * Checks whether the room is a start room.
     *
     * @return myStart as true if the room is a start room, false otherwise
     */
    public final boolean getStart() {
        return myStart;
    }
    /**
     * Sets whether the room is a start one.
     *
     * @param theStart The start room status.
     * @throws IllegalArgumentException if the room is already marked as start room.
     */
    public final void setStart(final boolean theStart) {
        if (myStart && theStart) {
            throw new IllegalStateException("Room is already marked as a start room.");
        }
        myStart = theStart;
    }
    /**
     * Returns the list of doors in the room.
     *
     * @return myDoors returns the list of doors.
     */
    public final List<Door> getDoors() {
        return myDoors;
    }
}

