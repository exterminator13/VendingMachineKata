
package vendingmachinekata;

public class CoinDispenser {
    int nickels;
    int dimes;
    int quarters;
    Money amountInMachine;
    public CoinDispenser(){
        this.nickels = 0;
        this.dimes = 0;
        this.quarters = 0;
        this.amountInMachine = new Money();
    }
    public void addQuarter(){
        this.quarters++;
        this.amountInMachine.addMoney(.25);
    }
    public String getCoinAmount(){
        return this.quarters + " Quarters, " + this.dimes + " Dimes, " + this.nickels + " Nickels";
    }
}
