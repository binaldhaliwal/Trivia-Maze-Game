package Test;

import Model.MultipleChoiceQuestion;
import Model.Question;
import Model.ShortAnswerQuestion;
import Model.SQLQuestionDataBase;
import Model.TrueFalseQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SQLQuestionDataBase class.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class SQLDataBaseTest {
    /**
     * The SQLQuestionDataBase instance to be tested.
     */
    private SQLQuestionDataBase questionDatabase;

    @BeforeEach
    void setUp() {
        questionDatabase = new SQLQuestionDataBase();
        questionDatabase.initializeDatabase();
    }

    @Test
    void testInitializeDatabase() {
        assertNotNull(questionDatabase.getDataSource(), "The SQLiteDataSource instance should not be null");
    }

    @Test
    void testReadQuestionsFromDatabase_NotEmpty() {
        List<Question> questions = questionDatabase.readQuestionsFromDatabase();
        assertFalse(questions.isEmpty(), "The list of questions should not be empty");
    }

    @Test
    void testReadQuestionsFromDatabase_ContainsMultipleChoiceQuestion() {
        List<Question> questions = questionDatabase.readQuestionsFromDatabase();
        boolean containsMultipleChoiceQuestion = false;
        for (Question q : questions) {
            if (q instanceof MultipleChoiceQuestion) {
                containsMultipleChoiceQuestion = true;
                break;
            }
        }
        assertTrue(containsMultipleChoiceQuestion, "The list of questions should contain at least one MultipleChoiceQuestion");
    }

    @Test
    void testReadQuestionsFromDatabase_ContainsTrueFalseQuestion() {
        List<Question> questions = questionDatabase.readQuestionsFromDatabase();
        boolean containsTrueFalseQuestion = false;
        for (Question q : questions) {
            if (q instanceof TrueFalseQuestion) {
                containsTrueFalseQuestion = true;
                break;
            }
        }
        assertTrue(containsTrueFalseQuestion, "The list of questions should contain at least one TrueFalseQuestion");
    }

    @Test
    void testReadQuestionsFromDatabase_ContainsShortAnswerQuestion() {
        List<Question> questions = questionDatabase.readQuestionsFromDatabase();
        boolean containsShortAnswerQuestion = false;
        for (Question q : questions) {
            if (q instanceof ShortAnswerQuestion) {
                containsShortAnswerQuestion = true;
                break;
            }
        }
        assertTrue(containsShortAnswerQuestion, "The list of questions should contain at least one ShortAnswerQuestion");
    }
}
