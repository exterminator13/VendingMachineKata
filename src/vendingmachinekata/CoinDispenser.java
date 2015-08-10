
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
    public void addCoins(int value, int amount){
        if(value == 25){
            this.quarters = this.quarters + amount;
            this.amountInMachine.addMoney(.25 * amount);
        }
        if(value == 10){
            this.dimes = this.dimes + amount;
            this.amountInMachine.addMoney(.10 * amount);
        }
        if(value == 5){
            this.nickels = this.nickels + amount;
            this.amountInMachine.addMoney(.05 * amount);
        }
    }
    public int getCoins(int value){
        if(value == 25){
            return this.quarters;
        }
        else if(value == 10){
            return this.dimes;
        }
        else if(value == 5){
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
    public String getDifference(CoinDispenser coinDispenser){
        return stringMaker(this.getCoins(25) - coinDispenser.getCoins(25), this.getCoins(10) - coinDispenser.getCoins(10), this.getCoins(5) - coinDispenser.getCoins(5));
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
    //rounds the double to proper .00 format
    public double round(double number){
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
            }else if(amountToReturn >= .1 && dimesLeft > 0){
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
