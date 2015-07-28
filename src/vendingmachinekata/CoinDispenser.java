
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
    public void addQuarters(int amount){
        this.quarters = this.quarters + amount;
        this.amountInMachine.addMoney(.25 * amount);
    }
    public void addDimes(int amount){
        this.dimes = this.dimes + amount;
        this.amountInMachine.addMoney(.10 * amount);
    }
    public void addNickels(int amount){
        this.nickels = this.nickels + amount;
        this.amountInMachine.addMoney(.05 * amount);
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
        int quartersLeft = this.quarters;
        int dimesLeft = this.dimes;
        int nickelsLeft = this.nickels;
        int totalCoins = this.quarters + this.dimes + this.nickels;
        while(originalAmountToReturn > amountReturned){
            if(amountToReturn >= .25 && quartersLeft > 0){
                amountToReturn = amountToReturn - .25;
                quartersLeft = quartersLeft - 1;
                quartersReturned++;
                totalCoins = totalCoins - 1;
                amountReturned = amountReturned + .25;
                amountReturned = round(amountReturned);
                amountToReturn = round(amountToReturn);
            }else if(amountToReturn >= .10 && dimesLeft > 0){
                amountToReturn = amountToReturn - .1;
                dimesLeft = dimesLeft - 1;
                dimesReturned++;
                totalCoins = totalCoins - 1;
                amountReturned = amountReturned + .1;
                amountReturned = round(amountReturned);
                amountToReturn = round(amountToReturn);
            }else if(amountToReturn >= .05 && nickelsLeft > 0){
                amountToReturn = amountToReturn - .05;
                nickelsLeft = nickelsLeft - 1;
                nickelsReturned++;
                totalCoins = totalCoins - 1;
                amountReturned = amountReturned + .05;
                amountReturned = round(amountReturned);
                amountToReturn = round(amountToReturn);
            }
            else{
                return "Can't make change";
            }
        }
        this.quarters = this.quarters - quartersReturned;
        this.dimes = this.dimes - dimesReturned;
        this.nickels = this.nickels - nickelsReturned;
        return stringMaker(quartersReturned, dimesReturned, nickelsReturned);
    }
}
