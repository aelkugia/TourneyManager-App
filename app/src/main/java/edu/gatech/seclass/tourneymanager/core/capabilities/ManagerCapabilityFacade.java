package edu.gatech.seclass.tourneymanager.core.capabilities;

import java.util.List;

import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;
import edu.gatech.seclass.tourneymanager.core.services.SystemService;
import edu.gatech.seclass.tourneymanager.core.services.TournamentService;

/**
 * Created by justinp on 2/25/17.
 */

public final class ManagerCapabilityFacade {
    private SystemService systemService;
    private TournamentService tournamentService;

    public ManagerCapabilityFacade() {
        systemService = new SystemService();
        tournamentService = new TournamentService();
    }
    public void addPlayerToSystem(Player player) throws Exception {
        systemService.addPlayer(player);
    }

    public void removePlayerFromSystem(Player player) throws Exception {
        systemService.removePlayer(player);
    }

    public List<Player> getPlayersFromSystem() {
        return systemService.getPlayers();
    }

    public long saveTournament(Tournament tournament) {
        return tournamentService.saveTournament(tournament);
    }

    public TournamentResult simulateTournamentResult(Tournament tournament) throws Exception {
        return tournamentService.simulateTournamentResult(tournament);
    }

    public void runTournament(Tournament tournament) throws Exception {
        tournamentService.runTournament(tournament);
    }

    public List<TournamentResult> getPrizesAndProfits() {
        return tournamentService.getAllPrizesAndProfits();
    }

    public List<Match> getReadyMatches() {
        return tournamentService.getReadyMatches();
    }

    public void startMatch(Match match) throws Exception {
        tournamentService.startMatch(match);
    }

    public void endMatch(Match match, Player winner) throws Exception {
        tournamentService.endMatch(match, winner);
    }

    // todo: the return val needs to be updated in the UML Class Diagram
    public TournamentResult endTournament() throws Exception {
        return tournamentService.endTournament();
    }

    public List<Player> getAllPlayerTotals() {
        return systemService.getPlayersTotals();
    }

    public boolean startNextRoundIfPossible() {
        return tournamentService.startNextRoundIfPossible();
    }

    public List<Integer> getHouseWinnings() {
        return systemService.getHouseWinnings();
    }
}
