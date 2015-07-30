package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    CoinDispenser coinDispenser;
    Item cola;
    Item candy;
    Item chips;
    public VendingMachineTransaction(CoinDispenser coinDispenser){
        this.currentAmount = new Money();
        this.coinDispenser = coinDispenser; 
        this.cola = new Item(1.00);
        this.candy = new Item(0.65);
        this.chips = new Item(0.50);
    }
    public double getCurrentAmount(){
        return this.currentAmount.getAmount();
    }
    public CoinDispenser getCoinDispenser(){
        return this.coinDispenser;
    }
    public String transactionTotalCoins(){
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
}