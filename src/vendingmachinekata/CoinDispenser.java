
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
    public String stringMaker(int quarterAmount, int dimeAmount, int nickelAmount){
        String quarter = " Quarters, ";
        String dime = " Dimes, ";
        String nickel = " Nickels";
        if(quarterAmount == 1){
            quarter = " Quarter, ";
        }
        if(dimeAmount == 1){
            dime = " Dime, ";
        }
        if(nickelAmount == 1){
            nickel = " Nickel";
        }
        return quarterAmount + quarter + dimeAmount + dime + nickelAmount + nickel;
    }
    public String getCoinAmount(){
        return stringMaker(this.quarters, this.dimes, this.nickels);
    }
    public String makeChange(double amountToReturn){
        double amountReturned = 0;
        int quartersReturned = 0;
        int dimesReturned = 0;
        int nickelsReturned = 0;
        while(amountToReturn > amountReturned){
            if(amountToReturn % .25 == 0 && this.quarters > 0){
                amountToReturn = amountToReturn - .25;
                this.quarters = this.quarters - 1;
                quartersReturned++;
                amountReturned = amountReturned + .25;
            }else if(amountToReturn % .10 == 0 && this.dimes > 0){
                amountToReturn = amountToReturn - .10;
                this.dimes = this.dimes - 1;
                dimesReturned++;
                amountReturned = amountReturned + .10;
            }
        }
        return stringMaker(quartersReturned, dimesReturned, nickelsReturned);
    }
}
