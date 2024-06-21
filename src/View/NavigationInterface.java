package View;

import Model.Room;

/**
 * NavigationInterface represents an interface for displaying navigation option in the maze.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public interface NavigationInterface {
    /**
     * displayNavigationOptions displays only options that are valid for that specified room.
     *
     * @param theRoom represents the current room for which the navigation options are displayed.
     */
    void displayNavigationOptions(final Room theRoom);
}