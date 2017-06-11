package edu.gatech.seclass.tourneymanager.unit_tests.core.services;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

import edu.gatech.seclass.tourneymanager.core.services.CalculationService;

/**
 * Simple unit test coverage for the calculationService and calculations
 */
public class CalculationService_UnitTest {
    private CalculationService calculationService;

    @Before
    public void setup() {
        calculationService = new CalculationService();
    }

    // rule 2.a: the house cut, computed as the house percentage of the total amount collected (i.e., entrance fee * number of entrants), rounded to the nearest integer
    @Test
    public void houseCut_isCorrect_happyPath() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(4);
        calculationService.setHousePercentage(25);

        // test
        Assert.assertEquals(3, calculationService.getHouseProfits());
    }

    // rule 2.a: the house cut, computed as the house percentage of the total amount collected (i.e., entrance fee * number of entrants), rounded to the nearest integer
    @Test
    public void houseCut_isCorrect_lowerHousePercentageBound() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(4);
        calculationService.setHousePercentage(0);

        // test
        Assert.assertEquals(0, calculationService.getHouseProfits());
    }

    // rule 2.a: the house cut, computed as the house percentage of the total amount collected (i.e., entrance fee * number of entrants), rounded to the nearest integer
    @Test
    public void houseCut_isCorrect_upperHousePercentageBound() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(4);
        calculationService.setHousePercentage(100);

        // test
        Assert.assertEquals(12, calculationService.getHouseProfits());
    }

    // rule 2.a: the house cut, computed as the house percentage of the total amount collected (i.e., entrance fee * number of entrants), rounded to the nearest integer
    @Test
    public void houseCut_isCorrect_largerNumbers() throws Exception {
        // setup
        calculationService.setEntrants(300);
        calculationService.setEntranceFee(400);
        calculationService.setHousePercentage(50);

        // test
        Assert.assertEquals(60000, calculationService.getHouseProfits());
    }

    // rule 2.a: the house cut, computed as the house percentage of the total amount collected (i.e., entrance fee * number of entrants), rounded to the nearest integer
    @Test
    public void houseCut_isCorrect_entranceFeeIsZero() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(0);
        calculationService.setHousePercentage(100);

        // test
        Assert.assertEquals(0, calculationService.getHouseProfits());
    }

    // rule 2.b :the amounts assigned to 1st, 2nd, and 3rd prices, computed as the 50%, 30% and 20% of
    // whatever remains after the house cut is removed from the total amount collected, also rounded
    // to the nearest integer (do not worry if prizes and house cut do not add up to the total–just
    // assume the house will make it work)
    @Test
    public void getPrize_isCorrect_happyPath() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(10);
        calculationService.setHousePercentage(0);

        // test
        Assert.assertEquals(15, calculationService.getPrize(CalculationService.Prizes.FIRST));
        Assert.assertEquals(9, calculationService.getPrize(CalculationService.Prizes.SECOND));
        Assert.assertEquals(6, calculationService.getPrize(CalculationService.Prizes.THIRD));
    }

    // rule 2.b :the amounts assigned to 1st, 2nd, and 3rd prices, computed as the 50%, 30% and 20% of
    // whatever remains after the house cut is removed from the total amount collected, also rounded
    // to the nearest integer (do not worry if prizes and house cut do not add up to the total–just
    // assume the house will make it work)
    @Test
    public void getPrize_isCorrect_housePercentageUpperBound() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(10);
        calculationService.setHousePercentage(100);

        // test
        Assert.assertEquals(0, calculationService.getPrize(CalculationService.Prizes.FIRST));
        Assert.assertEquals(0, calculationService.getPrize(CalculationService.Prizes.SECOND));
        Assert.assertEquals(0, calculationService.getPrize(CalculationService.Prizes.THIRD));
    }

    // rule 2.b :the amounts assigned to 1st, 2nd, and 3rd prices, computed as the 50%, 30% and 20% of
    // whatever remains after the house cut is removed from the total amount collected, also rounded
    // to the nearest integer (do not worry if prizes and house cut do not add up to the total–just
    // assume the house will make it work)
    @Test
    public void getPrize_isCorrect_entranceFeeLowerBound() throws Exception {
        // setup
        calculationService.setEntrants(3);
        calculationService.setEntranceFee(0);
        calculationService.setHousePercentage(0);

        // test
        Assert.assertEquals(0, calculationService.getPrize(CalculationService.Prizes.FIRST));
        Assert.assertEquals(0, calculationService.getPrize(CalculationService.Prizes.SECOND));
        Assert.assertEquals(0, calculationService.getPrize(CalculationService.Prizes.THIRD));
    }

    // rule 1.b: the number of entrants (an integer greater than 3)
    @Test(expected=Exception.class)
    public void setEntrants_throwsException_entrantsBelowLowerBound() throws Exception {
        // setup
        calculationService.setEntrants(2);
    }

    // rule 1.c: the house percentage  (an integer percentage between 0 and 100)
    @Test(expected=Exception.class)
    public void setHousePercentage_throwsException_housePercentageBelowLowerBound() throws Exception {
        // setup
        calculationService.setHousePercentage(-1);
    }

    // rule 1.c: the house percentage  (an integer percentage between 0 and 100)
    @Test(expected=Exception.class)
    public void setHousePercentage_throwsException_housePercentageAboveUpperBound() throws Exception {
        // setup
        calculationService.setHousePercentage(101);
    }

    // rule 1.a: the entrance fee (a positive integer)
    @Test(expected=Exception.class)
    public void setEntranceFee_throwsException_entranceFeeBelowLowerBound() throws Exception {
        // setup
        calculationService.setEntranceFee(-1);
    }
}