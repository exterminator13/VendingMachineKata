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
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    public VendingMachineTest() {
    }
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        System.setOut(new PrintStream(outStream));
    }
    @Test
    public void machineDisplaysExactChangeOnlyWhenNoChange(){
        String selection = "1\n" + "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().contains("EXACT CHANGE ONLY\n"));
        
    }
    @Test
    public void inputNumberForItemWithNoStockReturnsSoldOut(){ 
        String selection = "1\n" + "2\n" + "3\n" + "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(selection.getBytes());
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
        ByteArrayInputStream in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals("EXACT CHANGE ONLY\n" + "$1.00\n"
        + "EXACT CHANGE ONLY\n" + "$0.65\n" + "EXACT CHANGE ONLY\n" + "$0.50\n" + "EXACT CHANGE ONLY\n"));
    }
    @After
    public void tearDown(){
        System.setIn(null);
        System.setOut(null);
    }
}

