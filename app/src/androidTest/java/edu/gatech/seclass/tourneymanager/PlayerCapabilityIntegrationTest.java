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
import edu.gatech.seclass.tourneymanager.core.capabilities.PlayerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.DeckType;
import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentPlayer;

import static junit.framework.Assert.assertEquals;

/**
 * Created by justinp on 3/10/17.
 */

@RunWith(AndroidJUnit4.class)
public class PlayerCapabilityIntegrationTest extends SugarApp {
    private ManagerCapabilityFacade mgmtFacade;
    private Tournament tournament;

    @BeforeClass
    public static void setUp() throws Exception {
        // SugarORM need some time to start up, else tests will fail
        Thread.sleep(3000);
    }

    @Before
    public void setupEach() throws Exception {
        Player.deleteAll(Player.class);
        TournamentPlayer.deleteAll(TournamentPlayer.class);
        Tournament.deleteAll(Tournament.class);
        Match.deleteAll(Match.class);
        mgmtFacade = new ManagerCapabilityFacade();

        // setup
        String name = "test8";

        // add players to system

        mgmtFacade.addPlayerToSystem(new Player(name, "1", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "2", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "3", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "4", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "5", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "6", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "7", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "8", name, DeckType.Buzz));

        // create tournament
        tournament = new Tournament(0, 0, false, 0);

        // add players to tournament
        List<Player> players = mgmtFacade.getPlayersFromSystem();
        tournament.addPlayer(players.get(0));
        tournament.addPlayer(players.get(1));
        tournament.addPlayer(players.get(2));
        tournament.addPlayer(players.get(3));
        tournament.addPlayer(players.get(4));
        tournament.addPlayer(players.get(5));
        tournament.addPlayer(players.get(6));
        tournament.addPlayer(players.get(7));
    }

    @After
    public void teardownEach() {
        mgmtFacade = null;
        tournament = null;
    }

    @AfterClass
    public static void teardown() {
        Player.deleteAll(Player.class);
        TournamentPlayer.deleteAll(TournamentPlayer.class);
        Tournament.deleteAll(Tournament.class);
        Match.deleteAll(Match.class);
    }

    @Test
    public void GivenATournamentIsActive_WhenLoggedInAsPlayer__TheListOfMatchesShouldBeDisplayed() throws Exception {
        PlayerCapabilityFacade playerFacade = new PlayerCapabilityFacade();
        mgmtFacade.runTournament(tournament);
        List<Match> matches = playerFacade.getMatchList();

        assertEquals(4, matches.size());
    }

    @Test
    public void GivenATournamentIsActive_WhenCheckingForActiveTournaments__TheReturnIsTrue() throws Exception {
        PlayerCapabilityFacade playerFacade = new PlayerCapabilityFacade();
        mgmtFacade.runTournament(tournament);

        assertEquals(true, playerFacade.isTournamentActive());
    }

    @Test
    public void GivenATournamentIsNoteActive_WhenCheckingForActiveTournaments__TheReturnIsFalse() throws Exception {
        PlayerCapabilityFacade playerFacade = new PlayerCapabilityFacade();

        assertEquals(false, playerFacade.isTournamentActive());
    }
}
