package View;

import Model.*;
import Controller.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The MazePanel class represents the view for the maze game. It handles the drawing
 * of the maze, the player, and the exit, and manages player movement and game state.
 *
 * @author Anagha Krishna, Binal Dhaliwal, Bhavneet Bhargava
 * @version 1.0
 */
public class MazePanel extends JPanel {
    /**
     * The logger for logging messages related to the MazePanel class.
     */
    private static final Logger LOGGER = Logger.getLogger(MazePanel.class.getName());
    /**
     * The player object representing the player in the maze.
     */
    private Player myPlayer;
    /**
     * The game model containing the current state of the game.
     */
    private GameModel myGameModel;
    /**
     * The maze object representing the structure of the maze.
     */
    private Maze myMaze;
    /**
     * The image representing the player character.
     */
    private BufferedImage myPlayerImage;
    /**
     * The image representing the exit point in the maze.
     */
    private BufferedImage myEndImage;
    /**
     * The current room where the player is located in the maze.
     */
    private Room myCurrentRoom;
    /**
     * The listener for room change events in the maze.
     */
    private RoomChangeListener myRoomChangeListener;
    /**
     * Field for MazePaintComponent class.
     */
    private final MazePaintComponent myMazeComp;
    /**
     * Constructs a MazePanel with the specified player and game model.
     *
     * @param thePlayer The player object.
     * @param theGameModel The game model object.
     */
    public MazePanel(final Player thePlayer, final GameModel theGameModel) {
        myPlayer = thePlayer;
        myGameModel = theGameModel;
        myMaze = myGameModel.getMaze();
        myCurrentRoom = myGameModel.getPlayerLocation();
        myMazeComp = new MazePaintComponent();
        loadPlayerImage();
        loadExitImage();

        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * Sets the game model for the maze panel.
     * Assigns myPlayer with name, score, and maze.
     * Calls the notifyRoomChangeListener method to notify changes
     * to current room state.
     *
     * @param theGameModel The new game model.
     * @throws IllegalArgumentException if the game model is null.
     */
    public final void setGameModel(final GameModel theGameModel) {
        if (theGameModel == null) {
            throw new IllegalArgumentException("Game model cannot be null");
        }
        myGameModel = theGameModel;
        myMaze = myGameModel.getMaze();
        myPlayer = new Player("Player1", 0, myMaze);
        myCurrentRoom = myGameModel.getPlayerLocation();
        notifyRoomChangeListener(myCurrentRoom);
        repaint();
    }

    /**
     * Opens a door in the specified direction.
     * Updates the current room based on direction and whether it is locked or open.
     *
     * @param theDirection The direction of the door to open.
     */
    public final void openDoor(final Direction theDirection) {
        final Room currentRoom = myGameModel.getPlayerLocation();
        final int currentRow = myPlayer.getCurrentRow();
        final int currentCol = myPlayer.getCurrentCol();

        int newRow = currentRow;
        int newCol = currentCol;

        switch (theDirection) {
            case NORTH -> newRow = currentRow - 1;
            case SOUTH -> newRow = currentRow + 1;
            case EAST -> newCol = currentCol + 1;
            case WEST -> newCol = currentCol - 1;
        }

        if (newRow >= 0 && newRow < myMaze.myMaze.length && newCol >= 0 && newCol < myMaze.myMaze[0].length) {
            final Room otherRoom = myMaze.myMaze[newRow][newCol];
            final Door door = currentRoom.getDoor(theDirection);

            final Direction oppositeDirection = getOppositeDirection(theDirection);
            final Door otherDoor = otherRoom.getDoor(oppositeDirection);

            if (door != null && !door.permanentlyLocked()) {
                door.doorOpen();
                if (otherDoor != null && !otherDoor.permanentlyLocked()) {
                    otherDoor.doorOpen();
                }
                myGameModel.updateDoorState(currentRoom, theDirection, door.isLock());
                assert otherDoor != null;
                myGameModel.updateDoorState(otherRoom, oppositeDirection, otherDoor.isLock());

                myGameModel.setPlayerLocation(otherRoom);
                myPlayer.setCurrentLocation(newRow, newCol);
                updateCurrentRoom(otherRoom);

                notifyRoomChangeListener(otherRoom);
                repaint();
                if (otherRoom.getExit()) {
                    handleWin();
                } else {
                    handleLoss();
                }
            } else {
                System.out.println("This door is permanently locked and cannot be opened.");
            }
        } else {
            System.out.println("Cannot move in the given direction, out of maze bounds.");
        }
    }

    /**
     * Locks a door in the specified direction.
     * Updates the current room based on direction and whether it is locked or open.
     *
     * @param theDirection The direction of the door to lock.
     */
    public final void lockDoor(final Direction theDirection) {
        final Room currentRoom = myGameModel.getPlayerLocation();
        final int currentRow = myPlayer.getCurrentRow();
        final int currentCol = myPlayer.getCurrentCol();

        int newRow = currentRow;
        int newCol = currentCol;

        switch (theDirection) {
            case NORTH -> newRow = currentRow - 1;
            case SOUTH -> newRow = currentRow + 1;
            case EAST -> newCol = currentCol + 1;
            case WEST -> newCol = currentCol - 1;
        }

        if (newRow >= 0 && newRow < myMaze.myMaze.length && newCol >= 0 && newCol < myMaze.myMaze[0].length) {
            final Room otherRoom = myMaze.myMaze[newRow][newCol];
            final Door door = currentRoom.getDoor(theDirection);

            final Direction oppositeDirection = getOppositeDirection(theDirection);
            final Door otherDoor = otherRoom.getDoor(oppositeDirection);

            if (door != null) {
                door.setPermanentlyLocked(true);
                door.setLock(true);
                if (otherDoor != null) {
                    otherDoor.setPermanentlyLocked(true);
                    otherDoor.setLock(true);
                }
                myGameModel.updateDoorState(currentRoom, theDirection, door.isLock());
                assert otherDoor != null;
                myGameModel.updateDoorState(otherRoom, oppositeDirection, otherDoor.isLock());

                repaint();
                notifyRoomChangeListener(currentRoom);
                handleLoss();
            }
        } else {
            System.out.println("Cannot move in the given direction, out of maze bounds.");
        }
    }

    /**
     * Handles the win scenario by displaying a victory message.
     */
    private void handleWin() {
        try {
            MusicController.playWinSound();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling win sound", e);
        }

        JOptionPane.showMessageDialog(this,
                "Congratulations! You have reached the exit and won the game.", "Victory", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the loss scenario by displaying a game over message if there is no possible path to the exit.
     */
    private void handleLoss() {
        if (noPossiblePathToExit()) {
            try {
                MusicController.playLossSound();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error handling loss sound", e);
            }

            JOptionPane.showMessageDialog(this,
                    "Game Over! There is no possible path to the exit. Press 'New Game' to start again.",
                    "End of Maze", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Checks if there is no possible path to the exit.
     *
     * @return true if there is no possible path to the exit, false otherwise.
     */
    private boolean noPossiblePathToExit() {
        final boolean[][] visited = new boolean[myMaze.getRows()][myMaze.getCols()];
        return !canReachExit(myPlayer.getCurrentRow(), myPlayer.getCurrentCol(), visited);
    }

    /**
     * Recursively checks if the exit can be reached from the specified position.
     *
     * @param theRow The row of the current position.
     * @param theCol The column of the current position.
     * @param theVisited A 2D array tracking visited positions.
     * @return true if the exit can be reached, false otherwise.
     */
    private boolean canReachExit(final int theRow, final int theCol, final boolean[][] theVisited) {
        if (theRow < 0 || theRow >= myMaze.getRows() || theCol < 0 || theCol >= myMaze.getCols()) {
            return false;
        }
        if (theVisited[theRow][theCol]) {
            return false;
        }
        theVisited[theRow][theCol] = true;

        final Room room = myMaze.myMaze[theRow][theCol];
        if (room.getExit()) {
            return true;
        }

        for (Direction direction : Direction.values()) {
            Door door = room.getDoor(direction);
            if (door != null && !door.permanentlyLocked()) {
                int newRow = theRow;
                int newCol = theCol;

                switch (direction) {
                    case NORTH -> newRow = theRow - 1;
                    case SOUTH -> newRow = theRow + 1;
                    case EAST -> newCol = theCol + 1;
                    case WEST -> newCol = theCol - 1;
                }

                if (canReachExit(newRow, newCol, theVisited)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Handles key events to move the player.
     *
     * @param theE The key event.
     */
    public final void handleKeyEvent(final KeyEvent theE) {
        final Direction direction = getDirectionFromKeyEvent(theE);
        if (direction != null) {
            final Room currentRoom = myMaze.myMaze[myPlayer.getCurrentRow()][myPlayer.getCurrentCol()];
            final Door door = currentRoom.getDoor(direction);

            if (door != null && !door.isClosed() && !door.permanentlyLocked()) {
                movePlayer(direction);
            } else {
                System.out.println("Cannot move in the given direction, door is closed or locked.");
            }

            repaint();
        }
    }

    /**
     * Returns the opposite direction of the given direction.
     *
     * @param theDirection The original direction.
     * @return The opposite direction.
     */
    private Direction getOppositeDirection(final Direction theDirection) {
        return switch (theDirection) {
            case NORTH -> Direction.SOUTH;
            case SOUTH -> Direction.NORTH;
            case EAST -> Direction.WEST;
            case WEST -> Direction.EAST;
        };
    }

    /**
     * Gets the direction from the key event.
     *
     * @param theE The key event.
     * @return The direction, or null if no valid direction is found.
     */
    private Direction getDirectionFromKeyEvent(final KeyEvent theE) {
        return switch (theE.getKeyCode()) {
            case KeyEvent.VK_UP -> Direction.NORTH;
            case KeyEvent.VK_DOWN -> Direction.SOUTH;
            case KeyEvent.VK_LEFT -> Direction.WEST;
            case KeyEvent.VK_RIGHT -> Direction.EAST;
            default -> null;
        };
    }

    /**
     * Moves the player in the specified direction.
     * Checks to see if the north door is closed or locked. If it so not,
     * the player moves in that direction.
     * Updates the current room and notifies changes.
     *
     * @param theDirection The direction to move the player.
     */
    private void movePlayer(final Direction theDirection) {
        switch (theDirection) {
            case NORTH -> {
                if (myPlayer.getCurrentRow() > 0) {
                    final Room currentRoom = myMaze.myMaze[myPlayer.getCurrentRow()][myPlayer.getCurrentCol()];
                    final Door northDoor = currentRoom.getDoor(Direction.NORTH);
                    if (!northDoor.isClosed() && !northDoor.permanentlyLocked()) {
                        myPlayer.moveNorth();
                    }
                }
            }
            case SOUTH -> {
                if (myPlayer.getCurrentRow() < myMaze.getRows() - 1) {
                    final Room currentRoom = myMaze.myMaze[myPlayer.getCurrentRow()][myPlayer.getCurrentCol()];
                    final Door southDoor = currentRoom.getDoor(Direction.SOUTH);
                    if (!southDoor.isClosed() && !southDoor.permanentlyLocked()) {
                        myPlayer.moveSouth();
                    }
                }
            }
            case WEST -> {
                if (myPlayer.getCurrentCol() > 0) {
                    final Room currentRoom = myMaze.myMaze[myPlayer.getCurrentRow()][myPlayer.getCurrentCol()];
                    final Door westDoor = currentRoom.getDoor(Direction.WEST);
                    if (!westDoor.isClosed() && !westDoor.permanentlyLocked()) {
                        myPlayer.moveWest();
                    }
                }
            }
            case EAST -> {
                if (myPlayer.getCurrentCol() < myMaze.getCols() - 1) {
                    final Room currentRoom = myMaze.myMaze[myPlayer.getCurrentRow()][myPlayer.getCurrentCol()];
                    final Door eastDoor = currentRoom.getDoor(Direction.EAST);
                    if (!eastDoor.isClosed() && !eastDoor.permanentlyLocked()) {
                        myPlayer.moveEast();
                    }
                }
            }
        }
        final Room newRoom = myMaze.myMaze[myPlayer.getCurrentRow()][myPlayer.getCurrentCol()];
        myGameModel.setPlayerLocation(newRoom);
        updateCurrentRoom(newRoom);
        notifyRoomChangeListener(newRoom);
        if (newRoom.getExit()) {
            handleWin();
        } else {
            handleLoss();
        }
    }

    /**
     * Updates the current room.
     *
     * @param theRoom The new current room.
     */
    public final void updateCurrentRoom(final Room theRoom) {
        myCurrentRoom = theRoom;
    }

    /**
     * Notifies the room change listener about the room change.
     *
     * @param theRoom The new current room.
     */
    private void notifyRoomChangeListener(final Room theRoom) {
        if (myRoomChangeListener != null) {
            myRoomChangeListener.onRoomChange(theRoom);
        }
    }

    /**
     * Sets the room change listener.
     *
     * @param theListener The room change listener.
     */
    public final void setRoomChangeListener(final RoomChangeListener theListener) {
        myRoomChangeListener = theListener;
    }

    /**
     * Paints the component, drawing the current room, player, and exit.
     * Draws unlocked, locked, and closed doors.
     *
     * @param theG The graphics object.
     */
    @Override
    protected final void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        final Graphics2D g2d = (Graphics2D) theG;

        final int panelWidth = getWidth();
        final int panelHeight = getHeight();

        myMazeComp.paintComponent(g2d, myMaze, myPlayerImage, myEndImage, myPlayer, panelWidth, panelHeight);
    }

    /**
     * Loads the image for the player character.
     */
    private void loadPlayerImage() {
        try {
            myPlayerImage = ImageIO.read(new File("src/Resources/player.png"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading player image", e);
        }
    }

    /**
     * Loads the image for the maze exit.
     */
    private void loadExitImage() {
        try {
            myEndImage = ImageIO.read(new File("src/Resources/Exit.png"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading exit image", e);
        }
    }

    /**
     * Sets the player object for the maze panel.
     *
     * @param thePlayer The player object to set.
     */
    public final void setPlayer(final Player thePlayer) {
        myPlayer = thePlayer;
    }

    /**
     * Resets the state of all doors in the maze.
     */
    public final void resetDoorsState() {
        for (int i = 0; i < myMaze.getRows(); i++) {
            for (int j = 0; j < myMaze.getCols(); j++) {
                Room room = myMaze.myMaze[i][j];
                if (room != null) {
                    for (Door door : room.getDoors()) {
                        door.reset();
                    }
                }
            }
        }
        repaint();
    }

    /**
     * Sets whether the player can move in the maze.
     *
     * @param theCanPlayerMove True if the player can move, false otherwise.
     */
    public final void setCanPlayerMove(final boolean theCanPlayerMove) {
    }

    /**
     * The interface for listening to room changes.
     */
    public interface RoomChangeListener {
        /**
         * Called when the room changes.
         *
         * @param theNewRoom The new current room.
         */
        void onRoomChange(final Room theNewRoom);
    }
}

