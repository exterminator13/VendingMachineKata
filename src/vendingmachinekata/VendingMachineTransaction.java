package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    MachineCoinTracker coinTracker;
    MachineCoinTracker transactionDispenser;
    Item cola;
    Item candy;
    Item chips;
    public VendingMachineTransaction(MachineCoinTracker coinTracker){
        this.currentAmount = new Money();
        this.coinTracker = coinTracker; 
    }
    public void startNewTransaction(){
        this.currentAmount = new Money();
        this.transactionDispenser = new MachineCoinTracker();
        this.transactionDispenser.setCoins(coinTracker.getCoins(CoinValues.QUARTER_VALUE), coinTracker.getCoins(CoinValues.DIME_VALUE), coinTracker.getCoins(CoinValues.NICKEL_VALUE));
    }
    public MachineCoinTracker getTransactionDispenser(){
        return this.transactionDispenser;
    }
    public double getCurrentAmount(){
        return this.currentAmount.getAmount();
    }
    public MachineCoinTracker getCoinTracker(){
        return this.coinTracker;
    }
    public String display(){
        if(this.coinTracker.getAmountInMachine().getAmount() == 0.00){
            return "EXACT CHANGE ONLY";
        }
        if(this.currentAmount.getAmount() == 0.00){
            return "INSERT COINS";
        }else{
            return this.currentAmount.toString();
        }
    }
}
