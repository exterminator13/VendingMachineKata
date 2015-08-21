
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.ChangeMaker;
import vendingmachinekata.MachineCoinTracker;
import vendingmachinekata.CoinValues;

public class ChangeMakerTest {
    MachineCoinTracker test;
    public ChangeMakerTest() {
    }
    @Before
    public void setUp(){
        test = new MachineCoinTracker();
    }
    @Test
    public void makesChangeWithQuarter(){
        test.addCoins(CoinValues.QUARTER_VALUE, 1);
        ChangeMaker changeMaker = new ChangeMaker(CoinValues.QUARTER_VALUE, test);
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", changeMaker.returnChange());
    }
    @Test
    public void makesChangeWithDime(){
        test.addCoins(CoinValues.DIME_VALUE, 1);
        ChangeMaker changeMaker = new ChangeMaker(CoinValues.DIME_VALUE, test);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", changeMaker.returnChange());
    }
    @Test
    public void makesChangeWithNickel(){
        test.addCoins(CoinValues.NICKEL_VALUE, 1);
        ChangeMaker changeMaker = new ChangeMaker(CoinValues.NICKEL_VALUE, test);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", changeMaker.returnChange());
        test.addCoins(CoinValues.NICKEL_VALUE, 2);
        ChangeMaker changeMaker2 = new ChangeMaker(CoinValues.DIME_VALUE, test);
        assertEquals("0 Quarters, 0 Dimes, 2 Nickels", changeMaker2.returnChange());
    }
    @Test
    public void makesChangeMultipleWaysCorrectly(){
        test.addCoins(CoinValues.QUARTER_VALUE, 2);
        test.addCoins(CoinValues.DIME_VALUE, 2);
        test.addCoins(CoinValues.NICKEL_VALUE, 1);
        ChangeMaker changeMaker = new ChangeMaker(.55, test);
        assertEquals("2 Quarters, 0 Dimes, 1 Nickel", changeMaker.returnChange());
        test.addCoins(CoinValues.QUARTER_VALUE, 1);
        test.addCoins(CoinValues.DIME_VALUE, 3);
        test.addCoins(CoinValues.NICKEL_VALUE, 1);
        ChangeMaker changeMaker2 = new ChangeMaker(.45, test);
        assertEquals("1 Quarter, 2 Dimes, 0 Nickels", changeMaker2.returnChange());
        ChangeMaker changeMaker3 = new ChangeMaker(.35, test);
        assertEquals("0 Quarters, 3 Dimes, 1 Nickel", changeMaker3.returnChange());
    }
    @Test
    public void checksIfMachineCanMakeChangeForItem(){
        test.addCoins(CoinValues.QUARTER_VALUE, 1);
        test.addCoins(CoinValues.NICKEL_VALUE, 1);
        test.addCoins(CoinValues.DIME_VALUE, 1);
        ChangeMaker changeMaker = new ChangeMaker(.20, test);
        assertEquals("Can't make change", changeMaker.returnChange());
        ChangeMaker changeMaker2 = new ChangeMaker(.45, test);
        assertEquals("Can't make change", changeMaker2.returnChange());
        ChangeMaker changeMaker3 = new ChangeMaker(1.00, test);
        assertEquals("Can't make change", changeMaker3.returnChange());
    }
}
