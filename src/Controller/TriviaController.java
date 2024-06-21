package Controller;

import Model.*;
import View.View;

/**
 * The TriviaController class manages the interaction between the view and the model
 * in the Trivia Maze Game. Essentially it runs the game.
 *
 * @author Binal Dhaliwal, Anagha Krishna, Bhavneet Bhargava
 * @version 1.0
 */
public class TriviaController {

    /** The game model. */
    private final GameModel myGame;

    /**
     * Constructs a new TriviaController with the specified view.
     *
     * @param theView the view to be managed by this controller.
     */
    public TriviaController(final View theView) {
        myGame = new GameModel();
    }

    /**
     * The main method to launch the Trivia Maze Game.
     *
     * @param theArgs command-line arguments.
     */
    public static void main(final String[] theArgs) {
        final View view = new View();
        final TriviaController controller = new TriviaController(view);
        controller.startGame();
    }

    /**
     * Starts the game by initializing the game model.
     */
    public final void startGame() {
        myGame.startGame();
    }
}
