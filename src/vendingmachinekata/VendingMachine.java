
package vendingmachinekata;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine implements Runnable{
    CoinDispenser coinDispenser;
    VendingMachineTransaction vendingMachineTransaction;
    Item cola;
    Item candy;
    Item chips;
    List<Item> items;
    public VendingMachine(){
        this.coinDispenser = new CoinDispenser();
        this.vendingMachineTransaction = new VendingMachineTransaction(this.coinDispenser);
        this.items = new ArrayList();
        this.cola = new Item(1.00);
        this.candy = new Item(0.65);
        this.chips = new Item(0.50);
        this.items.add(this.cola);
        this.items.add(this.candy);
        this.items.add(this.chips);
        
    }
    public Item itemSelector(int itemNumber){
        return this.items.get(itemNumber);
    }
    public boolean stockItem(int itemNumber, int amount){
        itemNumber = itemNumber - 1;
        if(this.items.size() > itemNumber && itemNumber >= 0){
            itemSelector(itemNumber).setStock(amount);
            return true;
        }
        return false;
    }
    @Override
    public void run(){
        Scanner reader = new Scanner(System.in);
        while(true){
            System.out.print(this.vendingMachineTransaction.display()+"\n");
            String input = reader.nextLine();
            if(input.contains("g")){
                String diameter = reader.nextLine();
                if(diameter.contains("in")){
                    this.vendingMachineTransaction.coinRecognition(input, diameter);
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
                    System.out.print(this.vendingMachineTransaction.selectItem(this.itemSelector(item))+"\n");
                }else{
                    System.out.print("INVALID SELECTION\n");
                }
            }
        }
    }
}
