
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
    public String getDifference(CoinDispenser coinDispenser){
        return stringMaker(this.getCoins(CoinValues.QUARTER_VALUE) - coinDispenser.getCoins(CoinValues.QUARTER_VALUE), this.getCoins(CoinValues.DIME_VALUE) - coinDispenser.getCoins(CoinValues.DIME_VALUE), this.getCoins(CoinValues.NICKEL_VALUE) - coinDispenser.getCoins(CoinValues.NICKEL_VALUE));
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
        double originalAmountToReturn = amountToReturn;
        int quartersReturned = 0;
        int dimesReturned = 0;
        int nickelsReturned = 0;        
        int quartersLeft = this.quarters;
        int dimesLeft = this.dimes;
        int nickelsLeft = this.nickels;
        int totalCoins = this.quarters + this.dimes + this.nickels;
        while(originalAmountToReturn > amountReturned){
            if(amountToReturn >= CoinValues.QUARTER_VALUE && quartersLeft > 0){
                amountToReturn = amountToReturn - CoinValues.QUARTER_VALUE;
                quartersLeft = quartersLeft - 1;
                quartersReturned++;
                totalCoins = totalCoins - 1;
                amountReturned = amountReturned + CoinValues.QUARTER_VALUE;
                amountReturned = RoundDoubles.round(amountReturned);
                amountToReturn = RoundDoubles.round(amountToReturn);
            }else if(amountToReturn >= CoinValues.DIME_VALUE && dimesLeft > 0){
                amountToReturn = amountToReturn - CoinValues.DIME_VALUE;
                dimesLeft = dimesLeft - 1;
                dimesReturned++;
                totalCoins = totalCoins - 1;
                amountReturned = amountReturned + CoinValues.DIME_VALUE;
                amountReturned = RoundDoubles.round(amountReturned);
                amountToReturn = RoundDoubles.round(amountToReturn);
            }else if(amountToReturn >= CoinValues.NICKEL_VALUE && nickelsLeft > 0){
                amountToReturn = amountToReturn - CoinValues.NICKEL_VALUE;
                nickelsLeft = nickelsLeft - 1;
                nickelsReturned++;
                totalCoins = totalCoins - 1;
                amountReturned = amountReturned + CoinValues.NICKEL_VALUE;
                amountReturned = RoundDoubles.round(amountReturned);
                amountToReturn = RoundDoubles.round(amountToReturn);
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
