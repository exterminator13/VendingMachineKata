
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
        test.addQuarter();
        assertEquals("1 Quarter, 0 Dimes, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.25", test.getAmountInMachine().toString());
    }
    @Test
    public void addingDimesToDispenser(){
        test.addDime();
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.getCoinAmount());
        assertEquals("$0.10", test.getAmountInMachine().toString());
    }
    @Test
    public void addingNickelsToDispenser(){
        test.addNickel();
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.getCoinAmount());
        assertEquals("$0.05", test.getAmountInMachine().toString());
    }
    @Test
    public void makesChangeWithQuarter(){
        test.addQuarter();
        assertEquals("1 Quarter, 0 Nickels, 0 Dimes", test.makeChange(.25));
    }
}
