package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    MachineCoinTracker coinTracker;
    MachineCoinTracker transactionDispenser;
    Item cola;
    Item candy;
    Item chips;
    public VendingMachineTransaction(MachineCoinTracker coinDispenser){
        this.currentAmount = new Money();
        this.coinTracker = coinDispenser; 
    }
    public void startNewTransaction(){
        this.transactionDispenser = new MachineCoinTracker();
        this.transactionDispenser.setCoins(coinTracker.getCoins(CoinValues.QUARTER_VALUE), coinTracker.getCoins(CoinValues.DIME_VALUE), coinTracker.getCoins(CoinValues.NICKEL_VALUE));
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
    public boolean coinRecognition(String weight, String diameter){
        if(weight.equals("5.670 g") && diameter.equals("0.955 in")){
            this.currentAmount.addMoney(CoinValues.QUARTER_VALUE);
            this.coinTracker.addCoins(CoinValues.QUARTER_VALUE, 1);
            return true;
        }
        if(weight.equals("2.268 g") && diameter.equals("0.705 in")){
            this.currentAmount.addMoney(CoinValues.DIME_VALUE);
            this.coinTracker.addCoins(CoinValues.DIME_VALUE, 1);
            return true;
        }
        if(weight.equals("5.000 g") && diameter.equals("0.835 in")){
            this.currentAmount.addMoney(CoinValues.NICKEL_VALUE);
            this.coinTracker.addCoins(CoinValues.NICKEL_VALUE, 1);
            return true;
        }
        return false;
    }
    public String selectItem(Item item){
        if(item.getStock() <= 0){
            return "SOLD OUT";
        }else{
            if(this.currentAmount.getAmount() == 0.00){
                return item.getMoney().toString();
            }else if (this.currentAmount.getAmount() < item.getPrice()){
                return "PRICE " + item.getMoney().toString();
            }else if(this.currentAmount.getAmount() == item.getPrice()){
                item.itemSold();
                this.currentAmount.removeMoney(item.getPrice());
                return "THANK YOU";
            }else if(this.currentAmount.getAmount() > 0.00){
                this.currentAmount.removeMoney(item.getPrice());
                String changeMade = this.makeChange();
                if(changeMade.equals("Can't make change")){
                    this.currentAmount.addMoney(item.getPrice());
                    return changeMade;
                }else{ 
                    item.itemSold();
                    return "THANK YOU\n" + changeMade;
                }
            }
            return "";
        }
    }
    public String makeChange(){
        this.currentAmount.setAmount(RoundDoubles.round(this.currentAmount.getAmount()));
        double amountToReturn = this.currentAmount.getAmount();
        ChangeMaker changeMaker = new ChangeMaker(amountToReturn, this.coinTracker);
        String changeMade = changeMaker.returnChange();
        if(changeMade.equals("Can't make change")){
            return changeMade;
        }
        this.currentAmount.removeMoney(amountToReturn);
        return changeMade + " returned";
    }
    public String returnCoins(){
        String coinsReturned = this.coinTracker.getDifference(this.transactionDispenser);
        this.coinTracker.setCoins(this.transactionDispenser.getCoins(CoinValues.QUARTER_VALUE), this.transactionDispenser.getCoins(CoinValues.DIME_VALUE), this.transactionDispenser.getCoins(CoinValues.NICKEL_VALUE));
        this.currentAmount.setAmount(0.00);
        return coinsReturned;
    }
}
