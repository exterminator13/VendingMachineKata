
package vendingmachinekata;

public class Item {
    double price;
    Money money;
    public Item(double price){
        this.price = price;
        this.money = new Money();
        this.money.addMoney(this.price);
    }
    public double getPrice(){
        return this.price;
    }
    public Money getMoney(){
        return this.money;
    }
}
