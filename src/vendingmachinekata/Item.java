
package vendingmachinekata;

public class Item {
    String itemName;
    double price;
    Money money;
    int stock;
    public Item(String itemName, double price){
        this.itemName = itemName;
        this.price = price;
        this.money = new Money();
        this.money.addMoney(this.price);
        this.stock = 0;
    }
    public String getName(){
        return this.itemName;
    }
    public void setStock(int amount){
        this.stock = amount;
    }
    public void addStock(int amount){
        this.stock += amount;
    }
    public int getStock(){
        return this.stock;
    }
    public double getPrice(){
        return this.price;
    }
    public Money getMoney(){
        return this.money;
    }
    public void itemSold(){
        this.stock = this.stock - 1;
    }
}
