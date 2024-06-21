package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents a maze. It contains a 2D array of rooms.
 *
 * @author Bhavneet Bhargava, Binal Dhlaiwal, and Anagha Krishna
 * @version 1.0
 */
public class Maze implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 4L;
    /**
     * The size of the maze.
     */
    private static final int MAZE_SIZE = 3;
    /**
     *  A constant representing the number one.
     */
    private static final int ONE = 1;
    /**
     * A constant representing the number zero.
     */
    private static final int ZERO = 0;
    /**
     * The number of rows in the maze.
     */
    private int myRows;
    /**
     * The number of columns in the maze.
     */
    private int myCols;
    /**
     * The row of the player.
     */
    private int myPlayerRow;
    /**
     * The column of the player.
     */
    private int myPlayerCol;
    /**
     * The maze of the game.
     */
    public final Room[][] myMaze;
    /**
     * The random number generator.
     */
    private final Random myRandom;

    /**
     * Constructs a maze with the given number of rows and columns.
     */
    public Maze() {
        myRows = MAZE_SIZE;
        myCols = MAZE_SIZE;
        myMaze = new Room[MAZE_SIZE][MAZE_SIZE];
        myRandom = new Random();
        myPlayerCol = ZERO;
        myPlayerRow = ZERO;
        initializeMaze();
        generateMaze();
    }

    /**
     * Returns the number of rows in the maze.
     *
     * @return the number of rows.
     */
    public final int getRows() {
        return myRows;
    }

    /**
     * Returns the number of columns in the maze.
     *
     * @return the number of columns.
     */
    public final int getCols() {
        return myCols;
    }

    /**
     * Generates a maze using the recursive backtracking algorithm.
     */
    public final void generateMaze() {
        initializeMaze();

        final int startRow = myRandom.nextInt(MAZE_SIZE);
        final int startCol = myRandom.nextInt(MAZE_SIZE);

        final Stack<Room> stack = new Stack<>();
        Room currentRoom = myMaze[startRow][startCol];
        currentRoom.setVisited(true);
        stack.push(currentRoom);

        while (!stack.isEmpty()) {
            currentRoom = stack.peek();
            final int rows = currentRoom.getRow();
            final int cols = currentRoom.getCol();

            final List<Room> neighbors = getUnvisitedNeighbors(rows, cols);

            if (!neighbors.isEmpty()) {
                final Room randomNeighbor = neighbors.get(myRandom.nextInt(neighbors.size()));

                removeWall(currentRoom, randomNeighbor);
                randomNeighbor.setVisited(true);
                stack.push(randomNeighbor);
            } else {
                stack.pop();
            }
        }
    }

    /**
     * Initializes the maze grid by creating a new Room object for each cell in the grid,
     * with Door objects for each direction (north, south, east, west).
     * Each Room object is initialized with the same set of Door objects.
     * The Door objects are initially set to have no lock and be closed.
     * <p>
     * This method creates Door objects for each direction (north, south, east, west)
     * with the initial state of unlocked and closed. Then, it iterates over each cell
     * in the maze grid and assigns a new Room object to it, passing the same set of
     * Door objects for each direction to the Room constructor.
     */
    private void initializeMaze() {
        for (int rows = ZERO; rows < MAZE_SIZE; rows++) {
            for (int cols = ZERO; cols < MAZE_SIZE; cols++) {

                final Door northDoor = new Door(false, true, Direction.NORTH);
                final Door southDoor = new Door(false, true, Direction.SOUTH);
                final Door eastDoor = new Door(false, true, Direction.EAST);
                final Door westDoor = new Door(false, true, Direction.WEST);

                final Room room = new Room(northDoor, southDoor, eastDoor, westDoor);

                if (rows == ZERO) {
                    northDoor.setPermanentlyLocked(true);
                }
                if (rows == MAZE_SIZE - ONE) {
                    southDoor.setPermanentlyLocked(true);
                }
                if (cols == ZERO) {
                    westDoor.setPermanentlyLocked(true);
                }
                if (cols == MAZE_SIZE - ONE) {
                    eastDoor.setPermanentlyLocked(true);
                }

                if (rows == ZERO && cols == ZERO) {
                    room.setStart(true);
                } else if (rows == MAZE_SIZE - ONE && cols == MAZE_SIZE - ONE) {
                    room.setExit(true);
                }

                myMaze[rows][cols] = room;
            }
        }
    }


    /**
     * Gets unvisited neighboring rooms of a given room.
     *
     * @param theRow The x-coordinate of the room.
     * @param theCol The y-coordinate of the room.
     * @return A list of unvisited neighboring rooms.
     */
    private List<Room> getUnvisitedNeighbors(final int theRow, final int theCol) {
        final List<Room> neighbors = new ArrayList<>();
        if (isValidRoom(theRow - ONE, theCol) && !myMaze[theRow - ONE][theCol].getVisited()) {
            neighbors.add(myMaze[theRow - ONE][theCol]);
        }
        if (isValidRoom(theRow + ONE, theCol) && !myMaze[theRow + ONE][theCol].getVisited()) {
            neighbors.add(myMaze[theRow + ONE][theCol]);
        }
        if (isValidRoom(theRow, theCol - ONE) && !myMaze[theRow][theCol - ONE].getVisited()) {
            neighbors.add(myMaze[theRow][theCol - ONE]);
        }
        if (isValidRoom(theRow, theCol + ONE) && !myMaze[theRow][theCol + ONE].getVisited()) {
            neighbors.add(myMaze[theRow][theCol + ONE]);
        }
        Collections.shuffle(neighbors);
        return neighbors;
    }

    /**
     * Checks if the given coordinates represent a valid room in the maze.
     *
     * @param theRow The x-coordinate of the room.
     * @param theCol The y-coordinate of the room.
     * @return True if the coordinates represent a valid room, false otherwise.
     */
    private boolean isValidRoom(final int theRow, final int theCol) {
        return theRow >= ZERO && theRow <= MAZE_SIZE && theCol >= ZERO && theCol <= MAZE_SIZE;
    }

    /**
     * Removes the wall between two adjacent rooms.
     *
     * @param theCurrentRoom The current room.
     * @param theNeighbor The neighboring room.
     */
    private void removeWall(final Room theCurrentRoom, final Room theNeighbor) {
        final int dRow = theNeighbor.getRow() - theCurrentRoom.getRow();
        final int dCol = theNeighbor.getCol() - theCurrentRoom.getCol();

        if (dRow == ONE) {
            theCurrentRoom.setEastDoor(false);
            theNeighbor.setWestDoor(false);
        } else if (dRow == -ONE) {
            theCurrentRoom.setWestDoor(false);
            theNeighbor.setEastDoor(false);
        } else if (dCol == ONE) {
            theCurrentRoom.setSouthDoor(false);
            theNeighbor.setNorthDoor(false);
        } else if (dCol == -ONE) {
            theCurrentRoom.setNorthDoor(false);
            theNeighbor.setSouthDoor(false);
        }
    }

    /**
     * Moves the player in the specified direction if possible.
     *
     * @param theDirection the direction to move the player.
     */
    public final void movePlayer(final Direction theDirection) {
        if (canMovePlayer(theDirection)) {
            switch (theDirection) {
                case NORTH:
                    if (myPlayerRow > ZERO) {
                        myPlayerRow--;
                    }
                    break;
                case SOUTH:
                    if (myPlayerRow < MAZE_SIZE - ONE) {
                        myPlayerRow++;
                    }
                    break;
                case EAST:
                    if (myPlayerCol < MAZE_SIZE - ONE) {
                        myPlayerCol++;
                    }
                    break;
                case WEST:
                    if (myPlayerCol > ZERO) {
                        myPlayerCol--;
                    }
                    break;
            }
        }
    }

    /**
     * Checks if the player can move in a given direction.
     *
     * @param theDirection the direction to check.
     * @return true if the player can move in the given direction, false otherwise.
     */
    public final boolean canMovePlayer(final Direction theDirection) {
        switch (theDirection) {
            case NORTH:
                if (myPlayerRow == ZERO) {
                    return false;
                }
                break;
            case SOUTH:
                if (myPlayerRow == MAZE_SIZE - ONE) {
                    return false;
                }
                break;
            case EAST:
                if (myPlayerCol == MAZE_SIZE - ONE) {
                    return false;
                }
                break;
            case WEST:
                if (myPlayerCol == ZERO) {
                    return false;
                }
                break;
        }

        final Room currentRoom = getCurrentRoom();
        final Door door = currentRoom.getDoor(theDirection);
        return !door.isLock() && !door.isClosed();
    }

    /**
     * Returns the current room the player is in.
     *
     * @return the current room
     */
    public final Room getCurrentRoom() {
        if (isValidRoom(myPlayerRow, myPlayerCol)) {
            return myMaze[myPlayerRow][myPlayerCol];
        }
        return null;
    }

    /**
     * Checks if there are possible moves the player can make.
     *
     * @return true if there are possible moves, false otherwise
     */
    public final boolean isPossibleMoves() {
        for (Direction direction : Direction.values()) {
            if (canMovePlayer(direction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player has reached the end of the maze.
     *
     * @return true if the player is at the end of the maze, false otherwise
     */
    public final boolean endOfMazeCheck() {
        return myRows == MAZE_SIZE - ONE && myCols == MAZE_SIZE - ONE;
    }

    /**
     * Resets the maze to its initial state and regenerates it.
     */
    public final void reset() {
        myRows = ZERO;
        myCols = ZERO;

        initializeMaze();
        generateMaze();
    }
}
