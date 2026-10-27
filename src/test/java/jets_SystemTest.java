import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class jets_SystemTest {
    //Create a new system to access all methods
    private jets_System system;
//setup to run before each test AKA a clean slate
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        system = new jets_System();
    }
//tests to see if a file can be opened, can be read and is then compared with the real output
    @Test
    void canOpenFile() {
        File file = new File("Jets.txt");
        boolean expectedExists = true;
        boolean expectedReads = true;

        boolean actualExists = file.exists();
        boolean actualReads = file.canRead();


        assertEquals(expectedExists, actualExists, "File does not exist");
        assertEquals(expectedReads, actualReads, "File should be readable");

    }
//creates a fake player, calls the display player method for an individual player and compares the output
    @Test
    void canAdd() {
        Player fakePlayer = new Player("Garrett Wilson", 80, 1000, 7, 12.5, 300.0);

        String expectedName = "Garrett Wilson";
        boolean expectedResult = true;
        boolean actualResult = system.addPlayer(fakePlayer);

        Player actualPlayer = system.displayPlayer("Garrett Wilson");
        assertNotNull(actualPlayer, "A player should be found since being added to the list");

        String actualName = actualPlayer.getName();

        assertEquals(expectedResult, actualResult, "This should return true");
        assertEquals(expectedName, actualName, "The two names did not match");


    }
//creates a new player, adds it to the list, removes it and then compares the boolean output to see if the player was deleted
    @Test
    void canRemove() {
        Player testPlayer = new Player("Breece Hall", 50, 600, 4, 12.0, 200.0);
        system.addPlayer(testPlayer);

        boolean expectedResult = true;
        boolean actualResult = system.deletePlayer(testPlayer);

        Player actualPlayerAfterRemove = system.displayPlayer("Breece Hall");
        assertEquals(expectedResult, actualResult, "This should return true");
        assertNull(actualPlayerAfterRemove, "You should not be able to find the player anymore in the system");
    }
//Creates a new player, calls the update method, changes all the stats and then compares the output to ensure both are true
    @Test
    void canUpdate()

    {
        Player testPlayer = new Player("Aaron Rodgers", 0, 0, 0, 0.0, 0.0);
        system.addPlayer(testPlayer);
        boolean expectedResult = true;
        int expectedNewYards = 4000;

        boolean actualResult = system.updatePlayer(testPlayer, 350, 4000, 30, 11.4, 150.0);

        Player updatedPlayer = system.displayPlayer("Aaron Rodgers");
        assertNotNull(updatedPlayer, "A player should be found since being added to the list");
        int actualNewYards = updatedPlayer.getReceiving_yards();
        assertEquals(expectedResult, actualResult, "This should return true");
        assertEquals(expectedNewYards, actualNewYards, "The yards should now match");

    }
//creates two players with full stats, does the math to find the difference in yards and then compares the two outputs
    @Test
    void canCompare(){
        Player p1 = new Player("Garrett Wilson", 80, 1000, 7, 12.5, 300.0);
        Player p2 = new Player("Malachi Corley", 20, 200, 1, 10.0, 50.0);
        int expectedYardDiff = 800;
        int actualYardDiff = p1.getReceiving_yards() - p2.getReceiving_yards();
        assertEquals(expectedYardDiff, actualYardDiff, "The yards differ");
    }
    @Test
    void canDisplay() {
        Player testPlayer = new Player("Garrett Wilson", 80, 10, 7, 12.5, 300.0);
        system.addPlayer(testPlayer);

        String expectedName = "Garrett Wilson";
        int expectedYards = 10;

        Player actualPlayer = system.displayPlayer("Garrett Wilson");

        assertNotNull(actualPlayer, "The display method should find and return the player");

        assertEquals(expectedName, actualPlayer.getName(), "The actual player name did not match expected");
        assertEquals(expectedYards, actualPlayer.getReceiving_yards(), "The actual receiving yards did not match expected");
    }


    }
