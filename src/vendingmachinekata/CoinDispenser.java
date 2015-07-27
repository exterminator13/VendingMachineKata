
package vendingmachinekata;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public static double round(double number){
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
    public String makeChange(double amountToReturn){
        double amountReturned = 0;
        double originalAmountToReturn = amountToReturn;
        int quartersReturned = 0;
        int dimesReturned = 0;
        int nickelsReturned = 0;
        while(originalAmountToReturn > amountReturned){
            if(amountToReturn >= .25 && this.quarters > 0){
                amountToReturn = amountToReturn - .25;
                this.quarters = this.quarters - 1;
                quartersReturned++;
                amountReturned = amountReturned + .25;
                amountReturned = round(amountReturned);
                amountToReturn = round(amountToReturn);
            }else if(amountToReturn >= .10 && this.dimes > 0){
                amountToReturn = amountToReturn - .1;
                this.dimes = this.dimes - 1;
                dimesReturned++;
                amountReturned = amountReturned + .1;
                amountReturned = round(amountReturned);
                amountToReturn = round(amountToReturn);
            }else if(amountToReturn >= .05 && this.nickels > 0){
                amountToReturn = amountToReturn - .05;
                this.nickels = this.nickels - 1;
                nickelsReturned++;
                amountReturned = amountReturned + .05;
                amountReturned = round(amountReturned);
                amountToReturn = round(amountToReturn);
            }
        }
        return stringMaker(quartersReturned, dimesReturned, nickelsReturned);
    }
}
