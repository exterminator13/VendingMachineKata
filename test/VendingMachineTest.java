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
        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().contains("EXACT CHANGE ONLY\n"));
        
    }
    @Test
    public void inputNumberForItemWithNoStockReturnsSoldOut(){ 
        String selection = "1\n" + "2\n" + "3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().contentEquals("EXACT CHANGE ONLY\n" + "SOLD OUT\n"));
    }
    @Test
    public void vendingMachineDisplaysAmountForItemWithNoCoinsInserted(){
        vendingMachine.stockItem(1, 20);
        String selection = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(selection.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().contentEquals("EXACT CHANGE ONLY\n" + "$1.00\n"));
    }
    @After
    public void tearDown(){
        System.setIn(null);
        System.setOut(null);
    }
}

