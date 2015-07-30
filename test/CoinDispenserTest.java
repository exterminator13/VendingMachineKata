
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
        test.addQuarters(1);
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.25", test.getAmountInMachine().toString());
    }
    @Test
    public void addingDimesToDispenser(){
        test.addDimes(1);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.10", test.getAmountInMachine().toString());
    }
    @Test
    public void addingNickelsToDispenser(){
        test.addNickels(1);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.getCoinAmount());
        assertEquals("$0.05", test.getAmountInMachine().toString());
    }
    @Test
    public void makesChangeWithQuarter(){
        test.addQuarters(1);
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", test.makeChange(.25));
    }
    @Test
    public void makesChangeWithDime(){
        test.addDimes(1);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.makeChange(.1));
    }
    @Test
    public void makesChangeWithNickel(){
        test.addNickels(1);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.makeChange(.05));
        test.addNickels(2);
        assertEquals("0 Quarters, 0 Dimes, 2 Nickels", test.makeChange(.1));
    }
    @Test
    public void makesChangeMultipleWaysCorrectly(){
        test.addQuarters(2);
        test.addDimes(2);
        test.addNickels(1);
        assertEquals("2 Quarters, 0 Dimes, 1 Nickel", test.makeChange(.55));
        test.addQuarters(1);
        test.addDimes(3);
        test.addNickels(1);
        assertEquals("1 Quarter, 2 Dimes, 0 Nickels", test.makeChange(.45));
        assertEquals("0 Quarters, 3 Dimes, 1 Nickel", test.makeChange(.35));
    }
    @Test
    public void checksIfMachineCanMakeChangeForItem(){
        test.addQuarters(1);
        test.addNickels(1);
        test.addDimes(1);
        assertEquals("Can't make change", test.makeChange(.20));
        assertEquals("Can't make change", test.makeChange(.45));
        assertEquals("Can't make change", test.makeChange(1.00));
    }
    @Test
    public void getCoinDifferenceBetweenTwoCoinDispensers(){
        test.addQuarters(1);
        test.addDimes(2);
        test.addNickels(3);
        CoinDispenser test2 = new CoinDispenser();
        test2.addQuarters(3);
        test2.addDimes(4);
        test2.addNickels(4);
        assertEquals("2 Quarters, 2 Dimes, 1 Nickel", test2.getDifference(test));
    }
    @Test
    public void setAmountOfCoinsFromOtherDispenser(){
        test.addQuarters(3);
        test.addDimes(4);
        test.addNickels(1);
        CoinDispenser test2 = new CoinDispenser();
        test2.addQuarters(2);
        test2.addDimes(5);
        test2.addNickels(3);
        test2.setCoins(test.getQuarters(), test.getDimes(), test.getNickels());
        assertEquals("3 Quarters, 4 Dimes, 1 Nickel", test2.getCoinAmount());
    }
}