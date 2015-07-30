
package vendingmachinekata;

public class Item {
    double price;
    Money money;
    int stock;
    public Item(double price){
        this.price = price;
        this.money = new Money();
        this.money.addMoney(this.price);
        this.stock = 0;
    }
    public void setStock(int amount){
        this.stock = amount;
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
    
}
