package edu.gatech.seclass.tourneymanager.core.models;

import com.orm.SugarRecord;

/**
 * Created by justinp on 3/3/17.
 */

public class TournamentPlayer extends SugarRecord {
    private long tourneyId;
    private String username;

    public TournamentPlayer() {}

    public TournamentPlayer(long tourneyId, String username) {
        this.tourneyId = tourneyId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Player getPlayer() {
        return Player.find(Player.class, "username = ?", username).get(0);
    }

    public void setTourneyId(long id) {
        tourneyId = id;
    }
}
