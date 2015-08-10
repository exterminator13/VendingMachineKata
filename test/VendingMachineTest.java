import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachine;
import vendingmachinekata.VendorAccess;
public class VendingMachineTest {
    VendingMachine vendingMachine;
    Scanner reader;
    ByteArrayInputStream in;
    VendorAccess vendorAccess;
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    public VendingMachineTest() {
    }
    String instructions = "Press 1 for COLA\n" + "Press 2 for CANDY\n" + "Press 3 for CHIPS\n" + "Press 0 to exit\n";
    String quarterWeight = "5.670 g\n";
    String quarterDiameter = "0.955 in\n";
    String dimeWeight = "2.268 g\n";
    String dimeDiameter = "0.705 in\n";
    String nickelWeight = "5.000 g\n";
    String nickelDiameter = "0.835 in\n";
    String exact = "EXACT CHANGE ONLY\n";
    String insert = "INSERT COINS\n";
    String thank = "THANK YOU\n";
    String sold = "SOLD OUT\n";
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        System.setOut(new PrintStream(outStream));
        vendorAccess = vendingMachine.getVendorAccess();
    }
    @Test
    public void machineDisplaysExactChangeOnlyWhenNoChange(){
        String selection = "1\n" + "0\n";
        in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().contains(instructions + "EXACT CHANGE ONLY\n"));
    }
    @Test
    public void inputNumberForItemWithNoStockReturnsSoldOut(){ 
        String selection = "1\n" + "2\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + "EXACT CHANGE ONLY\n" + "SOLD OUT\n" +
        "EXACT CHANGE ONLY\n" + "SOLD OUT\n" + "EXACT CHANGE ONLY\n" + "SOLD OUT\n" + 
        "EXACT CHANGE ONLY\n"));
    }
    @Test
    public void vendingMachineDisplaysAmountForItemWithNoCoinsInserted(){
        vendorAccess.stockItem(1, 20);
        vendorAccess.stockItem(2, 13);
        vendorAccess.stockItem(3, 10);
        String selection = "1\n" + "2\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + "EXACT CHANGE ONLY\n" + "$1.00\n"
        + "EXACT CHANGE ONLY\n" + "$0.65\n" + "EXACT CHANGE ONLY\n" + "$0.50\n" + "EXACT CHANGE ONLY\n"));
    }
    @Test
    public void vendingMachineDisplaysThankYouAfterEachTransactionOfExactAmount(){
        vendorAccess.stockItem(1, 5);
        vendorAccess.stockItem(2, 1);
        vendorAccess.stockItem(3, 2);
        String input = quarterWeight + quarterDiameter + quarterWeight + quarterDiameter
        + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter + "1\n"
        + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter
        + dimeWeight + dimeDiameter + nickelWeight + nickelDiameter + "2\n" 
        + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + "EXACT CHANGE ONLY\n" + "$0.25\n" + "$0.50\n"
        + "$0.75\n" + "$1.00\n" + "THANK YOU\n"
        + "INSERT COINS\n" + "$0.25\n" + "$0.50\n" + "$0.60\n" + "$0.65\n"
        + "THANK YOU\n" + "INSERT COINS\n" + "$0.25\n" + "$0.50\n" + "THANK YOU\n" + "INSERT COINS\n"));
    }
    @Test
    public void displaysPriceAndThenAmountAfterSelectingItemWithInsufficentFunds(){
        vendorAccess.stockItem(1, 3);
        vendorAccess.stockItem(2, 4);
        vendorAccess.stockItem(3, 1);
        String input = quarterWeight + quarterDiameter + "1\n" + dimeWeight + dimeDiameter + "2\n" + nickelWeight
        + nickelDiameter + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + exact + "$0.25\n"+ "PRICE $1.00\n" + "$0.25\n" + "$0.35\n"
        + "PRICE $0.65\n" + "$0.35\n" + "$0.40\n" + "PRICE $0.50\n" + "$0.40\n"));
    }
    @Test
    public void returnsCorrectAmountOfCoinsIntoCoinDispenserWhenItCanMakeChange(){
        vendorAccess.stockItem(2, 4);
        vendorAccess.stockItem(1, 3);
        vendorAccess.stockItem(3, 1);
        vendorAccess.stockCoins(10, 2);
        String input = quarterWeight + quarterDiameter + dimeWeight + dimeDiameter
        + dimeWeight + dimeDiameter + dimeWeight + dimeDiameter 
        + "3\n" + nickelWeight + nickelDiameter + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + insert + "$0.25\n" + "$0.35\n"
        + "$0.45\n" + "$0.55\n" + "Can't make change\n" + "$0.55\n"
        + "$0.60\n" + "THANK YOU\n" + "0 Quarters, 1 Dime, 0 Nickels returned\n" + insert));
    }
    @Test
    public void coinsAreReturnedToCustomerMidTransaction(){
        vendorAccess.stockItem(1, 3);
        vendorAccess.stockCoins(10, 2);
        String input = quarterWeight + quarterDiameter + dimeWeight + dimeDiameter 
        + nickelWeight + nickelDiameter + "return\n" + "1\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + insert + "$0.25\n" + "$0.35\n" 
        + "$0.40\n" + "1 Quarter, 1 Dime, 1 Nickel\n" + insert + "$1.00\n" + insert));
    }
    @Test
    public void itemIsRemovedFromStockAfterSuccessfulTransaction(){
        vendorAccess.stockItem(1, 1);
        vendorAccess.stockCoins(10, 4);
        String input = quarterWeight + quarterDiameter + quarterWeight
        + quarterDiameter + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter
        + "1\n" + "1\n" + "return\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + insert + "$0.25\n" + "$0.50\n"
        + "$0.75\n" + "$1.00\n" + thank + insert + sold + insert 
        + "0 Quarters, 0 Dimes, 0 Nickels\n" + insert));
    }
    @Test
    public void invalidCoinsAreReturnedToCustomer(){
        String input = "5.670 g\n" + "0.789 in\n" + "5.670 g\n" + "0.955 in\n" + "3.479 g\n"
        + "0.835 in\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + exact + "Coin returned\n"
        + exact + "$0.25\n" + "Coin returned\n" + "$0.25\n"));
    }
    @Test
    public void vendingMachineDisplaysErrorIfSelectionIsInvalid(){
        String input = "098\n" + "aaslkdjf\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(instructions + exact + "INVALID SELECTION\n"
        + exact + "INVALID SELECTION\n" + exact));
    }
    @After
    public void tearDown(){
        System.setIn(null);
        System.setOut(null);
    }
}

