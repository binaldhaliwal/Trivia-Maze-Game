package Test;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GameState class.
 *
 * @author Anagha Krishna
 * @version 1.0
 */
public class GameStateTest {
    /**
     * The GameState instance to be tested.
     */
    private GameState myGameState;

    @BeforeEach
    public void setUp() {
        myGameState = new GameState();
    }

    @Test
    public void testSetPlayerLocation() {
        Door northDoor = new Door(true, true, Direction.NORTH);
        Door southDoor = new Door(true, true, Direction.SOUTH);
        Door eastDoor = new Door(false, false, Direction.EAST);
        Door westDoor = new Door(true, false, Direction.WEST);

        Room room = new Room(northDoor, southDoor, eastDoor, westDoor);

        GameState gameState = new GameState();

        assertDoesNotThrow(() -> gameState.setPlayerLocation(room));
        assertEquals(room, gameState.getPlayerLocation());
    }

    @Test
    public void testAddAnsweredQuestion() {
        Question question = new Question("What is the capital of France?", "Paris", "Geography");

        myGameState.addAnsweredQuestions(question);
        assertTrue(myGameState.getAnsweredQuestions().contains(question));
    }

    @Test
    public void testUpdateState() {
        GameState gameState = new GameState();

        GameState otherGameState = new GameState();

        Door northDoor = new Door(true, true, Direction.NORTH);
        Door southDoor = new Door(true, true, Direction.SOUTH);
        Door eastDoor = new Door(false, false, Direction.EAST);
        Door westDoor = new Door(true, false, Direction.WEST);

        Room room = new Room(northDoor, southDoor, eastDoor, westDoor);

        otherGameState.setPlayerLocation(room);
        Question question = new Question("What is the capital of France?", "Paris", "Geography");
        otherGameState.addAnsweredQuestions(question);

        gameState.updateState(otherGameState);

        assertEquals(room, gameState.getPlayerLocation());
        assertTrue(gameState.getAnsweredQuestions().contains(question));
    }

}


