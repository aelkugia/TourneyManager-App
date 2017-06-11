package edu.gatech.seclass.tourneymanager.core.models;

import com.orm.SugarRecord;

/**
 * Created by justinp on 3/3/17.
 */

public class Prize extends SugarRecord {
    private long playerId;
    private int prize;

    public Prize() {}

    public Prize(long playerId, int prize) {
        this.playerId = playerId;
        this.prize = prize;
    }

    public long getPlayerId() {
        return playerId;
    }

    public int getPrize() {
        return prize;
    }
}
