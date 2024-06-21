package View;

import Controller.Player;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Font;

/**
 * This class represents the graphical user interface for the Trivia Maze Game.
 * It implements various interfaces for handling questions, navigation, and room changes.
 *
 * @author Binal Dhaliwal, Anagha Krishna, Bhavneet Bhargava
 * @version 1.0
 */
public class View extends JFrame implements QuestionInterface, NavigationInterface, MazePanel.RoomChangeListener {

    /** The current room the player is in. */
    private Room myCurrentRoom;

    /** The index of the current question being displayed. */
    private int myCurrentQuestionIndex = 0;

    /** The text area displaying the question. */
    private JTextArea myQuestionTextArea;

    /** Flag to indicate if the next button has been added. */
    private boolean myNextButtonAdded = false;

    /** Panel containing the question components. */
    private final JPanel myQuestionPanel;

    /** Panel containing the question text area. */
    private final JPanel myQuestionTextPanel;

    /** Panel displaying the maze. */
    private final MazePanel myGamePanel;

    /** Panel for navigation options. */
    private final JPanel myGreenBoxPanel;

    /** The current player. */
    private Player myCurrentPlayer;

    /** Radio buttons for multiple-choice options. */
    private JRadioButton[] myMultipleChoiceOptions;

    /** Radio button for True option in True/False questions. */
    private JRadioButton myTrueOption;

    /** Radio button for False option in True/False questions. */
    private JRadioButton myFalseOption;

    /** Text field for short answer input. */
    private JTextField myShortAnswerField;

    /** Button to submit the short answer. */
    private JButton mySubmitButton;

    /** The current multiple-choice question being displayed. */
    private MultipleChoiceQuestion myCurrentMcQuestion;

    /** The current short answer question being displayed. */
    private ShortAnswerQuestion myCurrentSaQuestion;

    /** Panel for short answer input. */
    private JPanel myShortAnswerPanel;

    /** List of questions. */
    private List<Question> myQuestions;

    /** The direction the player is attempting to move. */
    private Direction myAttemptedDirection;

    /** The game model. */
    private GameModel myGameModel;

    /** Component displaying room information. */
    private final RoomInfo myRoomInfo;

    /** The game state. */
    private final GameState myGameState;

    /**
     * Constructs a new View instance and initializes the GUI components.
     */
    public View() {
        MenuFile myMenuFile = new MenuFile(this);
        myMenuFile.fileMenu();
        setTitle("Trivia Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        add(contentPanel, BorderLayout.CENTER);

        myGameModel = new GameModel();
        myGameState = new GameState();

        if (myGameModel.getMaze().getCurrentRoom() == null) {
            myCurrentRoom = new Room(null, null, null, null);
        } else {
            myCurrentRoom = myGameModel.getMaze().getCurrentRoom();
        }

        myCurrentPlayer = new Player("Player1", 0, myGameModel.getMaze());

        JTextArea roomInfoTextArea = new JTextArea();
        roomInfoTextArea.setBackground(new Color(255, 204, 153));
        roomInfoTextArea.setEditable(false);
        roomInfoTextArea.setLineWrap(true);
        roomInfoTextArea.setWrapStyleWord(true);
        roomInfoTextArea.setPreferredSize(new Dimension(200, 150));
        roomInfoTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPanel.add(roomInfoTextArea);
        myRoomInfo = new RoomInfo(this, roomInfoTextArea);

        myGamePanel = new MazePanel(myCurrentPlayer, myGameModel);
        myGamePanel.setRoomChangeListener(this);
        myGamePanel.setBackground(new Color(64, 224, 208));
        myGamePanel.setPreferredSize(new Dimension(350, 500));
        myGamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPanel.add(myGamePanel);

        myQuestionPanel = new JPanel();
        myQuestionPanel.setPreferredSize(new Dimension(350, 500));
        myQuestionPanel.setLayout(new BoxLayout(myQuestionPanel, BoxLayout.Y_AXIS));
        myQuestionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        myQuestionPanel.setBackground(new Color(255, 153, 51));

        myQuestionTextPanel = new JPanel();
        myQuestionTextPanel.setLayout(new BoxLayout(myQuestionTextPanel, BoxLayout.Y_AXIS));
        myQuestionTextPanel.setBackground(new Color(255, 153, 51));
        myQuestionPanel.add(myQuestionTextPanel);

        myGreenBoxPanel = new JPanel();
        myGreenBoxPanel.setBackground(new Color(255, 204, 153));
        myGreenBoxPanel.setPreferredSize(new Dimension(350, 300));
        myGreenBoxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        myQuestionPanel.add(myGreenBoxPanel);

        JLabel myGameDurationLabel = new JLabel("Game Duration: 0 seconds");
        myGameDurationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        myGameDurationLabel.setForeground(new Color(237, 201, 175));
        myGameDurationLabel.setForeground(new Color(25, 25, 112));
        myGamePanel.add(myGameDurationLabel, BorderLayout.NORTH);

        GameTimer myGameTimer = new GameTimer(myGameDurationLabel);

        contentPanel.add(myQuestionPanel);

        InfoPanel infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension(200, 150));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPanel.add(infoPanel);

        setLocationRelativeTo(null);
        MusicController.playMusic();

        questionShow();

        SwingUtilities.invokeLater(myMenuFile::showMessage);
        displayNavigationOptions(myCurrentRoom);
        updateRoomInfo(myCurrentRoom);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key Pressed: " + e.getKeyCode());
                myGamePanel.handleKeyEvent(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }
    /**
     * Gets the current game state.
     *
     * @return the current game state.
     */
    public final GameState getGameState() {
        return myGameState;
    }
    /**
     * Resets the game by reinitializing the game model and player,
     * resetting the doors state, and updating the display.
     */
    public final void resetGame() {
        myGameModel = new GameModel();
        myCurrentRoom = myGameModel.getMaze().getCurrentRoom();
        myCurrentPlayer = new Player("Player1", 0, myGameModel.getMaze());
        myGamePanel.setPlayer(myCurrentPlayer);
        myGamePanel.setGameModel(myGameModel);
        myGamePanel.resetDoorsState();
        myGamePanel.repaint();
        myNextButtonAdded = false;
        myCurrentQuestionIndex = 0;

        clearInputComponents();
        GameTimer.resetTimer();
        questionShow();

        myQuestionPanel.revalidate();
        myQuestionPanel.repaint();

        updateRoomInfo(myCurrentRoom);
        displayNavigationOptions(myCurrentRoom);
    }

    /**
     * Displays a question from the shuffled list of questions.
     */
    private void questionShow() {
        SQLQuestionDataBase.initializeDatabase();
        final List<String> questionTypes = Arrays.asList("Multiple-Choice", "True/False", "Short Answer");
        final List<Question> questions = QuestionFactory.getQuestionsFromDatabase(questionTypes);
        myQuestions = new ArrayList<>(questions);
        Collections.shuffle(myQuestions);

        if (!questions.isEmpty()) {
            myCurrentQuestionIndex = 0;
            displayQuestion(myQuestions.get(myCurrentQuestionIndex));
        } else {
            JOptionPane.showMessageDialog(this, "No questions found.");
        }

        if (!myNextButtonAdded) {
            myQuestionPanel.setBackground(new Color(255, 153, 51));
            myNextButtonAdded = true;
        }

        revalidate();
        repaint();
    }

    /**
     * Displays the given question.
     *
     * @param theQuestion the question to display.
     */
    @Override
    public final void displayQuestion(final Question theQuestion) {
        if (myQuestionTextArea == null) {
            myQuestionTextArea = new JTextArea();
            myQuestionTextArea.setEditable(false);
            myQuestionTextArea.setLineWrap(true);
            myQuestionTextArea.setWrapStyleWord(true);
            myQuestionTextArea.setPreferredSize(new Dimension(400, 100));
            myQuestionTextArea.setBackground(new Color(255, 153, 51));
            myQuestionTextPanel.add(myQuestionTextArea);
        }

        clearInputComponents();

        myQuestionTextArea.setText("Question:\n" + theQuestion.getQuestionText());

        if (theQuestion instanceof MultipleChoiceQuestion mcQuestion) {
            displayMultipleChoiceOptions(mcQuestion);
        } else if (theQuestion instanceof TrueFalseQuestion tfQuestion) {
            displayTrueFalseOptions(tfQuestion);
        } else if (theQuestion instanceof ShortAnswerQuestion shortAnswerQuestion) {
            displayShortAnswerInput(shortAnswerQuestion);
        }

        disableMultipleChoiceOptions();
        disableTrueFalseOptions();
        disableShortAnswerInput();

        revalidate();
        repaint();
    }

    /**
     * Clears the input components from the question panel.
     */
    private void clearInputComponents() {
        if (myMultipleChoiceOptions != null) {
            for (JRadioButton option : myMultipleChoiceOptions) {
                myQuestionTextPanel.remove(option);
                option.setEnabled(false);
            }
           myMultipleChoiceOptions = null;
        }
        if (myTrueOption != null) {
            myQuestionTextPanel.remove(myTrueOption);
            myTrueOption.setEnabled(false);
            myQuestionTextPanel.remove(myFalseOption);
            myFalseOption.setEnabled(false);
            myTrueOption = null;
            myFalseOption = null;
        }
        if (myShortAnswerField != null) {
            myQuestionTextPanel.remove(myShortAnswerField);
            myShortAnswerField.setEnabled(false);
            myShortAnswerField = null;
        }
        if (myShortAnswerPanel != null) {
            myQuestionTextPanel.remove(myShortAnswerPanel);
            myShortAnswerPanel.removeAll();
            myShortAnswerPanel = null;
        }
        myQuestionTextPanel.revalidate();
        myQuestionTextPanel.repaint();
    }

    /**
     * Displays multiple-choice options for the given question.
     *
     * @param theMcQuestion the multiple-choice question.
     */
    private void displayMultipleChoiceOptions(final MultipleChoiceQuestion theMcQuestion) {
        myCurrentMcQuestion = theMcQuestion;

        if (myMultipleChoiceOptions != null) {
            for (JRadioButton option : myMultipleChoiceOptions) {
                myQuestionTextPanel.remove(option);
            }
            myMultipleChoiceOptions = null;
        }

        final String[] choices = theMcQuestion.getChoices();
        if (choices != null && choices.length > 0) {
            final ButtonGroup optionsGroup = new ButtonGroup();
            myMultipleChoiceOptions = new JRadioButton[choices.length];
            for (int i = 0; i < choices.length; i++) {
                myMultipleChoiceOptions[i] = new JRadioButton(choices[i]);
                optionsGroup.add(myMultipleChoiceOptions[i]);
                myQuestionTextPanel.add(myMultipleChoiceOptions[i]);

                final int finalI = i;
                myMultipleChoiceOptions[i].addActionListener(e -> checkMultipleChoiceAnswer(myMultipleChoiceOptions[finalI].getText()));
            }
        }

        revalidate();
        repaint();
    }

    /**
     * Disables multiple-choice options.
     */
    private void disableMultipleChoiceOptions() {
        if (myMultipleChoiceOptions != null) {
            for (JRadioButton option : myMultipleChoiceOptions) {
                option.setEnabled(false);
            }
        }
    }

    /**
     * Enables multiple-choice options.
     */
    private void enableMultipleChoiceOptions() {
        if (myMultipleChoiceOptions != null) {
            for (JRadioButton option : myMultipleChoiceOptions) {
                option.setEnabled(true);
            }
        }
    }

    /**
     * Checks the selected answer for multiple-choice questions.
     *
     * @param theSelectedAnswer the selected answer.
     */
    private void checkMultipleChoiceAnswer(final String theSelectedAnswer) {
        if (myCurrentMcQuestion == null) {
            return;
        }

        final String correctAnswer = myCurrentMcQuestion.getAnswerText();

        if (theSelectedAnswer.equalsIgnoreCase(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct!");
            myGamePanel.openDoor(myAttemptedDirection);
            displayNavigationOptions(myCurrentRoom);
            disableMultipleChoiceOptions();
            moveToNextQuestion();
            myGamePanel.setCanPlayerMove(true);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is " + correctAnswer + ".");
            myGamePanel.lockDoor(myAttemptedDirection);
            moveToNextQuestion();
        }
    }

    /**
     * Displays True/False options for the given question.
     *
     * @param theTfQuestion the True/False question.
     */
    private void displayTrueFalseOptions(final TrueFalseQuestion theTfQuestion) {
        myTrueOption = new JRadioButton("True");
        myFalseOption = new JRadioButton("False");
        ButtonGroup optionsGroup = new ButtonGroup();
        optionsGroup.add(myTrueOption);
        optionsGroup.add(myFalseOption);
        myQuestionTextPanel.add(myTrueOption);
        myQuestionTextPanel.add(myFalseOption);

        myTrueOption.addActionListener(e -> checkAnswer(theTfQuestion, true));
        myFalseOption.addActionListener(e -> checkAnswer(theTfQuestion, false));
    }

    /**
     * Disables True/False options.
     */
    private void disableTrueFalseOptions() {
        if (myTrueOption != null && myFalseOption != null) {
            myTrueOption.setEnabled(false);
            myFalseOption.setEnabled(false);
        }
    }

    /**
     * Enables True/False options.
     */
    private void enableTrueFalseOptions() {
        if (myTrueOption != null && myFalseOption != null) {
            myTrueOption.setEnabled(true);
            myFalseOption.setEnabled(true);
        }
    }

    /**
     * Checks the selected answer for True/False questions.
     *
     * @param theTfQuestion the True/False question.
     * @param theSelectedAnswer the selected answer.
     */
    private void checkAnswer(final TrueFalseQuestion theTfQuestion, final boolean theSelectedAnswer) {
        final String correctAnswer = theTfQuestion.getAnswerText();
        final boolean isCorrect = (theSelectedAnswer && correctAnswer.equalsIgnoreCase("true")) ||
                (!theSelectedAnswer && correctAnswer.equalsIgnoreCase("false"));

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Correct!");
            myGamePanel.openDoor(myAttemptedDirection);
            displayNavigationOptions(myCurrentRoom);
            disableTrueFalseOptions();
            moveToNextQuestion();
            myGamePanel.setCanPlayerMove(true);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is " + correctAnswer + ".");
            myGamePanel.lockDoor(myAttemptedDirection);
            moveToNextQuestion();
        }
    }

    /**
     * Displays the input field for short answer questions.
     *
     * @param theShortAnswerQuestion the short answer question.
     */
    private void displayShortAnswerInput(final ShortAnswerQuestion theShortAnswerQuestion) {
        myCurrentSaQuestion = theShortAnswerQuestion;
        clearInputSA();

        myShortAnswerField = new JTextField(20);
        myShortAnswerField.setEnabled(true);
        final JLabel wordLimitLabel = new JLabel("Answer is limited to 2 words");
        mySubmitButton = new JButton("Submit");
        mySubmitButton.setEnabled(true);
        mySubmitButton.addActionListener(e -> checkShortAnswer());

        myShortAnswerPanel = new JPanel(new BorderLayout());

        final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(wordLimitLabel);
        topPanel.add(mySubmitButton);
        myShortAnswerPanel.add(topPanel, BorderLayout.NORTH);

        myShortAnswerPanel.add(myShortAnswerField, BorderLayout.CENTER);

        myQuestionTextPanel.add(myShortAnswerPanel, BorderLayout.SOUTH);

        myShortAnswerPanel.setVisible(true);

        revalidate();
        repaint();
    }

    /**
     * Clears the short answer input components.
     */
    private void clearInputSA() {
        if (myShortAnswerPanel != null) {
            myQuestionTextPanel.remove(myShortAnswerPanel);
            myShortAnswerPanel.removeAll();
            myShortAnswerPanel = null;
            myShortAnswerField = null;
        }
        myQuestionTextPanel.revalidate();
        myQuestionTextPanel.repaint();
    }

    /**
     * Checks the short answer input.
     */
    private void checkShortAnswer() {
        if (myCurrentSaQuestion == null) {
            return;
        }
        final String userAnswer = myShortAnswerField.getText().trim();
        final String correctAnswer = myCurrentSaQuestion.getAnswerText();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            displayFeedbackAndNextQuestion("Correct!");
            myGamePanel.openDoor(myAttemptedDirection);
            displayNavigationOptions(myCurrentRoom);
            moveToNextQuestion();
            myGamePanel.setCanPlayerMove(true);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is " + correctAnswer + ".");
            myGamePanel.lockDoor(myAttemptedDirection);
            moveToNextQuestion();
        }
    }

    /**
     * Enables short answer input components.
     */
    private void enableShortAnswerInput() {
        if (myShortAnswerField != null) {
            myShortAnswerField.setEnabled(true);
        }
        if (mySubmitButton != null) {
            mySubmitButton.setEnabled(true);
        }
    }

    /**
     * Disables short answer input components.
     */
    private void disableShortAnswerInput() {
        if (myShortAnswerField != null) {
            myShortAnswerField.setEnabled(false);
        }
        if (mySubmitButton != null) {
            mySubmitButton.setEnabled(false);
        }
    }

    /**
     * Displays feedback for the answer and moves to the next question.
     *
     * @param theFeedbackMessage the feedback message.
     */
    private void displayFeedbackAndNextQuestion(final String theFeedbackMessage) {
        JOptionPane optionPane = new JOptionPane(theFeedbackMessage, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Answer Feedback");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);

        clearInput();
    }

    /**
     * Clears input components.
     */
    private void clearInput() {
        if (myShortAnswerPanel != null) {
            myQuestionTextPanel.remove(myShortAnswerPanel);
            myShortAnswerPanel.removeAll();
            myShortAnswerPanel = null;
            revalidate();
            repaint();
        }
    }

    /**
     * Moves to the next question in the list.
     */
    private void moveToNextQuestion() {
        clearInputComponents();
        if (myCurrentQuestionIndex < myQuestions.size() - 1) {
            myCurrentQuestionIndex++;
            displayQuestion(myQuestions.get(myCurrentQuestionIndex));
        } else {
            JOptionPane.showMessageDialog(this, "You have answered all questions.");
        }
    }

    /**
     * Displays navigation options for the current room.
     *
     * @param theRoom the current room.
     */
    @Override
    public final void displayNavigationOptions(final Room theRoom) {
        if (theRoom != null) {
            myGreenBoxPanel.removeAll();

            final JLabel directionLabel = new JLabel("<html>Choose a direction to move<br>before answering a question:</html>");
            directionLabel.setFont(new Font("Arial", Font.BOLD, 14));
            directionLabel.setForeground(Color.BLACK);

            directionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            myGreenBoxPanel.add(directionLabel);

            final JButton northButton = new JButton("Go North");
            northButton.addActionListener(e -> {
                myAttemptedDirection = Direction.NORTH;
                displayQuestion(myQuestions.get(myCurrentQuestionIndex));
                enableMultipleChoiceOptions();
                enableTrueFalseOptions();
                enableShortAnswerInput();
            });

            final JButton southButton = new JButton("Go South");
            southButton.addActionListener(e -> {
                myAttemptedDirection = Direction.SOUTH;
                displayQuestion(myQuestions.get(myCurrentQuestionIndex));
                enableMultipleChoiceOptions();
                enableTrueFalseOptions();
                enableShortAnswerInput();
            });

            final JButton eastButton = new JButton("Go East");
            eastButton.addActionListener(e -> {
                myAttemptedDirection = Direction.EAST;
                displayQuestion(myQuestions.get(myCurrentQuestionIndex));
                enableMultipleChoiceOptions();
                enableTrueFalseOptions();
                enableShortAnswerInput();
            });

            final JButton westButton = new JButton("Go West");
            westButton.addActionListener(e -> {
                myAttemptedDirection = Direction.WEST;
                displayQuestion(myQuestions.get(myCurrentQuestionIndex));
                enableMultipleChoiceOptions();
                enableTrueFalseOptions();
                enableShortAnswerInput();
            });

            checkDoorStateAndBounds(northButton, theRoom, Direction.NORTH);
            checkDoorStateAndBounds(southButton, theRoom, Direction.SOUTH);
            checkDoorStateAndBounds(eastButton, theRoom, Direction.EAST);
            checkDoorStateAndBounds(westButton, theRoom, Direction.WEST);

            myGreenBoxPanel.add(northButton);
            myGreenBoxPanel.add(southButton);
            myGreenBoxPanel.add(eastButton);
            myGreenBoxPanel.add(westButton);

            myGreenBoxPanel.revalidate();
            myGreenBoxPanel.repaint();

        } else {
            System.out.println("Room object is null. Cannot display navigation options.");
        }
    }

    /**
     * Checks the state of the door and bounds, enabling or disabling the navigation button accordingly.
     *
     * @param theButton the navigation button.
     * @param room the current room.
     * @param theDirection the direction of the door.
     */
    private void checkDoorStateAndBounds(final JButton theButton, final Room room, final Direction theDirection) {
        final Door door = room.getDoor(theDirection);
        if (door == null || door.permanentlyLocked() || !door.isClosed()) {
            theButton.setEnabled(false);
        } else {
            theButton.setEnabled(true);
        }
    }

    /**
     * Handles room changes by updating the current room and refreshing the display.
     *
     * @param theNewRoom the new room.
     */
    @Override
    public final void onRoomChange(final Room theNewRoom) {
        myCurrentRoom = theNewRoom;
        myRoomInfo.displayRoomInfo(theNewRoom);
        updateRoomInfo(theNewRoom);
        displayNavigationOptions(theNewRoom);
    }

    /**
     * Updates room information and navigation options for the given room.
     *
     * @param theRoom the room.
     */
    private void updateRoomInfo(final Room theRoom) {
        myRoomInfo.displayRoomInfo(theRoom);
        displayNavigationOptions(theRoom);
    }
}
