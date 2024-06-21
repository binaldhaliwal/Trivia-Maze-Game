package View;
/**
 * UserInterface represents an interface with menu options.
 *
 * @author Binal Dhaliwal, Bhavneet Bhargava
 * @version 1.0
 */
public interface UserInterface {
    /**
     * showMessage displays message to user.
     */
    void showMessage();

    /**
     * displayTitle displays the title of the game.
     */
    void displayTitle();

    /**
     * about displays the information about the game.
     */
    void about();

    /**
     * fileMenu displays the file menu option.
     */
    void fileMenu();

    /**
     * saveGame saves the current game state.
     */
    void saveGame();

    /**
     * loadGame loads a saved game state.
     */
    void loadGame();

    /**
     * exitGame exists the game.
     */
    void exitGame();

    /**
     * helpMenu displays the help menu options.
     */
    void helpMenu();

    /**
     * instructions displays instructions on how to play the game.
     */
    void instructions();

    /**
     * newGame for a new game to start.
     */
    void newGame();
}


