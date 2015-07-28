
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
    String quarterWeight = "5.670 g";
    String quarterDiameter = "0.955 in";
    String dimeWeight = "2.268 g";
    String dimeDiameter = "0.705 in";
    String nickelWeight = "5.000 g";
    String nickelDiameter = "0.835 in";
    @Before
    public void setUp(){
        test = new VendingMachineTransaction();
    }
    @Test
    public void displaysInsertCoinWhileNotInTransaction(){
        assertEquals("INSERT COINS", test.transactionTotalCoins());
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
        assertEquals("$1.00", test.selectCola());
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
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("$0.25",  test.transactionTotalCoins());
    }
    @Test
    public void addingADimeToMachineAndGettingTotalBack(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("$0.10", test.transactionTotalCoins());
    }
    @Test
    public void addingANickelToMachineAndGettingTotalBack(){
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("$0.05", test.transactionTotalCoins());
    }
    @Test
    public void havingExactChangeReturnsThankYouForColaAndSubtractsFromTotal(){
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("THANK YOU", test.selectCola());
        assertEquals("INSERT COINS", test.transactionTotalCoins());
    }
    @Test
    public void havingExactChangeReturnsThankYouForChipsAndSubtractsFromTotal(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("THANK YOU", test.selectChips());
        assertEquals("INSERT COINS", test.transactionTotalCoins());
    }
    @Test
    public void havingExactChangeReturnsThankYouForCandyAndSubtractsFromTotal(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("THANK YOU", test.selectCandy());
        assertEquals("INSERT COINS", test.transactionTotalCoins());
    }
    @Test
    public void notEnoughMoneyInsertedReturnsPriceOfColaAndAfterDisplaysCurrentTransactionAmount(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $1.00", test.selectCola());
        assertEquals("$0.45", test.transactionTotalCoins());
    }
    @Test
    public void notEnoughMoneyReturnsPriceOfChipsAndTotalInsertedAfter(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $0.50", test.selectChips());
        assertEquals("$0.35", test.transactionTotalCoins());
    }
    @Test
    public void notEnoughMoneyReturnsPriceOfCandyAndTotalInsertedAfter(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("PRICE $0.65", test.selectCandy());
        assertEquals("$0.10", test.transactionTotalCoins());
    }
    @Test
    public void extraMoneyReturedAfterColaTransaction(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("Change made is $0.05 THANK YOU", test.selectCola());
    }
    @Test
    public void returnsCoinDispenser(){
        assertEquals("Can't make change", test.getCoinDispenser().makeChange(0.10));
    }
}
