
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.CoinDispenser;

public class CoinDispenserTest {
    private CoinDispenser test;
    public CoinDispenserTest() {
    }
    
    @Before
    public void setUp(){
        test = new CoinDispenser();
    }
    @Test
    public void addingQuarterToDispenser(){
        test.addCoins(25, 1);
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.25", test.getAmountInMachine().toString());
    }
    @Test
    public void addingDimesToDispenser(){
        test.addCoins(10, 1);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.10", test.getAmountInMachine().toString());
    }
    @Test
    public void addingNickelsToDispenser(){
        test.addCoins(5, 1);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.getCoinAmount());
        assertEquals("$0.05", test.getAmountInMachine().toString());
    }
    @Test
    public void makesChangeWithQuarter(){
        test.addCoins(25, 1);
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", test.makeChange(.25));
    }
    @Test
    public void makesChangeWithDime(){
        test.addCoins(10, 1);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.makeChange(.1));
    }
    @Test
    public void makesChangeWithNickel(){
        test.addCoins(5, 1);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.makeChange(.05));
        test.addCoins(5, 2);
        assertEquals("0 Quarters, 0 Dimes, 2 Nickels", test.makeChange(.1));
    }
    @Test
    public void makesChangeMultipleWaysCorrectly(){
        test.addCoins(25, 2);
        test.addCoins(10, 2);
        test.addCoins(5, 1);
        assertEquals("2 Quarters, 0 Dimes, 1 Nickel", test.makeChange(.55));
        test.addCoins(25, 1);
        test.addCoins(10, 3);
        test.addCoins(5, 1);
        assertEquals("1 Quarter, 2 Dimes, 0 Nickels", test.makeChange(.45));
        assertEquals("0 Quarters, 3 Dimes, 1 Nickel", test.makeChange(.35));
    }
    @Test
    public void checksIfMachineCanMakeChangeForItem(){
        test.addCoins(25, 1);
        test.addCoins(5, 1);
        test.addCoins(10, 1);
        assertEquals("Can't make change", test.makeChange(.20));
        assertEquals("Can't make change", test.makeChange(.45));
        assertEquals("Can't make change", test.makeChange(1.00));
    }
    @Test
    public void getCoinDifferenceBetweenTwoCoinDispensers(){
        test.addCoins(25, 1);
        test.addCoins(10, 2);
        test.addCoins(5, 3);
        CoinDispenser test2 = new CoinDispenser();
        test2.addCoins(25, 3);
        test2.addCoins(10, 4);
        test2.addCoins(5, 4);
        assertEquals("2 Quarters, 2 Dimes, 1 Nickel", test2.getDifference(test));
    }
    @Test
    public void setAmountOfCoinsFromOtherDispenser(){
        test.addCoins(25, 3);
        test.addCoins(10, 4);
        test.addCoins(5, 1);
        CoinDispenser test2 = new CoinDispenser();
        test2.addCoins(25, 2);
        test2.addCoins(10, 5);
        test2.addCoins(5, 3);
        test2.setCoins(test.getCoins(25), test.getCoins(10), test.getCoins(5));
        assertEquals("3 Quarters, 4 Dimes, 1 Nickel", test2.getCoinAmount());
    }
}
