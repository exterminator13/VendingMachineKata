import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachine;
public class VendingMachineTest {
    VendingMachine vendingMachine;
    Scanner reader;
    ByteArrayInputStream in;
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    public VendingMachineTest() {
    }
    String quarterWeight = "5.670 g\n";
    String quarterDiameter = "0.955 in\n";
    String dimeWeight = "2.268 g\n";
    String dimeDiameter = "0.705 in\n";
    String nickelWeight = "5.000 g\n";
    String nickelDiameter = "0.835 in\n";
    String exact = "EXACT CHANGE ONLY\n";
    String thank = "THANK YOU\n";
    String sold = "SOLD OUT\n";
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        System.setOut(new PrintStream(outStream));
        System.setIn(in);
        
    }
    @Test
    public void machineDisplaysExactChangeOnlyWhenNoChange(){
        String selection = "1\n" + "0\n";
        in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().contains("EXACT CHANGE ONLY\n"));
        
    }
    @Test
    public void inputNumberForItemWithNoStockReturnsSoldOut(){ 
        String selection = "1\n" + "2\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals("EXACT CHANGE ONLY\n" + "SOLD OUT\n" +
        "EXACT CHANGE ONLY\n" + "SOLD OUT\n" + "EXACT CHANGE ONLY\n" + "SOLD OUT\n" + 
        "EXACT CHANGE ONLY\n"));
    }
    @Test
    public void vendingMachineDisplaysAmountForItemWithNoCoinsInserted(){
        vendingMachine.stockItem(1, 20);
        vendingMachine.stockItem(2, 10);
        vendingMachine.stockItem(3, 10);
        String selection = "1\n" + "2\n" + "3\n" + "0\n";
        in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals("EXACT CHANGE ONLY\n" + "$1.00\n"
        + "EXACT CHANGE ONLY\n" + "$0.65\n" + "EXACT CHANGE ONLY\n" + "$0.50\n" + "EXACT CHANGE ONLY\n"));
    }
    @Test
    public void vendingMachineDisplaysThankYouAfterEachTransactionOfExactAmount(){
        vendingMachine.stockItem(1, 5);
        vendingMachine.stockItem(2, 1);
        vendingMachine.stockItem(3, 2);
        String input = quarterWeight + quarterDiameter + quarterWeight + quarterDiameter
        + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter + "1\n"
        + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter
        + dimeWeight + dimeDiameter + nickelWeight + nickelDiameter + "2\n" 
        + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals("EXACT CHANGE ONLY\n" + "$0.25\n" + "$0.50\n"
        + "$0.75\n" + "$1.00\n" + "THANK YOU\n"
        + "INSERT COINS\n" + "$0.25\n" + "$0.50\n" + "$0.60\n" + "$0.65\n"
        + "THANK YOU\n" + "INSERT COINS\n" + "$0.25\n" + "$0.50\n" + "THANK YOU\n" + "INSERT COINS\n"));
    }
    @Test
    public void displaysPriceAndThenAmountAfterSelectingItemWithInsufficentFunds(){
        vendingMachine.stockItem(1, 3);
        vendingMachine.stockItem(2, 4);
        vendingMachine.stockItem(3, 1);
        String input = quarterWeight + quarterDiameter + "1\n" + dimeWeight + dimeDiameter + "2\n" + nickelWeight
        + nickelDiameter + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals(exact + "$0.25\n"+ "PRICE $1.00\n" + "$0.25\n" + "$0.35\n"
        + "PRICE $0.65\n" + "$0.35\n" + "$0.40\n" + "PRICE $0.50\n" + "$0.40\n"));
    }
    @After
    public void tearDown(){
        System.setIn(null);
        System.setOut(null);
    }
}

