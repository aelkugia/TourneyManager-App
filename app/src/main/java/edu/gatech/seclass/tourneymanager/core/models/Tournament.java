package edu.gatech.seclass.tourneymanager.core.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

import static java.lang.Math.log;

/**
 * Created by justinp on 2/25/17.
 */
// todo: builder pattern would work really well here.
public class Tournament extends SugarRecord implements Serializable {
    @Ignore
    private List<List<Match>> rounds;
    @Ignore
    private List<TournamentPlayer> players;
    private int activeRound;
    private int houseCut;
    private int entryFee;
    @Ignore
    public final int MIN_PLAYERS = 8;
    @Ignore
    public final int MAX_PLAYERS = 16;
    private boolean active;

    public Tournament() {}

    public Tournament(int houseCut, int entryFee, boolean active, int activeRound) throws Exception {
        setHouseCut(houseCut);
        setEntryFee(entryFee);
        this.active = active;
        this.activeRound = activeRound;
        this.players = TournamentPlayer.find(TournamentPlayer.class, "tourney_id = ?", String.valueOf(getId()));
    }

    public int getHouseCut() {
        return houseCut;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public List<TournamentPlayer> getPlayers() {
        return (players = players == null ?
                TournamentPlayer.find(TournamentPlayer.class, "tourney_id = ?", String.valueOf(getId())) :
                players);
    }

    public int getActiveRound() {
        return activeRound;
    }

    // todo: this may need a deep copy to create recursive immutable lists.
    public List<Match> getRound(int round) {
        return Match.find(Match.class, "tourney_id = ? AND round = ?", String.valueOf(getId()), String.valueOf(round));
    }

    public void addRound(int round, List<Match> matches) throws Exception {
        // todo, this should really exist some place else, not in the model.
        boolean roundHasNotBeenAdded = rounds.size() < round;
        boolean roundIsHalfSizeOfPreviousOrFirstRound = round == 0 || rounds.get(round - 1).size() == (matches.size() * 2);
        boolean roundCountIsUnderMax = MAX_PLAYERS / 2 >= matches.size();
        boolean roundIsUnderMaxNumberOfRounds = ((int) log((double) MAX_PLAYERS) / log(2)) <= round;
        if(roundHasNotBeenAdded && roundIsHalfSizeOfPreviousOrFirstRound && roundIsUnderMaxNumberOfRounds && roundCountIsUnderMax) {
            rounds.add(round, matches);
            return;
        }
        throw new Exception("Could have failed for a bunch of reasons.");
    }

    public void setPlayers(List<TournamentPlayer> players){
        this.players = players;
    }

    public void setHouseCut(int houseCut) throws Exception {
        if(houseCut > 100 || houseCut < 0) {
            throw new Exception("House cut must be between 0 and 100.");
        }
        this.houseCut = houseCut;
    }

    public void setEntryFee(int entryFee) throws Exception {
        if(entryFee < 0){
            throw new Exception("Entrance fee must be positive.");
        }
        this.entryFee = entryFee;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addPlayer(Player player) throws Exception {
        if(getPlayers().size() < MAX_PLAYERS) {
            players.add(new TournamentPlayer(-1, player.getUsername()));
            return;
        }

        throw new Exception(String.format("Tournament already contains %s players, cannot add more players.", MAX_PLAYERS));
    }

    public void removePlayer(Player player) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).equals(player.getUsername())) {
                players.remove(i);
                break;
            }
        }
    }

    @Override
    public long save() {
        if(players != null && players.size() != MAX_PLAYERS && players.size() != MIN_PLAYERS) {
            return -1;
        }
        long id = super.save();
        if(players != null) {
            for(TournamentPlayer tPlayer: players) {
                tPlayer.setTourneyId(id);
                tPlayer.save();
            }
        }
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActiveRound(int activeRound) {
        this.activeRound = activeRound;
    }
}
