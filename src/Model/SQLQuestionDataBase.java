package Model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a database manager for storing and retrieving questions using SQLite.
 *
 * @author Binal Dhaliwal, Anagha Krishna
 * @version 1.0
 */
public class SQLQuestionDataBase {
    /**
     * The logger for logging messages related to the SQLQuestionDataBase class.
     */
    private static final Logger LOGGER = Logger.getLogger(SQLQuestionDataBase.class.getName());
    /**
     * The data source for the database.
     */
    private static SQLiteDataSource DS;

    /**
     * Entry point for the database operations.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) {
        initializeDatabase();
        createQuestionsTable();
        insertQuestions();
        displayAllQuestions();
        readQuestionsFromDatabase();
        closeScanner();
    }

    /**
     * Gets the SQLiteDataSource object.
     *
     * @return the SQLiteDataSource object used for database connection
     */
    public static SQLiteDataSource getDataSource() {
        return DS;
    }

    /**
     * Initializes the SQLite database connection.
     */
    public static void initializeDatabase() {
        try {
            DS = new SQLiteDataSource();
            DS.setUrl("jdbc:sqlite:questions.db");
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing database", e);
            System.exit(0);
        }
    }

    /**
     * Creates the 'questions' table if it does not exist in the database.
     * The table structure includes QUESTION, ANSWER, and OPTIONS columns.
     */
    private static void createQuestionsTable() {
        final String query = "CREATE TABLE IF NOT EXISTS questions ( " +
                "QUESTION TEXT NOT NULL, " +
                "ANSWER TEXT NOT NULL, " +
                "OPTIONS TEXT NOT NULL" +
                ")";

        try (Connection conn = DS.getConnection();
             final Statement stmt = conn.createStatement()) {
            final int rv = stmt.executeUpdate(query);
            System.out.println("Created questions table successfully. executeUpdate() returned " + rv);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing database", e);
            System.exit(0);
        }
    }

    /**
     * Inserts sample questions into the 'questions' table.
     */
    private static void insertQuestions() {
        System.out.println("Inserting questions into questions table");
        final String[] queries = {
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'Which actress played the lead role in the movie ''The Hunger Games''?', 'c) Jennifer Lawrence', 'a) Jennifer Aniston;b) Jennifer Garner;c) Jennifer Lawrence')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'Who is the main antagonist in ''The Lion King''?', 'a) Scar', 'a) Scar;b) Simba;c) Rafiki')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'In which country would you find the Great Wall?', 'a) China', 'a) China;b) United States;c) India')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'In which city is the famous Taj Mahal located?', 'a) Agra, India', 'a) Agra, India;b) Beijing, China;c) Cairo, Egypt')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'What is the capital city of Australia?', 'c) Canberra', 'a) Sydney;b) Melbourne;c) Canberra')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'What''s the smallest country in the world?', 'b) Vatican City', 'a) Monaco;b) Vatican City;c) Luxembourg')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'What is the largest animal on Earth?', 'a) Blue whale', 'a) Blue whale;b) Elephant;c) Giraffe')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'What is the first element on the periodic table?', 'a) Hydrogen', 'a) Hydrogen;b) Oxygen;c) Carbon')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'Which famous scientist developed the theory of relativity?', 'b) Albert Einstein', 'a) Isaac Newton;b) Albert Einstein;c) Galileo Galileio')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Multiple-Choice', 'Who is the founder of Microsoft?', 'a) Bill Gates', 'a) Bill Gates;b) Steve Jobs;c) Mark Zuckerberg')",

                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'A chicken can live without a head long after it is chopped off.', 'True', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'Bananas are berries.', 'True', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'You can sneeze while asleep.', 'False', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'Google was initially called BackRub.', 'True', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'The Titanic sank on its maiden voyage.', 'True', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'A group of crows is called a ''murder.''', 'True', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'The Big Apple is a nickname given to Washington D.C in 1971.', 'False', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'Mars is known as the ''Red Planet'' due to its blue skies.', 'False', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'A kilogram of lead is heavier than a kilogram of feathers.', 'False', 'True;False')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('True/False', 'Leonardo da Vinci painted the Sistine Chapel ceiling.', 'False', 'True;False')",


                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'What is the largest mammal in the world?', 'Blue whale', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'What is the name of the largest ocean on Earth?', 'Pacific Ocean', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'In the story of Snow White, how many dwarfs are there?', 'Seven', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'Who is the king of the gods in Greek mythology?', 'Zeus', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'What do bees collect to make honey?', 'Nectar', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'How many wives did King Henry VIII have?', 'Six', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'In mathematics, what is the name for a number that is not a prime number and has more than two factors?', 'Composite Number', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'Who is the Greek god of war and son of Zeus and Hera?', 'Ares', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'What is the chemical symbol for the element mercury?', 'Hg', '')",
                "INSERT INTO questions (QUESTION_TYPE, QUESTION, ANSWER, OPTIONS) VALUES ('Short Answer', 'What is the official animal of Scotland?', 'Unicorn', '')"


        };
        try (Connection conn = DS.getConnection();
             final Statement stmt = conn.createStatement()) {
            for (String query : queries) {
                System.out.println("Executing query: " + query);
                final int rv = stmt.executeUpdate(query);
                System.out.println("executeUpdate() returned " + rv);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing SQL queries", e);
            System.exit(0);
        }
    }

    /**
     * Displays all questions stored in the 'questions' table.
     */
    private static void displayAllQuestions() {
        System.out.println("Selecting all rows from questions table");
        String query = "SELECT * FROM questions";
        try (Connection conn = DS.getConnection();
             final Statement stmt = conn.createStatement()) {
           final  ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                final String question = rs.getString("QUESTION");
                final String answer = rs.getString("ANSWER");
                System.out.println("Result: Question = " + question + ", Answer = " + answer);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error displaying all questions", e);
            System.exit(0);
        }
    }

    /**
     * Reads questions from the 'questions' table and converts them into Question objects.
     *
     * @return a list of Question objects representing the questions from the database
     */
    public static List<Question> readQuestionsFromDatabase() {
        final List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";

        try (Connection conn = DS.getConnection();
             final Statement stmt = conn.createStatement();
             final ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                final String questionText = rs.getString("QUESTION");
                final String answerText = rs.getString("ANSWER");
                final String questionType = rs.getString("QUESTION_TYPE");

                switch (questionType) {
                    case "Multiple-Choice" -> {
                        final String optionsText = rs.getString("OPTIONS");
                        final String[] choices = optionsText.split(";");
                        final MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion(questionText, answerText, questionType);
                        mcQuestion.setChoices(choices);
                        questions.add(mcQuestion);
                    }
                    case "True/False" -> {
                        final TrueFalseQuestion tfQuestion = new TrueFalseQuestion(questionText, answerText, questionType);
                        questions.add(tfQuestion);
                    }
                    case "Short Answer" -> {
                        final ShortAnswerQuestion shortAnswerQ = new ShortAnswerQuestion(questionText, answerText, questionType);
                        questions.add(shortAnswerQ);
                    }
                    default -> LOGGER.warning("Invalid question type: " + questionType);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error reading questions from database", e);
        }
        return questions;
    }

    /**
     * Closes the scanner used for user input.
     */
    private static void closeScanner() {
        System.out.println("Press enter to close the program/window");
        final Scanner input = new Scanner(System.in);
        input.nextLine();
    }
}


