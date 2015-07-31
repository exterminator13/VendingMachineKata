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
        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertTrue(outStream.toString().equals("EXACT CHANGE ONLY\n" + "SOLD OUT\n"));
    }
    @After
    public void tearDown(){
        System.setIn(null);
        System.setOut(null);
    }
}

