
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachineTransaction;
import vendingmachinekata.CoinDispenser;
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
    Item candy = new Item(0.65);
    Item cola = new Item(1.00);
    Item chips = new Item(0.50);
    @Before
    public void setUp(){
        CoinDispenser coinDispenser = new CoinDispenser();
        test = new VendingMachineTransaction(coinDispenser);
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
        assertEquals("$1.00", test.selectItem(cola));
    }
    @Test
    public void selectChipsFromMachineWithNoMoneyReturnsTotalPrice(){
        assertEquals("$0.50", test.selectItem(chips));
    }
    @Test
    public void selectCandyFromMachineWithNoMoneyReturnsTotalPrice(){
        assertEquals("$0.65", test.selectItem(candy));
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
        assertEquals("THANK YOU", test.selectItem(cola));
        assertEquals("INSERT COINS", test.transactionTotalCoins());
    }
    @Test
    public void havingExactChangeReturnsThankYouForChipsAndSubtractsFromTotal(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("THANK YOU", test.selectItem(chips));
        assertEquals("INSERT COINS", test.transactionTotalCoins());
    }
    @Test
    public void havingExactChangeReturnsThankYouForCandyAndSubtractsFromTotal(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("THANK YOU", test.selectItem(candy));
        assertEquals("INSERT COINS", test.transactionTotalCoins());
    }
    @Test
    public void notEnoughMoneyInsertedReturnsPriceOfColaAndAfterDisplaysCurrentTransactionAmount(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $1.00", test.selectItem(cola));
        assertEquals("$0.45", test.transactionTotalCoins());
    }
    @Test
    public void notEnoughMoneyReturnsPriceOfChipsAndTotalInsertedAfter(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $0.50", test.selectItem(chips));
        assertEquals("$0.35", test.transactionTotalCoins());
    }
    @Test
    public void notEnoughMoneyReturnsPriceOfCandyAndTotalInsertedAfter(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("PRICE $0.65", test.selectItem(candy));
        assertEquals("$0.10", test.transactionTotalCoins());
    }
    @Test
    public void extraMoneyReturedAfterColaTransaction(){
        test.getCoinDispenser().addNickels(5);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.selectItem(cola));
        assertEquals("3 Quarters, 3 Dimes, 4 Nickels", test.getCoinDispenser().getCoinAmount());
    }
    @Test
    public void extraMoneyReturnedAfterCandyTransaction(){
        test.getCoinDispenser().addDimes(3);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("0 Quarters, 1 Dime, 0 Nickels", test.selectItem(candy));
    }
    @Test
    public void extraMoneyReturnedAfterChipsTransaction(){
        test.getCoinDispenser().addNickels(2);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("0 Quarters, 0 Dimes, 1 Nickel", test.selectItem(chips));
    }
    @Test
    public void returnsWorkingCoinDispenser(){
        assertEquals("Can't make change", test.getCoinDispenser().makeChange(0.10));
    }
    @Test
    public void coinsAddedToTransactionGetAddedToCoinDispenser(){
        test.coinRecognition(dimeWeight, dimeDiameter);
        test.coinRecognition(quarterWeight, quarterDiameter);
        test.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("1 Quarter, 1 Dime, 1 Nickel",test.getCoinDispenser().getCoinAmount());
    }
}
