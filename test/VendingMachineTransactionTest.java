
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachineTransaction;

public class VendingMachineTransactionTest {
    
    public VendingMachineTransactionTest() {

    }
    @Test
    public void displaysInsertCoinWhileNotInTransaction(){
        VendingMachineTransaction test = new VendingMachineTransaction();
        assertEquals("INSERT COIN", test.transactionTotalCoins());
    }
    @Test
    public void recognizesWeightAndDiameterOfQuater(){
        VendingMachineTransaction test = new VendingMachineTransaction();
        assertEquals(25, test.coinRecognition("5.670 g", "0.955 in"));
        assertEquals(10, test.coinRecognition("2.268 g", "0.705 in"));
        assertEquals(5, test.coinRecognition("5.000 g", "0.835 in"));
    }
}
