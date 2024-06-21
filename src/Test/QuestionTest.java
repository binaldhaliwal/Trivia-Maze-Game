package Test;

import Model.MultipleChoiceQuestion;
import Model.Question;
import Model.ShortAnswerQuestion;
import Model.TrueFalseQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Question class.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class QuestionTest {
    /**
     * The Question instance to be tested.
     */
    private Question myQuestion;

    @BeforeEach
    public void setUp() {
        myQuestion = new Question
                ("What is 2 + 2?", "4", "Multiple Choice");
    }

    @Test
    public void testGetQuestionText() {
        assertEquals("What is 2 + 2?", myQuestion.getQuestionText());
    }

    @Test
    public void testGetAnswerText() {
        assertEquals("4", myQuestion.getAnswerText());
    }

    @Test
    public void testGetQuestionType() {
        assertEquals("Multiple Choice", myQuestion.getQuestionType());
    }

    @Test
    public void testSetQuestionText() {
        myQuestion.setQuestionText("What is 3 + 3?");
        assertEquals("What is 3 + 3?", myQuestion.getQuestionText());
    }

    @Test
    public void testSetAnswerText() {
        myQuestion.setAnswerText("6");
        assertEquals("6", myQuestion.getAnswerText());
    }

    @Test
    public void testSetQuestionType() {
        myQuestion.setQuestionType("True/False");
        assertEquals("True/False", myQuestion.getQuestionType());
    }

    // MultipleChoiceQuestion class test
    @Test
    public void testGetCorrectOption() {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion
                ("What is 2 + 2?", "4", "Multiple Choice");
        multipleChoiceQuestion.setCorrectOption("4");
        assertEquals("4", multipleChoiceQuestion.getCorrectOption());
    }

    @Test
    public void testGetQuestionTypeMultipleChoice() {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion
                ("What is 2 + 2?", "4", "Multiple Choice");
        assertEquals("Multiple-Choice", multipleChoiceQuestion.getQuestionType());
    }

    @Test
    public void testSetAndGetChoices() {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion
                ("What is 2 + 2?", "4", "Multiple Choice");
        String[] choices = {"A", "B", "C", "D"};
        multipleChoiceQuestion.setChoices(choices);
        assertArrayEquals(choices, multipleChoiceQuestion.getChoices());
    }

    // ShortAnswerQuestion class test
    @Test
    public void testGetMaxAnswerLength() {
        ShortAnswerQuestion shortAnswerQuestion = new ShortAnswerQuestion
                ("What is the capital of France?", "Paris", "Short Answer");
        assertEquals(2, shortAnswerQuestion.getMaxAnswerLength());
    }

    @Test
    public void testGetQuestionTypeShortAnswer() {
        ShortAnswerQuestion shortAnswerQuestion = new ShortAnswerQuestion
                ("What is the capital of France?", "Paris", "Short Answer");
        assertEquals("Short Answer", shortAnswerQuestion.getQuestionType());
    }

    //TrueFalseQuestion class test
    @Test
    public void testGetCorrectAnswer() {
        TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion
                ("Is the sky blue?", "True", "True/False");
        assertTrue(trueFalseQuestion.getCorrectAnswer());
    }

    @Test
    public void testGetQuestionTypeTrueFalse() {
        TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion
                ("Is the sky blue?", "True", "True/False");
        assertEquals("True/False", trueFalseQuestion.getQuestionType());
    }
}