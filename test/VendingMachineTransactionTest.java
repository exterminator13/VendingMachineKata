
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.ChangeMaker;
import vendingmachinekata.VendingMachineTransaction;
import vendingmachinekata.MachineCoinTracker;
import vendingmachinekata.CoinValues;
import vendingmachinekata.Item;

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
    Item candy = new Item("candy", 0.65);
    Item cola = new Item("cola", 1.00);
    Item chips = new Item("chips", 0.50);
    @Before
    public void setUp(){
        MachineCoinTracker coinTracker = new MachineCoinTracker();
        test = new VendingMachineTransaction(coinTracker);
        candy.setStock(1);
        cola.setStock(1);
        chips.setStock(1);
    }
    @Test
    public void displaysInsertCoinWhileNotInTransaction(){
        test.getCoinTracker().addCoins(CoinValues.DIME_VALUE, 1);
        assertEquals("INSERT COINS", test.display());
    }
    @Test
    public void recognizesWeightAndDiameterOfQuater(){
        assertTrue(test.coinRecognition("5.670 g", "0.955 in"));
    }
    @Test
    public void recognizesWeightAndDiameterOfDime(){
        assertTrue(test.coinRecognition("2.268 g", "0.705 in"));
    }
    @Test
    public void recognizesWeightAndDiameterOfNickel(){
        assertTrue(test.coinRecognition("5.000 g", "0.835 in"));
    }
    @Test
    public void addingAQuarterToMachineAndGettingTotalBack(){
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("$0.25",  test.display());
    }
    @Test
    public void addingADimeToMachineAndGettingTotalBack(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("$0.10", test.display());
    }
    @Test
    public void addingANickelToMachineAndGettingTotalBack(){
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("$0.05", test.display());
    }
    @Test
    public void returnsWorkingCoinDispenser(){
        ChangeMaker changeMaker = new ChangeMaker(.10, test.getCoinTracker());
        assertEquals("Can't make change", changeMaker.returnChange());
    }
    @Test
    public void coinsAddedToTransactionGetAddedToCoinDispenser(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("1 Quarter, 1 Dime, 1 Nickel",test.getCoinTracker().getCoinAmount());
    }
    @Test
    public void returnCoinsAddedDuringTransaction(){
        test.startNewTransaction();
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("1 Quarter, 1 Dime, 1 Nickel", test.getCoinTracker().getCoinAmount());
    }
    @Test
    public void selectingReturnCoinsWithNoMoneyInserted(){
        test.startNewTransaction();
        assertEquals("0 Quarters, 0 Dimes, 0 Nickels", test.getCoinTracker().getCoinAmount());
    }
    @Test
    public void displaysExactChangeOnlyWhenDispenserIsEmpty(){
        assertEquals("EXACT CHANGE ONLY", test.display());
    }
    @Test
    public void accurateCoinAmountAfterMultipleTransactions(){
        test.startNewTransaction();
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals(0.40, test.getCurrentAmount(), 0.01);
        assertEquals("1 Quarter, 1 Dime, 1 Nickel", test.getCoinTracker().getCoinAmount());
        assertEquals(0.00, test.getTransactionDispenser().getAmountInMachine().getAmount(), 0.01);
        test.startNewTransaction();
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals(0.20, test.getCurrentAmount(), 0.01);
        assertEquals("0 Quarters, 2 Dimes, 0 Nickels", test.getCoinTracker().getDifference(test.getTransactionDispenser()));      
    }
}
