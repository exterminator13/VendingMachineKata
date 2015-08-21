
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.MachineCoinTracker;
import vendingmachinekata.CoinValues;

public class CoinDispenserTest {
    private MachineCoinTracker test;
    public CoinDispenserTest() {
    }
    @Before
    public void setUp(){
        test = new MachineCoinTracker();
    }
    @Test
    public void addingQuarterToDispenser(){
        test.addCoins(CoinValues.QUARTER_VALUE, 1);
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.25", test.getAmountInMachine().toString());
    }
    @Test
    public void addingDimesToDispenser(){
        test.addCoins(CoinValues.DIME_VALUE, 1);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.10", test.getAmountInMachine().toString());
    }
    @Test
    public void addingNickelsToDispenser(){
        test.addCoins(CoinValues.NICKEL_VALUE, 1);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.getCoinAmount());
        assertEquals("$0.05", test.getAmountInMachine().toString());
    }
    @Test
    public void getCoinDifferenceBetweenTwoCoinDispensers(){
        test.addCoins(CoinValues.QUARTER_VALUE, 1);
        test.addCoins(CoinValues.DIME_VALUE, 2);
        test.addCoins(CoinValues.NICKEL_VALUE, 3);
        MachineCoinTracker test2 = new MachineCoinTracker();
        test2.addCoins(CoinValues.QUARTER_VALUE, 3);
        test2.addCoins(CoinValues.DIME_VALUE, 4);
        test2.addCoins(CoinValues.NICKEL_VALUE, 4);
        assertEquals("2 Quarters, 2 Dimes, 1 Nickel", test2.getDifference(test));
    }
    @Test
    public void setAmountOfCoinsFromOtherDispenser(){
        test.addCoins(CoinValues.QUARTER_VALUE, 3);
        test.addCoins(CoinValues.DIME_VALUE, 4);
        test.addCoins(CoinValues.NICKEL_VALUE, 1);
        MachineCoinTracker test2 = new MachineCoinTracker();
        test2.addCoins(CoinValues.QUARTER_VALUE, 2);
        test2.addCoins(CoinValues.DIME_VALUE, 5);
        test2.addCoins(CoinValues.NICKEL_VALUE, 3);
        test2.setCoins(test.getCoins(CoinValues.QUARTER_VALUE), test.getCoins(CoinValues.DIME_VALUE), test.getCoins(CoinValues.NICKEL_VALUE));
        assertEquals("3 Quarters, 4 Dimes, 1 Nickel", test2.getCoinAmount());
    }
}
