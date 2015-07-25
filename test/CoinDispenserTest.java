
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
        assertEquals("1 Quarters, 0 Dimes, 0 Nickels", test.getCoinAmount());
    }
}
