
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
        String input = "1\n" + "1\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome
        + "ITEM'S NUMBER TO BE STOCKED:\n" + "AMOUNT ADDED:\n" 
        + "ITEM: COLA\n" + "STOCK: 3\n" + welcome));
        assertEquals(3, vendingMachine.getListOfItems().get(0).getStock());
    }
    @Test
    public void vendorCanStockCoins(){
        String input = "2\n" + "10\n" + "2\n" + "2\n" + "10\n" + "1\n" + "2\n" + "25\n" + "4\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome
        + "COIN VALUE:\n" + "AMOUNT OF COINS:\n" + "0 Quarters, 2 Dimes, 0 Nickels\n"
        + welcome + "COIN VALUE:\n" + "AMOUNT OF COINS:\n" + "0 Quarters, 3 Dimes, 0 Nickels\n"
        + welcome + "COIN VALUE:\n" + "AMOUNT OF COINS:\n" + "4 Quarters, 3 Dimes, 0 Nickels\n"
        + welcome));
        assertEquals("4 Quarters, 3 Dimes, 0 Nickels", vendingMachine.getCoinTracker().getCoinAmount());
    }
    @Test
    public void vendorCanEmptyCoinDispenser(){
        String input = "2\n" + "05\n" + "10\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome
        + "COIN VALUE:\n" + "AMOUNT OF COINS:\n" + "0 Quarters, 0 Dimes, 10 Nickels\n"
        + welcome + "COINS EMPTIED\n" + welcome));
        assertEquals("0 Quarters, 0 Dimes, 0 Nickels", vendingMachine.getCoinTracker().getCoinAmount());
    }
    @Test
    public void newItemAddedToItemList(){
        String input = "4\n" + "water\n" + "1.25\n" + "1\n" + "4\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
        assertTrue(outStream.toString().equals(instructions + welcome + "ENTER ITEM NAME:\n"
        + "PRICE:\n" + "NEW ITEM: WATER\n" + "PRICE: $1.25\n" + "ITEM NUMBER: 4\n" + welcome
        + "ITEM'S NUMBER TO BE STOCKED:\n" + "AMOUNT ADDED:\n" + "ITEM: WATER\n"
        + "STOCK: 3\n" + welcome));
        assertEquals(3, vendingMachine.getListOfItems().get(3).getStock());
    }
    @Test
    public void invalidChoicesOnItemStockingDisplaysErrorsKeepsGoing(){
        String input = "1\n" + "4\n" + "1\n" + "3\n" + "-2098\n" + "1\n" + "3\n" + "83\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendorAccess.run();
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
