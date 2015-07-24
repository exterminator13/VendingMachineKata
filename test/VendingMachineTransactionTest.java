
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachineTransaction;

public class VendingMachineTransactionTest {
    VendingMachineTransaction test;
    public VendingMachineTransactionTest() {
        
    }
    @Before
    public void setUp(){
        test = new VendingMachineTransaction();
    }
    @Test
    public void displaysInsertCoinWhileNotInTransaction(){
        assertEquals("INSERT COIN", test.transactionTotalCoins());
    }
    @Test
    public void recognizesWeightAndDiameterOfQuater(){
        assertEquals(25, test.coinRecognition("5.670 g", "0.955 in"));
    }
    @Test
    public void recognizesWeightAndDiameterOfDime(){
        assertEquals(10, test.coinRecognition("2.268 g", "0.705 in"));
    }
    @Test
    public void recognizesWeightAndDiameterOfNickel(){
        assertEquals(5, test.coinRecognition("5.000 g", "0.835 in"));
    }
    @Test
    public void selectColaFromMachineWithNoMoneyReturnsTotalPrice(){
        assertEquals("$1.50", test.selectCola());
    }
    @Test
    public void selectChipsFromMachineWithNoMoneyReturnsTotalPrice(){
        assertEquals("$0.50", test.selectChips());
    }
    @Test
    public void selectCandyFromMachineWithNoMoneyReturnsTotalPrice(){
        assertEquals("$0.65", test.selectCandy());
    }
    @Test
    public void addingAQuarterToMachineAndGettingTotalBack(){
        test.coinRecognition("5.670 g", "0.955 in");
        assertEquals("$0.25",  test.transactionTotalCoins());
    }
}
