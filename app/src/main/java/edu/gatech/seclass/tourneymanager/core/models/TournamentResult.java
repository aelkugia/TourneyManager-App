package edu.gatech.seclass.tourneymanager.core.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by justinp on 2/25/17.
 */

public class TournamentResult extends SugarRecord implements Serializable {
    private int profit;
    private int firstPrize;
    private int secondPrize;
    private int thirdPrize;

    public TournamentResult() {}

    public TournamentResult(int profit, int firstPrize, int secondPrize, int thirdPrize) {
        this.profit = profit;
        this.firstPrize = firstPrize;
        this.secondPrize = secondPrize;
        this.thirdPrize = thirdPrize;
    }

    public int getProfit() {
        return profit;
    }

    public int getFirstPrize() {
        return firstPrize;
    }

    public int getSecondPrize() {
        return secondPrize;
    }

    public int getThirdPrize() {
        return thirdPrize;
    }
}
