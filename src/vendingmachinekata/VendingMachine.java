
package vendingmachinekata;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VendingMachine implements Runnable{
    MachineCoinTracker coinTracker;
    VendingMachineTransaction vendingMachineTransaction;
    VendorAccess vendorAccess;
    Item cola;
    Item candy;
    Item chips;
    List<Item> items;
    public VendingMachine(){
        this.coinTracker = new MachineCoinTracker();
        this.vendingMachineTransaction = new VendingMachineTransaction(this.coinTracker);
        this.items = new ArrayList();
        this.cola = new Item("cola", 1.00);
        this.candy = new Item("candy", 0.65);
        this.chips = new Item("chips", 0.50);
        this.items.add(this.cola);
        this.items.add(this.candy);
        this.items.add(this.chips);
        this.vendorAccess = new VendorAccess(this);
    }
    public List<Item> getListOfItems(){
        return this.items;
    }
    public VendorAccess getVendorAccess(){
        return this.vendorAccess;
    }
    public Item itemSelector(int itemNumber){
        return this.items.get(itemNumber);
    }
    public MachineCoinTracker getCoinTracker(){
        return this.coinTracker;
    }
    @Override
    public void run(){
        //Uses new line statements for testing purposes
        int i = 1;
        for(Item item : this.items){
            System.out.print("Press " + i + " for " + item.getName().toUpperCase() + "\n");
            i++;
        }
        System.out.print("Press 0 to exit\n");
        Scanner reader = new Scanner(System.in);
        String pattern = "(\\d)(.)(\\d{3})(\\s)(g)";
        Pattern match = Pattern.compile(pattern);
        while(true){
            System.out.print(this.vendingMachineTransaction.display()+"\n");
            String input = reader.nextLine();
            if(input.equals("return")){
                ChangeMaker changeMaker = new ChangeMaker(this.vendingMachineTransaction.getCurrentAmount(), this.vendingMachineTransaction.getCoinTracker());
                System.out.print(changeMaker.returnChange()+"\n");
                this.vendingMachineTransaction.startNewTransaction();
            }else if(input.equals("3689")){
                vendorAccess.run();
                System.out.print("EXITING\n");
            }else{
                Matcher m = match.matcher(input);
                if(m.matches()){
                    String diameter = reader.nextLine();
                    boolean validCoin = false;
                    String inchPattern = "(\\d)(.)(\\d{3})(\\s)(in)";
                    Pattern diameterMatch = Pattern.compile(inchPattern);
                    Matcher matcher = diameterMatch.matcher(diameter);
                    if(matcher.matches()){
                        validCoin = this.vendingMachineTransaction.coinRecognition(input, diameter);    
                    }
                    if(validCoin == false){
                        System.out.print("Coin returned\n");
                    }
                }else{
                    //Assigned default value to prevent exceptions
                    int item = -2;
                    //Subtract one to accommodate for 0 based index
                    try{
                    item = Integer.parseInt(input) - 1;
                    }catch(Exception d){
                    }
                    if(item == -1){
                        break;
                    }
                    if(this.items.size() > item && item >= 0){
                        ItemSelector itemSelector = new ItemSelector(this.vendingMachineTransaction);
                        String itemSelect = itemSelector.selectItem(this.items.get(item));
                        if(itemSelect.contains("THANK YOU")){
                            this.items.get(item).itemSold();
                            this.vendingMachineTransaction.startNewTransaction();
                        }
                        System.out.print(itemSelect+"\n");
                    }else{
                        System.out.print("INVALID SELECTION\n");
                    }
                }
            }
        }
    }
}
