package Test;

import Model.Direction;
import Model.Maze;
import Model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Maze class.
 *
 * @author Bhavneet Bhargava, Binal Dhaliwal
 * @version 1.0
 */
public class MazeTest {
    /**
     * The Maze instance to be tested.
     */
    private Maze myMaze;

    @BeforeEach
    public void setUp() {
        myMaze = new Maze();
    }

    @Test
    public void testInitialPlayerPosition() {
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void testCannotMoveWest() {
        assertFalse(myMaze.canMovePlayer(Direction.WEST));
    }

    @Test
    public void testEndOfMazeCheck() {
        assertFalse(myMaze.endOfMazeCheck());
    }

    @Test
    public void testMovePlayer() {
        if (myMaze.canMovePlayer(Direction.EAST)) {
            myMaze.movePlayer(Direction.EAST);
            assertEquals(3, myMaze.getRows());
            assertEquals(3, myMaze.getCols());
        }
    }

    @Test
    public void testCanMovePlayer() {
        assertFalse(myMaze.canMovePlayer(Direction.EAST));
    }

    @Test
    public void testGetCurrentRoom() {
        assertNotNull(myMaze.getCurrentRoom());
    }

    @Test
    public void testIsPossibleMoves() {
        assertFalse(myMaze.isPossibleMoves());
    }

    @Test
    public void testMovePlayerNorth() {
        myMaze.movePlayer(Direction.NORTH);
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void testMovePlayerSouth() {
        myMaze.movePlayer(Direction.SOUTH);
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void testMovePlayerWest() {
        myMaze.movePlayer(Direction.WEST);
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void testMovePlayerEast() {
        myMaze.movePlayer(Direction.EAST);
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void testInvalidMovePlayer() {
        myMaze.movePlayer(Direction.WEST);
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void testMovePlayerMultipleTimes() {
        myMaze.movePlayer(Direction.EAST);
        myMaze.movePlayer(Direction.NORTH);
        assertEquals(3, myMaze.getRows());
        assertEquals(3, myMaze.getCols());
    }

    @Test
    public void generateMaze() {
        myMaze.generateMaze();
    }

    @Test
    public void testGenerateMaze() {
        myMaze.generateMaze();

        for (int i = 0; i < myMaze.getRows(); i++) {
            for (int j = 0; j < myMaze.getCols(); j++) {
                assertNotNull(myMaze.myMaze[i][j]);
            }
        }

        Room startRoom = myMaze.myMaze[0][0];
        assertTrue(startRoom.getStart());

        Room endRoom = myMaze.myMaze[myMaze.getRows() - 1][myMaze.getCols() - 1];
        assertTrue(endRoom.getExit());
    }
}
