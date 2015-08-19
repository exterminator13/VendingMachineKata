package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    CoinDispenser coinDispenser;
    CoinDispenser transactionDispenser;
    Item cola;
    Item candy;
    Item chips;
    public VendingMachineTransaction(CoinDispenser coinDispenser){
        this.currentAmount = new Money();
        this.coinDispenser = coinDispenser; 
    }
    public void startNewTransaction(){
        this.transactionDispenser = new CoinDispenser();
        this.transactionDispenser.setCoins(coinDispenser.getCoins(CoinValues.QUARTER_VALUE), coinDispenser.getCoins(CoinValues.DIME_VALUE), coinDispenser.getCoins(CoinValues.NICKEL_VALUE));
    }
    public double getCurrentAmount(){
        return this.currentAmount.getAmount();
    }
    public CoinDispenser getCoinDispenser(){
        return this.coinDispenser;
    }
    public String display(){
        if(this.coinDispenser.getAmountInMachine().getAmount() == 0.00){
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
            this.coinDispenser.addCoins(CoinValues.QUARTER_VALUE, 1);
            return true;
        }
        if(weight.equals("2.268 g") && diameter.equals("0.705 in")){
            this.currentAmount.addMoney(CoinValues.DIME_VALUE);
            this.coinDispenser.addCoins(CoinValues.DIME_VALUE, 1);
            return true;
        }
        if(weight.equals("5.000 g") && diameter.equals("0.835 in")){
            this.currentAmount.addMoney(CoinValues.NICKEL_VALUE);
            this.coinDispenser.addCoins(CoinValues.NICKEL_VALUE, 1);
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
        String changeMade = this.coinDispenser.makeChange(amountToReturn);
        if(changeMade.equals("Can't make change")){
            return changeMade;
        }
        this.currentAmount.removeMoney(amountToReturn);
        return changeMade + " returned";
    }
    public String returnCoins(){
        String coinsReturned = this.coinDispenser.getDifference(this.transactionDispenser);
        this.coinDispenser.setCoins(this.transactionDispenser.getCoins(CoinValues.QUARTER_VALUE), this.transactionDispenser.getCoins(CoinValues.DIME_VALUE), this.transactionDispenser.getCoins(CoinValues.NICKEL_VALUE));
        this.currentAmount.setAmount(0.00);
        return coinsReturned;
    }
}
