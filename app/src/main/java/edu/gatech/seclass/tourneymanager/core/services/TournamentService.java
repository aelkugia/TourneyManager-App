package edu.gatech.seclass.tourneymanager.core.services;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Prize;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentPlayer;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;

/**
 * Created by justinp on 2/25/17.
 */

public final class TournamentService {
    private CalculationService calcService;

    public TournamentService() {
        calcService = new CalculationService();
    }

    public TournamentResult simulateTournamentResult(Tournament tournament) throws Exception {
        calcService.setEntranceFee(tournament.getEntryFee());
        calcService.setHousePercentage(tournament.getHouseCut());
        calcService.setEntrants(tournament.getPlayers().size());

        return new TournamentResult(
            calcService.getHouseProfits(),
            calcService.getPrize(CalculationService.Prizes.FIRST),
            calcService.getPrize(CalculationService.Prizes.SECOND),
            calcService.getPrize(CalculationService.Prizes.THIRD));
    }

    public long saveTournament(Tournament tournament) {
        long id = tournament.save();
        if(id > 0) {
            for (TournamentPlayer player : tournament.getPlayers()) {
                player.setTourneyId(tournament.getId());
                player.save();
            }
        }
        return id;
    }

    public void runTournament(Tournament tournament) throws Exception {
        int activeCount = Tournament.find(Tournament.class, "active = ?", "1").size();
        if(activeCount > 0) {
            throw new Exception("Cannot start Tournament when an existing Tournament is ongoing.");
        }

        if(!validatePlayerCount(tournament)) {
            throw new Exception(String.format("Too many or too few players in tournament, must be either %s or %s players", tournament.MIN_PLAYERS, tournament.MAX_PLAYERS));
        }
        if(tournament.getRound(tournament.getActiveRound()).size() > 0) {
            throw new Exception("This is a tournament that was already ended.");
        }

        tournament.setActive(true);
        tournament.save();

        // create initial round of matches
        createRound(TournamentPlayer.find(TournamentPlayer.class, "tourney_id = ?", String.valueOf(tournament.getId())), tournament.getId(), false, 0);
    }

    public boolean startNextRoundIfPossible() {
        List<Tournament> tournaments = Tournament.find(Tournament.class, "active = 1");
        if(tournaments.size() <= 0) {
            return false;
        }
        Tournament tournament = tournaments.get(0);
        int nextRound = tournament.getActiveRound() + 1;
        List<Match> lastRound = Match.find(Match.class, "round = ? AND tourney_id = ? AND winner > 0", String.valueOf(tournament.getActiveRound()), String.valueOf(tournament.getId()));
        if(!checkRoundBounds(nextRound, (lastRound.size() * 2)) ||
            !checkifRoundIsDone(lastRound, tournament.getId(), tournament.getActiveRound())) {
            return false;
        }

        List<TournamentPlayer> winners = new ArrayList<>();
        for(Player player: getWinners(lastRound)) {
            winners.add(new TournamentPlayer(tournament.getId(), player.getUsername()));
        }
        if(checkIfFinalRound(winners.size())) {
            // since this is final round add the losers too for the third place match.
            for(Player player: getLosers(lastRound)) {
                winners.add(new TournamentPlayer(tournament.getId(), player.getUsername()));
            }
            createRound(winners, tournament.getId(), true, nextRound);
        } else {
            createRound(winners, tournament.getId(), false, nextRound);
        }
        tournament.setActiveRound(nextRound);
        tournament.save();
        return true;
    }

    public List<TournamentResult> getAllPrizesAndProfits() {
        return TournamentResult.listAll(TournamentResult.class, "id DESC");
    }

    public List<Match> getReadyMatches() {
        Tournament tournament = Tournament.find(Tournament.class, "active = 1").get(0);
        if(tournament == null) {
            return null;
        }

        return tournament.getRound(tournament.getActiveRound());
    }

    public void startMatch(Match match) throws Exception {
        if(match.isStarted()) {
            throw new Exception("Match already started.");
        }
        match.setStarted(true);
        match.save();
    }

    public void endMatch(Match match, Player winner) throws Exception {
        if(match.isStarted() && match.getWinner() == null) {
            match.setWinner(winner);
            match.save();
        } else {
            throw new Exception("Match should be started and not ended in order to be ended.");
        }

    }

    public TournamentResult endTournament() throws Exception {
        Tournament tournament = Tournament.find(Tournament.class, "active = 1").get(0);
        if(tournament == null) {
            throw new Exception("No ongoing tournament.");
        }
        tournament.setActive(false);
        if(!tournamentEndedEarly(tournament)) {
            TournamentResult results = simulateTournamentResult(tournament);
            dispensePrizesAndProfits(results, tournament);
            tournament.save();
            results.save();
            return results;
        }
        tournament.save();
        return null;
    }

    private void dispensePrizesAndProfits(TournamentResult results, Tournament tournament) {
        List<Match> finalMatches = tournament.getRound(tournament.getActiveRound());
        for(Match match: finalMatches) {
            if(match.isFinalMatch()) {
                Player firstPlace = match.getWinner();
                long p1 = match.getPlayer1().getId();
                long p2 = match.getPlayer2().getId();
                (new Prize(firstPlace.getId(), results.getFirstPrize())).save();
                long secondPlace = firstPlace.getId() != p1 ? p2 : p1;
                (new Prize(secondPlace, results.getSecondPrize())).save();
            } else {
                (new Prize(match.getWinnerId(), results.getThirdPrize())).save();
            }
        }
    }

    private boolean validatePlayerCount(Tournament tournament) {
        return tournament.getPlayers().size() == tournament.MAX_PLAYERS || tournament.getPlayers().size() == tournament.MIN_PLAYERS;
    }

    private void createRound(List<TournamentPlayer> players, long tournamentId, boolean finalRound, int round) {
        List<Match> matches = new ArrayList<>();
        if(finalRound) {
            // final round, assume 0,1 are the first place /second place match and 2,3 are the third place match.
            matches.add(new Match(players.get(0).getPlayer(), players.get(1).getPlayer(), false, tournamentId, true, false, round));
            matches.add(new Match(players.get(2).getPlayer(), players.get(3).getPlayer(), false, tournamentId, false, true, round));
        } else {
            // create matches
            for (int i = 0; i < players.size(); i = i + 2) {
                matches.add(new Match(players.get(i).getPlayer(), players.get(i + 1).getPlayer(), false, tournamentId, false, false, round));
            }
        }
        // save matches
        for(Match match: matches) {
            match.save();
        }
    }

    private boolean tournamentEndedEarly(Tournament tournament){
        boolean isFinalRound = false;
        int completedMatches = 0;
        List<Match> matches = tournament.getRound(tournament.getActiveRound());
        for(Match match: matches) {
            if(match.isFinalMatch() || match.isThirdPlaceMatch()) {
                isFinalRound = true;
            }
            if(match.getWinnerId() > 0) {
                completedMatches++;
            }
        }
        return !isFinalRound || !(completedMatches == matches.size());
    }

    private boolean checkifRoundIsDone(List<Match> matches, long tournamentId, int round) {
        List<Match> allMatchesFromRound = Match.find(Match.class,
                "tourney_id = ? and round = ?",
                String.valueOf(tournamentId),
                String.valueOf(round));
        int unfinishedRoundCount = allMatchesFromRound.size() - matches.size();
        if(unfinishedRoundCount > 0) {
            return false;
        }
        return true;
    }

    private boolean checkIfFinalRound(long playerCount) {
        if(playerCount == 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkRoundBounds(int round, int playerCount) {
        if(round >= 0 && round <= Math.round(Math.log(playerCount) / Math.log(2))) {
            return true;
        } else {
            return false;
        }
    }

    private List<Player> getWinners(List<Match> lastRound) {
        List<Player> winners = new ArrayList<>();
        for(Match match: lastRound) {
            winners.add(match.getWinner());
        }
        return winners;
    }

    private List<Player> getLosers(List<Match> lastRound) {
        List<Player> losers = new ArrayList<>();
        for(Match match: lastRound) {
            Player player1 = match.getPlayer1();
            Player player2 = match.getPlayer2();
            Player winner = match.getWinner();

            if(winner.getId() != player2.getId()) {
                losers.add(player1);
            } else {
                losers.add(player2);
            }
        }
        return losers;
    }

    public boolean isTournamentActive() {
        List<Tournament> tournaments = Tournament.find(Tournament.class, "active = 1");
        return tournaments.size() > 0;
    }
}
