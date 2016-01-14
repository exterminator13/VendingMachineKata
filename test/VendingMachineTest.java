import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachinekata.CoinValues;
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
    String soldOut = "SOLD OUT\n";
    int length;
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        System.setOut(new PrintStream(outStream));
        vendorAccess = vendingMachine.getVendorAccess();
        length = 0;
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
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 1 entered for cola
        assertEquals(soldOut, outStream.toString().substring(length, length + soldOut.length()));
        length += soldOut.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 2 entered for candy
        assertEquals(soldOut, outStream.toString().substring(length, length + soldOut.length()));
        length += soldOut.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 3 entered for chips
        assertEquals(soldOut, outStream.toString().substring(length, length + soldOut.length()));
        length += soldOut.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        // 0 entered to exit
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
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 1 entered for cola
        String colaPrice = "$1.00\n";
        assertEquals(colaPrice, outStream.toString().substring(length, length + colaPrice.length()));
        length += colaPrice.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 2 entered for candy
        String candyPrice = "$0.65\n";
        assertEquals(candyPrice, outStream.toString().substring(length, length + candyPrice.length()));
        length += candyPrice.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 3 entered for chips
        String chipsPrice = "$0.50\n";
        assertEquals(chipsPrice, outStream.toString().substring(length, length + chipsPrice.length()));
        length += chipsPrice.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 0 entered to exit
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
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 1st quarter inserted
        String firstQuarterAdded = "$0.25\n";
        assertEquals(firstQuarterAdded, outStream.toString().substring(length, length + firstQuarterAdded.length()));
        length += firstQuarterAdded.length();
        // 2nd quarter inserted
        String secondQuarterAdded = "$0.50\n";
        assertEquals(secondQuarterAdded, outStream.toString().substring(length, length + secondQuarterAdded.length()));
        length += secondQuarterAdded.length();
        // 3rd quarter inserted
        String thirdQuarterAdded = "$0.75\n";
        assertEquals(thirdQuarterAdded, outStream.toString().substring(length, length + thirdQuarterAdded.length()));
        length += thirdQuarterAdded.length();
        // 4th quarter inserted
        String fourthQuarterAdded = "$1.00\n";
        assertEquals(fourthQuarterAdded, outStream.toString().substring(length, length + fourthQuarterAdded.length()));
        length += fourthQuarterAdded.length();
        // 1 entered for cola
        assertEquals(thank, outStream.toString().substring(length, length + thank.length()));
        length += thank.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // 1st quarter inserted
        assertEquals(firstQuarterAdded, outStream.toString().substring(length, length + firstQuarterAdded.length()));
        length += firstQuarterAdded.length();
        // 2nd quarter inserted
        assertEquals(secondQuarterAdded, outStream.toString().substring(length, length + secondQuarterAdded.length()));
        length += secondQuarterAdded.length();
        // 1 dime inserted
        String dimeAdded = "$0.60\n";
        assertEquals(dimeAdded, outStream.toString().substring(length, length + dimeAdded.length()));
        length += dimeAdded.length();
        // 1 nickel inserted
        String nickelAdded = "$0.65\n";
        assertEquals(nickelAdded, outStream.toString().substring(length, length + nickelAdded.length()));
        length += nickelAdded.length();
        // 2 entered for candy
        assertEquals(thank, outStream.toString().substring(length, length + thank.length()));
        length += thank.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // 1st quarter inserted
        assertEquals(firstQuarterAdded, outStream.toString().substring(length, length + firstQuarterAdded.length()));
        length += firstQuarterAdded.length();
        // 2nd quarter inserted
        assertEquals(secondQuarterAdded, outStream.toString().substring(length, length + secondQuarterAdded.length()));
        length += secondQuarterAdded.length();
        // 3 entered for chips
        assertEquals(thank, outStream.toString().substring(length, length + thank.length()));
        length += thank.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        // 0 entered to exit
        
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
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // 1st quarter inserted
        String firstQuarterAdded = "$0.25\n";
        assertEquals(firstQuarterAdded, outStream.toString().substring(length, length + firstQuarterAdded.length()));
        length += firstQuarterAdded.length();
        // 1 entered for cola
        String colaPrice = "PRICE $1.00\n";
        assertEquals(colaPrice, outStream.toString().substring(length, length + colaPrice.length()));
        length += colaPrice.length();
        assertEquals(firstQuarterAdded, outStream.toString().substring(length, length + firstQuarterAdded.length()));
        length += firstQuarterAdded.length();
        // dime inserted
        String dimeAdded = "$0.35\n";
        assertEquals(dimeAdded, outStream.toString().substring(length, length + dimeAdded.length()));
        length += dimeAdded.length();
        // 2 entered for candy
        String candyPrice = "PRICE $0.65\n";
        assertEquals(candyPrice, outStream.toString().substring(length, length + candyPrice.length()));
        length += candyPrice.length();
        assertEquals(dimeAdded, outStream.toString().substring(length, length + dimeAdded.length()));
        length += dimeAdded.length();
        // nickel inserted
        String nickelAdded = "$0.40\n";
        assertEquals(nickelAdded, outStream.toString().substring(length, length + nickelAdded.length()));
        length += nickelAdded.length();
        // 3 entered for cihps
        String chipsPrice = "PRICE $0.50\n";
        assertEquals(chipsPrice, outStream.toString().substring(length, length + chipsPrice.length()));
        length += chipsPrice.length();
        assertEquals(nickelAdded, outStream.toString().substring(length, length + nickelAdded.length()));
        length += nickelAdded.length();
        // 0 entered to exit  
    }
    @Test
    public void returnsCorrectAmountOfCoinsIntoCoinDispenserWhenItCanMakeChange(){
        vendorAccess.stockItem(2, 4);
        vendorAccess.stockItem(1, 3);
        vendorAccess.stockItem(3, 1);
        vendorAccess.stockCoins(CoinValues.DIME_VALUE, 2);
        String input = quarterWeight + quarterDiameter + dimeWeight + dimeDiameter
        + dimeWeight + dimeDiameter + dimeWeight + dimeDiameter 
        + "3\n" + nickelWeight + nickelDiameter + "3\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // quarter inserted
        String quarterAdded = "$0.25\n";
        assertEquals(quarterAdded, outStream.toString().substring(length, length + quarterAdded.length()));
        length += quarterAdded.length();
        // 1st dime inserted
        String firstDimeAdded = "$0.35\n";
        assertEquals(firstDimeAdded, outStream.toString().substring(length, length + firstDimeAdded.length()));
        length += firstDimeAdded.length();
        // 2nd dime inserted
        String secondDimeAdded = "$0.45\n";
        assertEquals(secondDimeAdded, outStream.toString().substring(length, length + secondDimeAdded.length()));
        length += secondDimeAdded.length();
        // 3rd dime inserted
        String thirdDimeAdded = "$0.55\n";
        assertEquals(thirdDimeAdded, outStream.toString().substring(length, length + thirdDimeAdded.length()));
        length += thirdDimeAdded.length();
        // 3 entered for candy
        String cantMakeChange = "Can't make change\n";
        assertEquals(cantMakeChange, outStream.toString().substring(length, length + cantMakeChange.length()));
        length += cantMakeChange.length();
        assertEquals(thirdDimeAdded, outStream.toString().substring(length, length + thirdDimeAdded.length()));
        length += thirdDimeAdded.length();
        // nickel inserted
        String nickelAdded = "$0.60\n";
        assertEquals(nickelAdded, outStream.toString().substring(length, length + nickelAdded.length()));
        length += nickelAdded.length();
        // 3 entered for candy
        assertEquals(thank, outStream.toString().substring(length, length + thank.length()));
        length += thank.length();
        String amountReturned = "0 Quarters, 1 Dime, 0 Nickels returned\n";
        assertEquals(amountReturned, outStream.toString().substring(length, length + amountReturned.length()));
        length += amountReturned.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // 0 entered to exit
    }
    @Test
    public void coinsAreReturnedToCustomerMidTransaction(){
        vendorAccess.stockItem(1, 3);
        vendorAccess.stockCoins(CoinValues.DIME_VALUE, 2);
        String input = quarterWeight + quarterDiameter + dimeWeight + dimeDiameter 
        + nickelWeight + nickelDiameter + "return\n" + "1\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // quarter inserted
        String quarterAdded = "$0.25\n";
        assertEquals(quarterAdded, outStream.toString().substring(length, length + quarterAdded.length()));
        length += quarterAdded.length();
        // dime inserted
        String dimeAdded = "$0.35\n";
        assertEquals(dimeAdded, outStream.toString().substring(length, length + dimeAdded.length()));
        length += dimeAdded.length();
        // nickel inserted
        String nickelAdded = "$0.40\n";
        assertEquals(nickelAdded, outStream.toString().substring(length, length + nickelAdded.length()));
        length += nickelAdded.length();
        // return button pressed
        String returnedAmount = "1 Quarter, 1 Dime, 1 Nickel\n";
        assertEquals(returnedAmount, outStream.toString().substring(length, length + returnedAmount.length()));
        length += returnedAmount.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // 1 pressed for cola
        String colaPrice = "$1.00\n";
        assertEquals(colaPrice, outStream.toString().substring(length, length + colaPrice.length()));
        length += colaPrice.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        // 0 pressed to exit
    }
    @Test
    public void itemIsRemovedFromStockAfterSuccessfulTransaction(){
        vendorAccess.stockItem(1, 1);
        vendorAccess.stockCoins(CoinValues.DIME_VALUE, 4);
        String input = quarterWeight + quarterDiameter + quarterWeight
        + quarterDiameter + quarterWeight + quarterDiameter + quarterWeight + quarterDiameter
        + "1\n" + "1\n" + "return\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // quarter inserted
        String firstQuarter = "$0.25\n";
        assertEquals(firstQuarter, outStream.toString().substring(length, length + firstQuarter.length()));
        length += firstQuarter.length();
        // second quarter inserted
        String secondQuarter = "$0.50\n";
        assertEquals(secondQuarter, outStream.toString().substring(length, length + secondQuarter.length()));
        length += secondQuarter.length();
        // third quarter inserted
        String thirdQuarter = "$0.75\n";
        assertEquals(thirdQuarter, outStream.toString().substring(length, length + thirdQuarter.length()));
        length += thirdQuarter.length();
        // fourth quarter inserted
        String fourthQuarter = "$1.00\n";
        assertEquals(fourthQuarter, outStream.toString().substring(length, length + fourthQuarter.length()));
        length += fourthQuarter.length();
        // 1 entered for cola
        assertEquals(thank, outStream.toString().substring(length, length + thank.length()));
        length += thank.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // 1 entered for cola
        assertEquals(soldOut, outStream.toString().substring(length, length + soldOut.length()));
        length += soldOut.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // return button pressed
        String noCoinsReturned = "0 Quarters, 0 Dimes, 0 Nickels\n";
        assertEquals(noCoinsReturned, outStream.toString().substring(length, length + noCoinsReturned.length()));
        length += noCoinsReturned.length();
        assertEquals(insert, outStream.toString().substring(length, length + insert.length()));
        length += insert.length();
        // 0 entered to exit
    }
    @Test
    public void invalidCoinsAreReturnedToCustomer(){
        String input = "5.670 g\n" + "0.789 in\n" + "5.670 g\n" + "0.955 in\n" + "3.479 g\n"
        + "0.835 in\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // invalid coin inserted
        String coinReturned = "Coin returned\n";
        assertEquals(coinReturned, outStream.toString().substring(length, length + coinReturned.length()));
        length += coinReturned.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // quarter inserted
        String quarter = "$0.25\n";
        assertEquals(quarter, outStream.toString().substring(length, length + quarter.length()));
        length += quarter.length();
        // invalid coin entered
        assertEquals(coinReturned, outStream.toString().substring(length, length + coinReturned.length()));
        length += coinReturned.length();
        assertEquals(quarter, outStream.toString().substring(length, length + quarter.length()));
        length += quarter.length();
        // 0 entered to exit
    }
    @Test
    public void vendingMachineDisplaysErrorIfSelectionIsInvalid(){
        String input = "098\n" + "aaslkdjf\n" + "0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        vendingMachine.run();
        assertEquals(instructions, outStream.toString().substring(0, instructions.length()));
        length += instructions.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // invalid number entered
        String invalid = "INVALID SELECTION\n";
        assertEquals(invalid, outStream.toString().substring(length, length + invalid.length()));
        length += invalid.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        length += exact.length();
        // invalid characters entered
        assertEquals(invalid, outStream.toString().substring(length, length + invalid.length()));
        length += invalid.length();
        assertEquals(exact, outStream.toString().substring(length, length + exact.length()));
        // 0 entered to exit
    }
    @After
    public void tearDown(){
        System.setIn(null);
        System.setOut(null);
    }
}

