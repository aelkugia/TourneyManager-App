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
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentPlayer;

import static junit.framework.Assert.assertEquals;

/**
 * Created by justinp on 3/8/17.
 */

@RunWith(AndroidJUnit4.class)
public class MatchManagementIntegrationTests extends SugarApp {
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
    public void GivenATournamentExist_whenStartingAMatch_ShouldStartTheMatch() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);

        Match match = tournament.getRound(0).get(0);

        mgmtFacade.startMatch(match);

        Match startedMatch = Match.find(Match.class, "started = ? AND round = 0 AND tourney_id = ?", "1", String.valueOf(id)).get(0);

        assertEquals(match.getId(), startedMatch.getId());
    }

    @Test(expected = Exception.class)
    public void GivenAMatchHasStarted_whenStartingAMatch_ShouldThrowException() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);

        Match match = tournament.getRound(0).get(0);

        mgmtFacade.startMatch(match);

        mgmtFacade.startMatch(match);
    }

    @Test
    public void GivenATournamentExist_whenEndingAMatch_ShouldEndTheMatch() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);

        Match match = tournament.getRound(0).get(0);

        mgmtFacade.startMatch(match);
        mgmtFacade.endMatch(match, match.getPlayer1());

        Match endedMatch = Match.find(Match.class, "winner = ? AND round = 0 AND tourney_id = ?", String.valueOf(match.getWinner().getId()), String.valueOf(id)).get(0);

        assertEquals(match.getId(), endedMatch.getId());
    }

    @Test(expected = Exception.class)
    public void GivenAnUnStartedMatch_whenEndingAMatch_ShouldThrowException() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);

        Match match = tournament.getRound(0).get(0);

        mgmtFacade.endMatch(match, match.getPlayer1());
    }

    @Test(expected = Exception.class)
    public void GivenAnEndedMatch_whenEndingAMatch_ShouldThrowException() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);

        Match match = tournament.getRound(0).get(0);

        mgmtFacade.startMatch(match);
        mgmtFacade.endMatch(match, match.getPlayer1());
        mgmtFacade.endMatch(match, match.getPlayer2());
    }

    @Test
    public void GivenCurrentRoundMatchesEnded_whenStartingANewRound_ShouldStartNewRound() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);
        int currentRound = tournament.getActiveRound();

        List<Match> matches =tournament.getRound(tournament.getActiveRound());

        for(Match match: matches) {
            mgmtFacade.startMatch(match);
            mgmtFacade.endMatch(match, match.getPlayer2());
        }

        boolean startedNextRound = mgmtFacade.startNextRoundIfPossible();

        List<Match> nextMatches = tournament.getRound(tournament.getActiveRound());

        assertEquals(true, startedNextRound);
        assertEquals(matches.size() / 2, nextMatches.size());
        assertEquals(currentRound + 1, tournament.getActiveRound());
    }

    @Test
    public void GivenTheNextRoundIsFinal_whenStartingANewRound_ShouldStartNewRound() throws Exception {

        long id = tournament.save();

        mgmtFacade.runTournament(tournament);
        int currentRound = tournament.getActiveRound();

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

        boolean startedFinalRound = mgmtFacade.startNextRoundIfPossible();

        List<Match> finalMatches = tournament.getRound(tournament.getActiveRound());

        assertEquals(true, startedNextRound);
        assertEquals(nextMatches.size(), finalMatches.size());
        assertEquals(currentRound + 2, tournament.getActiveRound());
    }
}
