package View;

import Model.Direction;
import Model.Door;
import Model.Room;
import javax.swing.*;
import java.awt.*;

/**
 * The RoomInfo class is responsible for displaying information
 * about a room in the game.
 * <p>
 * This class provides a textual representation of the room's doors
 * and their states, as well as indicating if the room is the start
 * or exit of the maze.
 *
 * @author Anagha Krishna, Bhavneet Bhargava
 * @version 1.0
 */
public class RoomInfo implements RoomInfoInterface {
    private final JTextArea myRoomInfoTextArea;

    /**
     * Constructs a RoomInfo object and calls to initialize the text area.
     *
     * @param theView The View object associated with this RoomInfo.
     * @param theRoomInfoTextArea The JTextArea used for displaying room information.
     */
    public RoomInfo(final View theView, final JTextArea theRoomInfoTextArea) {
       myRoomInfoTextArea = theRoomInfoTextArea;
        initializeTextArea();
    }

    /**
     * Initializes the JTextArea used for displaying room information.
     */
    private void initializeTextArea() {
        myRoomInfoTextArea.setBackground(new Color(255, 204, 153));
        myRoomInfoTextArea.setEditable(false);
        myRoomInfoTextArea.setLineWrap(true);
        myRoomInfoTextArea.setWrapStyleWord(true);
        myRoomInfoTextArea.setPreferredSize(new Dimension(200, 150));
        myRoomInfoTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Displays information about the specified room.
     * Shows whether the doors in the current room are permanently locked,
     * closed, or open.
     *
     * @param theRoom The room for which to display information.
     */
    @Override
    public final void displayRoomInfo(final Room theRoom) {
        if (theRoom != null) {
            final StringBuilder roomInfoText = new StringBuilder();
            roomInfoText.append("\n");
            roomInfoText.append(" Room Information:\n\n");
            roomInfoText.append(" Doors:\n\n");

            for (Direction direction : Direction.values()) {
                final Door door = theRoom.getDoor(direction);
                if (door != null) {
                    roomInfoText.append(direction).append(": ");
                    if (door.permanentlyLocked()) {
                        roomInfoText.append(" Permanently Locked\n\n");
                    } else if (door.isClosed()) {
                        roomInfoText.append(" Closed\n\n");
                    } else {
                        roomInfoText.append(" Open\n\n");
                    }
                } else {
                    roomInfoText.append(direction).append(": No door\n\n");
                }
            }

            if (theRoom.getExit()) {
                roomInfoText.append("This room is the Exit.\n\n");
            }
            if (theRoom.getStart()) {
                roomInfoText.append("This room is the Start.\n\n");
            }
            final Font customFont = new Font("Arial", Font.PLAIN, 14);
            myRoomInfoTextArea.setFont(customFont);
            myRoomInfoTextArea.setText(roomInfoText.toString());
        } else {
            System.out.println("Room object is null. Cannot display room information.");
        }
    }
}
