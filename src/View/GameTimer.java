package View;

import javax.swing.*;

/**
 * Represents a game timer that tracks the duration of a game.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class GameTimer {
    /**
     * Represents the duration of the game in seconds.
     */
    private static int GAME_DURATION = 0;
    /**
     * The label that displays the game duration.
     */
    private static JLabel GAME_DURATION_LABEL;

    /**
     * Constructs a game timer with the given label.
     *
     * @param theLabel the label that displays the game duration.
     */
    public GameTimer(final JLabel theLabel) {
        GAME_DURATION_LABEL = theLabel;
        startTimer();
    }

    /**
     * Starts the timer that updates the game duration label.
     */
    private void startTimer() {
        final Timer myTimer = new Timer(1000, e -> {
            GAME_DURATION++;
            GAME_DURATION_LABEL.setText("Game Duration: " + GAME_DURATION + " seconds");
        });
        myTimer.start();
    }

    /**
     * Resets the game duration to zero.
     */
    public static void resetTimer() {
        GAME_DURATION = 0;
        GAME_DURATION_LABEL.setText("Game Duration: " + GAME_DURATION + " seconds");
    }

}
