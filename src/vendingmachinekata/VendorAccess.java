
package vendingmachinekata;

import java.util.List;
import java.util.Scanner;

public class VendorAccess implements Runnable{
    VendingMachine vendingMachine;
    List<Item> items;
    public VendorAccess(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
        this.items = this.vendingMachine.getListOfItems();
    }
    public boolean stockItem(int itemNumber, int amount){
        itemNumber = itemNumber - 1;
        if(this.items.size() > itemNumber && itemNumber >= 0){
            this.vendingMachine.itemSelector(itemNumber).addStock(amount);
            return true;
        }
        return false;
    }
    public String stockCoins(int coinValue, int amount){
        if(coinValue == 25){
            this.vendingMachine.coinDispenser.addCoins(25, amount);
            return this.vendingMachine.coinDispenser.getCoinAmount();
        }
        if(coinValue == 10){
            this.vendingMachine.coinDispenser.addCoins(10, amount);
            return this.vendingMachine.coinDispenser.getCoinAmount();
        }
        if(coinValue == 5){
            this.vendingMachine.coinDispenser.addCoins(5, amount);
            return this.vendingMachine.coinDispenser.getCoinAmount();
        }else{
            return "INVALID AMOUNT";
        }
    }
    @Override
    public void run() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Press 1 to stock items\n" + "Press 2 to stock coins\n"
        + "Press 3 to empty coin dispenser\n" + "Press 4 to add new item\n" + "Press 0 to exit\n");
        while(true){              
            System.out.print("WELCOME VENDOR\n");   
            int input = -1;
            try{
                input = Integer.parseInt(reader.nextLine()); 
            }catch(Exception d){
            }
            if(input == 1){
                System.out.print("ITEM'S NUMBER TO BE STOCKED:\n");
                //Adjust for 0 based index
                int itemNumber = -1;
                try{
                    itemNumber = Integer.parseInt(reader.nextLine()) - 1;
                }catch(Exception d){
                }
                if(itemNumber >= 0 && itemNumber < this.items.size()){
                    System.out.print("AMOUNT ADDED:\n");
                    int amount = -1;
                    try{
                        amount = Integer.parseInt(reader.nextLine());
                    }catch(Exception d){
                    }
                    if(amount < 1){
                    System.out.print("INVALID AMOUNT\n");
                    }else{
                    // Added back 1 because test file for vending machine written to match number with item
                    this.stockItem(itemNumber + 1, amount);
                    String itemName = this.items.get(itemNumber).getName().toUpperCase() + "\n";
                    int itemStockAmount = this.items.get(itemNumber).getStock();
                    System.out.print("ITEM: " + itemName + "STOCK: " +  itemStockAmount + "\n");
                    }
                }else{
                    System.out.print("INVALID ITEM NUMBER\n");
                }
            }
            if(input == 2){
                System.out.print("COIN VALUE:\n");
                int coinValue = -1;
                try{
                    coinValue = Integer.parseInt(reader.nextLine());
                }catch(Exception d){
                }
                if(coinValue == 5 || coinValue == 10 || coinValue == 25){
                    System.out.print("AMOUNT OF COINS:\n");
                    int coinAmount = -1;
                    try{
                        coinAmount = Integer.parseInt(reader.nextLine());
                    }catch(Exception d){
                    }
                    if(coinAmount < 1){
                        System.out.print("INVALID AMOUNT\n");
                    }else{
                    System.out.print(stockCoins(coinValue, coinAmount) + "\n");
                    }
                }else{
                    System.out.print("INVALID VALUE\n");
                }               
            }
            if(input == 3){
                this.vendingMachine.coinDispenser.setCoins(0, 0, 0);
                if("0 Quarters, 0 Dimes, 0 Nickels".equals(this.vendingMachine.coinDispenser.getCoinAmount())){
                    System.out.print("COINS EMPTIED\n");
                }else{
                    System.out.print("ERROR\n");
                }
            }
            if(input == 4){
                System.out.print("ENTER ITEM NAME:\n");
                String itemName = reader.nextLine();
                System.out.print("PRICE:\n");
                //Preventes invalid pricing
                double price = -1.00;
                try{
                    price = Double.parseDouble(reader.nextLine());
                }catch(Exception d){
                }
                if(price > 0.00){
                    Item newItem = new Item(itemName, price);
                    this.items.add(newItem);
                    System.out.print("NEW ITEM: " + newItem.getName().toUpperCase() + "\n"
                    + "PRICE: " + newItem.getMoney() + "\n" + "ITEM NUMBER: " + this.items.size()
                    + "\n");
                }else{
                    System.out.print("INVALID PRICE\n");
                }
            }
            if(input == 0){
                break;
            }
            if(input < 0 || input > 4){
                System.out.print("INVALID SELECTION\n");
            }   
        }
    }
}
