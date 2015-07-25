
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
    public Money getAmountInMachine(){
        return this.amountInMachine;
    }
    public void addQuarter(){
        this.quarters++;
        this.amountInMachine.addMoney(.25);
    }
    public void addDime(){
        this.dimes++;
        this.amountInMachine.addMoney(.10);
    }
    public void addNickel(){
        this.nickels++;
        this.amountInMachine.addMoney(.05);
    }
    public String getCoinAmount(){
        String quarter = " Quarters, ";
        String dime = " Dimes, ";
        String nickel = " Nickels";
        if(this.quarters == 1){
            quarter = " Quarter, ";
        }
        if(this.dimes == 1){
            dime = " Dime, ";
        }
        if(this.nickels == 1){
            nickel = " Nickel";
        }
        return this.quarters + quarter + this.dimes + dime + this.nickels + nickel;
    }
    public String makeChange(double amountToReturn){
        double amountReturned = 0;
        int quartersReturned = 0;
        int dimesReturned = 0;
        int nickelsReturned = 0;
        String quarter = " Quarters, ";
        String nickel = " Nickels, ";
        String dime = " Dimes";
        while(amountToReturn > amountReturned){
            if(amountToReturn % .25 == 0 && this.quarters > 0){
                amountToReturn = amountToReturn - .25;
                this.quarters = this.quarters - 1;
                quartersReturned++;
                amountReturned = amountReturned + .25;
            }
        }
        if(quartersReturned == 1){
            quarter = " Quarter, ";
        }
        return quartersReturned + quarter + nickelsReturned + nickel + dimesReturned + dime;
    }
}
