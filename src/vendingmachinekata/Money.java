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
    @Override
    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");
        return "$" + decimalFormat.format(money);
        
    }
}
