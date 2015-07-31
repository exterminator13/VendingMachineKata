
package vendingmachinekata;
import java.lang.Runnable;
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
    @Override
    public void run(){
        Scanner reader = new Scanner(System.in);
        System.out.print(this.vendingMachineTransaction.display()+"\n");
        int item = Integer.parseInt(reader.nextLine()) - 1;
        if(this.items.size() > item && item >= 0){
            System.out.print(this.vendingMachineTransaction.selectItem(this.itemSelector(item))+"\n");
        }else{
            System.out.print("INVALID SELECTION\n");
        }
        
    }
}
