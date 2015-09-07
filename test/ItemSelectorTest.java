
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.CoinRecognizer;
import vendingmachinekata.CoinValues;
import vendingmachinekata.Item;
import vendingmachinekata.ItemSelector;
import vendingmachinekata.MachineCoinTracker;
import vendingmachinekata.VendingMachineTransaction;
public class ItemSelectorTest {
    ItemSelector test;
    VendingMachineTransaction vendingMachineTransaction;
    CoinRecognizer coinRecognizer;
    MachineCoinTracker machineCoinTracker;
    Item cola;
    Item candy;
    Item chips;
    public ItemSelectorTest() {

    }
    String quarterWeight = "5.670 g";
    String quarterDiameter = "0.955 in";
    String dimeWeight = "2.268 g";
    String dimeDiameter = "0.705 in";
    String nickelWeight = "5.000 g";
    String nickelDiameter = "0.835 in";
    @Before
    public void setUp(){
        cola = new Item("cola", 1.00);
        candy = new Item("candy", 0.65);
        chips = new Item("chips", 0.50);
        this.machineCoinTracker = new MachineCoinTracker();
        this.vendingMachineTransaction = new VendingMachineTransaction(machineCoinTracker);
        this.test = new ItemSelector(vendingMachineTransaction);
        this.coinRecognizer = new CoinRecognizer(vendingMachineTransaction);
        cola.addStock(1);
        candy.addStock(1);
        chips.addStock(1);
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
    public void havingExactChangeReturnsThankYouForColaAndSubtractsFromTotal(){
        vendingMachineTransaction.getCoinTracker().addCoins(CoinValues.NICKEL_VALUE, 3);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(nickelWeight, nickelDiameter);
        ItemSelector test2 = new ItemSelector(vendingMachineTransaction);
        assertEquals("THANK YOU", test2.selectItem(cola));
        assertEquals("INSERT COINS", vendingMachineTransaction.display());
    }
    @Test
    public void havingExactChangeReturnsThankYouForChipsAndSubtractsFromTotal(){
        vendingMachineTransaction.getCoinTracker().addCoins(CoinValues.DIME_VALUE, 10);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(nickelWeight, nickelDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("THANK YOU", test.selectItem(chips));
        assertEquals("INSERT COINS", vendingMachineTransaction.display());
    }
    @Test
    public void havingExactChangeReturnsThankYouForCandyAndSubtractsFromTotal(){
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(nickelWeight, nickelDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("THANK YOU", test.selectItem(candy));
        assertEquals("INSERT COINS", vendingMachineTransaction.display());
    }
    @Test
    public void notEnoughMoneyInsertedReturnsPriceOfColaAndAfterDisplaysCurrentTransactionAmount(){
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $1.00", test.selectItem(cola));
        assertEquals("$0.45", vendingMachineTransaction.display());
    }
    @Test
    public void notEnoughMoneyReturnsPriceOfChipsAndTotalInsertedAfter(){
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $0.50", test.selectItem(chips));
        assertEquals("$0.35", vendingMachineTransaction.display());
    }
    @Test
    public void notEnoughMoneyReturnsPriceOfCandyAndTotalInsertedAfter(){
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("PRICE $0.65", test.selectItem(candy));
        assertEquals("$0.10", vendingMachineTransaction.display());
    }
    @Test
    public void extraMoneyReturedAfterColaTransaction(){
        vendingMachineTransaction.getCoinTracker().addCoins(CoinValues.NICKEL_VALUE, 5);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("THANK YOU\n0 Quarters, 0 Dimes, 1 Nickel returned", test.selectItem(cola));
        assertEquals("3 Quarters, 3 Dimes, 4 Nickels", vendingMachineTransaction.getCoinTracker().getCoinAmount());
    }
    @Test
    public void extraMoneyReturnedAfterCandyTransaction(){
        vendingMachineTransaction.getCoinTracker().addCoins(CoinValues.DIME_VALUE, 3);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("THANK YOU\n0 Quarters, 1 Dime, 0 Nickels returned", test.selectItem(candy));
    }
    @Test
    public void extraMoneyReturnedAfterChipsTransaction(){
        vendingMachineTransaction.getCoinTracker().addCoins(CoinValues.NICKEL_VALUE, 2);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("THANK YOU\n0 Quarters, 0 Dimes, 1 Nickel returned", test.selectItem(chips));
    }
    @Test
    public void ifItemIsNotInStockItemIsNotSoldAndDisplaysSoldOut(){
        cola.setStock(0);
        candy.setStock(0);
        chips.setStock(0);
        assertEquals("SOLD OUT", test.selectItem(cola));
        assertEquals("SOLD OUT", test.selectItem(candy));
        assertEquals("SOLD OUT", test.selectItem(chips));
    }
    @Test
    public void outOfStockItemDoesNotChangeAnything(){
        cola.setStock(0);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("2 Quarters, 2 Dimes, 0 Nickels", vendingMachineTransaction.getCoinTracker().getCoinAmount());
        assertEquals(0.7, vendingMachineTransaction.getCurrentAmount(), 0.01);
        assertEquals("SOLD OUT", test.selectItem(cola));
        assertEquals("2 Quarters, 2 Dimes, 0 Nickels", vendingMachineTransaction.getCoinTracker().getCoinAmount());
        assertEquals(0.7, vendingMachineTransaction.getCurrentAmount(), 0.01);
    }
    @Test
    public void canNotMakeChangeDisplayedWhenNotEnoughChange(){
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        assertEquals("Can't make change", test.selectItem(cola));
    }
    @Test
    public void removesItemFromInventoryWhenTransactionSuccessful(){
        candy.setStock(1);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("THANK YOU\n" + "0 Quarters, 1 Dime, 0 Nickels returned", test.selectItem(candy));  
        assertEquals("SOLD OUT", test.selectItem(candy));
    }
    @Test
    public void doesNotRemoveItemFromInventoryWhenTransactionFails(){
        candy.setStock(1);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        this.coinRecognizer.coinRecognition(quarterWeight, quarterDiameter);
        assertEquals("PRICE $0.65", test.selectItem(candy));
        this.coinRecognizer.coinRecognition(dimeWeight, dimeDiameter);
        this.coinRecognizer.coinRecognition(nickelWeight, nickelDiameter);
        assertEquals("THANK YOU", test.selectItem(candy));
    }
}
