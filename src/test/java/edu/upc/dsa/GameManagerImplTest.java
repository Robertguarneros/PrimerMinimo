package edu.upc.dsa;

import edu.upc.dsa.exceptions.GameIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIsCurrentlyInMatchException;
import edu.upc.dsa.exceptions.UserIsNotInMatchException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerImplTest {

    GameManager gm;
    @BeforeEach
    void setUp() {
        this.gm = new GameManagerImpl();
        this.gm.createGame("Spider-Man","Play the Spider-Man game",10);
        this.gm.createGame("Hogwarts Legacy","Experience being a wizard in Hogwarts", 3);
        this.gm.createGame("Stardew Valley", "Create your own farm and explore the region while making friends", 2);
        this.gm.createUser("Roberto");
        this.gm.createUser("User1");
        this.gm.createUser("User2");
    }

    @AfterEach
    void tearDown() {
        this.gm = null;
    }

    @Test
    void size() {
        Assert.assertEquals(3,this.gm.size());
    }

    @Test
    void numberOfUsers() {
        Assert.assertEquals(3,this.gm.numberOfUsers());
    }

    @Test
    void numberOfGames() {
        Assert.assertEquals(3,this.gm.numberOfGames());
    }

    @Test
    void createUser() {
        this.gm.createUser("UserTest1");
        Assert.assertEquals(4,this.gm.numberOfUsers());

        this.gm.createUser("UserTest2");
        Assert.assertEquals(5,this.gm.numberOfUsers());
    }

    @Test
    void createGame() {
        this.gm.createGame("Indiana Jones Lego", "Lego Game",5);
        Assert.assertEquals(4,this.gm.numberOfGames());

        this.gm.createGame("Formula 1", "Driving Game",5);
        Assert.assertEquals(5,this.gm.numberOfGames());
    }

    @Test
    void createMatch() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException {
        //Test, Roberto has no current match
        Assert.assertEquals(false, this.gm.getMatch("Roberto").isCurrentMatch());

        //Test Roberto created a match
        this.gm.createMatch("Spider-Man","Roberto");
        Assert.assertEquals(true, this.gm.getMatch("Roberto").isCurrentMatch());

        Assert.assertEquals("Spider-Man",this.gm.getMatch("Roberto").getGameID());
        Assert.assertEquals("Roberto",this.gm.getMatch("Roberto").getUserID());

        //Testing exceptions
        Assert.assertThrows(GameIDDoesNotExistException.class,()->this.gm.createMatch("Test","Roberto"));
        Assert.assertThrows(UserIDDoesNotExistException.class,()->this.gm.createMatch("Spider-Man","Test"));
        Assert.assertThrows(UserIsCurrentlyInMatchException.class,()->this.gm.createMatch("Hogwarts Legacy","Roberto"));
    }

    @Test
    void getLevelFromMatch() throws UserIDDoesNotExistException, UserIsNotInMatchException, UserIsCurrentlyInMatchException, GameIDDoesNotExistException {
        //Create a match
        this.gm.createMatch("Spider-Man","Roberto");
        //Get Level
        Assert.assertEquals(1, this.gm.getLevelFromMatch("Roberto"));

        //Testing exceptions
        Assert.assertThrows(UserIDDoesNotExistException.class,()->this.gm.getLevelFromMatch("Test"));
        Assert.assertThrows(UserIsNotInMatchException.class,()->this.gm.getLevelFromMatch("User1"));

    }

    @Test
    void getMatchPoints() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsNotInMatchException {
        //Create a match
        this.gm.createMatch("Spider-Man","Roberto");
        //Get points from match
        Assert.assertEquals(50, this.gm.getMatchPoints("Roberto"));

        //Testing exceptions
        Assert.assertThrows(UserIDDoesNotExistException.class,()->this.gm.getMatchPoints("Test"));
        Assert.assertThrows(UserIsNotInMatchException.class,()->this.gm.getMatchPoints("User1"));
    }

    @Test
    void nextLevel() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsNotInMatchException {
        //Create a match
        this.gm.createMatch("Hogwarts Legacy","Roberto");
        //Advance to next level
        this.gm.NextLevel("Roberto",50,"04/11/23");
        //Testing level to verify if it has changed
        Assert.assertEquals(2, this.gm.getLevelFromMatch("Roberto"));
        //Testing points to make sure it is adding them
        Assert.assertEquals(100, this.gm.getMatchPoints("Roberto"));
        //Advance until last level to test the match ended
        this.gm.NextLevel("Roberto",100,"05/11/23");
        Assert.assertEquals(3, this.gm.getLevelFromMatch("Roberto"));
        this.gm.NextLevel("Roberto",100,"06/11/23");
        Assert.assertEquals(false, this.gm.getMatch("Roberto").isCurrentMatch());

        //Testing exceptions
        Assert.assertThrows(UserIDDoesNotExistException.class,()-> this.gm.NextLevel("Test",50,"04/11/23"));
        Assert.assertThrows(UserIsNotInMatchException.class,()-> this.gm.NextLevel("User1",50,"04/11/23"));
    }

    @Test
    void endMatch() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsNotInMatchException {
        //Create a match
        this.gm.createMatch("Spider-Man","Roberto");
        //Testing if it is created
        Assert.assertEquals(0,this.gm.getUser("Roberto").getMatchesPlayed().size());
        //Ending match
        this.gm.EndMatch("Roberto");
        //Testing if it was ended
        Assert.assertEquals(1,this.gm.getUser("Roberto").getMatchesPlayed().size());
        Assert.assertEquals(false, this.gm.getMatch("Roberto").isCurrentMatch());

        //Testing exceptions
        Assert.assertThrows(UserIDDoesNotExistException.class,()-> this.gm.EndMatch("Test"));
        Assert.assertThrows(UserIsNotInMatchException.class,()-> this.gm.EndMatch("User1"));
    }

    @Test
    void getUserHistoryForGame() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsNotInMatchException {
        //Create three matches for three users in a game and change levels in some
        this.gm.createMatch("Spider-Man","Roberto");
        this.gm.NextLevel("Roberto",100,"20/11/23");
        this.gm.createMatch("Spider-Man","User1");
        this.gm.NextLevel("User1",50,"20/11/23");
        this.gm.createMatch("Spider-Man","User2");
        //End this matches
        this.gm.EndMatch("Roberto");
        this.gm.EndMatch("User1");
        this.gm.EndMatch("User2");

        //Testing if we get correct history
        Assert.assertEquals(3,this.gm.getUserHistoryForGame("Spider-Man").size());
        Assert.assertEquals("Roberto",this.gm.getUserHistoryForGame("Spider-Man").get(0).getUserID());
        Assert.assertEquals("User1",this.gm.getUserHistoryForGame("Spider-Man").get(1).getUserID());
        Assert.assertEquals("User2",this.gm.getUserHistoryForGame("Spider-Man").get(2).getUserID());

        //Testing exception
        Assert.assertThrows(GameIDDoesNotExistException.class,()->this.gm.getUserHistoryForGame("Test"));
    }

    @Test
    void getUserMatches() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsNotInMatchException {
        //Create a match
        this.gm.createMatch("Spider-Man","Roberto");
        //Ending match
        this.gm.EndMatch("Roberto");
        //Create match in another game
        this.gm.createMatch("Hogwarts Legacy","Roberto");
        //End another match
        this.gm.EndMatch("Roberto");

        //Test user matches
        Assert.assertEquals(2,this.gm.getUserMatches("Roberto").size());
        Assert.assertEquals("Spider-Man",this.gm.getUserMatches("Roberto").get(0).getGameID());
        Assert.assertEquals("Hogwarts Legacy",this.gm.getUserMatches("Roberto").get(1).getGameID());

        //Testing exceptions
        Assert.assertThrows(UserIDDoesNotExistException.class,()->this.gm.getUserMatches("Test"));
    }

    @Test
    void getStatsFromUserPerGame() throws UserIsCurrentlyInMatchException, GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsNotInMatchException {
        //Create a match
        this.gm.createMatch("Hogwarts Legacy","Roberto");
        //Advance to next level
        this.gm.NextLevel("Roberto",50,"04/11/23");
        this.gm.NextLevel("Roberto",100,"05/11/23");
        this.gm.NextLevel("Roberto",100,"06/11/23");

        Assert.assertEquals("Roberto",this.gm.getStatsFromUserPerGame("Hogwarts Legacy","Roberto").getUserID());
        Assert.assertEquals("Hogwarts Legacy",this.gm.getStatsFromUserPerGame("Hogwarts Legacy","Roberto").getGameID());
        Assert.assertEquals("04/11/23",this.gm.getStatsFromUserPerGame("Hogwarts Legacy","Roberto").getLevelDetails().get(0).getLevelDate());
        Assert.assertEquals("05/11/23",this.gm.getStatsFromUserPerGame("Hogwarts Legacy","Roberto").getLevelDetails().get(1).getLevelDate());
        Assert.assertEquals("06/11/23",this.gm.getStatsFromUserPerGame("Hogwarts Legacy","Roberto").getLevelDetails().get(2).getLevelDate());

    }
}