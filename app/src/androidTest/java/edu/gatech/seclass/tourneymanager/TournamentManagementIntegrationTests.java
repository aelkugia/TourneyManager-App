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
import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Prize;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentPlayer;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by justinp on 3/8/17.
 */
@RunWith(AndroidJUnit4.class)
public class TournamentManagementIntegrationTests extends SugarApp {
    private ManagerCapabilityFacade mgmtFacade;

    @BeforeClass
    public static void setUp() throws Exception {
        // SugarORM need some time to start up, else tests will fail
        Thread.sleep(3000);
    }

    @Before
    public void setupEach() {
        Player.deleteAll(Player.class);
        TournamentPlayer.deleteAll(TournamentPlayer.class);
        Tournament.deleteAll(Tournament.class);
        Match.deleteAll(Match.class);
        mgmtFacade = new ManagerCapabilityFacade();
    }

    @After
    public void teardownEach() {
        mgmtFacade = null;
    }

    @AfterClass
    public static void teardown() {
        Player.deleteAll(Player.class);
        TournamentPlayer.deleteAll(TournamentPlayer.class);
        Tournament.deleteAll(Tournament.class);
        Match.deleteAll(Match.class);
        Prize.deleteAll(Prize.class);
    }

    @Test(expected = Exception.class)
    public void GivenLowEntryFee_whenCreatingATournament_exceptionIsThrown() throws Exception {
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
        Tournament tournament = new Tournament(25, -1, false, 0);
    }

    @Test(expected = Exception.class)
    public void GivenOverUpperBoundaryHouseCut_whenCreatingATournament_exceptionIsThrown() throws Exception {
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
        Tournament tournament = new Tournament(101, 0, false, 0);
    }

    @Test(expected = Exception.class)
    public void GivenUnderLowerBoundaryHouseCut_whenCreatingATournament_exceptionIsThrown() throws Exception {
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
        Tournament tournament = new Tournament(-1, 0, false, 0);
    }

    @Test
    public void GivenValidInput_whenCreatingATournament_TournamentShouldBeCreated() throws Exception {
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
        Tournament tournament = new Tournament(0, 0, false, 0);
    }

    @Test
    public void GivenATournament_whenSavingATournament_TournamentShouldBeSaved() throws Exception {
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
        Tournament tournament = new Tournament(0, 0, false, 0);

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

        // action
        long id = mgmtFacade.saveTournament(tournament);

        int tournamentCount = Tournament.find(Tournament.class, "id = ?", String.valueOf(id)).size();

        assertEquals(1, tournamentCount);
    }

    @Test
    public void GivenATournamentWithNoPlayers_whenSavingATournament_ShouldThrowException() throws Exception {
        // setup
        String name = "test8";

        // create tournament
        Tournament tournament = new Tournament(0, 0, false, 0);

        // action
        long id = mgmtFacade.saveTournament(tournament);

        // assert
        assertEquals(id, -1);
    }

    @Test
    public void GivenATournamentWithTooFewPlayers_whenSavingATournament_ShouldThrowException() throws Exception {
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

        // create tournament
        Tournament tournament = new Tournament(0, 0, false, 0);

        // add players to tournament
        List<Player> players = mgmtFacade.getPlayersFromSystem();
        tournament.addPlayer(players.get(0));
        tournament.addPlayer(players.get(1));
        tournament.addPlayer(players.get(2));
        tournament.addPlayer(players.get(3));
        tournament.addPlayer(players.get(4));
        tournament.addPlayer(players.get(5));
        tournament.addPlayer(players.get(6));

        // action
        long id = mgmtFacade.saveTournament(tournament);

        assertEquals(id, -1);
    }

    @Test
    public void GivenATournamentExist_whenRunningATournament_RoundZeroMatchesAreCreated() throws Exception {
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
        Tournament tournament = new Tournament(0, 0, false, 0);

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

        long id = mgmtFacade.saveTournament(tournament);

        Tournament savedTournament = Tournament.find(Tournament.class, "id = ?", String.valueOf(id)).get(0);

        mgmtFacade.runTournament(tournament);

        int matchCount = savedTournament.getRound(0).size();
        // saved for round count
        //assertEquals((int) Math.round(Math.log(players.size()) / Math.log(2)), matchCount);
        assertEquals(players.size() / 2, matchCount);
    }

    @Test(expected = Exception.class)
    public void GivenATournamentIsActive_whenRunningATournament_ShouldThrowException() throws Exception {
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
        Tournament tournament = new Tournament(0, 0, true, 0);

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

        long id = mgmtFacade.saveTournament(tournament);

        Tournament savedTournament = Tournament.find(Tournament.class, "id = ?", String.valueOf(id)).get(0);

        mgmtFacade.runTournament(tournament);
    }

    @Test
    public void GivenAValidTournament_whenSimulatingResults_resultsAreReturned() throws Exception {
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
        Tournament tournament = new Tournament(25, 25, false, 0);

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

        // action
        TournamentResult result = mgmtFacade.simulateTournamentResult(tournament);

        // assert
        assertEquals(result.getProfit(), 50);
        assertEquals(result.getFirstPrize(), 75);
        assertEquals(result.getSecondPrize(), 45);
        assertEquals(result.getThirdPrize(), 30);
    }

    @Test(expected = Exception.class)
    public void GivenTooFewPlayers_whenSimulatingResults_exceptionIsThrown() throws Exception {
        // setup
        String name = "test8";

        // add players to system
        mgmtFacade.addPlayerToSystem(new Player(name, "1", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "2", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "3", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "4", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "5", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "6", name, DeckType.Buzz));

        // create tournament
        Tournament tournament = new Tournament(25, 25, false, 0);

        // add players to tournament
        List<Player> players = mgmtFacade.getPlayersFromSystem();
        tournament.addPlayer(players.get(0));
        tournament.addPlayer(players.get(1));
        tournament.addPlayer(players.get(2));
        tournament.addPlayer(players.get(3));
        tournament.addPlayer(players.get(4));
        tournament.addPlayer(players.get(5));
        tournament.addPlayer(players.get(6));

        // action
        TournamentResult result = mgmtFacade.simulateTournamentResult(tournament);
    }

    @Test(expected = Exception.class)
    public void GivenIncorrectNumberOfPlayers_whenSimulatingResults_exceptionIsThrown() throws Exception {
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
        Tournament tournament = new Tournament(25, 25, false, 0);

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
        tournament.addPlayer(players.get(8));

        // action
        TournamentResult result = mgmtFacade.simulateTournamentResult(tournament);
    }

    @Test(expected = Exception.class)
    public void GivenTooManyPlayers_whenSimulatingResults_exceptionIsThrown() throws Exception {
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
        mgmtFacade.addPlayerToSystem(new Player(name, "9", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "10", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "11", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "12", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "13", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "14", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "15", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "16", name, DeckType.Buzz));
        mgmtFacade.addPlayerToSystem(new Player(name, "17", name, DeckType.Buzz));

        // create tournament
        Tournament tournament = new Tournament(25, 25, false, 0);

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
        tournament.addPlayer(players.get(8));
        tournament.addPlayer(players.get(9));
        tournament.addPlayer(players.get(10));
        tournament.addPlayer(players.get(11));
        tournament.addPlayer(players.get(12));
        tournament.addPlayer(players.get(13));
        tournament.addPlayer(players.get(14));
        tournament.addPlayer(players.get(15));
        tournament.addPlayer(players.get(16));

        // action
        TournamentResult result = mgmtFacade.simulateTournamentResult(tournament);
    }

    @Test
    public void GivenAValidTournament_whenEndingATheTournament_resultsAreReturned() throws Exception {
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
        Tournament tournament = new Tournament(25, 25, false, 0);

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

        // action
        mgmtFacade.saveTournament(tournament);

        mgmtFacade.runTournament(tournament);

        List<Match> matches =tournament.getRound(tournament.getActiveRound());

        for(Match match: matches) {
            mgmtFacade.startMatch(match);
            mgmtFacade.endMatch(match, match.getPlayer2());
        }

        boolean startedNextRound = mgmtFacade.startNextRoundIfPossible();

        List<Match> nextMatches = tournament.getRound(tournament.getActiveRound());

        for(Match match: nextMatches) {
            mgmtFacade.startMatch(match);
            mgmtFacade.endMatch(match, match.getPlayer1());
        }

        boolean finalNextRound = mgmtFacade.startNextRoundIfPossible();

        List<Match> finalMatches = tournament.getRound(tournament.getActiveRound());

        for(Match match: finalMatches) {
            mgmtFacade.startMatch(match);
            mgmtFacade.endMatch(match, match.getPlayer1());
        }

        TournamentResult result = mgmtFacade.endTournament();

        // assert
        assertEquals(result.getProfit(), 50);
        assertEquals(result.getFirstPrize(), 75);
        assertEquals(result.getSecondPrize(), 45);
        assertEquals(result.getThirdPrize(), 30);
    }
}
