
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
    String exit = "0\n";
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        System.setOut(new PrintStream(outStream));
        System.setIn(in);
        vendorAccess = new VendorAccess(vendingMachine);
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
        assertTrue(outStream.toString().equals(instructions + welcome //1 entered for stock items
        + "ITEM'S NUMBER TO BE STOCKED:\n" //1 entered representing item's assigned number
        + "AMOUNT ADDED:\n" //3 entered for amount of items to be stocked
        + "ITEM: COLA\n" + "STOCK: 3\n" + welcome)); // 0 entered to exit
        assertEquals(3, vendingMachine.getListOfItems().get(0).getStock());
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
        assertTrue(outStream.toString().equals(instructions + welcome //2 entered for adding coins
        + "COIN VALUE:\n" // 10 entered for coin's value
        + "AMOUNT OF COINS:\n" // 2 entered for amount of dimes added 
        + "0 Quarters, 2 Dimes, 0 Nickels\n" + welcome  // 2 entered for adding coins
        + "COIN VALUE:\n" // 10 entered for coin's value
        + "AMOUNT OF COINS:\n" // 1 entered for amount of dimes added
        + "0 Quarters, 3 Dimes, 0 Nickels\n" + welcome + "COIN VALUE:\n" //25 entered for coin's value 
        + "AMOUNT OF COINS:\n" //4 entered for amount of quarters
        + "4 Quarters, 3 Dimes, 0 Nickels\n" + welcome //0 entered to exit
        ));
        assertEquals("4 Quarters, 3 Dimes, 0 Nickels", vendingMachine.getCoinTracker().getCoinAmount());
    }
    @Test
    public void vendorCanEmptyCoinDispenser(){
        String nickelValue = "05\n";
        String amountOfNickels = "10\n";
        String input = stockCoins + nickelValue + amountOfNickels + emptyDispenser + exit;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome //2 entered for stock coins
        + "COIN VALUE:\n" //05 entered for nickel value
        + "AMOUNT OF COINS:\n" //10 entered for amount of nickels
        + "0 Quarters, 0 Dimes, 10 Nickels\n" + welcome //3 pressed to empty coin dispenser
        +"COINS EMPTIED\n" + welcome //0 pressed to exit
        ));
        assertEquals("0 Quarters, 0 Dimes, 0 Nickels", vendingMachine.getCoinTracker().getCoinAmount());
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
        assertTrue(outStream.toString().equals(instructions + welcome // 4 entered for new item
        +"ENTER ITEM NAME:\n" // water entered for item's name
        + "PRICE:\n" // 1.25 entered to set price of water
        + "NEW ITEM: WATER\n" + "PRICE: $1.25\n" + "ITEM NUMBER: 4\n" 
        + welcome // 1 entered to stock item
        + "ITEM'S NUMBER TO BE STOCKED:\n" // 4 entered for water's number
        + "AMOUNT ADDED:\n" // 3 entered for amount of water added
        + "ITEM: WATER\n" + "STOCK: 3\n" + welcome // 0 entered to exit
        ));
        assertEquals(3, vendingMachine.getListOfItems().get(3).getStock());
    }
    @Test
    public void invalidChoicesOnItemStockingDisplaysErrorsKeepsGoing(){
        String input = "1\n" + "4\n" + "1\n" + "3\n" + "-2098\n" + "1\n" + "3\n" + "83\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        int length = 0;
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
        assertTrue(outStream.toString().equals(instructions + welcome 
        + "ITEM'S NUMBER TO BE STOCKED:\n" + "INVALID ITEM NUMBER\n" + welcome
        + "ITEM'S NUMBER TO BE STOCKED:\n" + "AMOUNT ADDED:\n" + "INVALID AMOUNT\n"
        + welcome + "ITEM'S NUMBER TO BE STOCKED:\n" + "AMOUNT ADDED:\n" + "ITEM: CHIPS\n"
        + "STOCK: 83\n" + welcome));
    }
    @Test
    public void invalidSelectionAtStartDisplaysErrorMessageKeepsGoing(){
        String input = "2098\n" + "ahflk\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome + "INVALID SELECTION\n"
        + welcome + "INVALID SELECTION\n" + welcome));
    }
    @Test
    public void invalidSelectionAtCoinStockDisplaysErrorMessageKeepsGoing(){
        String input = "2\n" + "589\n" + "2\n" + "10\n" + "-129\n" + "2\n" + "asldkf\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome + "COIN VALUE:\n"
        + "INVALID VALUE\n" + welcome + "COIN VALUE:\n" + "AMOUNT OF COINS:\n"
        + "INVALID AMOUNT\n" + welcome + "COIN VALUE:\n" + "INVALID VALUE\n" + welcome));
    }
    @Test
    public void invalidSelectinAtAddingNewItemDisplaysErrorMessageKeepsGoing(){
        String input = "4\n" + "LKJoiu823098\n" + "afdlk\n" + "1\n" + "4\n" + "4\n"
        + "holdasde\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome + "ENTER ITEM NAME:\n"
        + "PRICE:\n" + "INVALID PRICE\n" + welcome + "ITEM'S NUMBER TO BE STOCKED:\n"
        + "INVALID ITEM NUMBER\n" + welcome + "ENTER ITEM NAME:\n" + "PRICE:\n"
        + "NEW ITEM: HOLDASDE\n" + "PRICE: $3.00\n" + "ITEM NUMBER: 4\n" + welcome));
    }
}
