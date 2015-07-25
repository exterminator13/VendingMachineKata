
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
    public void addDime(){
        this.dimes++;
        this.amountInMachine.addMoney(.10);
    }
    public String getCoinAmount(){
        String quarters = " Quarters, ";
        String dimes = " Dimes, ";
        String nickels = " Nickels";
        if(this.quarters == 1){
            quarters = " Quarter, ";
        }
        if(this.dimes == 1){
            dimes = " Dime, ";
        }
        if(this.nickels == 1){
            nickels = " Nickel";
        }
        return this.quarters + quarters + this.dimes + dimes + this.nickels + nickels;
    }
}
