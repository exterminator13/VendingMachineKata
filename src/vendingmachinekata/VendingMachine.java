
package vendingmachinekata;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine implements Runnable{
    CoinDispenser coinDispenser;
    VendingMachineTransaction vendingMachineTransaction;
    VendorAccess vendorAccess;
    Item cola;
    Item candy;
    Item chips;
    List<Item> items;
    public VendingMachine(){
        this.coinDispenser = new CoinDispenser();
        this.vendingMachineTransaction = new VendingMachineTransaction(this.coinDispenser);
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
    @Override
    public void run(){
        int i = 1;
        for(Item item : this.items){
            System.out.print("Press " + i + " for " + item.getName().toUpperCase() + "\n");
            i++;
        }
        Scanner reader = new Scanner(System.in);
        this.vendingMachineTransaction.startNewTransaction();
        while(true){
            System.out.print(this.vendingMachineTransaction.display()+"\n");
            String input = reader.nextLine();
            if(input.equals("return")){
                System.out.print(this.vendingMachineTransaction.returnCoins()+"\n");
                this.vendingMachineTransaction.startNewTransaction();
            }else if(input.equals("3689")){
                vendorAccess.run();
                System.out.print("EXITING\n");
            }else{
                if(input.contains("g")){
                    String diameter = reader.nextLine();
                    boolean validCoin = false;
                    if(diameter.contains("in")){
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
                        String itemSelector = this.vendingMachineTransaction.selectItem(this.itemSelector(item));
                        if(itemSelector.contains("THANK YOU")){
                            this.items.get(item).itemSold();
                            this.vendingMachineTransaction.startNewTransaction();
                        }
                        System.out.print(itemSelector+"\n");
                    }else{
                        System.out.print("INVALID SELECTION\n");
                    }
                }
            }
        }
    }
}
