
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    + "Press 3 to empty coin dispenser\n";
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
    }
}
