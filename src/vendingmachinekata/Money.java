package vendingmachinekata;
import java.text.DecimalFormat;

public class Money {
    double money;
    public Money(){
        this.money = 0.00;
    }
    public void addMoney(double amount){
        this.money += amount;
    }
    public double getAmount(){
        return this.money;
    }
    public void setAmount(double amount){
        this.money = amount;
    }
    @Override
    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");
        return "$" + decimalFormat.format(money);
        
    }
    public void removeMoney(double amount){
        if(this.money >= amount){
            this.money = this.money - amount;
        }
    }
}
