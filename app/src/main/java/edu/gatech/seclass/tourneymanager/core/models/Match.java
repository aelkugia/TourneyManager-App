package edu.gatech.seclass.tourneymanager.core.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by justinp on 2/25/17.
 */

public class Match extends SugarRecord {
    private Player player1;
    private Player player2;
    private long winner;
    private boolean started;
    private long tourneyId;
    private boolean finalMatch;
    private boolean thirdPlaceMatch;
    private int round;

    public Match() {}

    public Match(Player player2, Player player1, boolean started, long tourneyId, boolean finalMatch, boolean thirdPlaceMatch, int round) {
        this.player2 = player2;
        this.player1 = player1;
        this.started = started;
        this.tourneyId = tourneyId;
        this.finalMatch = finalMatch;
        this.thirdPlaceMatch = thirdPlaceMatch;
        this.round = round;
    }

    public Match(Player player2, Player player1, boolean started, long tourneyId, boolean finalMatch, boolean thirdPlaceMatch, int round, long winner) {
        this.player2 = player2;
        this.player1 = player1;
        this.started = started;
        this.tourneyId = tourneyId;
        this.finalMatch = finalMatch;
        this.winner = winner;
        this.thirdPlaceMatch = thirdPlaceMatch;
        this.round = round;
    }

    public Player getPlayer1() {
        return player1;
    }

    public int getRound() {
        return round;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setWinner(Player player) throws Exception {
        if(player.getUsername().equals(player1.getUsername()) || player.getUsername().equals(player2.getUsername())) {
            winner = player.getId();
        } else {
            throw new Exception("Player was not in match.");
        }
    }

    public Player getWinner() {
        List<Player> playerList = Player.find(Player.class, "id = ?", String.valueOf(winner));
        if(playerList.size() > 0) {
            return Player.find(Player.class, "id = ?", String.valueOf(winner)).get(0);
        }
        return null;
    }

    public boolean isStarted() {
        return started;
    }

    public long getTourneyId() {
        return tourneyId;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinalMatch() {
        return finalMatch;
    }

    public boolean isThirdPlaceMatch() {
        return thirdPlaceMatch;
    }

    public long getWinnerId() {
        return winner;
    }
}
