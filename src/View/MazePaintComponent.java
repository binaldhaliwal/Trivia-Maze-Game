package View;

import Controller.Player;
import Model.Direction;
import Model.Door;
import Model.Maze;
import Model.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The MazePaintComponent class is responsible for drawing the maze, the player, and the exit.
 *
 * @author Anagha Krishna, Binal Dhaliwal, Bhavneet Bhargava
 * @version 1.0
 */
public class MazePaintComponent {

    /**
     * The paintComponent method draws the maze, the player, doors, rooms and the exit.
     *
     * @param theG the Graphics object used for drawing.
     * @param theMaze the maze object representing the structure of the maze.
     * @param thePlayerImage the image representing the player character.
     * @param theEndImage the image representing the exit point in the maze.
     * @param thePlayer the player object representing the player in the maze.
     * @param thePanelWidth the width of the panel.
     * @param thePanelHeight the height of the panel.
     */
    protected final void paintComponent(final Graphics2D theG, final Maze theMaze,
                                        final BufferedImage thePlayerImage,
                                        final BufferedImage theEndImage,
                                        final Player thePlayer, final int thePanelWidth,
                                        final int thePanelHeight) {
        if (theMaze == null || thePlayerImage == null || theEndImage == null) {
            return;
        }

        final int rows = theMaze.getRows();
        final int cols = theMaze.getCols();

        final int cellSize = Math.min(thePanelWidth / cols, thePanelHeight / rows);

        final int mazeWidth = cols * cellSize;
        final int mazeHeight = rows * cellSize;

        final double scalingFactor = 0.8;
        final double iconScalingFactor = 0.9;

        final int scaledMazeWidth = (int) (mazeWidth * scalingFactor);
        final int scaledMazeHeight = (int) (mazeHeight * scalingFactor);

        final int offsetX = (thePanelWidth - scaledMazeWidth) / 2;
        final int offsetY = (thePanelHeight - scaledMazeHeight) / 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final Room room = theMaze.myMaze[i][j];

                if (room != null) {
                    final int x = offsetX + (int) (j * cellSize * scalingFactor);
                    final int y = offsetY + (int) (i * cellSize * scalingFactor);
                    final int scaledCellSize = (int) (cellSize * scalingFactor);

                    if (room.getStart()) {
                        final int scaledBoxSize = (int) (scaledCellSize * 0.8);
                        theG.setColor(Color.BLUE);
                        theG.fillRect(x + (scaledCellSize - scaledBoxSize) / 2, y + (scaledCellSize - scaledBoxSize) / 2, scaledBoxSize, scaledBoxSize);
                    } else if (room.getExit()) {
                        final int scaledIconSize = (int) (cellSize * scalingFactor * iconScalingFactor);
                        theG.drawImage(theEndImage, x, y, scaledIconSize, scaledIconSize, null);
                    }

                    theG.setColor(Color.BLACK);

                    for (Door door : room.getDoors()) {
                        if (door.permanentlyLocked()) {
                            drawLockedDoor(theG, x, y, scaledCellSize, door.getDirection());
                        } else if (door.isClosed()) {
                            drawClosedDoor(theG, x, y, scaledCellSize, door.getDirection());
                        } else {
                            drawUnlockedDoor(theG, x, y, scaledCellSize, door.getDirection());
                        }
                    }
                }
            }
        }

        if (thePlayer != null) {
            final int playerX = offsetX + (int) (thePlayer.getCurrentCol() * cellSize * scalingFactor);
            final int playerY = offsetY + (int) (thePlayer.getCurrentRow() * cellSize * scalingFactor);
            final int scaledIconSize = (int) (cellSize * scalingFactor * iconScalingFactor);
            theG.drawImage(thePlayerImage, playerX, playerY, scaledIconSize, scaledIconSize, null);
        }
    }

    /**
     * The drawUnlockedDoor method draws an closed door.
     *
     * @param theG the Graphics object used for drawing.
     * @param theX the x coordinate of the door.
     * @param theY the y coordinate of the door.
     * @param theCellSize the size of the cell.
     * @param theDirection the direction of the door.
     */
    private void drawClosedDoor(final Graphics2D theG, final int theX, final int theY, final int theCellSize, final Direction theDirection) {
        theG.setColor(Color.WHITE);
        drawingDoors(theG, theX, theY, theCellSize, theDirection);
    }

    /**
     * The drawUnlockedDoor method draws an unlocked door.
     *
     * @param theG the Graphics object used for drawing.
     * @param theX the x coordinate of the door.
     * @param theY the y coordinate of the door.
     * @param theCellSize the size of the cell.
     * @param theDirection the direction of the door.
     */
    private void drawLockedDoor(final Graphics2D theG, final int theX, final int theY, final int theCellSize, final Direction theDirection) {
        theG.setColor(new Color(0, 0, 139));
        drawingDoors(theG, theX, theY, theCellSize, theDirection);
    }

    /**
     * The drawingDoors method draws the doors.
     *
     * @param theG the Graphics object used for drawing.
     * @param theX the x coordinate of the door.
     * @param theY the y coordinate of the door.
     * @param theCellSize the size of the cell.
     * @param theDirection the direction of the door.
     */
    private void drawingDoors(final Graphics2D theG, final int theX, final int theY, final int theCellSize, final Direction theDirection) {
        switch (theDirection) {
            case NORTH -> theG.drawLine(theX, theY, theX + theCellSize, theY);
            case SOUTH -> theG.drawLine(theX, theY + theCellSize, theX + theCellSize, theY + theCellSize);
            case EAST -> theG.drawLine(theX + theCellSize, theY, theX + theCellSize, theY + theCellSize);
            case WEST -> theG.drawLine(theX, theY, theX, theY + theCellSize);
        }
    }

    /**
     * The drawUnlockedDoor method draws an unlocked door.
     *
     * @param theG the Graphics object used for drawing.
     * @param theX the x coordinate of the door.
     * @param theY the y coordinate of the door.
     * @param theCellSize the size of the cell.
     * @param theDirection the direction of the door.
     */
    private void drawUnlockedDoor(final Graphics2D theG, final int theX, final int theY, final int theCellSize, final Direction theDirection) {
        theG.setColor(new Color(255, 153, 204));
        drawingDoors(theG, theX, theY, theCellSize, theDirection);
    }

}


