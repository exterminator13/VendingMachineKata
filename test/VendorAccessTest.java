
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachine;
import vendingmachinekata.VendorAccess;
public class VendorAccessTest {
    VendingMachine vendingMachine;
    VendorAccess vendorAccess;
    Scanner reader;
    ByteArrayInputStream in;
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    public VendorAccessTest() {
    }
    String instructions = "Press 1 to stock items\n" + "Press 2 to stock coins\n"
    + "Press 3 to empty coin dispenser\n" + "Press 4 to add new item\n" + "Press 0 to exit\n";
    String welcome = "WELCOME VENDOR\n";
    String itemsNumberToBeStocked = "ITEM'S NUMBER TO BE STOCKED:\n";
    String stockItems = "1\n";
    String stockCoins = "2\n";
    String emptyDispenser = "3\n";
    String addNewItem = "4\n";
    String enterItemName = "ENTER ITEM NAME:\n";
    String price = "PRICE:\n";
    String exit = "0\n";
    int length;
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        System.setOut(new PrintStream(outStream));
        System.setIn(in);
        vendorAccess = new VendorAccess(vendingMachine);
        length = 0;
    }
    @Test
    public void vendorCanExit(){
        String input = "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome));
    }
    @Test
    public void vendorCanAddStockToItems(){
        String itemsNumber = "1\n";
        String amountOfItemsToStock = "3\n";
        String input = stockItems + itemsNumber + amountOfItemsToStock + exit;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 1 entered for stock items
        assertEquals(itemsNumberToBeStocked, outStream.toString().substring(length, length + itemsNumberToBeStocked.length()));
        length += itemsNumberToBeStocked.length();
        // 1 entered representing item's assigned number
        String amountAdded = "AMOUNT ADDED:\n";
        assertEquals(amountAdded, outStream.toString().substring(length, length + amountAdded.length()));
        length += amountAdded.length();
        // 3 entered for amount of items to be stocked
        String itemStock = "ITEM: COLA\n" + "STOCK: 3\n";
        assertEquals(itemStock, outStream.toString().substring(length, length + itemStock.length()));
        length += itemStock.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        // 0 entered to exit
    }
    @Test
    public void vendorCanStockCoins(){
        String dimeValue = "10\n";
        String quarterValue = "25\n";
        String amountOfDimes = "2\n";
        String amountOfQuarters = "4\n";
        String amountOfDimesAgain = "1\n";
        String input = stockCoins + dimeValue + amountOfDimes + stockCoins
        + dimeValue + amountOfDimesAgain + stockCoins + quarterValue + amountOfQuarters + exit;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for adding coins
        String coinValue = "COIN VALUE:\n";
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // 10 entered for coin's value
        String amountOfCoins = "AMOUNT OF COINS:\n";
        assertEquals(amountOfCoins, outStream.toString().substring(length, length + amountOfCoins.length()));
        length += amountOfCoins.length();
        // 2 entered for amount of dimes added
        String coinTotal = "0 Quarters, 2 Dimes, 0 Nickels\n";
        assertEquals(coinTotal, outStream.toString().substring(length, length + coinTotal.length()));
        length += coinTotal.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for adding coins
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // 10 entered for coin's value
        assertEquals(amountOfCoins, outStream.toString().substring(length, length + amountOfCoins.length()));
        length += amountOfCoins.length();
        // 1 entered for amount of dimes added
        String coinTotal2 = "0 Quarters, 3 Dimes, 0 Nickels\n";
        assertEquals(coinTotal2, outStream.toString().substring(length, length + coinTotal2.length()));
        length += coinTotal2.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for adding coins
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // 25 entered for coin's value
        assertEquals(amountOfCoins, outStream.toString().substring(length, length + amountOfCoins.length()));
        length += amountOfCoins.length();
        // 4 entered for amount of quarters
        String coinTotal3 = "4 Quarters, 3 Dimes, 0 Nickels\n";
        assertEquals(coinTotal3, outStream.toString().substring(length, length + coinTotal3.length()));
        length += coinTotal3.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 0 entered to exit
    }
    @Test
    public void vendorCanEmptyCoinDispenser(){
        String nickelValue = "05\n";
        String amountOfNickels = "10\n";
        String input = stockCoins + nickelValue + amountOfNickels + emptyDispenser + exit;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, length + instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for stock coins
        String coinValue = "COIN VALUE:\n";
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // 05 entered for nickel value
        String amountOfCoins = "AMOUNT OF COINS:\n";
        assertEquals(amountOfCoins, outStream.toString().substring(length, length + amountOfCoins.length()));
        length += amountOfCoins.length();
        // 10 entered for amount of nickels
        String totalCoins = "0 Quarters, 0 Dimes, 10 Nickels\n";
        assertEquals(totalCoins, outStream.toString().substring(length, length + totalCoins.length()));
        length += totalCoins.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 3 pressed to empty coin dispsner
        String coinsEmptied = "COINS EMPTIED\n";
        assertEquals(coinsEmptied, outStream.toString().substring(length, length + coinsEmptied.length()));
        length += coinsEmptied.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 0 pressed to exit
    }
    @Test
    public void newItemAddedToItemList(){
        String setCostOfWater = "1.25\n";
        String watersNumber = "4\n";
        String amountOfWaterToStock = "3\n";
        String input = addNewItem + "water\n" + setCostOfWater + stockItems + watersNumber + amountOfWaterToStock + exit;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 4 entered for adding new item
        assertEquals(enterItemName, outStream.toString().substring(length, length + enterItemName.length()));
        length += enterItemName.length();
        // water entered for item's name
        assertEquals(price, outStream.toString().substring(length, length + price.length()));
        length += price.length();
        // 1.25 entered to set price of water
        String newItemDescription = "NEW ITEM: WATER\n" + "PRICE: $1.25\n" + "ITEM NUMBER: 4\n";
        assertEquals(newItemDescription, outStream.toString().substring(length, length + newItemDescription.length()));
        length += newItemDescription.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 1 entered to stock item
        assertEquals(itemsNumberToBeStocked, outStream.toString().substring(length, length + itemsNumberToBeStocked.length()));
        length += itemsNumberToBeStocked.length();
        // 4 entered for water's number
        String amountAdded = "AMOUNT ADDED:\n";
        assertEquals(amountAdded, outStream.toString().substring(length, length + amountAdded.length()));
        length += amountAdded.length();
        // 3 entered for amount of water added
        String stockConfirmation = "ITEM: WATER\n" + "STOCK: 3\n";
        assertEquals(stockConfirmation, outStream.toString().substring(length, length + stockConfirmation.length()));
        length += stockConfirmation.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        // 0 entered to exit
    }
    @Test
    public void invalidChoicesOnItemStockingDisplaysErrorsKeepsGoing(){
        String input = "1\n" + "4\n" + "1\n" + "3\n" + "-2098\n" + "1\n" + "3\n" + "83\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().subSequence(length, length + welcome.length()));
        length += welcome.length();
        // 1 entered to stock item
        assertEquals(itemsNumberToBeStocked, outStream.toString().substring(length, length + itemsNumberToBeStocked.length()));
        length += itemsNumberToBeStocked.length();
        // 4 entered for item's number
        String invalidItemNumber = "INVALID ITEM NUMBER\n";
        assertEquals(invalidItemNumber, outStream.toString().substring(length, length + invalidItemNumber.length()));
        length += invalidItemNumber.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 1 entered to stock item
        assertEquals(itemsNumberToBeStocked, outStream.toString().substring(length, length + itemsNumberToBeStocked.length()));
        length += itemsNumberToBeStocked.length();
        // 3 entered for item's number
        String amountAdded = "AMOUNT ADDED:\n";
        assertEquals(amountAdded, outStream.toString().substring(length, length + amountAdded.length()));
        length += amountAdded.length();
        // -2098 entered for amount added
        String invalidAmount = "INVALID AMOUNT\n";
        assertEquals(invalidAmount, outStream.toString().substring(length, length + invalidAmount.length()));
        length += invalidAmount.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 1 entered to stock item
        assertEquals(itemsNumberToBeStocked, outStream.toString().substring(length, length + itemsNumberToBeStocked.length()));
        length += itemsNumberToBeStocked.length();
        // 3 entered for item's number
        assertEquals(amountAdded, outStream.toString().substring(length, length + amountAdded.length()));
        length += amountAdded.length();
        // 83 entered for amount added
        String itemChips = "ITEM: CHIPS\n";
        String stock83 = "STOCK: 83\n";
        assertEquals(itemChips + stock83, outStream.toString().substring(length, length + itemChips.length() + stock83.length()));
        length += stock83.length() + itemChips.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        // 0 pressed to exit
    }
    @Test
    public void invalidSelectionAtStartDisplaysErrorMessageKeepsGoing(){
        String input = "2098\n" + "ahflk\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2098 entered
        String invalidSelection = "INVALID SELECTION\n";
        assertEquals(invalidSelection, outStream.toString().substring(length, length + invalidSelection.length()));
        length += invalidSelection.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // ahflk entered
        assertEquals(invalidSelection, outStream.toString().substring(length, length + invalidSelection.length()));
        length += invalidSelection.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        // 0 entered to exit
    }
    @Test
    public void invalidSelectionAtCoinStockDisplaysErrorMessageKeepsGoing(){
        String input = "2\n" + "589\n" + "2\n" + "10\n" + "-129\n" + "2\n" + "asldkf\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for coin stocking
        String coinValue = "COIN VALUE:\n";
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // 589 entered for coin value
        String invalidValue = "INVALID VALUE\n";
        assertEquals(invalidValue, outStream.toString().substring(length, length + invalidValue.length()));
        length += invalidValue.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for coin stocking
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // 10 entered for coin value
        String amountOfCoins = "AMOUNT OF COINS:\n";
        assertEquals(amountOfCoins, outStream.toString().substring(length, length + amountOfCoins.length()));
        length += amountOfCoins.length();
        //-129 entered for amount of coins
        String invalidAmount = "INVALID AMOUNT\n";
        assertEquals(invalidAmount, outStream.toString().substring(length, length + invalidAmount.length()));
        length += invalidAmount.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 2 entered for coin stocking
        assertEquals(coinValue, outStream.toString().substring(length, length + coinValue.length()));
        length += coinValue.length();
        // asldkf entered for coin value
        assertEquals(invalidValue, outStream.toString().substring(length, length + invalidValue.length()));
        length += invalidValue.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        // 0 entered to exit
    }
    @Test
    public void invalidSelectinAtAddingNewItemDisplaysErrorMessageKeepsGoing(){
        String input = "4\n" + "LKJoiu823098\n" + "afdlk\n" + "1\n" + "4\n" + "4\n"
        + "holdasde\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 4 entered for new item
        assertEquals(enterItemName, outStream.toString().substring(length, length + enterItemName.length()));
        length += enterItemName.length();
        // LKJoiu823098 entered for item name
        assertEquals(price, outStream.toString().substring(length, length + price.length()));
        length += price.length();
        // afdlk entered for price
        String invalidPrice = "INVALID PRICE\n";
        assertEquals(invalidPrice, outStream.toString().substring(length, length + invalidPrice.length()));
        length += invalidPrice.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 1 entered for stocking item
        assertEquals(itemsNumberToBeStocked, outStream.toString().substring(length, length + itemsNumberToBeStocked.length()));
        length += itemsNumberToBeStocked.length();
        // 4 entered for item's number
        String invalidItemNumber = "INVALID ITEM NUMBER\n";
        assertEquals(invalidItemNumber, outStream.toString().substring(length, length + invalidItemNumber.length()));
        length += invalidItemNumber.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        length += welcome.length();
        // 4 entered for new item
        assertEquals(enterItemName, outStream.toString().substring(length, length + enterItemName.length()));
        length += enterItemName.length();
        // holdasde entered for item name
        assertEquals(price, outStream.toString().substring(length, length + price.length()));
        length += price.length();
        // 3 entered for price
        String newItemSummary = "NEW ITEM: HOLDASDE\n" + "PRICE: $3.00\n" + "ITEM NUMBER: 4\n";
        assertEquals(newItemSummary, outStream.toString().substring(length, length + newItemSummary.length()));
        length += newItemSummary.length();
        assertEquals(welcome, outStream.toString().substring(length, length + welcome.length()));
        // 0 entered to exit
    }
}