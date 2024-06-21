package Test;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the Door class.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class DoorTest {
    /**
     * The Door instance to be tested.
     */
    private Door myDoor;
    /**
     * The list of questions to be added to the Door instance.
     */
    private List<Question> myQuestions;

    @BeforeEach
    public void setUp() {
        myDoor = new Door(true, true, Direction.NORTH);
        myQuestions = new ArrayList<>();
        myQuestions.add(new MultipleChoiceQuestion("What is 2+2?", "4", "Multiple Choice"));
        myQuestions.add(new TrueFalseQuestion
                ("Is the sky blue?", "True", "True/False"));
        myQuestions.add(new ShortAnswerQuestion
                ("What is the capital of France?", "Paris", "Short Answer"));
    }


    @Test
    public void testAddQuestion() {
        myDoor.addQuestion(myQuestions.get(0));
        assertEquals(1, myDoor.getQuestions().size());
        assertTrue(myDoor.getQuestions().contains(myQuestions.get(0)));
    }

    @Test
    public void testIsLock() {
        assertTrue(myDoor.isLock());
        myDoor.setLock(false);
        assertFalse(myDoor.isLock());
    }

    @Test
    public void testIsClosed() {
        assertTrue(myDoor.isClosed());
        myDoor.setClosed(false);
        assertFalse(myDoor.isClosed());
    }

    @Test
    public void testDoorLock() {
        myDoor.doorLock();
        assertTrue(myDoor.isLock());
    }

    @Test
    public void testDoorOpen() {
        myDoor.doorOpen();
        assertFalse(myDoor.isLock());
        assertFalse(myDoor.isClosed());
    }

    @Test
    public void testPermanentlyLocked() {
        assertFalse(myDoor.permanentlyLocked());
        myDoor.setPermanentlyLocked(true);
        assertTrue(myDoor.permanentlyLocked());
    }
}
