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
        this.transactionDispenser = new CoinDispenser();
        this.transactionDispenser.setCoins(coinDispenser.getQuarters(), coinDispenser.getDimes(), coinDispenser.getNickels());
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
        if(this.currentAmount.toString().equals("$0.00")){
            return "INSERT COINS";
        }else{
            return this.currentAmount.toString();
        }
    }
    public int coinRecognition(String weight, String diameter){
        if(weight.equals("5.670 g") && diameter.equals("0.955 in")){
            this.currentAmount.addMoney(.25);
            this.coinDispenser.addQuarters(1);
            return 25;
        }
        if(weight.equals("2.268 g") && diameter.equals("0.705 in")){
            this.currentAmount.addMoney(.10);
            this.coinDispenser.addDimes(1);
            return 10;
        }
        if(weight.equals("5.000 g") && diameter.equals("0.835 in")){
            this.currentAmount.addMoney(.05);
            this.coinDispenser.addNickels(1);
            return 5;
        }
        return 0;
    }
    public String selectItem(Item item){
        if(item.getStock() == 0){
            return "SOLD OUT";
        }else{
            if(this.currentAmount.getAmount() == 0.00){
                return item.getMoney().toString();
            }else if (this.currentAmount.getAmount() < item.getPrice()){
                return "PRICE " + item.getMoney().toString();
            }else if(this.currentAmount.getAmount() >= item.getPrice()){
                this.currentAmount.removeMoney(item.getPrice());
                if(this.currentAmount.getAmount() > 0.00){
                    return this.makeChange();
                }
                return "THANK YOU";
            }
            return "";
        }
    }
    public String makeChange(){
        this.currentAmount.setAmount(this.coinDispenser.round(this.currentAmount.getAmount()));
        double amountToReturn = this.currentAmount.getAmount();
        String changeMade = this.coinDispenser.makeChange(amountToReturn);
        if(changeMade.equals("Can't make change")){
            return changeMade;
        }
        this.currentAmount.removeMoney(amountToReturn);
        return changeMade;
    }
    public String returnCoins(){
        String coinsReturned = this.coinDispenser.getDifference(this.transactionDispenser);
        this.coinDispenser = this.transactionDispenser;
        return coinsReturned;
    }
}