package edu.gatech.seclass.tourneymanager.core.services;

/**
 * Created by justinp on 2/3/17.
 * Simple model that contains all of the calculations used by the app
 */

public class CalculationService {
    // instance variables
    private int entranceFee;
    private int entrants;
    private int housePercentage;
    private final double FIRST_PLACE = 0.5;
    private final double SECOND_PLACE = 0.3;
    private final double THIRD_PLACE = 0.2;

    // prize placing enum
    public enum Prizes {
        FIRST,
        SECOND,
        THIRD
    }

    /**
     * set the entrance fee
     * @param entranceFee the entrance fee
     * @throws Exception throws if the entrance fee arg is less than zero
     */
    public void setEntranceFee(int entranceFee) throws Exception {
        if(entranceFee < 0){
            throw new Exception("Entrance fee must be positive.");
        }
        this.entranceFee = entranceFee;
    }

    /**
     * sets the number of entrants
     * @param entrants the number of entrants
     * @throws Exception throws if the number of entrants is less than 3
     */
    public void setEntrants(int entrants) throws Exception {
        if(entrants < 3) {
            throw new Exception("Entrants must be 3 or greater.");
        }
        this.entrants = entrants;
    }

    /**
     * sets the house percentage
     * @param housePercentage the house percentage
     * @throws Exception throws if the house percentage is greater than 100 or less than 0
     */
    public void setHousePercentage(int housePercentage) throws Exception {
        if(housePercentage > 100 || 0 > housePercentage) {
            throw new Exception("House percentage must be between 0 and 100.");
        }
        this.housePercentage = housePercentage;
    }

    /**
     * returns the house cut based on the entrance fee, extrants, and house percentage
     * @return house cut
     */
    public int getHouseProfits() {
       return  calculateHouseCut();
    }

    /**
     * returns  the prize calculation for the provided prize place
     * @param prize Prize placing
     * @return Prize calculation
     * @throws Exception throws if prize is null
     */
    public int getPrize(Prizes prize) throws Exception {
        switch(prize) {
            case FIRST:
                return getPrize(FIRST_PLACE);
            case SECOND:
                return getPrize(SECOND_PLACE);
            case THIRD:
                return getPrize(THIRD_PLACE);
            default:
                throw new Exception("Unknown prize given as argument when valid prize was expected.");
        }
    }

    private int calculateHouseCut() {
        return (int) Math.round((calculateGross() * housePercentage) / 100);
    }

    private int calculateGross() {
        return entranceFee * entrants;
    }

    private int getPrize(double percentage) {
        return (int) Math.round((calculateGross() - calculateHouseCut()) * percentage);
    }
}
