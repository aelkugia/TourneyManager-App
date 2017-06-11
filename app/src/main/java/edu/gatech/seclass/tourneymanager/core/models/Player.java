package edu.gatech.seclass.tourneymanager.core.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * Created by justinp on 2/25/17.
 */

public class Player extends SugarRecord {
    private String name;
    @Unique
    private String username;
    private String phoneNumber;
    @Ignore
    private List<Integer> prizes;
    private DeckType deck;

    public Player() {}

    public Player(String name, String username, String phoneNumber, DeckType deck) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;

        this.deck = deck;
    }

    public DeckType getDeck() { return deck; }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setPrizes(List<Integer> prizes) {
        this.prizes = prizes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getPrizes() {
        return prizes;
    }
}
