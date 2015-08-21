
package vendingmachinekata;

public class ChangeMaker {
    double amountReturned;
    double originalAmountToReturn;
    double amountToReturn;
    int quartersReturned;
    int dimesReturned;
    int nickelsReturned;        
    int quartersLeft;
    int dimesLeft;
    int nickelsLeft;
    int totalCoins;
    MachineCoinTracker coinTracker;
    public ChangeMaker(double amountToReturn, MachineCoinTracker coinTracker){
        this.coinTracker = coinTracker;
        this.amountReturned = 0;
        this.amountToReturn = amountToReturn;
        this.originalAmountToReturn = amountToReturn;
        this.quartersReturned = 0;
        this.dimesReturned = 0;
        this.nickelsReturned = 0;
        this.quartersLeft = this.coinTracker.getCoins(CoinValues.QUARTER_VALUE);
        this.dimesLeft = this.coinTracker.getCoins(CoinValues.DIME_VALUE);
        this.nickelsLeft = this.coinTracker.getCoins(CoinValues.NICKEL_VALUE);
        this.totalCoins = this.quartersLeft + this.dimesLeft + this.nickelsLeft;
    }
    public String returnChange(){
        while(this.originalAmountToReturn > this.amountReturned){
            if(this.amountToReturn >= CoinValues.QUARTER_VALUE && this.quartersLeft > 0){
                returnCoin(CoinValues.QUARTER_VALUE);
            }else if(this.amountToReturn >= CoinValues.DIME_VALUE && this.dimesLeft > 0){
                returnCoin(CoinValues.DIME_VALUE);
            }else if(this.amountToReturn >= CoinValues.NICKEL_VALUE && this.nickelsLeft > 0){
                returnCoin(CoinValues.NICKEL_VALUE);
            }                
            else{
                return "Can't make change";
            }
            this.amountReturned = RoundDoubles.round(this.amountReturned);
            this.amountToReturn = RoundDoubles.round(this.amountToReturn);
        }
        this.coinTracker.setCoins(quartersLeft, dimesLeft, nickelsLeft);
        return StringMaker.stringMaker(this.quartersReturned, this.dimesReturned, this.nickelsReturned);
    } 
    public void returnCoin(double coinValue){
        if(coinValue == CoinValues.QUARTER_VALUE){
            this.quartersLeft--;
            this.quartersReturned++;
        }
        if(coinValue == CoinValues.DIME_VALUE){
            this.dimesLeft--;
            this.dimesReturned++;
        }
        if(coinValue == CoinValues.NICKEL_VALUE){
            this.nickelsLeft--;
            this.nickelsReturned++;
        }
        this.amountReturned = this.amountReturned + coinValue;
        this.amountToReturn = this.amountToReturn - coinValue;
        this.totalCoins--;
    }
}
