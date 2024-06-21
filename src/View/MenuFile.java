package View;

import Model.GameState;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * A MenuFile class that implements UserInterface and all the methods required.
 * Represents the menu operations for the Trivia Maze Game.
 *
 * @author Anagha Krishna, Binal Dhaliwal
 * @version 1.0
 */
public class MenuFile extends Component implements UserInterface {
    /**
     * The logger for logging messages related to the MenuFile class.
     */
    private static final Logger LOGGER = Logger.getLogger(MenuFile.class.getName());

    /**
     * The View class that is associated with this menu file.
     */
    private final View myView;
    /**
     * Constructs a MenuFile object with the specified myView.
     *
     * @param theView the View class to associate with this menu file.
     */
    public MenuFile(final View theView) {
        myView = theView;
    }
    /**
     * Displays a welcome message to the user.
     */
    @Override
    public final void showMessage() {
        JOptionPane.showMessageDialog(this,
                "Welcome to the Trivia Maze Game!\n\nNavigate through the maze by answering trivia questions.",
                "Trivia Maze Game",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the title screen with an image and scrolling effect.
     */
    @Override
    public final void displayTitle() {
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());

        java.net.URL imageUrl = getClass().getResource("/Resources/LoadingScreen.png");
        assert imageUrl != null;
        ImageIcon imageIcon = new ImageIcon(imageUrl);

        final Image image = imageIcon.getImage();
        final Image scaledImage = image.getScaledInstance(1100, 500, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        final JLabel imageLabel = new JLabel(imageIcon);
        titlePanel.add(imageLabel, BorderLayout.CENTER);

        myView.getContentPane().add(titlePanel, BorderLayout.CENTER);
        myView.pack();

        final int panelHeight = titlePanel.getHeight();

        final Timer timer = new Timer(12, e -> {
            final int y = titlePanel.getY() - 1;
            if (y + panelHeight < 0) {
                ((Timer) e.getSource()).stop();
                myView.getContentPane().remove(titlePanel);
                revalidate();
                repaint();
            } else {
                titlePanel.setLocation(titlePanel.getX(), y);
            }
        });

        timer.start();
    }

    /**
     * Displays the 'about dialog' with information about the game.
     */
    @Override
    public final void about() {
        final JDialog aboutDialog = new JDialog(myView, "About", true);
        aboutDialog.setLayout(new BorderLayout());

        final JTextArea aboutArea = new JTextArea();
        aboutArea.setEditable(false);
        aboutArea.setLineWrap(true);
        aboutArea.setWrapStyleWord(true);
        aboutArea.setText("""
                Welcome to the Trivia Maze Game!

                This game challenges you to navigate through a maze by answering questions.

                Each room in the maze contains a question, and you must answer it correctly to unlock the door and move to the next room.

                Be careful! Incorrect answers can lead to locked doors and dead ends.

                Explore the maze, answer questions, and find your way to the exit!""");

        aboutDialog.add(new JScrollPane(aboutArea), BorderLayout.CENTER);
        aboutDialog.pack();
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setVisible(true);
    }

    /**
     * Creates and displays the file menu with options for new game, save game,
     * load game, exit, and music controls.
     */
    @Override
    public final void fileMenu() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        final JMenuItem menuFileNewGame = new JMenuItem("New Game");
        menuFileNewGame.addActionListener(e -> newGame());
        menuFile.add(menuFileNewGame);

        final JMenuItem menuFileItemSaveGame = new JMenuItem("Save Game");
        menuFileItemSaveGame.addActionListener(e -> saveGame());
        menuFile.add(menuFileItemSaveGame);

        final JMenuItem menuFileItemLoadGame = new JMenuItem("Load Game");
        menuFileItemLoadGame.addActionListener(e -> loadGame());
        menuFile.add(menuFileItemLoadGame);

        final JMenuItem menuFileItemExit = new JMenuItem("Exit");
        menuFileItemExit.addActionListener(e -> exitGame());
        menuFile.add(menuFileItemExit);

        final JMenuItem menuFileItemMute = new JMenuItem("Mute Music");
        menuFileItemMute.addActionListener(e -> MusicController.muteMusic());
        menuFile.add(menuFileItemMute);

        final JMenuItem menuFileItemUnmute = new JMenuItem("Unmute Music");
        menuFileItemUnmute.addActionListener(e -> MusicController.unmuteMusic());
        menuFile.add(menuFileItemUnmute);

        final JMenu menuHelp = new JMenu("Help");
        menuHelp.addActionListener(e -> helpMenu());
        menuBar.add(menuHelp);

        final JMenuItem menuHelpItemAbout = new JMenuItem("About");
        menuHelpItemAbout.addActionListener(e -> about());
        menuHelp.add(menuHelpItemAbout);

        final JMenuItem menuHelpItemInstructions = new JMenuItem("Instructions");
        menuHelpItemInstructions.addActionListener(e -> instructions());
        menuHelp.add(menuHelpItemInstructions);

        myView.setJMenuBar(menuBar);
        displayTitle();
        setVisible(true);
    }

    /**
     * Saves the current game state to a file.
     */
    @Override
    public final void saveGame() {
        final int option = JOptionPane.showConfirmDialog(this, "Do you want to save the game?",
                "Save Game", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try (FileOutputStream fileOut = new FileOutputStream("gameState.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(myView.getGameState());
                JOptionPane.showMessageDialog(this, "Game saved successfully.",
                        "Save Game", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error saving the game", e);
                JOptionPane.showMessageDialog(this, "Error saving the game: " + e.getMessage(),
                        "Save Game", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads the game state from a file.
     */
    @Override
    public final void loadGame() {
        final int option = JOptionPane.showConfirmDialog(this, "Do you want to load the game?",
                "Load Game", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try (FileInputStream fileIn = new FileInputStream("gameState.ser");
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                GameState loadedGameState = (GameState) in.readObject();
                myView.getGameState().updateState(loadedGameState);
                JOptionPane.showMessageDialog(this, "Game loaded successfully.",
                        "Load Game", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading the game", e);
                JOptionPane.showMessageDialog(this, "Error loading the game: " + e.getMessage(),
                        "Load Game", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Exits the game after confirming with the user.
     */
    @Override
    public final void exitGame() {
        final int exit = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the game?", "Exit Game", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            MusicController.musicStop();
            System.exit(0);
        }
    }

    /**
     * Displays the help menu with options for about and instructions.
     */
    @Override
    public final void helpMenu() {
        final JDialog help = new JDialog(myView, "Help Menu", true);
        help.setLayout(new GridLayout(2, 1, 10, 10));
        help.setPreferredSize(new Dimension(300, 100));

        final JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(e -> about());

        final JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> instructions());

        help.add(aboutButton);
        help.add(instructionsButton);

        help.pack();
        help.setLocationRelativeTo(this);
        help.setVisible(true);
    }

    /**
     * Displays the game 'instructions dialog'.
     */
    @Override
    public final void instructions() {
        final JDialog instructions = new JDialog(myView, "Game Instructions", true);
        instructions.setLayout(new BorderLayout());

        final JTextArea instructionsArea = new JTextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setText("""
                Game Play Instructions:

                1. Enter the maze and navigate through each room.
                2. To pass through each door, answer one of the three possible questions:
                   - Multiple Choice
                   - True/False
                   - Short Answer (one or two words/numbers)
                3. If you answer correctly, the door will be unlocked.
                4. If you answer incorrectly, the door will be permanently locked, and the game ends.
                5. Your goal is to reach the exit of the maze.""");

        instructions.add(new JScrollPane(instructionsArea), BorderLayout.CENTER);
        instructions.pack();
        instructions.setLocationRelativeTo(this);
        instructions.setVisible(true);
    }

    /**
     * Starts a new game after confirming with the user.
     */
    @Override
    public final void newGame() {
        final int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to start a new game?", "New Game Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            myView.resetGame();
        }
    }
}
