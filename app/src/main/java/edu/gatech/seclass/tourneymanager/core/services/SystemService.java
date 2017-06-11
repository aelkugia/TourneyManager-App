package edu.gatech.seclass.tourneymanager.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Prize;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;

/**
 * Created by justinp on 2/25/17.
 */

public final class SystemService {
    public Player addPlayer(Player player) throws Exception {
        if(Player.find(Player.class, "username = ?", player.getUsername()).size() <= 0) {
            try {
                long playerId = player.save();
                Player savedPlayer = Player.findById(Player.class, playerId);
                if(savedPlayer == null) {
                    throw new Exception("Error occured in inserting Player, player could not be found.");
                }
                return savedPlayer;
            } catch(Exception e){
                throw e;
            }
        } else {
            throw new Exception(String.format("Player with username %s already exists", player.getUsername()));
        }
    }

    public boolean removePlayer(Player player) throws Exception {
        if(Player.find(Player.class, "username = ?", player.getUsername()).size() > 0) {
            return player.delete();
        } else {
            throw new Exception("The player provided for delete does not exist.");
        }
    }

    public Player getPlayer(String username) {
        return Player.find(Player.class, "username = ?", username).get(0);
    }


    public List<Player> getPlayers() {
        return Player.listAll(Player.class);
    }

    public List<Integer> getPlayerTotals(String username) {
        Player player = Player.find(Player.class, "username = ?", username).get(0);
        List<Prize> prizes = Prize.find(Prize.class, "player_id = ?", String.valueOf(player.getId()));
        List<Integer> totals = new ArrayList<>();
        for(Prize prize: prizes) {
            totals.add(prize.getPrize());
        }
        return totals;
    }

    public List<Player> getPlayersTotals() {
        HashMap<Long, List<Integer>> prizeMap = new HashMap<>();
        Iterator<Prize> prizes = Prize.findAll(Prize.class);
        while(prizes.hasNext()) {
            Prize prize = prizes.next();
            if(prizeMap.containsKey(prize.getPlayerId())) {
                prizeMap.get(prize.getPlayerId()).add(prize.getPrize());
            } else {
                List<Integer> prizesPlayer = new ArrayList<Integer>();
                prizesPlayer.add(prize.getPrize());
                prizeMap.put(prize.getPlayerId(),  prizesPlayer);
            }
        }
        Iterator<Player> players = Player.findAll(Player.class);
        List<Player> playerResults = new ArrayList<>();
        while(players.hasNext()) {
            Player player = players.next();
            if(prizeMap.containsKey(player.getId())) {
                player.setPrizes(prizeMap.get(player.getId()));
            }
            playerResults.add(player);
        }
        return playerResults;
    }

    public List<Integer> getHouseWinnings() {
        List<TournamentResult> results = TournamentResult.find(TournamentResult.class, "1 = 1");
        List<Integer> output = new ArrayList<>();
        for(TournamentResult result: results) {
            output.add(result.getProfit());
        }
        return output;
    }
}
