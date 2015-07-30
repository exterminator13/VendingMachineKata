package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    CoinDispenser coinDispenser;
    public VendingMachineTransaction(CoinDispenser coinDispenser){
        this.currentAmount = new Money();
        this.coinDispenser = coinDispenser; 
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
    public String selectCola(){
        if(this.currentAmount.toString().equals("$0.00")){
            return "$1.00";
        }else if (this.currentAmount.getAmount() < 1.00){
            return "PRICE $1.00";
        }else if(this.currentAmount.getAmount() >= 1.00){
            this.currentAmount.removeMoney(1.00);
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
    public String selectChips(){
        if(this.currentAmount.toString().equals("$0.00")){
            return "$0.50";
        }else if (this.currentAmount.getAmount() < 0.5){
            return "PRICE $0.50";
        }else if(this.currentAmount.toString().equals("$0.50")){
            this.currentAmount.removeMoney(.50);
            return "THANK YOU";
        }
        return "";
    }
    public String selectCandy(){
        if(this.currentAmount.toString().equals("$0.00")){
            return "$0.65";
        }else if(this.currentAmount.getAmount() < 0.65){
            return "PRICE $0.65";
        }else if(this.currentAmount.toString().equals("$0.65")){
            this.currentAmount.removeMoney(.65);
            return "THANK YOU";
        }
        return "";
    }
}