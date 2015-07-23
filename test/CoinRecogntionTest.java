
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.CoinRecognition;

public class CoinRecogntionTest {
    
    public CoinRecogntionTest() {

    }
    @Test
    public void displaysInsertCoinWhileNotInTransaction(){
        CoinRecognition test = new CoinRecognition();
        assertEquals("INSERT COIN", test.transactionTotalCoins());
    }
}
