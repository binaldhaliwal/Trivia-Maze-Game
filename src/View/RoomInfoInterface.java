package View;

import Model.Room;

/**
 * RoomInfoInterface represents an interface displaying information about a room in a game.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public interface RoomInfoInterface {
    /**
     * displayRoomInfo displays information that are valid for a room.
     *
     * @param theRoom the room object for which information will be displayed.
     */
    void displayRoomInfo(final Room theRoom);
}

