import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.VendingMachine;
public class VendingMachineTest {
    VendingMachine vendingMachine;
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
        vendingMachine.run();
        assertEquals("EXACT CHANGE ONLY\n", outStream.toString());
    }
    @After
    public void tearDown(){
        System.setOut(null);
    }
}
