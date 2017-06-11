package edu.gatech.seclass.tourneymanager;

import android.support.test.runner.AndroidJUnit4;

import com.orm.SugarApp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.DeckType;
import edu.gatech.seclass.tourneymanager.core.models.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by justinp on 3/2/17.
 */
@RunWith(AndroidJUnit4.class)
public class PlayerManagementIntegrationTests extends SugarApp {
    private ManagerCapabilityFacade mgmtFacade;

    @BeforeClass
    public static void setUp() throws Exception {
        // SugarORM need some time to start up, else tests will fail
        Thread.sleep(3000);
    }

    @Before
    public void setupEach() {
        Player.deleteAll(Player.class);
        mgmtFacade = new ManagerCapabilityFacade();
    }

    @After
    public void teardownEach() {
        mgmtFacade = null;
    }

    @AfterClass
    public static void teardown() {
        Player.deleteAll(Player.class);
    }

    @Test
    public void GivenThePlayerDoesNotExist_whenSavingaPlayer_thePlayerIsPersisted() throws Exception {
        // setup
        String name = "test1";

        // action
        mgmtFacade.addPlayerToSystem(new Player(name, name, name, DeckType.Buzz));

        // assert
        Player savedPlayer = Player.find(Player.class, "username = ?", name).get(0);
        assertNotEquals(savedPlayer, null);
    }

    @Test(expected = Exception.class)
    public void GivenThePlayerExists_whenSavingaPlayer_throwsException() throws Exception {
        // setup
        String name = "test2";
        mgmtFacade.addPlayerToSystem(new Player(name, name, name, DeckType.Buzz));

        // action
        mgmtFacade.addPlayerToSystem(new Player(name, name, name, DeckType.Buzz));
    }

    @Test
    public void GivenThePlayerExists_whenDeletingaPlayer_thePlayerIsDeleted() throws Exception {
        // setup
        String name = "test3";
        mgmtFacade.addPlayerToSystem(new Player(name, name, name, DeckType.Buzz));
        Player savedPlayer = Player.find(Player.class, "username = ?", name).get(0);

        // action
        mgmtFacade.removePlayerFromSystem(savedPlayer);

        // assert
        assertEquals(Player.find(Player.class, "username = ?", name).size(), 0);
    }

    @Test(expected = Exception.class)
    public void GivenThePlayerDoesNotExists_whenDeletingaPlayer_anExceptionIsThrown() throws Exception {
        // setup
        String name = "test4";
        Player savedPlayer = Player.find(Player.class, "username = ?", name).get(0);

        // action
        mgmtFacade.removePlayerFromSystem(savedPlayer);
    }

    @Test
    public void GivenNoPlayers_whenGettingPlayers_noPlayerIsReturned() {
        // setup
        String name = "test5";
        ManagerCapabilityFacade mgmtFacade = new ManagerCapabilityFacade();

        // action
        List<Player> players = mgmtFacade.getPlayersFromSystem();

        // assert
        assertEquals(players.size(), 0);
    }

    @Test
    public void GivenOnePlayer_whenGettingPlayers_onePlayerIsReturned() throws Exception {
        // setup
        String name = "test6";
        mgmtFacade.addPlayerToSystem(new Player(name, name, name, DeckType.Buzz));

        // action
        List<Player> players = mgmtFacade.getPlayersFromSystem();

        // assert
        assertEquals(players.size(), 1);
    }

    @Test
    public void GivenMultiplePlayers_whenGettingPlayers_multiplePlayersAreReturned() throws Exception {
        // setup
        String name = "test7";
        mgmtFacade.addPlayerToSystem(new Player(name, "1", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "2", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "3", name, DeckType.Buzz));

        // action
        List<Player> players = mgmtFacade.getPlayersFromSystem();

        // assert
        assertEquals(players.size(), 3);
    }
}
