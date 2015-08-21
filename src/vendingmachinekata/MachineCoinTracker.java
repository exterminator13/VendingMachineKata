
package vendingmachinekata;

public class MachineCoinTracker {
    int nickels;
    int dimes;
    int quarters;
    Money amountInMachine;
    public MachineCoinTracker(){
        this.nickels = 0;
        this.dimes = 0;
        this.quarters = 0;
        this.amountInMachine = new Money();
    }
    public Money getAmountInMachine(){
        return this.amountInMachine;
    }
    public void addCoins(double value, int amount){
        if(value == CoinValues.QUARTER_VALUE){
            this.quarters = this.quarters + amount;
            this.amountInMachine.addMoney(CoinValues.QUARTER_VALUE * amount);
        }
        if(value == CoinValues.DIME_VALUE){
            this.dimes = this.dimes + amount;
            this.amountInMachine.addMoney(CoinValues.DIME_VALUE * amount);
        }
        if(value == CoinValues.NICKEL_VALUE){
            this.nickels = this.nickels + amount;
            this.amountInMachine.addMoney(CoinValues.NICKEL_VALUE * amount);
        }
    }
    public int getCoins(double value){
        if(value == CoinValues.QUARTER_VALUE){
            return this.quarters;
        }
        else if(value == CoinValues.DIME_VALUE){
            return this.dimes;
        }
        else if(value == CoinValues.NICKEL_VALUE){
            return this.nickels;
        }else{
            return 0;
        }
    }
    public void setCoins(int quarterAmount, int dimeAmount, int nickelAmount){
        this.quarters = quarterAmount;
        this.dimes = dimeAmount;
        this.nickels = nickelAmount;
    }
    public String getDifference(MachineCoinTracker coinTracker){
        return StringMaker.stringMaker(this.getCoins(CoinValues.QUARTER_VALUE) - coinTracker.getCoins(CoinValues.QUARTER_VALUE), this.getCoins(CoinValues.DIME_VALUE) - coinTracker.getCoins(CoinValues.DIME_VALUE), this.getCoins(CoinValues.NICKEL_VALUE) - coinTracker.getCoins(CoinValues.NICKEL_VALUE));
    }
    public String getCoinAmount(){
        return StringMaker.stringMaker(this.quarters, this.dimes, this.nickels);
    }
}
