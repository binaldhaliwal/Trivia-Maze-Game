package Test;

import Model.Direction;
import Model.Door;
import Model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Room class.
 *
 * @author Binal Dhaliwal, Anagha Krishna
 * @version 1.0
 */
public class RoomTest {
    /**
     * The Room instance to be tested.
     */
    private Room room;
    /**
     * The north door instance to be tested.
     */
    private Door northDoor;
    /**
     * The south instance to be tested.
     */
    private Door southDoor;
    /**
     * The east door instance to be tested.
     */
    private Door eastDoor;
    /**
     * The west door instance to be tested.
     */
    private Door westDoor;

    @BeforeEach
    public void setUp() {
        northDoor = new Door(Boolean.TRUE, Boolean.TRUE, Direction.NORTH);
        southDoor = new Door(Boolean.TRUE, Boolean.TRUE, Direction.SOUTH);
        eastDoor = new Door(Boolean.TRUE, Boolean.TRUE, Direction.EAST);
        westDoor = new Door(Boolean.TRUE, Boolean.TRUE, Direction.WEST);
        room = new Room(northDoor, southDoor, eastDoor, westDoor);
    }
    @Test
    public void testGetRow() {
        room = new Room(null, null, null, null);
         room.setRow(5);
         assertEquals(5, room.getRow());
    }

    @Test
    public void testGetCol() {
        room = new Room(null, null, null, null);
         room.setCol(3);
         assertEquals(3, room.getCol());
    }
    @Test
    public void testSetVisited() {
        room.setVisited(true);
        assertTrue(room.getVisited());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setVisited(true);
        });

        String expectedMessage = "Room is already marked as visited";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testGetVisited() {
        assertFalse(room.getVisited());
        room.setVisited(true);
        assertTrue(room.getVisited());
    }
    @Test
    public void testSetExit() {
        room.setExit(true);
        assertTrue(room.getExit());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setExit(true);
        });

        String expectedMessage = "Room is already marked as an exit.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testGetExit() {
        assertFalse(room.getExit());
        room.setExit(true);
        assertTrue(room.getExit());
    }
    @Test
    public void testSetNorthDoor() {
        room.setNorthDoor(true);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setNorthDoor(true);
        });

        String expectedMessage = "North door is already set.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetSouthDoor() {
        room.setSouthDoor(true);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setSouthDoor(true);
        });

        String expectedMessage = "South door is already set.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetEastDoor() {
        room.setEastDoor(true);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setEastDoor(true);
        });

        String expectedMessage = "East door is already set.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetWestDoor() {
        room.setWestDoor(true);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setWestDoor(true);
        });
        String expectedMessage = "West door is already set.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testGetDoorByIndex() {
        assertEquals(northDoor, room.getDoor(0));
        assertEquals(southDoor, room.getDoor(1));
        assertEquals(eastDoor, room.getDoor(2));
        assertEquals(westDoor, room.getDoor(3));
    }
    @Test
    public void testGetDoorByDirection() {
        assertEquals(northDoor, room.getDoor(Direction.NORTH));
        assertEquals(southDoor, room.getDoor(Direction.SOUTH));
        assertEquals(eastDoor, room.getDoor(Direction.EAST));
        assertEquals(westDoor, room.getDoor(Direction.WEST));
    }
    @Test
    public void testGetStart() {
        assertFalse(room.getStart());
        room.setStart(true);
        assertTrue(room.getStart());
    }
    @Test
    public void testSetStart() {
        room.setStart(true);
        assertTrue(room.getStart());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            room.setStart(true);
        });

        String expectedMessage = "Room is already marked as a start room.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testGetDoors() {
        List<Door> doors = room.getDoors();
        assertTrue(doors.contains(northDoor));
        assertTrue(doors.contains(southDoor));
        assertTrue(doors.contains(eastDoor));
        assertTrue(doors.contains(westDoor));
    }

}
