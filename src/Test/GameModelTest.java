package Test;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GameModel class.
 *
 * @author Anagha Krishna
 * @version 1.0
 */
public class GameModelTest {
    /**
     * The GameModel instance to be tested.
     */
    private GameModel myGameModel;

    @BeforeEach
    public void setUp() {
        myGameModel = new GameModel();
    }

    @Test
    public void testSetPlayerLocation() {
        Door northDoor = new Door(true, true, Direction.NORTH);
        Door southDoor = new Door(true, true, Direction.SOUTH);
        Door eastDoor = new Door(false, false, Direction.EAST);
        Door westDoor = new Door(true, false, Direction.WEST);
        Room room = new Room(northDoor, southDoor, eastDoor, westDoor);

        assertDoesNotThrow(() -> myGameModel.setPlayerLocation(room));
        assertEquals(room, myGameModel.getPlayerLocation());
    }

    @Test
    public void testAddAnsweredQuestion() {
        Question question = new Question("What is the capital of France?", "Paris", "Geography");

        myGameModel.addAnsweredQuestions(question);
        List<Question> answeredQuestions = myGameModel.getAnsweredQuestions();
        assertTrue(answeredQuestions.contains(question));
    }
    @Test
    public void testUpdateDoorState() {
        Door northDoor = new Door(false, false, Direction.NORTH);
        Door southDoor = new Door(false, false, Direction.SOUTH);
        Door eastDoor = new Door(false, false, Direction.EAST);
        Door westDoor = new Door(false, false, Direction.WEST);
        Room room = new Room(northDoor, southDoor, eastDoor, westDoor);


        assertFalse(northDoor.isLock());
        assertFalse(southDoor.isLock());
        assertFalse(eastDoor.isLock());
        assertFalse(westDoor.isLock());

        assertFalse(northDoor.isClosed());
        assertFalse(southDoor.isClosed());
        assertFalse(eastDoor.isClosed());
        assertFalse(westDoor.isClosed());

        myGameModel.updateDoorState(room, Direction.NORTH, true);

        assertTrue(northDoor.isLock());
        assertFalse(northDoor.isClosed());

        myGameModel.updateDoorState(room, Direction.SOUTH, true);

        assertTrue(southDoor.isLock());
        assertFalse(southDoor.isClosed());
    }

    @Test
    public void testStartGame() {
        myGameModel.startGame();

        assertEquals(myGameModel.getMaze().getCurrentRoom(), myGameModel.getPlayerLocation());
        assertTrue(myGameModel.getAnsweredQuestions().isEmpty());
    }

    @Test
    public void testEndGame() {
        myGameModel.endGame();

        assertEquals(myGameModel.getMaze().getCurrentRoom(), myGameModel.getPlayerLocation());
        assertTrue(myGameModel.getAnsweredQuestions().isEmpty());
    }
}


