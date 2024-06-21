package Test;

import Model.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Direction class.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class DirectionTest {
    @Test
    void testNorthDirection() {
        Direction direction = Direction.NORTH;
        assertEquals(-1, direction.getRow(), "NORTH direction row should be -1");
        assertEquals(0, direction.getCol(), "NORTH direction col should be 0");
    }

    @Test
    void testSouthDirection() {
        Direction direction = Direction.SOUTH;
        assertEquals(1, direction.getRow(), "SOUTH direction row should be 1");
        assertEquals(0, direction.getCol(), "SOUTH direction col should be 0");
    }

    @Test
    void testEastDirection() {
        Direction direction = Direction.EAST;
        assertEquals(0, direction.getRow(), "EAST direction row should be 0");
        assertEquals(1, direction.getCol(), "EAST direction col should be 1");
    }

    @Test
    void testWestDirection() {
        Direction direction = Direction.WEST;
        assertEquals(0, direction.getRow(), "WEST direction row should be 0");
        assertEquals(-1, direction.getCol(), "WEST direction col should be -1");
    }
}

