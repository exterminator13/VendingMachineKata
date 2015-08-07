
package vendingmachinekata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendorAccess implements Runnable{
    VendingMachine vendingMachine;
    List<Item> items;
    public VendorAccess(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
        this.items = this.vendingMachine.getListOfItems();
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
    public void stockCoins(int coinValue, int amount){
        if(coinValue == 25){
            this.vendingMachine.coinDispenser.addQuarters(amount);
        }
        if(coinValue == 10){
            this.vendingMachine.coinDispenser.addDimes(amount);
        }
        if(coinValue == 5){
            this.vendingMachine.coinDispenser.addNickels(amount);
        }
    }
    @Override
    public void run() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Press 1 to stock items\n" + "Press 2 to stock coins\n"
        + "Press 3 to empty coin dispenser\n");
        while(true){              
            System.out.print("WELCOME VENDOR\n");   
            int input = Integer.parseInt(reader.nextLine()); 
            if(input == 0){
                break;
            }
        }
    }
}