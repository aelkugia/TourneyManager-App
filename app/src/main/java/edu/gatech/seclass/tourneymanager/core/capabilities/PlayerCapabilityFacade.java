package edu.gatech.seclass.tourneymanager.core.capabilities;

import java.util.List;

import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.services.SystemService;
import edu.gatech.seclass.tourneymanager.core.services.TournamentService;

/**
 * Created by justinp on 2/25/17.
 */

public final class PlayerCapabilityFacade {
    private TournamentService tournamentService;
    private SystemService systemService;
    public PlayerCapabilityFacade() {
        tournamentService = new TournamentService();
        systemService = new SystemService();
    }
    public List<Match> getMatchList() {
        return tournamentService.getReadyMatches();
    }

    public List<Integer> getTotalPrizes(String username) {
        return systemService.getPlayerTotals(username);
    }

    public List<Player> getAllPlayerTotals() {
        return systemService.getPlayersTotals();
    }

    public boolean isTournamentActive() {
        return tournamentService.isTournamentActive();
    }
}
